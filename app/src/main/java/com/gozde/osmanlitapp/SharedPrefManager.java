package com.gozde.osmanlitapp;

import android.content.Context;
import android.content.SharedPreferences;

import Models.User;
import Models.UserToken;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME="my_shared_pref";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx){
        this.mCtx=mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx){

        if (mInstance==null){
            mInstance= new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    public void saveUser(User user){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Email",user.getEmail());
        editor.putString("Password",user.getPassword());

        editor.apply();

    }
    public  void saveToken(UserToken userToken){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString("access_token",userToken.getAccess_token());
        editor.apply();
    }

   public String getToken(){
       SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
      String token = sharedPreferences.getString("access_token",null);
       return  token;
   }

//    public Address getAdres() {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return new Address(
//                sharedPreferences.getString("id", null),
//                sharedPreferences.getString("title", null),
//                sharedPreferences.getString("description", null)
//
//        );
//    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        //return sharedPreferences.getInt("id",-1)!=-1;
        if (sharedPreferences.getString("access_token", null) == null) {
            return false;
        } return true;
    }





      public void clear(){
          SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
          SharedPreferences.Editor editor=sharedPreferences.edit();
          editor.clear();
          editor.apply();

      }


}
