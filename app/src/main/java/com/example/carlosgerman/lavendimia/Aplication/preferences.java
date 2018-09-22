package com.example.carlosgerman.lavendimia.Aplication;

import android.content.Context;
import android.content.SharedPreferences;

public class preferences {
    private final String SHARED_PREFS_FILE = "Preferencias";
    private static preferences mInstance;
    private static SharedPreferences mMyPreferences;
    private Context mContext;


    public static String User = "Suscriptor";
    public static String Userid;
    public static String Img;

    public preferences() {

    }

    public static preferences getInstance() {
        if (mInstance == null) mInstance = new preferences();
        return mInstance;
    }

    public void Initialize(Context ctxt) {
        mContext = ctxt;
        mMyPreferences = mContext.getSharedPreferences(SHARED_PREFS_FILE, 0);
    }

    public void clear() {
        SharedPreferences.Editor editor = mMyPreferences.edit();
        editor.clear();
    }


    public void setUser(String user) {
        SharedPreferences.Editor editor = mMyPreferences.edit();
        editor.putString(User, user);
        editor.apply();
    }

    public String getUser() {
        return mMyPreferences.getString(User, " ");
    }



    public  void setUSerId(String userid) {
        SharedPreferences.Editor editor = mMyPreferences.edit();
        editor.putString(Userid, userid);
        editor.apply();
    }

    public String getUserid() {
        return mMyPreferences.getString(Userid, "");
    }

    public  void setImg(String img) {
        SharedPreferences.Editor editor = mMyPreferences.edit();
        editor.putString(Img, img);
        editor.apply();
    }

    public String getImg() {
        return mMyPreferences.getString(Img, "");
    }
}
