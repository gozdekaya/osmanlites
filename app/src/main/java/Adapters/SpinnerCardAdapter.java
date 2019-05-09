package Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gozde.osmanlitapp.R;

import java.util.List;

import Models.cardDetails;

public class SpinnerCardAdapter extends ArrayAdapter<String> {
    private LayoutInflater mInflater;
    private List<cardDetails> items;
    Context mContext;
    private int mResource;


    public SpinnerCardAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, 0, objects);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        items = objects;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }


    private View createItemView(int position, View convertView, ViewGroup parent){
        final View view = mInflater.inflate(mResource, parent, false);

        TextView spinnertext = (TextView) view.findViewById(R.id.spinneritem);


        cardDetails cards = items.get(position);

         spinnertext.setText(cards.getCardAlias());

        return view;
    }
}
