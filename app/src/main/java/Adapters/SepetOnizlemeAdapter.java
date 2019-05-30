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

import java.util.List;

import Models.Cart;

public class SepetOnizlemeAdapter extends RecyclerView.Adapter<SepetOnizlemeAdapter.ViewHolder> {
    private List<Cart> items;

    public SepetOnizlemeAdapter(List<Cart> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public SepetOnizlemeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sepet_onizleme_cardview,viewGroup,false);

        return new SepetOnizlemeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SepetOnizlemeAdapter.ViewHolder viewHolder, int i) {
        Picasso.get().load(items.get(i).getProduct().getProfile_image()).into(viewHolder.image);
        viewHolder.text.setText(items.get(i).getProduct().getTitle());
        viewHolder.price.setText(items.get(i).getProduct().getPrice());
        viewHolder.count.setText(String.valueOf(items.get(i).getCount()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text,price,count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            text=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            count=itemView.findViewById(R.id.count);

        }
    }
}
