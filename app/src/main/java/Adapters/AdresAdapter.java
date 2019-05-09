package Adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Models.Address;

import com.gozde.osmanlitapp.R;

import java.util.List;

import Fragments.FragmentAdresDetay;

public class AdresAdapter extends RecyclerView.Adapter<AdresAdapter.ViewHolder> {
    private List<Address> mAdresses;


    public AdresAdapter(List<Address> mAdresses) {
        this.mAdresses = mAdresses;

    }

    @NonNull
    @Override
    public AdresAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adresler_cardview, viewGroup, false);
        return new AdresAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdresAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.tv_title.setText(mAdresses.get(i).getTitle() + " / " + mAdresses.get(i).getName());

        viewHolder.tv_desc.setText(mAdresses.get(i).getDescription());
        viewHolder.tv_ilce.setText(mAdresses.get(i).getTown() +" , " + mAdresses.get(i).getCity()  +" , "+mAdresses.get(i).getCountry().getTitle()  + "  " +mAdresses.get(i).getPostCode());
        viewHolder.tv_telefon.setText(mAdresses.get(i).getPhone());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentAdresDetay fragmentAdresDetay=new FragmentAdresDetay();
                Bundle args=new Bundle();
                String str=mAdresses.get(i).getId();
                args.putString("title",mAdresses.get(i).getTitle());
                args.putString("name",mAdresses.get(i).getName());

                args.putString("ID",str);
                fragmentAdresDetay.setArguments(args);

               AppCompatActivity activity = (AppCompatActivity) v.getContext();
           activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentAdresDetay).addToBackStack(null).commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mAdresses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_desc,tv_ilce,tv_telefon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_telefon=itemView.findViewById(R.id.telefon);
            tv_ilce=itemView.findViewById(R.id.ilce);
            tv_title=itemView.findViewById(R.id.adres_title);
            tv_desc=itemView.findViewById(R.id.adres_desc);

        }
    }
}
