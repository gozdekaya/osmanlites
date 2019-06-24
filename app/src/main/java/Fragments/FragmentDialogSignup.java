package Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.JsonObject;
import com.gozde.osmanlitapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class FragmentDialogSignup extends DialogFragment implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{
    ImageButton close_dialog;
    Button btn_fb,g_btn,e_btn;
    TextView textgiris;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    LoginButton loginButton;
    private GoogleApiClient googleApiClient;
    private static final int code=100;
    SignInButton btnGoogleSignIn;

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
        View view = inflater.inflate(R.layout.fragment_dialog,container,false);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(getContext()).enableAutoManage(getActivity(), this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();

         btnGoogleSignIn = view.findViewById(R.id.btnGoogleSignIn);
        btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSocialLogin();
            }
        });

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
        loginButton.setReadPermissions(Arrays.asList("public_profile",EMAIL));

        loginButton.setFragment(this);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               final AccessToken accessToken=loginResult.getAccessToken();
                GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        //  LoginManager.getInstance().logOut();

                        LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile",EMAIL));

                    }
                }).executeAsync();
                Toast.makeText(getContext(), "Login Success with facebook", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().replace(R.id.container,new FragmentHome()).commit();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });



        // If you are using in a fragment, call loginButton.setFragment(this);
      /*   loginButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile"));

             }
         });*/
        // Callback registration









      getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));









      e_btn=(Button)view.findViewById(R.id.btn_e);
      textgiris=(TextView)view.findViewById(R.id.txt_giris);
       close_dialog=(ImageButton)view.findViewById(R.id.close);
       close_dialog.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dismiss();
           }
       });
      textgiris.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FragmentDialogLogin dialogSignup = new FragmentDialogLogin();
              dialogSignup.show(getFragmentManager(),"FragmentLogin");
            dismiss();
          }
      });

      e_btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FragmentDialogRegister dialogRegister=new FragmentDialogRegister();
              dialogRegister.show(getFragmentManager(), "FragmentRegister");
          }
      });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == code) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResultGoogleSignIn(result);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void googleSocialLogin() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, code);
    }
        private void handleResultGoogleSignIn(GoogleSignInResult result) {
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                String socialFirstName = account.getGivenName();
                String socialLastName = account.getFamilyName();
                String socialEmail = account.getEmail();
                String socialUserId = account.getId();

            }
        }

    private void googleLogout() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();
    }
}

