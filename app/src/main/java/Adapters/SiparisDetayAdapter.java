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

import Models.Item;

public class SiparisDetayAdapter  extends RecyclerView.Adapter<SiparisDetayAdapter.ViewHolder> {
    List<Item> items;

    public SiparisDetayAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public SiparisDetayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.siparis_detay_cardview, viewGroup, false);
        return new SiparisDetayAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiparisDetayAdapter.ViewHolder viewHolder, int i) {
        Picasso.get().load(items.get(i).getProduct().getProfile_image()).into(viewHolder.image);
        viewHolder.name.setText(items.get(i).getProduct().getTitle());
        viewHolder.desc.setText(items.get(i).getProduct().getDescription());
        viewHolder.totalprice.setText(items.get(i).getPrice());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,desc,totalprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            desc=itemView.findViewById(R.id.desc);
            totalprice=itemView.findViewById(R.id.pricetotal);
        }
    }
}
