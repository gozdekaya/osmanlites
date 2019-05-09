package Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import Models.KategoriUrun;

import com.gozde.osmanlitapp.R;

import Adapters.KategoriContentAdapter;
import Responses.KategoriUrunResponse;
import Utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentKategori extends Fragment {
private KategoriUrun mProducts;
KategoriContentAdapter adapter;
TextView kat_title;
ImageButton buttonback;
Boolean isConnected = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kategori, container, false);
        kat_title=(TextView)view.findViewById(R.id.kat_title);
       final RecyclerView recyclerView=(RecyclerView) view.findViewById(R.id.recycler_kat);

        buttonback=(ImageButton)view.findViewById(R.id.back);
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

       final String title=getArguments().getString("title",null);

       int kategoriId=getArguments().getInt("id",0);
       if (isConnected){
           Call<KategoriUrunResponse>call=ApiClient.getInstance(getContext()).getApi().kategoriUrunler("application/json",kategoriId);
           call.enqueue(new Callback<KategoriUrunResponse>() {
               @Override
               public void onResponse(Call<KategoriUrunResponse> call, Response<KategoriUrunResponse> response) {
                   mProducts=response.body().getData();
                   kat_title.setText(title);
                   adapter=new KategoriContentAdapter(mProducts.products);
                   recyclerView.setAdapter(adapter);
                   recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));



               }

               @Override
               public void onFailure(Call<KategoriUrunResponse> call, Throwable t) {

               }
           });

       }else {
           Toast.makeText(getContext(), "internet bağlantını kontrol et", Toast.LENGTH_LONG).show();


       }



        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (checkConnection()) isConnected = true;
        else isConnected = false;
    }

    private boolean checkConnection(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (!(networkInfo != null && networkInfo.isConnected() ))
        {
            return false;
            //
        } else return true;

    }

}
