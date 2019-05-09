package Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import Models.Cart;
import Models.UpdateInstructions;
import Responses.AddCartResponse;
import Responses.DeleteCartResponse;
import Utils.ApiClient;
import Utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>  {

    private List<Cart> items;
    private UpdateInstructions updIns;

    public CartAdapter() {

    }

    public List<Cart> getItems() {
        return items;
    }

    public void setItems(List<Cart> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sepet_cardview,viewGroup,false);

        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.ViewHolder viewHolder, final int i) {

       final int currentCount = items.get(i).getCount();
       viewHolder.count.setText(String.valueOf(currentCount));
       viewHolder.title_product.setText(items.get(i).getProduct().getTitle() +"      "+ items.get(i).getProduct().getPrice());
       Picasso.get().load(items.get(i).getProduct().getProfile_image()).into(viewHolder.image_product);
       viewHolder.decrease.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ApiInterface apiInterface1=ApiClient.getInstance(v.getContext()).getApi();
               String productID=items.get(i).getProduct().getId();
               String bearer= SharedPrefManager.getInstance(v.getContext()).getToken();
               Call<DeleteCartResponse> call1=apiInterface1.urunsil("Bearer " +bearer,"application/json",productID);
               call1.enqueue(new Callback<DeleteCartResponse>() {
                   @Override
                   public void onResponse(Call<DeleteCartResponse> call, Response<DeleteCartResponse> response) {
                       viewHolder.count.setText(String.valueOf(Integer.valueOf(viewHolder.count.getText().toString())-1));
                       updIns.update();
                   }

                   @Override
                   public void onFailure(Call<DeleteCartResponse> call, Throwable t) {
                          t.printStackTrace();
                   }
               });
           }
       });
       viewHolder.increase.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               ApiInterface apiInterface= ApiClient.getInstance(v.getContext()).getApi();
               String productId= items.get(i).getProduct().getId();
               String bearer= SharedPrefManager.getInstance(v.getContext()).getToken();
               final int count=1;
               Call<AddCartResponse> call=apiInterface.sepeteekle("Bearer "+bearer,"application/json",productId,count);
               call.enqueue(new Callback<AddCartResponse>() {
                   @Override
                   public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {

                     if (response.errorBody()==null){

                         viewHolder.count.setText(String.valueOf(Integer.valueOf(viewHolder.count.getText().toString()) + 1));
                         updIns.update();
                     }
                   }

                   @Override
                   public void onFailure(Call<AddCartResponse> call, Throwable t) {
                       t.printStackTrace();
                   }
               });

           }
       });
       viewHolder.delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AlertDialog.Builder adb = new AlertDialog.Builder(new ContextThemeWrapper(v.getContext(),R.style.AlertDialogCustom));
               adb.setTitle("Sepetten Çıkarılsın Mı?");
               adb.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               });
               adb.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                   }
               });
               adb.show();

           }
       });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setUpdIns(UpdateInstructions updIns) {
        this.updIns = updIns;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       ImageView image_product,delete;
       TextView title_product,count;
       ImageButton increase,decrease;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            count=itemView.findViewById(R.id.count);
            delete=itemView.findViewById(R.id.delete);
            image_product=itemView.findViewById(R.id.image_product);
            title_product=itemView.findViewById(R.id.title_product);
            increase=itemView.findViewById(R.id.increase);
            decrease=itemView.findViewById(R.id.decrease);

        }
    }
}
