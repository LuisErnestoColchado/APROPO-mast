package com.fabrizziochavez.apropo.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.fabrizziochavez.apropo.LoginActivity;

import java.util.HashMap;

public class SessionManager {
    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;
    private final Context _context;

    // Sharedpref file name
    private static final String PREF_NAME = "Apropo";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_USUARIO = "usuario";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_CODIGO = "codigo";
    public static final String KEY_ZONAID = "zonaid";
    public static final String KEY_ZONANOMBRE = "zonanombre";
    public static final String KEY_EMAIL = "email";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        int PRIVATE_MODE = 0;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String codigo,String nombre, int zonaid, String zonanombre, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NOMBRE, nombre);
        editor.putString(KEY_CODIGO, codigo);
        editor.putInt(KEY_ZONAID, zonaid);
        editor.putString(KEY_ZONANOMBRE, zonanombre);
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }


    public HashMap<String, Object> getUserDetails(){
        HashMap<String, Object> user = new HashMap<>();
        user.put(KEY_CODIGO, pref.getString(KEY_CODIGO, null));
        user.put(KEY_NOMBRE, pref.getString(KEY_NOMBRE, null));
        user.put(KEY_ZONAID, pref.getInt(KEY_ZONAID,0));
        user.put(KEY_ZONANOMBRE, pref.getString(KEY_ZONANOMBRE, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
