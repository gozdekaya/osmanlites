package Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import Adapters.SpinnerCountryAdapter;
import Models.Address;
import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import Models.AdresError;
import Models.Country;
import Responses.AdresDetayResponse;
import Responses.CountryResponse;
import Utils.ApiClient;
import Utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAdresDetay extends Fragment {
    Context mContext;
   EditText adresbaslik,desc,name,phone,post,city,town,defaultIs;
    TextView err_name,err_title,err_adres,err_phone,err_post,err_city,err_town;
   Spinner countrysp;
    SpinnerCountryAdapter adapter1;
    List<Country> countries;
    CheckBox cb;
    ImageButton buttonback;
    Object returningResult = new Object();
    Button save;
    Snackbar snackbar;
    LinearLayout linearLayout;
    Boolean isConnected = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_adresdetay,container,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
       adresbaslik=(EditText) view.findViewById(R.id.et_title);
       desc=(EditText) view.findViewById(R.id.et_desc);
       city=(EditText)view.findViewById(R.id.et_city);
       name=(EditText)view.findViewById(R.id.et_name) ;
       post=(EditText)view.findViewById(R.id.et_post);
       town=(EditText)view.findViewById(R.id.et_town) ;
       phone=(EditText)view.findViewById(R.id.et_num) ;
       cb=(CheckBox)view.findViewById(R.id.isDefault);
       countrysp=(Spinner)view.findViewById(R.id.country);
       linearLayout=(LinearLayout)view.findViewById(R.id.lin1);
        err_name = view.findViewById(R.id.error_name);
        err_title =view.findViewById(R.id.error_title);
        err_adres =view.findViewById(R.id.error_address);
        err_phone=view.findViewById(R.id.error_phone);
        err_post=view.findViewById(R.id.error_postcode);
        err_city=view.findViewById(R.id.error_city);
        err_town=view.findViewById(R.id.error_town);

       buttonback=(ImageButton)view.findViewById(R.id.back);
       buttonback.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().onBackPressed();
           }
       });
        save=(Button)view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAdress();
            }
        });

        ApiInterface apiInterface = ApiClient.getInstance(mContext).getApi();
        final String addressid=getArguments().getString("ID",null);

        String bearer= SharedPrefManager.getInstance(mContext).getToken();
        Call<CountryResponse> responseCall=apiInterface.ulkeler("application/json");
        responseCall.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
              countries  =  response.body().getData();
               adapter1=new SpinnerCountryAdapter(mContext,R.layout.spinner_ui,countries);
               countrysp.setAdapter(adapter1);
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {

            }
        });
        if (isConnected){Call<AdresDetayResponse> call =apiInterface.adresgor("Bearer "+bearer,"application/json",addressid);
            call.enqueue(new Callback<AdresDetayResponse>() {
                @Override
                public void onResponse(Call<AdresDetayResponse> call, Response<AdresDetayResponse> response) {
                    Log.d("adresdetay",response.body().toString());
                    Address address = response.body().getData();
                    adresbaslik.setText(address.getTitle());
                    desc.setText(address.getDescription());
                    name.setText(address.getName());
                    city.setText(address.getCity());
                    post.setText(address.getPostCode());
                    town.setText(address.getTown());
                    phone.setText(address.getPhone());

                }

                @Override
                public void onFailure(Call<AdresDetayResponse> call, Throwable t) {

                }
            });
        }else {
            Toast.makeText(mContext, R.string.internet_baglanti, Toast.LENGTH_LONG).show();
        }






        return view;
    }

    public void updateAdress(){


        String title = adresbaslik.getText().toString().trim();
        String description = desc.getText().toString().trim();
        String isim = name.getText().toString().trim();
        String phonenumber = phone.getText().toString().trim();
        String postCode = post.getText().toString().trim();
        String mCity = city.getText().toString().trim();
        String mTown=town.getText().toString().trim();
        final String addressid=getArguments().getString("ID",null);

        int defaultis=0;
        Country sc = (Country) countrysp.getSelectedItem();
        int countryId = sc.getId();
        if (cb.isChecked()){
            defaultis=1;

        }else {
            defaultis=0;
        }
        if (isConnected){
            Call<AdresDetayResponse> call = ApiClient.getInstance(mContext).getApi().adresGuncelle("Bearer " + SharedPrefManager.getInstance(mContext).getToken(),"application/json",title,description,isim,phonenumber,postCode,mCity,mTown,countryId,defaultis,addressid);
            call.enqueue(new Callback<AdresDetayResponse>() {
                @Override
                public void onResponse(Call<AdresDetayResponse> call, Response<AdresDetayResponse> response) {
                    Log.d("adresdetay",response.body().toString());
                    Address address = response.body().getData();
                    adresbaslik.setText(address.getTitle());
                    desc.setText(address.getDescription());
                    name.setText(address.getName());
                    city.setText(address.getCity());
                    post.setText(address.getPostCode());
                    town.setText(address.getTown());
                    phone.setText(address.getPhone());

                    if (response.errorBody() != null)
                    {
                        try {
                            JSONObject obj = new JSONObject(response.errorBody().string());

                            String error = obj.optString("errors");
                            Gson gsonError = new Gson();
                            returningResult = gsonError.fromJson(error, AdresError.class);

                            if (((AdresError) returningResult).getName() != null){
                                err_name.setVisibility(View.VISIBLE);
                                err_name.setText("*"+((AdresError) returningResult).getName().get(0));
                            }else{
                                err_name.setVisibility(View.GONE);
                            }

                            if (((AdresError) returningResult).getTitle() != null){
                                err_title.setVisibility(View.VISIBLE);
                                err_title.setText("*"+((AdresError) returningResult).getTitle().get(0));
                            }else{
                                err_title.setVisibility(View.GONE);
                            }

                            if (((AdresError) returningResult).getDescription() != null){
                                err_adres.setVisibility(View.VISIBLE);
                                err_adres.setText("*"+((AdresError) returningResult).getDescription().get(0));
                            }else{
                                err_adres.setVisibility(View.GONE);
                            }

                            if (((AdresError) returningResult).getPhone() != null){
                                err_phone.setVisibility(View.VISIBLE);
                                err_phone.setText("*"+((AdresError) returningResult).getPhone().get(0));
                            }else{
                                err_phone.setVisibility(View.GONE);
                            }
                            if (((AdresError) returningResult).getPostCode() != null){
                                err_post.setVisibility(View.VISIBLE);
                                err_post.setText("*"+((AdresError) returningResult).getPostCode().get(0));
                            }else{
                                err_post.setVisibility(View.GONE);
                            }
                            if (((AdresError) returningResult).getCity() != null){
                                err_city.setVisibility(View.VISIBLE);
                                err_city.setText("*"+((AdresError) returningResult).getCity().get(0));
                            }else{
                                err_city.setVisibility(View.GONE);
                            }
                            if (((AdresError) returningResult).getTown() != null){
                                err_town.setVisibility(View.VISIBLE);
                                err_town.setText("*"+((AdresError) returningResult).getTown().get(0));
                            }else{
                                err_town.setVisibility(View.GONE);
                            }

                            Log.d("err", "err");


                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        snackbar = Snackbar.make(linearLayout, "", Snackbar.LENGTH_LONG);
                        View view = snackbar.getView();
                        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setTextColor(Color.parseColor("#2ecc71"));
                        tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_black_24dp, 0, 0, 0);
                        snackbar.setText(R.string.adres_guncel);
                        snackbar.show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragmentAdresler()).commit();
                    }
                }

                @Override
                public void onFailure(Call<AdresDetayResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }else {
            Toast.makeText(mContext, R.string.internet_baglanti, Toast.LENGTH_LONG).show();
        }
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






