package Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.api.Api;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;

import Adapters.SepetOnizlemeAdapter;
import Models.Card;
import Models.Cart;
import Models.DataSepet;
import Models.Payment;
import Responses.CartResponse;
import Responses.PayJson;
import Utils.ApiClient;
import Utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentSiparisOnizleme extends Fragment {
RecyclerView recyclerView;
DataSepet items;
Button btnOnay;
ImageButton backbtn;
TextView toplam,faturadres,tesadres;
String fatura,teslimat, teslimatAdresId, faturaAdresId,holdername,cvvno,kartno,expmonth,expyear,kartlarim;


    public FragmentSiparisOnizleme() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.layout_sepet_onizleme, container, false);
         Bundle bundle=this.getArguments();

         fatura=bundle.getString("fatura","yok");
         teslimat=bundle.getString("teslimat","yok");
         teslimatAdresId= bundle.getString("billingAddressId");
         faturaAdresId=bundle.getString("shippingAddressId");
         holdername=bundle.getString("holderName");
         cvvno=bundle.getString("cvc");
         kartno=bundle.getString("number");
         expmonth=bundle.getString("expireMonth");
         expyear=bundle.getString("expireYear");
         btnOnay=view.findViewById(R.id.onay);
       toplam=view.findViewById(R.id.total);
       tesadres=view.findViewById(R.id.telimadres);
       tesadres.setText(teslimat);
     faturadres=view.findViewById(R.id.faturaadres);
     faturadres.setText(fatura);
       backbtn=view.findViewById(R.id.back);
       backbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getFragmentManager().beginTransaction().replace(R.id.container,new FragmentPayment()).commit();
           }
       });
       recyclerView=view.findViewById(R.id.sepet_urun);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        Call<CartResponse> call = ApiClient.getInstance(getContext()).getApi().sepeturunler("Bearer " + SharedPrefManager.getInstance(getContext()).getToken(), "application/json");
        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                items=response.body().getData();
                SepetOnizlemeAdapter adapter=new SepetOnizlemeAdapter(items.getCartList());
                recyclerView.setAdapter(adapter);
                toplam.setText(items.getTotalPrice());
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {

            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnOnay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObject PaymentJson = new JsonObject();
                 JsonObject cardobj =new JsonObject();

                PaymentJson.addProperty("shippingAddressId",teslimatAdresId);
                PaymentJson.addProperty("billingAddressId",faturaAdresId);
                cardobj.addProperty("holderName", holdername);
                cardobj.addProperty("number", kartno);
                cardobj.addProperty("cvc", cvvno);
                cardobj.addProperty("expireMonth", expmonth);
                cardobj.addProperty("expireYear", expyear);
                PaymentJson.add("card",cardobj);
                Log.d("paymentjson", String.valueOf(PaymentJson));

                Call<PayJson> call = ApiClient.getInstance(getContext()).getApi().postRawJSON("Bearer " + SharedPrefManager.getInstance(getContext()).getToken(), "application/json",PaymentJson);
                call.enqueue(new Callback<PayJson>() {
                    @Override
                    public void onResponse(Call<PayJson> call, Response<PayJson> response) {
                        try{
                            Log.e("response-success", response.body().toString());
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call<PayJson> call, Throwable t) {
                       t.printStackTrace();
                    }
                });

            }
        });
    }
}
