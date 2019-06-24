package Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import Utils.ApiClient;
import Responses.LoginResponse;
import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;

import Models.UserToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDialogLogin extends DialogFragment {

    Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    Object returningResult = new Object();
    TextView err_email,err_pass;
    Boolean isConnected = false;
    ImageButton close;

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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
         close=view.findViewById(R.id.close);
         close.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dismiss();
             }
         });
        editTextEmail = (EditText) view.findViewById(R.id.et_mail);
        editTextPassword = (EditText) view.findViewById(R.id.et_pw);
        buttonLogin = (Button) view.findViewById(R.id.btn_login);
        err_email=(TextView)view.findViewById(R.id.error_mail);
        err_pass=(TextView)view.findViewById(R.id.error_pass);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkConnection())
                isConnected = true;
            else isConnected = false;
                userLogin();
            }
        });


        return view;
    }


    private void userLogin() {


        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

//        if (email.isEmpty()) {
//            editTextEmail.setError("E-Posta gerekli");
//            editTextEmail.requestFocus();
//            return;
//        }
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            editTextEmail.setError("Geçerli bir e-posta giriniz");
//            editTextEmail.requestFocus();
//            return;
//        }
//
//
//        if (password.isEmpty()) {
//            editTextPassword.setError("Şifre gerekli");
//            editTextPassword.requestFocus();
//            return;
//        }
//
//        if (password.length() < 6) {
//            editTextPassword.setError("Şifre en az 6 karakter olmalıdır");
//            editTextPassword.requestFocus();
//            return;
//        }
        if (isConnected){ Call<LoginResponse> call = ApiClient
                .getInstance(getContext()).getApi().loginUser("application/json",email, password);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


                    LoginResponse loginResponse = response.body();

                    if ( response.isSuccessful()){
                        UserToken token = new UserToken(loginResponse.getTokenType(), loginResponse.getExpiresIn(), loginResponse.getAccessToken(), loginResponse.getRefreshToken());
                        SharedPrefManager.getInstance(getContext()).saveToken(token);
                        Toast.makeText(getContext(), R.string.basarili, Toast.LENGTH_SHORT).show();
                        dismiss();

                        getFragmentManager().beginTransaction().replace(R.id.container,new FragmentAyarlar()).commit();


                    }else {
                        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                        builder.setTitle("Uyarı!");
                        builder.setMessage("Kullanıcı Bilgileri Hatalı!");
                        builder.setIcon(R.drawable.ic_warning);
                        builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog=builder.create();
                        alertDialog.show();
                    }


//                    try {
//                        JSONObject object = new JSONObject(response.errorBody().string());
//                        String error = object.optString("errors");
//                        Gson gsonError = new Gson();
//                        returningResult = gsonError.fromJson(error, LoginError.class);
//
//                        if (((LoginError) returningResult).getEmail() != null) {
//                            err_email.setVisibility(View.VISIBLE);
//                            err_email.setText("*" + ((LoginError) returningResult).getEmail().get(0));
//                        } else {
//                            err_email.setVisibility(View.GONE);
//                        }
//                        if (((LoginError) returningResult).getPassword() != null) {
//                            err_pass.setVisibility(View.VISIBLE);
//                            err_pass.setText("*" + ((LoginError) returningResult).getPassword().get(0));
//                        } else {
//                            err_pass.setVisibility(View.GONE);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
                }





                @Override
                public void onFailure(Call< LoginResponse > call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }else {
            Toast.makeText(getContext(), "internet bağlantını kontrol et", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }



    private boolean checkConnection(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (!(networkInfo != null && networkInfo.isConnected() ))
        {
            return false;
            //
        } else return true;

    }
    }
