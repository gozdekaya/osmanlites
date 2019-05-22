package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import Fragments.FragmentUrunDetay;
import Models.Product;
import Responses.AddCartResponse;
import Utils.ApiClient;
import Utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;

import java.util.List;

public class MainPageAdapter extends RecyclerView.Adapter<MainPageAdapter.ViewHolder> {
    Snackbar snackbar;
    private List<Product> productList;
    private LayoutInflater inflater;
    private Context context;
    RecyclerView rv;
    String description;
    String halfdescription;

    MainHorzAdapter mRecycAdapter;

    public MainPageAdapter(List<Product> productList,Context context) {
        this.productList=productList;
        this.inflater=LayoutInflater.from(context);
        this.context=context;

    }

    @NonNull
    @Override
    public MainPageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mainpage_cardview, viewGroup, false);
        ViewHolder holder=new ViewHolder(view);
      LinearLayout layout=(LinearLayout)view.findViewById(R.id.lin1);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MainPageAdapter.ViewHolder viewHolder, int i) {
     final Product myProduct = productList.get(i);
     viewHolder.setData(myProduct,i);
     viewHolder.name.setText(myProduct.getTitle());
     viewHolder.price.setText(myProduct.getPrice());
     description=myProduct.getDescription();
     halfdescription = description;
     if (description.length() > 10) halfdescription=description.substring(0,6)+"...";
     viewHolder.desc.setText(halfdescription);
     viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             FragmentUrunDetay fragmentUrunDetay=new FragmentUrunDetay();
             Bundle args=new Bundle();
             String string=myProduct.getId();
             args.putString("ID",string);
             fragmentUrunDetay.setArguments(args);
             AppCompatActivity activity = (AppCompatActivity) v.getContext();
             activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentUrunDetay).addToBackStack(null).commit();

         }
     });
     viewHolder.price.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(final View v) {
             ApiInterface apiInterface= ApiClient.getInstance(v.getContext()).getApi();
             String productId=myProduct.getId();
             String bearer= SharedPrefManager.getInstance(v.getContext()).getToken();
             Call<AddCartResponse> call=apiInterface.sepeteekle("Bearer "+bearer,"application/json",productId,1);
             call.enqueue(new Callback<AddCartResponse>() {
                 @Override
                 public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {
                     if (SharedPrefManager.getInstance(context).isLoggedIn()){ snackbar = Snackbar.make(v,"", Snackbar.LENGTH_LONG);
                         View view = snackbar.getView();
                         TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                         tv.setTextColor(Color.parseColor("#2ecc71"));
                         tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_black_24dp, 0, 0, 0);
                         snackbar.setText(R.string.sepete_eklendi);
                         snackbar.show();}
                         else {
                         Toast.makeText(context,R.string.sepete_urun_eklemek_icin,Toast.LENGTH_SHORT).show();
                     }




                 }

                 @Override
                 public void onFailure(Call<AddCartResponse> call, Throwable t) {

                 }
             });
         }
     });

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView price,name,desc;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            price=itemView.findViewById(R.id.price_item);
            name=itemView.findViewById(R.id.name_item);
            desc=itemView.findViewById(R.id.desc_item);
            rv=itemView.findViewById(R.id.rv_horizontal);
        }
        public void setData(Product selectedProduct,int position){
            final MainHorzAdapter mRecycAdapter = new MainHorzAdapter(selectedProduct.getMedia(),context);
            rv.setAdapter(mRecycAdapter);
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv.setLayoutManager(linearLayoutManager);
            rv.setHasFixedSize(true);
            rv.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            if (rv.getOnFlingListener()==null){
                SnapHelper snapHelper = new PagerSnapHelper();
                snapHelper.attachToRecyclerView(rv);
            }
        }
    }

    public void addMore(List<Product> products){
        productList.addAll(products);
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }





}
