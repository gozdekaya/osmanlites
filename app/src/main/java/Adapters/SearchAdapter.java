package Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gozde.osmanlitapp.R;

import java.util.List;

import Fragments.FragmentUrunDetay;
import Models.Product;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Product> items;

    public SearchAdapter(List<Product> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_list_layout,viewGroup,false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder viewHolder, int i) {
        final Product myProduct = items.get(i);
        viewHolder.protv.setText(items.get(i).getTitle());
        viewHolder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentUrunDetay fragmentUrunDetay=new FragmentUrunDetay();
                        Bundle args=new Bundle();
                        String string=myProduct.getId();
                        args.putString("ID",string);
                        fragmentUrunDetay.setArguments(args);
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentUrunDetay).addToBackStack(null).commit();
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView protv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            protv=itemView.findViewById(R.id.proname);
        }
    }
}
