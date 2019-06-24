package Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gozde.osmanlitapp.R;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

import Adapters.ExploreAdapter;
import Adapters.ProfileFavAdapter;
import Models.Product;
import Responses.ProductResponse;
import Utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAyarlar extends Fragment {
ImageView settings;
    private List<Product> products;
    ProfileFavAdapter adapter;
RecyclerView recyclerfav;
    public FragmentAyarlar() {
        // Required empty public constructor
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ayarlar, container, false);
        settings=view.findViewById(R.id.settings);
        recyclerfav=view.findViewById(R.id.recyclerfav);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container,new FragmentProfile()).commit();
            }
        });

        Call<ProductResponse> call= ApiClient.getInstance(getContext()).getApi().urunler("application/json");
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {


                products=response.body().getData().getProducts();
                adapter=new ProfileFavAdapter(products);
                recyclerfav.setAdapter(adapter);
                recyclerfav.setLayoutManager(new GridLayoutManager(getContext(),2));


            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("failure",t.getMessage());
            }
        });
        return view;
}}
