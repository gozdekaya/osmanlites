package Fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;

import Responses.AddCreditCardResponse;
import Utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDialogKartEkle extends DialogFragment {
    ImageButton backbutton;
    Snackbar snackbar;
    LinearLayout layout;
    EditText name,holder,number,month,year;
    Button save;
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
        final View view = inflater.inflate(R.layout.fragment_kart_ekle, container, false);
        backbutton=(ImageButton)view.findViewById(R.id.back);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        name=view.findViewById(R.id.cardname);
        holder=view.findViewById(R.id.holdername);
        number=view.findViewById(R.id.cardnumber);
        month=view.findViewById(R.id.month);
        year=view.findViewById(R.id.year);
        save=view.findViewById(R.id.save);
        layout=view.findViewById(R.id.lin1);
save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        saveCard();
    }
});



        return  view;

    }


    private void saveCard(){
        String Name = name.getText().toString().trim();
        String Holder=holder.getText().toString().trim();
        String Number=number.getText().toString().trim();
        String Month=month.getText().toString().trim();
        String Year=year.getText().toString().trim();

        Call<AddCreditCardResponse> call = ApiClient.getInstance(getContext()).getApi().kartekle("Bearer " + SharedPrefManager.getInstance(getContext()).getToken(), "application/json",Name,Holder,Number,Month,Year);
        call.enqueue(new Callback<AddCreditCardResponse>() {
            @Override
            public void onResponse(Call<AddCreditCardResponse> call, Response<AddCreditCardResponse> response) {
                snackbar = Snackbar.make(getView(), "", Snackbar.LENGTH_LONG);
                View view = snackbar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.parseColor("#2ecc71"));
                tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_black_24dp, 0, 0, 0);
                snackbar.setText("Kartınız Başarıyla Eklendi");
                snackbar.show();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragmentCreditCards()).commit();
            }

            @Override
            public void onFailure(Call<AddCreditCardResponse> call, Throwable t) {

            }
        });
    }
}
