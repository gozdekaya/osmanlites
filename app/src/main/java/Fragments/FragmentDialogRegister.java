package Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;

import Models.UserToken;
import Responses.RegisterResponse;
import Utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDialogRegister extends DialogFragment {

    Button signup;
    ImageButton close;
    EditText name,surname,email,password,phone,tc;

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
        View view=inflater.inflate(R.layout.fragment_register,container,false);
        name=view.findViewById(R.id.et_uname);
        close=view.findViewById(R.id.close);
        surname=view.findViewById(R.id.et_usurname);
        email=view.findViewById(R.id.et_mail);
        password=view.findViewById(R.id.et_pw);
        phone=view.findViewById(R.id.et_phone);
        tc=view.findViewById(R.id.et_tc);
        signup=view.findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              userRegister();

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    private void userRegister() {
        String uname=name.getText().toString().trim();
        String usurname=surname.getText().toString().trim();
        String mail=email.getText().toString().trim();
        String upwd=password.getText().toString().trim();
        String uphone=phone.getText().toString().trim();
        String utc=tc.getText().toString().trim();
        Call<RegisterResponse> call = ApiClient.getInstance(getContext()).getApi().registerUser("application/json",uname,usurname,mail,upwd,uphone,utc);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse=response.body();
                UserToken token = new UserToken(registerResponse.getTokenType(), registerResponse.getExpiresIn(), registerResponse.getAccessToken(), registerResponse.getRefreshToken());
                SharedPrefManager.getInstance(getContext()).saveToken(token);
                Toast.makeText(getContext(), "Kaydınız Oluşturuldu", Toast.LENGTH_SHORT).show();
                dismiss();

                getFragmentManager().beginTransaction().replace(R.id.container,new FragmentDialogLogin()).commit();
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
              t.printStackTrace();
            }
        });

    }
}
