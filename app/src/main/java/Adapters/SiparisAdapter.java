package Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
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
    private Context context;

    public SiparisAdapter(List<Siparis> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }


    @NonNull
    @Override
    public SiparisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.siparisler_cardview, viewGroup, false);
        return new SiparisAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiparisAdapter.ViewHolder viewHolder, final int i) {
        Siparis siparis=orders.get(i);


       viewHolder.tarih.setText(orders.get(i).getDate());
      // viewHolder.toplam.setText(orders.get(i).getToplam);
     LinearLayoutManager   layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        SiparisHorzAdapter adapter=new SiparisHorzAdapter(orders.get(i).getItems());
        viewHolder.rchorz.setAdapter(adapter);
        viewHolder.rchorz.setLayoutManager(layoutManager1);
      viewHolder.detay.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FragmentSiparisDetay fragmentSiparisDetay=new FragmentSiparisDetay();
              Bundle args=new Bundle();
              String orderId=siparis.getId();
              args.putString("ID",orderId);
              fragmentSiparisDetay.setArguments(args);
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
        RecyclerView rchorz;
        ImageView img_order;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rchorz=itemView.findViewById(R.id.recycler_products);
            detay=(TextView)itemView.findViewById(R.id.display_detay);
            tarih=(TextView)itemView.findViewById(R.id.tv_tarih);
            toplam=(TextView)itemView.findViewById(R.id.tv_tutar);
            img_order=(ImageView)itemView.findViewById(R.id.image_order);
        }
    }
}
