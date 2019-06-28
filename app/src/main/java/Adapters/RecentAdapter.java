package Adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gozde.osmanlitapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Fragments.FragmentUrunDetay;
import Models.Product;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {
 List<Product> mProducts;

    public RecentAdapter(List<Product> mProducts) {
        this.mProducts = mProducts;
    }

    @NonNull
    @Override
    public RecentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recent_cardview,viewGroup,false);
        return new RecentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUrunDetay fragmentUrunDetay=new FragmentUrunDetay();
                Bundle args=new Bundle();
                String string=mProducts.get(i).getId();
                args.putString("ID",string);
                fragmentUrunDetay.setArguments(args);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentUrunDetay).addToBackStack(null).commit();
            }
        });
     viewHolder.name.setText(mProducts.get(i).getTitle());
     viewHolder.price.setText(mProducts.get(i).getPrice());
     viewHolder.disprice.setText(mProducts.get(i).getDiscount().getDiscounted_price());
     Picasso.get().load(mProducts.get(i).getProfile_image()).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,price,disprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            disprice=(TextView)itemView.findViewById(R.id.disprice) ;
            image=(ImageView)itemView.findViewById(R.id.product_image);
            name=(TextView)itemView.findViewById(R.id.product_title);
            price=(TextView)itemView.findViewById(R.id.price);
        }
    }
}
