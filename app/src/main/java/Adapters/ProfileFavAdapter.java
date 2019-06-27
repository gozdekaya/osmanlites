package Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gozde.osmanlitapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Models.Favori;
import Models.Product;

public class ProfileFavAdapter extends RecyclerView.Adapter<ProfileFavAdapter.ViewHolder>{
    List<Favori> products = new ArrayList<>();

    public ProfileFavAdapter(List<Favori> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProfileFavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.profile_fav_cardview, viewGroup, false);

        return new ProfileFavAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileFavAdapter.ViewHolder viewHolder, int i) {
       final Product product=products.get(i).getProduct();
        viewHolder.tvname.setText(product.getTitle());
        viewHolder.tvprice.setText(product.getPrice());
        Picasso.get().load(product.getProfile_image()).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tvname,tvprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.img);
            tvname=itemView.findViewById(R.id.name);
            tvprice=itemView.findViewById(R.id.price);
        }
    }
}
