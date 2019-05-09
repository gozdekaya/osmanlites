package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Fragments.FragmentUrunDetay;
import Models.Cart;
import Models.Product;
import Responses.AddCartResponse;
import Utils.ApiClient;
import Utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder> {
     List<Product> products = new ArrayList<>();
     Cart mCart;
      Context mContext;
      LinearLayout layout;
    private Snackbar snackbar;
    public ExploreAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ExploreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.explore_cardview, viewGroup, false);
        layout=(LinearLayout)v.findViewById(R.id.layout);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreAdapter.ViewHolder viewHolder, final int i) {
        final Product product = products.get(i);
        viewHolder.name.setText(product.getTitle());
        viewHolder.price.setText(product.getPrice());
        viewHolder.button_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiInterface apiInterface= ApiClient.getInstance(v.getContext()).getApi();
                String productId = product.getId();
                String bearer= SharedPrefManager.getInstance(v.getContext()).getToken();

                Call<AddCartResponse> call=apiInterface.sepeteekle("Bearer "+bearer,"application/json",productId,1);
                call.enqueue(new Callback<AddCartResponse>() {
                    @Override
                    public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {
                        snackbar = Snackbar.make( layout,"", Snackbar.LENGTH_LONG);
                        View view = snackbar.getView();
                        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setTextColor(Color.parseColor("#2ecc71"));
                        tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_black_24dp, 0, 0, 0);
                        snackbar.setText("Sepete Eklendi");
                        snackbar.show();
                    }

                    @Override
                    public void onFailure(Call<AddCartResponse> call, Throwable t) {

                    }
                });
            }
        });
        viewHolder.button_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUrunDetay fragmentUrunDetay = new FragmentUrunDetay();
                Bundle args=new Bundle();
                String string=product.getId();
                args.putString("ID",string);
                fragmentUrunDetay.setArguments(args);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentUrunDetay).addToBackStack(null).commit();
            }
        });
        Picasso.get().load(product.getProfile_image()).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,price;
        ImageButton button_display,button_buy;
        LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            button_buy=(ImageButton)itemView.findViewById(R.id.button_buy);
            button_display=(ImageButton)itemView.findViewById(R.id.button_display);
            image=(ImageView)itemView.findViewById(R.id.image);
            name=(TextView)itemView.findViewById(R.id.name);
            price=(TextView)itemView.findViewById(R.id.price);
            layout=(LinearLayout)itemView.findViewById(R.id.layout);
        }
    }
}
