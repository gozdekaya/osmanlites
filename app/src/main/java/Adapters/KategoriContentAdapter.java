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

import Fragments.FragmentUrunDetay;
import Models.Product;
import com.gozde.osmanlitapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KategoriContentAdapter extends RecyclerView.Adapter<KategoriContentAdapter.ViewHolder> {

    private List<Product> mProducts;

    public KategoriContentAdapter(List<Product> mProducts) {
        this.mProducts = mProducts;
    }

    @NonNull
    @Override
    public KategoriContentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.kategori_content_cardview,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriContentAdapter.ViewHolder viewHolder, final int i) {
        Product product = mProducts.get(i);
       viewHolder.product_title.setText(product.getTitle());
        Picasso.get().load(product.getProfile_image()).into(viewHolder.product_image);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUrunDetay fragmentUrunDetay = new FragmentUrunDetay();
                Bundle args=new Bundle();
                String str=mProducts.get(i).getId();
                //args.putString("title",mProducts.get(i).getTitle());
                //args.putString("description",mProducts.get(i).getDescription());
                //args.putString("media",mProducts.get(i).getMedia().get(i).getUrl());

                args.putString("ID",str);
                fragmentUrunDetay.setArguments(args);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentUrunDetay).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
       return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView product_image;
        TextView product_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            product_image=itemView.findViewById(R.id.product_image);
            product_title=itemView.findViewById(R.id.product_title);
        }
    }
}
