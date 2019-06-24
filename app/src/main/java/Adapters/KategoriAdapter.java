package Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Models.Categorie;
import com.gozde.osmanlitapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Fragments.FragmentKategori;
import de.hdodenhof.circleimageview.CircleImageView;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder> {

    private List<Categorie> mKategoriler;
    private Context mContext;


    public KategoriAdapter(List<Categorie> mKategoriler, Context mContext) {
        this.mKategoriler = mKategoriler;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.kategori_cardview,viewGroup,false);

        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
//     viewHolder.kategoriname.setText(mKategoriler.get(i).getTitle());
      viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FragmentKategori fragmentKategori = new FragmentKategori();
              Bundle args=new Bundle();
              int id = mKategoriler.get(i).getId();
              args.putInt("id",id);
              args.putString("title",mKategoriler.get(i).getTitle());
              fragmentKategori.setArguments(args);

              AppCompatActivity activity = (AppCompatActivity) v.getContext();
              activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentKategori).addToBackStack(null).commit();
          }
      });

       Picasso.get().load(mKategoriler.get(i).getMedia()).into(viewHolder.kategorimage);


    }

    @Override
    public int getItemCount() {
        return mKategoriler.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView kategorimage;
        TextView kategoriname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kategorimage=itemView.findViewById(R.id.katimage);
      //      kategoriname=itemView.findViewById(R.id.katitle);

        }
    }
}
