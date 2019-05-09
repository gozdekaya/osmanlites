package Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;

import java.util.List;

import Adapters.SpinnerAdresAdapter;
import Adapters.SpinnerCardAdapter;
import Models.Address;
import Models.cardDetails;
import Responses.AdresResponse;
import Responses.CreditCardResponse;
import Utils.ApiClient;
import Utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPayment extends Fragment {
    List<cardDetails> cards;
    ImageButton back;
    TextView editAdr,editCard;
    CheckBox cb1,cb2,cb3;
    LinearLayout linearFatura;
    Spinner spinnerAdr,spinnerFat,spinnerCard;
    SpinnerAdresAdapter adresAdapter;
    SpinnerCardAdapter cardAdapter;
    List<Address> adresler;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_payment,container,false);
        spinnerAdr=(Spinner)view.findViewById(R.id.spinnerAdr);
        spinnerFat=(Spinner)view.findViewById(R.id.spinnerFat);
        spinnerCard=(Spinner)view.findViewById(R.id.spinnerCard);
        back=(ImageButton)view.findViewById(R.id.back);
        cb1=(CheckBox)view.findViewById(R.id.check1);
        linearFatura=(LinearLayout)view.findViewById(R.id.linFat);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        editAdr=(TextView)view.findViewById(R.id.editAdr);
        editAdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentDialogAdresEkle fragmentDialogAdresEkle = new FragmentDialogAdresEkle();
                Bundle bundle = new Bundle();
                bundle.putString("from", "payment");
                fragmentDialogAdresEkle.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container, fragmentDialogAdresEkle).commit();
            }
        });
        editCard=(TextView)view.findViewById(R.id.editCard);
        editCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container,new FragmentDialogKartEkle()).commit();
            }
        });
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearFatura.setVisibility(View.GONE);
                } else {
                    linearFatura.setVisibility(View.VISIBLE);
                }
            }
        });


        ApiInterface apiInterface = ApiClient.getInstance(getContext()).getApi();
       // final String addressid=getArguments().getString("ID",null);

        String bearer= SharedPrefManager.getInstance(getContext()).getToken();
        Call<AdresResponse> responseCall=apiInterface.adreslerim("Bearer " + SharedPrefManager.getInstance(getContext()).getToken(), "application/json");
        responseCall.enqueue(new Callback<AdresResponse>() {
            @Override
            public void onResponse(Call<AdresResponse> call, Response<AdresResponse> response) {
                adresler  =  response.body().getData();
                adresAdapter=new SpinnerAdresAdapter(getContext(),R.layout.spinner_ui,adresler);

                spinnerAdr.setAdapter(adresAdapter);
                spinnerFat.setAdapter(adresAdapter);
            }

            @Override
            public void onFailure(Call<AdresResponse> call, Throwable t) {

            }
        });

        Call<CreditCardResponse> cardResponseCall =apiInterface.kartlarim("Bearer " + SharedPrefManager.getInstance(getContext()).getToken(), "application/json");
        cardResponseCall.enqueue(new Callback<CreditCardResponse>() {
            @Override
            public void onResponse(Call<CreditCardResponse> call, Response<CreditCardResponse> response) {
                cards =response.body().getCardDetails();
                cardAdapter=new SpinnerCardAdapter(getContext(),R.layout.spinner_ui,cards);
                spinnerCard.setAdapter(cardAdapter);
            }

            @Override
            public void onFailure(Call<CreditCardResponse> call, Throwable t) {

            }
        });
        return view;
    }
}
