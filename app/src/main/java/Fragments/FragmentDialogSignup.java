package Fragments;

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

import com.gozde.osmanlitapp.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FragmentDialogSignup extends DialogFragment {
    ImageButton close_dialog;
    Button btn_fb,g_btn,e_btn;
    TextView textgiris;


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

      getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

      btn_fb=(Button)view.findViewById(R.id.btn_fb);
      btn_fb.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              try {
                  PackageInfo info = getActivity().getPackageManager().getPackageInfo("com.example.osmanlitapp", PackageManager.GET_SIGNATURES);
                  for (Signature signature : info.signatures) {
                      MessageDigest md = MessageDigest.getInstance("SHA");
                      md.update(signature.toByteArray());
                      Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                  }
              } catch (PackageManager.NameNotFoundException e) {

              } catch (NoSuchAlgorithmException e) {

              }
          }
      });
      g_btn=(Button)view.findViewById(R.id.btn_g);
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


}
