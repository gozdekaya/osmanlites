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

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {
    ArrayList<String> images;
    ArrayList<String> names;

    public RecentAdapter(ArrayList<String> images, ArrayList<String> names) {
        this.images = images;
        this.names = names;
    }

    @NonNull
    @Override
    public RecentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recent_cardview,viewGroup,false);
        return new RecentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentAdapter.ViewHolder viewHolder, int i) {
       viewHolder.name.setText(names.get(i));
        Picasso.get().load("https://s.eticaretbox.com/2043/pictures/KYSTDPQNMT17201716417_Gumus-Nokta-Puskul-Surmeli-Sikma-Kehribar-Tesbih-2.jpg").into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.product_image);
            name=(TextView)itemView.findViewById(R.id.product_title);
        }
    }
}
