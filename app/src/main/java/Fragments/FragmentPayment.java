package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;

import java.util.List;

import Adapters.SpinnerAdresAdapter;
import Adapters.SpinnerCardAdapter;
import Models.Address;
import Models.Card;
import Models.cardDetails;
import Responses.AdresResponse;
import Responses.CreditCardResponse;
import Utils.ApiClient;
import Utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPayment extends Fragment {
    ProgressBar mProgressBar;
    List<cardDetails> cards;
    EditText cardno,cvv,holder;
    ImageButton back;
    TextView editAdr,editCard,kayitli_kart,kart_gir,devam;
    CheckBox cb1,cb2,cb3;
    LinearLayout linearFatura,kartbilgi,kartgiris,kartspin;
    Spinner spinnerAdr,spinnerFat,spinnerCard,spinnerExMonth,spinnerExYear;
    SpinnerAdresAdapter adresAdapter;
    SpinnerCardAdapter cardAdapter;
    List<Address> adresler ;
    Context mContext;
    String fat;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_payment,container,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
         kartbilgi=view.findViewById(R.id.kartbilgi);
         devam=view.findViewById(R.id.show);
        mProgressBar=(ProgressBar)view.findViewById(R.id.progressBar1);
         devam.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (cb2.isChecked() && cvv.getText().length() == 3 && cardno.getText().length() == 16){
                     int a =spinnerFat.getSelectedItemPosition();
                     int b =spinnerAdr.getSelectedItemPosition();
                     String c= spinnerExMonth.getSelectedItem().toString();
                     String d =spinnerExYear.getSelectedItem().toString();
                     String fatura;
                     String teslimat;
                     Fragment fragmentonizleme =new FragmentSiparisOnizleme();
                     Bundle bundle = new Bundle();

                     bundle.putString("holderName",holder.getText().toString());
                     bundle.putString("number",cardno.getText().toString());
                     bundle.putString("cvc",cvv.getText().toString());
                     bundle.putString("expireMonth",c);
                     bundle.putString("expireYear", d);

                     fatura =adresler.get(a).getName() + "-" +  adresler.get(a).getDescription() + " " +  adresler.get(a).getTown() + "/" +  adresler.get(a).getCity() + "/" +  adresler.get(a).getCountry().getTitle();
                     teslimat =  adresler.get(b).getName() + "-" +  adresler.get(b).getDescription() + " " +  adresler.get(b).getTown() + "/" +  adresler.get(b).getCity() + "/" +  adresler.get(b).getCountry().getTitle();
                     bundle.putString("fatura", fatura);
                     bundle.putString("teslimat",teslimat);
                     bundle.putString("shippingAddressId", adresler.get(b).getId().toString());
                     bundle.putString("billingAddressId", adresler.get(a).getId().toString());

                     fragmentonizleme.setArguments(bundle);
                     getFragmentManager().beginTransaction().replace(R.id.container,fragmentonizleme).addToBackStack(null).commit();
                 }else
                     Toast.makeText(getContext(),"Lütfen Tüm Alanları Doldurunuz ve Kullanıcı Sözleşmesini Onaylayınız",Toast.LENGTH_SHORT).show();

             }
         });
         cvv=view.findViewById(R.id.cvv);
         cardno=view.findViewById(R.id.kartno);
         holder=view.findViewById(R.id.kartholder);
        kayitli_kart=view.findViewById(R.id.kayit_kart_sec);
        kartspin=view.findViewById(R.id.kart_spin);
        kart_gir=view.findViewById(R.id.kart_gir);
        kartgiris=view.findViewById(R.id.kart_giris);
        cb2=view.findViewById(R.id.checkbox2);
        kayitli_kart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kartbilgi.setVisibility(View.VISIBLE);
                kartgiris.setVisibility(View.GONE);
                kart_gir.setVisibility(View.VISIBLE);
                kayitli_kart.setVisibility(View.GONE);

            }
        });
        kartbilgi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kartbilgi.setVisibility(View.GONE);
                kartgiris.setVisibility(View.VISIBLE);
                kart_gir.setVisibility(View.GONE);
                kayitli_kart.setVisibility(View.VISIBLE);
            }
        });


        spinnerAdr=(Spinner)view.findViewById(R.id.spinnerAdr);
        spinnerFat=(Spinner)view.findViewById(R.id.spinnerFat);
        spinnerExMonth=(Spinner)view.findViewById(R.id.spmonth) ;
        spinnerExYear=view.findViewById(R.id.spyear);
        spinnerCard=(Spinner)view.findViewById(R.id.spinnerCard);
        back=(ImageButton)view.findViewById(R.id.back);
        cb1=(CheckBox)view.findViewById(R.id.check1);
        linearFatura=(LinearLayout)view.findViewById(R.id.linFat);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getFragmentManager().beginTransaction().replace(R.id.container,new FragmentCart()).commit();
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
      /*  editCard=(TextView)view.findViewById(R.id.editCard);
        editCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container,new FragmentDialogKartEkle()).commit();
            }
        });*/
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
        mProgressBar.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getInstance(getContext()).getApi();
       // final String addressid=getArguments().getString("ID",null);

        String bearer= SharedPrefManager.getInstance(getContext()).getToken();
        Call<AdresResponse> responseCall=apiInterface.adreslerim("Bearer " + SharedPrefManager.getInstance(getContext()).getToken(), "application/json");
        responseCall.enqueue(new Callback<AdresResponse>() {
            @Override
            public void onResponse(Call<AdresResponse> call, Response<AdresResponse> response) {

                adresler  =  response.body().getData();
                mProgressBar.setVisibility(View.GONE);
                adresAdapter=new SpinnerAdresAdapter(mContext,R.layout.spinner_ui,adresler);
                if (adresler.size() == 0){
                    FragmentDialogAdresEkle fragmentDialogAdresEkle = new FragmentDialogAdresEkle();
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "payment");
                    fragmentDialogAdresEkle.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container, fragmentDialogAdresEkle).commit();
                }
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
                mProgressBar.setVisibility(View.GONE);
                cardAdapter=new SpinnerCardAdapter(mContext,R.layout.spinner_ui,cards);
                spinnerCard.setAdapter(cardAdapter);
            }

            @Override
            public void onFailure(Call<CreditCardResponse> call, Throwable t) {

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
