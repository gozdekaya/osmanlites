package com.gozde.osmanlitapp;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import Fragments.FragmentCart;
import Fragments.FragmentDialogSignup;
import Fragments.FragmentHome;
import Fragments.FragmentKesfet;
import Fragments.FragmentProfile;
import Fragments.FragmentSearch;

public class MainActivity extends AppCompatActivity {
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragmentHome()).commit();
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
