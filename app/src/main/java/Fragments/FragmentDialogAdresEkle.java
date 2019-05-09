package Fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
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

import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import Adapters.SpinnerCountryAdapter;
import Models.AdresError;
import Models.Country;
import Responses.AdresDetayResponse;
import Responses.CountryResponse;
import Utils.ApiClient;
import Utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDialogAdresEkle extends DialogFragment {
    LinearLayout layout;
    private Snackbar snackbar;
    private String fromArg;
    CheckBox cb;
    ImageButton backbutton;
    Button save;
    EditText adresbaslik, desc, name, phone, post, city, town, defaultIs;
    TextView err_name, err_title, err_adres, err_phone, err_post, err_city, err_town;
    Spinner spinner;
    List<Country> countries;
    SpinnerCountryAdapter adapter1;
    Object returningResult = new Object();
    int defaultis;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        } else {
            setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_NoActionBar);
        }
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_adresekle, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        fromArg = getArguments().getString("from");
        ApiInterface apiInterface = ApiClient.getInstance(getContext()).getApi();
        Call<CountryResponse> responseCall = apiInterface.ulkeler("application/json");
        responseCall.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {

                if (response.body() != null) {
                    countries = response.body().getData();
                    adapter1 = new SpinnerCountryAdapter(getContext(), R.layout.spinner_ui, countries);
                    spinner.setAdapter(adapter1);
                }


            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {

            }
        });
        backbutton = (ImageButton) view.findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromArg == "payment") {
                    getFragmentManager().beginTransaction().replace(R.id.container, new FragmentCart()).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, new FragmentAdresler()).commit();
                }
              //getActivity().onBackPressed();
            }
        });

        err_name = view.findViewById(R.id.error_name);
        err_title = view.findViewById(R.id.error_title);
        err_adres = view.findViewById(R.id.error_address);
        err_phone = view.findViewById(R.id.error_phone);
        err_post = view.findViewById(R.id.error_postcode);
        err_city = view.findViewById(R.id.error_city);
        err_town = view.findViewById(R.id.error_town);
        cb = (CheckBox) view.findViewById(R.id.isDefault);

        adresbaslik = (EditText) view.findViewById(R.id.et_title);
        desc = (EditText) view.findViewById(R.id.et_desc);
        name = (EditText) view.findViewById(R.id.et_name);
        phone = (EditText) view.findViewById(R.id.et_num);
        post = (EditText) view.findViewById(R.id.et_post);
        city = (EditText) view.findViewById(R.id.et_city);
        town = (EditText) view.findViewById(R.id.et_town);
        save = (Button) view.findViewById(R.id.save);
        layout = (LinearLayout) view.findViewById(R.id.lin1);
        //  defaultIs=(EditText)view.findViewById(R.id.et_default);
        spinner = (Spinner) view.findViewById(R.id.country);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddress();


            }
        });
        return view;
    }

    private void saveAddress() {
        String title = adresbaslik.getText().toString().trim();
        String description = desc.getText().toString().trim();
        String isim = name.getText().toString().trim();
        String phonenumber = phone.getText().toString().trim();
        String postCode = post.getText().toString().trim();
        String mCity = city.getText().toString().trim();
        String mTown = town.getText().toString().trim();
        Country sc = (Country) spinner.getSelectedItem();
        int countryId = sc.getId();


        if (cb.isChecked()) {
            defaultis = 1;

        } else {
            defaultis = 0;
        }

        Log.e("adresssssss", "" + defaultis);
        Call<AdresDetayResponse> call = ApiClient.getInstance(getContext()).getApi().adresEkle("Bearer " + SharedPrefManager.getInstance(getContext()).getToken(), "application/json", title, description, isim, phonenumber, postCode, mCity, mTown, countryId, defaultis);
        call.enqueue(new Callback<AdresDetayResponse>() {


            @Override
            public void onResponse(Call<AdresDetayResponse> call, Response<AdresDetayResponse> response) {
                Log.e("adressssss", "" + defaultis);
                if (response.errorBody() != null) {
                    try {
                        JSONObject obj = new JSONObject(response.errorBody().string());

                        String error = obj.optString("errors");
                        Gson gsonError = new Gson();
                        returningResult = gsonError.fromJson(error, AdresError.class);

                        if (((AdresError) returningResult).getName() != null) {
                            err_name.setVisibility(View.VISIBLE);
                            err_name.setText("*" + ((AdresError) returningResult).getName().get(0));
                        } else {
                            err_name.setVisibility(View.GONE);
                        }

                        if (((AdresError) returningResult).getTitle() != null) {
                            err_title.setVisibility(View.VISIBLE);
                            err_title.setText("*" + ((AdresError) returningResult).getTitle().get(0));
                        } else {
                            err_title.setVisibility(View.GONE);
                        }

                        if (((AdresError) returningResult).getDescription() != null) {
                            err_adres.setVisibility(View.VISIBLE);
                            err_adres.setText("*" + ((AdresError) returningResult).getDescription().get(0));
                        } else {
                            err_adres.setVisibility(View.GONE);
                        }

                        if (((AdresError) returningResult).getPhone() != null) {
                            err_phone.setVisibility(View.VISIBLE);
                            err_phone.setText("*" + ((AdresError) returningResult).getPhone().get(0));
                        } else {
                            err_phone.setVisibility(View.GONE);
                        }
                        if (((AdresError) returningResult).getPostCode() != null) {
                            err_post.setVisibility(View.VISIBLE);
                            err_post.setText("*" + ((AdresError) returningResult).getPostCode().get(0));
                        } else {
                            err_post.setVisibility(View.GONE);
                        }
                        if (((AdresError) returningResult).getCity() != null) {
                            err_city.setVisibility(View.VISIBLE);
                            err_city.setText("*" + ((AdresError) returningResult).getCity().get(0));
                        } else {
                            err_city.setVisibility(View.GONE);
                        }
                        if (((AdresError) returningResult).getTown() != null) {
                            err_town.setVisibility(View.VISIBLE);
                            err_town.setText("*" + ((AdresError) returningResult).getTown().get(0));
                        } else {
                            err_town.setVisibility(View.GONE);
                        }

                        Log.d("err", "err");


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    snackbar = Snackbar.make(layout, "", Snackbar.LENGTH_LONG);
                    View view = snackbar.getView();
                    TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(Color.parseColor("#2ecc71"));
                    tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_black_24dp, 0, 0, 0);
                    snackbar.setText("Adres Başarıyla Eklendi");
                    snackbar.show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragmentAdresler()).commit();
                }

            }

            @Override
            public void onFailure(Call<AdresDetayResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
