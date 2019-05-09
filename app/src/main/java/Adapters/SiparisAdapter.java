package Adapters;

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

import java.util.List;

import Fragments.FragmentSiparisDetay;
import Models.Siparis;

public class SiparisAdapter extends RecyclerView.Adapter<SiparisAdapter.ViewHolder> {

    private List<Siparis> orders;

    public SiparisAdapter(List<Siparis> orders) {
        this.orders = orders;
    }


    @NonNull
    @Override
    public SiparisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.siparisler_cardview, viewGroup, false);
        return new SiparisAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiparisAdapter.ViewHolder viewHolder, final int i) {
      viewHolder.tarih.setText(orders.get(i).getTarih());
      viewHolder.toplam.setText(orders.get(i).getTutar());
       Picasso.get().load("http://www.tesbihcizadem.com/image/cache/data/resimler/orjinal-deamla-kehribar-tesbih-ates-rengi-13-mm-3540-1200x1024.jpg").into(viewHolder.img_order);
      viewHolder.detay.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FragmentSiparisDetay fragmentSiparisDetay=new FragmentSiparisDetay();

              AppCompatActivity activity = (AppCompatActivity) v.getContext();
              activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentSiparisDetay).addToBackStack(null).commit();
          }
      });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tarih,toplam,detay;
        ImageView img_order;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detay=(TextView)itemView.findViewById(R.id.display_detay);
            tarih=(TextView)itemView.findViewById(R.id.tv_tarih);
            toplam=(TextView)itemView.findViewById(R.id.tv_tutar);
            img_order=(ImageView)itemView.findViewById(R.id.image_order);
        }
    }
}
