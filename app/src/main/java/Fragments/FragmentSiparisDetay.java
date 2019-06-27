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
import android.widget.TextView;

import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;

import java.util.List;

import Adapters.SiparisDetayAdapter;
import Models.Address;
import Models.Item;
import Models.Product;
import Models.Siparis;
import Responses.SiparisDetayResponse;
import Responses.SiparisResponse;
import Utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSiparisDetay extends Fragment {
    ImageButton back;
    Context mContext;
     TextView orderno,orderdate,total,adrname,adres,phone;
     List<Siparis> orders;
     List<Item> items;
     SiparisDetayAdapter adapter;
        RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_siparis_detay,container,false);
        back=(ImageButton)view.findViewById(R.id.back);
        orderno=view.findViewById(R.id.orderno);
        orderdate=view.findViewById(R.id.orderdate);
        total=view.findViewById(R.id.orderprice);
        adrname=view.findViewById(R.id.adrname);
        adres=view.findViewById(R.id.adres);
        phone=view.findViewById(R.id.phone);
        recyclerView=view.findViewById(R.id.rc_items);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

                 LinearLayoutManager   layoutManager = new LinearLayoutManager(mContext);

        final String orderId=getArguments().getString("ID",null);
        Call<SiparisDetayResponse> call = ApiClient.getInstance(mContext).getApi().siparisdetay("Bearer " + SharedPrefManager.getInstance(mContext).getToken(), "application/json",orderId);
        call.enqueue(new Callback<SiparisDetayResponse>() {
            @Override
            public void onResponse(Call<SiparisDetayResponse> call, Response<SiparisDetayResponse> response) {
                      Siparis siparis =response.body().getData();

                      adapter=new SiparisDetayAdapter(siparis.getItems());
                      recyclerView.setLayoutManager(layoutManager);
                      recyclerView.setAdapter(adapter);
                      orderno.setText( siparis.getOrderNumber());
                      orderdate.setText(siparis.getDate());
                      total.setText(siparis.getTotalPrice());
                      adrname.setText(siparis.getShippingAddress().getName());
                      adres.setText(siparis.getShippingAddress().getDescription());
                      phone.setText(siparis.getShippingAddress().getPhone());

            }

            @Override
            public void onFailure(Call<SiparisDetayResponse> call, Throwable t) {

            }
        });
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;


    }
}
