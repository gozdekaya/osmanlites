package com.gozde.osmanlitapp;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import Fragments.FragmentCart;
import Fragments.FragmentDialogSignup;
import Fragments.FragmentHome;
import Fragments.FragmentKesfet;
import Fragments.FragmentProfile;
import Fragments.FragmentSearch;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    Boolean isFirst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences=this.getSharedPreferences("gg", Context.MODE_PRIVATE);
        String dil ="aaaa";
        dil=preferences.getString("dil","tr");
        isFirst =preferences.getBoolean("firstrun",true);
        if (dil!=null){ Locale locale = new Locale(dil); // Sayfay� y�klemeden �nce default locale al�yoruz ve sayfay� ona g�re y�kl�yoruz.
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());}
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        final Dialog dialog=new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_dialog);
        Button btnturkce=(Button)dialog.findViewById(R.id.turkce);
        Button btning=(Button)dialog.findViewById(R.id.ingilizce);
        TextView tvBaslik=(TextView)dialog.findViewById(R.id.textview_baslik);
        tvBaslik.setText(R.string.dil_sec);
        btnturkce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().putBoolean("firstrun",false).commit();
                preferences.edit().putString("dil","").commit();
                Locale locale = new Locale("");
                Locale.setDefault(locale);
                Configuration config =new Configuration();
                config.locale=locale;
                getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
                finish();
                startActivity(getIntent());

                Toast.makeText(MainActivity.this,R.string.dil_degistir,Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        btning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().putBoolean("firstrun", false).commit();
                preferences.edit().putString("dil","en").commit();
                Locale locale=new Locale("en");
                Locale.setDefault(locale);
                Configuration config =new Configuration();
                config.locale=locale;
                getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
                finish();
                startActivity(getIntent());

                Toast.makeText(MainActivity.this,R.string.dil_degistir,Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
        if (isFirst){
            dialog.show();
            preferences.edit().putBoolean("firstrun", false).commit();
        }
      //  dialog.show();




        getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragmentHome()).commit();


        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.gozde.osmanlitapp", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }



    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment = null;
            switch (menuItem.getItemId()){
                case R.id.home:

                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragmentHome()).commit();
                    break;
                case R.id.search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragmentSearch()).commit();
                    break;
                case R.id.cart:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragmentCart()).commit();
                    break;
                case R.id.explore:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragmentKesfet()).commit();
                    break;
                case R.id.profile:
                    if (SharedPrefManager.getInstance(MainActivity.this).isLoggedIn()){
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragmentProfile()).commit();
                    }else {
                        FragmentDialogSignup dialogSignup = new FragmentDialogSignup();
                        dialogSignup.show(getSupportFragmentManager(),"DialogSignup");
                    }


                  //  Log.d("isloggedin", String.valueOf(SharedPrefManager.getInstance(MainActivity.this).isLoggedIn()));


                 // fragment=new FragmentProfile();


                    break;
            }

            return true;
        }
    };
}
