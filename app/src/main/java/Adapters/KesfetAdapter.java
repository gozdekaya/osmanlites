package Adapters;

import android.support.annotation.NonNull;
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

import Models.Product;

public class KesfetAdapter extends RecyclerView.Adapter<KesfetAdapter.ViewHolder>{
 List<Product> mProducts;

    public KesfetAdapter(List<Product> mProducts) {
        this.mProducts = mProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.kesfet_cardview,viewGroup,false);
        return new KesfetAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    viewHolder.name.setText(mProducts.get(i).getTitle());
    viewHolder.price.setText(mProducts.get(i).getPrice());
        Picasso.get().load(mProducts.get(i).getProfile_image()).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.img);
            name=(TextView)itemView.findViewById(R.id.name);
            price=(TextView)itemView.findViewById(R.id.price);
        }
    }
}
