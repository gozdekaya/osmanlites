package Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import Models.Address;
import Responses.AdresResponse;
import Utils.ApiClient;

import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;

import java.util.List;

import Adapters.AdresAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAdresler extends Fragment {
    ProgressBar mProgressBar;
    private List<Address> mAddresses;
    AdresAdapter adresAdapter;
    RecyclerView recyclerView;
    ImageButton backbutton;
    Boolean isConnected = false;
    Context mContext;
    FloatingActionButton addAdres;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adresler_actionbutton, container, false);

        //Set Toolbar
       /* android.support.v7.widget.Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar.setTitle("Adreslerim");
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        mProgressBar=(ProgressBar)view.findViewById(R.id.progressBar1);
        addAdres = (FloatingActionButton) view.findViewById(R.id.fab);
        addAdres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentDialogAdresEkle fragmentDialogAdresEkle = new FragmentDialogAdresEkle();
                Bundle bundle = new Bundle();
                bundle.putString("from", "addresses");
                fragmentDialogAdresEkle.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container, fragmentDialogAdresEkle).addToBackStack(null).commit();
            }
        });

        backbutton = (ImageButton) view.findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click", "click");
                getFragmentManager().beginTransaction().replace(R.id.container, new FragmentProfile()).addToBackStack(null).commit();
            }
        });

       mProgressBar.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_adresler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        if (isConnected){ Call<AdresResponse> call = ApiClient
                .getInstance(mContext).getApi().adreslerim("Bearer " + SharedPrefManager.getInstance(mContext).getToken(), "application/json");
            call.enqueue(new Callback<AdresResponse>() {
                @Override
                public void onResponse(Call<AdresResponse> call, Response<AdresResponse> response) {
                    Log.d("msg", String.valueOf(response.body()));
                    mProgressBar.setVisibility(View.GONE);
                    if (response.errorBody() == null) {
                        mAddresses = response.body().getData();

                        adresAdapter = new AdresAdapter(mAddresses);

                        recyclerView.setAdapter(adresAdapter);
                    } else {
                        Toast.makeText(mContext, "daha sonra tekrar deneyin", Toast.LENGTH_LONG).show();
                    }


                }


                @Override
                public void onFailure(Call<AdresResponse> call, Throwable t) {
                    t.printStackTrace();
                    Log.d("err", "err");
                }
            });
        }else {
            Toast.makeText(mContext, R.string.internet_baglanti, Toast.LENGTH_LONG).show();
        }


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext=context;
        if (checkConnection()) isConnected = true;
        else isConnected = false;
    }



    private boolean checkConnection(){

        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (!(networkInfo != null && networkInfo.isConnected() ))
        {
            return false;
            //
        } else return true;

    }


}
