package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gozde.osmanlitapp.R;

import java.util.List;

import Models.Item;
import Models.Siparis;

public class ProfileSiparisAdapter extends RecyclerView.Adapter<ProfileSiparisAdapter.ViewHolder>{

    private List<Siparis> orders;
    private Context context;

    public ProfileSiparisAdapter(List<Siparis> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileSiparisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_sip_cardview, viewGroup, false);
        return new ProfileSiparisAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileSiparisAdapter.ViewHolder viewHolder, int i) {
        Siparis siparis=orders.get(i);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
