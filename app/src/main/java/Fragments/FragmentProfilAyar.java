package Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gozde.osmanlitapp.MainActivity;
import com.gozde.osmanlitapp.R;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfilAyar extends Fragment {
ImageButton backbutton;
TextView dil_degis;
    SharedPreferences preferences;

    public FragmentProfilAyar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment_profil_ayar, container, false);

        preferences=getContext().getSharedPreferences("gg", Context.MODE_PRIVATE);
        String dil ="aaaa";
        dil=preferences.getString("dil","tr");
       dil_degis=view.findViewById(R.id.dil_degistir);
       dil_degis.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Dialog dialog = new Dialog(getContext());
               dialog.setContentView(R.layout.custom_dialog);
               Button btnturkce=(Button)dialog.findViewById(R.id.turkce);
               Button btning=(Button)dialog.findViewById(R.id.ingilizce);
               TextView tvBaslik=(TextView)dialog.findViewById(R.id.textview_baslik);
               tvBaslik.setText(R.string.dil_sec);
               btnturkce.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       preferences.edit().putString("dil","").commit();
                       Locale locale = new Locale("");
                       Locale.setDefault(locale);
                       Configuration config =new Configuration();
                       config.locale=locale;
                     getActivity().getBaseContext().getResources().updateConfiguration(config,getActivity().getBaseContext().getResources().getDisplayMetrics());
                       getActivity().finish();
                       startActivity(getActivity().getIntent());

                       Toast.makeText(getContext(),R.string.dil_degistir,Toast.LENGTH_SHORT).show();
                       dialog.dismiss();
                   }
               });
               btning.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       preferences.edit().putString("dil","en").commit();
                       Locale locale=new Locale("en");
                       Locale.setDefault(locale);
                       Configuration config =new Configuration();
                       config.locale=locale;
                      getActivity().getBaseContext().getResources().updateConfiguration(config,getActivity().getBaseContext().getResources().getDisplayMetrics());
                       getActivity().finish();
                       startActivity(getActivity().getIntent());

                       Toast.makeText(getContext(),R.string.dil_degistir,Toast.LENGTH_SHORT).show();

                       dialog.dismiss();
                   }
               });
               dialog.show();
           }

       });

        backbutton=(ImageButton)view.findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

}
