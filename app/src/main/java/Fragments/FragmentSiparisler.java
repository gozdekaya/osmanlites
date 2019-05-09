package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.gozde.osmanlitapp.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.SiparisAdapter;
import Models.Siparis;

public class FragmentSiparisler extends Fragment {
private List<Siparis> orders=new ArrayList<>();

RecyclerView recyclerView;
SiparisAdapter adapter;
ImageButton back;
Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_siparisler,container,false);
        back=(ImageButton)view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_siparis);
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new SiparisAdapter(orders);
        recyclerView.setAdapter(adapter);

        orders.add(new Siparis("18 Ocak 2019","345 TL",1,"Güzelyalı Mah Şebnem Sitesi Cblok Adana","http://www.tesbihcizadem.com/image/cache/data/resimler/orjinal-deamla-kehribar-tesbih-ates-rengi-13-mm-3540-1200x1024.jpg"));
        orders.add(new Siparis("17 Mayıs 2019","745 TL",1,"Güzelyalı Mah Şebnem Sitesi Cblok Adana","http://www.tesbihcizadem.com/image/cache/data/resimler/orjinal-deamla-kehribar-tesbih-ates-rengi-13-mm-3540-1200x1024.jpg"));
        orders.add(new Siparis("14 Mart 2019","345 TL",1,"Güzelyalı Mah Şebnem Sitesi Cblok Adana","http://www.tesbihcizadem.com/image/cache/data/resimler/orjinal-deamla-kehribar-tesbih-ates-rengi-13-mm-3540-1200x1024.jpg"));
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;


    }
}
