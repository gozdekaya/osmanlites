package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gozde.osmanlitapp.R;

import java.util.List;

import Models.Product;

public class SearchAdapter extends ArrayAdapter<Product> {
    private Context context;
    private List<Product> items;

  public SearchAdapter(Context context,List<Product> items){
      super(context, R.layout.search_list_layout,items);
      this.context=context;
      this.items=items;
  }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        convertView=layoutInflater.inflate(R.layout.search_list_layout,parent,false);
        Product product=items.get(position);
        TextView tvname=convertView.findViewById(R.id.proname);
        tvname.setText(product.getTitle());
        return convertView;
    }
}
