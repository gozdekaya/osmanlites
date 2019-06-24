package Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gozde.osmanlitapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Models.Product;

public class SearchDiscreteAdapter extends RecyclerView.Adapter<SearchDiscreteAdapter.ViewHolder> {
    List<Product> products = new ArrayList<>();

    public SearchDiscreteAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public SearchDiscreteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.search_discrete_cardview, viewGroup, false);

        return new SearchDiscreteAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchDiscreteAdapter.ViewHolder viewHolder, int i) {
                final Product product =products.get(i);
        Picasso.get().load(product.getProfile_image()).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
        }
    }
}
