package Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

import Adapters.ExploreAdapter;
import Adapters.ProfileFavAdapter;
import Adapters.SiparisAdapter;
import Models.Favori;
import Models.Product;
import Models.Siparis;
import Responses.FavoriResponse;
import Responses.ProductResponse;
import Responses.SiparisResponse;
import Utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAyarlar extends Fragment {
ImageView settings;
TextView pronum,favoriler;
    private List<Favori> products;
    ProfileFavAdapter adapter;
RecyclerView recyclerfav,recyclersip;
LinearLayout linsip,linfav;
List<Siparis> orders;
SiparisAdapter adapters;
    public FragmentAyarlar() {
        // Required empty public constructor
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ayarlar, container, false);

        linfav=view.findViewById(R.id.linfav);
        linsip=view.findViewById(R.id.linsip);

        linfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pronum.setTextColor(getResources().getColor(R.color.blue));
                recyclersip.setVisibility(View.GONE);
                recyclerfav.setVisibility(View.VISIBLE);
            }
        });

        linsip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclersip.setVisibility(View.VISIBLE);
                recyclerfav.setVisibility(View.GONE);
            }
        });
        pronum=view.findViewById(R.id.pronum);
        favoriler=view.findViewById(R.id.favoriler);
        recyclersip=view.findViewById(R.id.recyclersip);
        settings=view.findViewById(R.id.settings);
        recyclerfav=view.findViewById(R.id.recyclerfav);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container,new FragmentProfile()).commit();
            }
        });

        Call<SiparisResponse> call1=ApiClient.getInstance(getContext()).getApi().orders("Bearer " + SharedPrefManager.getInstance(getContext()).getToken(), "application/json");
        call1.enqueue(new Callback<SiparisResponse>() {
            @Override
            public void onResponse(Call<SiparisResponse> call1, Response<SiparisResponse> response) {
                orders=response.body().getData();
                LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
                recyclersip.setLayoutManager(layoutManager);
                adapters=new SiparisAdapter(orders, getContext());
                recyclersip.setAdapter(adapters);
            }

            @Override
            public void onFailure(Call<SiparisResponse> call, Throwable t) {

            }
        });



        String bearer= SharedPrefManager.getInstance(getContext()).getToken();
        Call<FavoriResponse> call= ApiClient.getInstance(getContext()).getApi().favoriler("Bearer " + bearer,"application/json");
        call.enqueue(new Callback<FavoriResponse>() {
            @Override
            public void onResponse(Call<FavoriResponse> call, Response<FavoriResponse> response) {


                products=response.body().getData();
                adapter=new ProfileFavAdapter(products);
                recyclerfav.setAdapter(adapter);
                recyclerfav.setLayoutManager(new GridLayoutManager(getContext(),2));


            }

            @Override
            public void onFailure(Call<FavoriResponse> call, Throwable t) {
                Log.d("failure",t.getMessage());
            }
        });
        return view;
}}
