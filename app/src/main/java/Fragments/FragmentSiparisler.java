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

import com.google.android.gms.common.api.Api;
import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import Adapters.SiparisAdapter;
import Models.Siparis;
import Responses.SiparisResponse;
import Utils.ApiClient;
import Utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
       Call<SiparisResponse> call=ApiClient.getInstance(mContext).getApi().orders("Bearer " + SharedPrefManager.getInstance(mContext).getToken(), "application/json");
       call.enqueue(new Callback<SiparisResponse>() {
           @Override
           public void onResponse(Call<SiparisResponse> call, Response<SiparisResponse> response) {
               orders=response.body().getData();
               LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
               recyclerView.setLayoutManager(layoutManager);
               adapter=new SiparisAdapter(orders, mContext);
               recyclerView.setAdapter(adapter);
           }

           @Override
           public void onFailure(Call<SiparisResponse> call, Throwable t) {

           }
       });
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_siparis);



        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;


    }
}
