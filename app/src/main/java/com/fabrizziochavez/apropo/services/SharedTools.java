package com.fabrizziochavez.apropo.services;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fabrizziochavez.apropo.R;
import com.fabrizziochavez.apropo.model.ErrorModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by fabri on 1/05/2017.
 */

public class SharedTools {
    public static final String api_url = "http://192.168.1.102:81/api/";
    public static boolean isNuevo;
    public static boolean fromDetalle;
    public static int id;
    private ActionBarDrawerToggle drawerToggle;
    public static RestInterface call() {
        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(interceptor);
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
        client.setWriteTimeout(30, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SharedTools.api_url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RestInterface.class);
    }
    public static void errorMessage(AppCompatActivity activity, String message) {
        new AlertDialog.Builder(activity)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("Aceptar", null)
                .show();
    }
    public static void showError(AppCompatActivity activity, Throwable t) {
        if (t.getMessage().isEmpty()) {
            errorMessage(activity, t.toString());
        } else {
            errorMessage(activity, t.getMessage());
        }
    }
    public static void getError(Response response, AppCompatActivity activity) {
        Gson gson = new GsonBuilder().create();
        try {
            ErrorModel error = gson.fromJson(response.errorBody().string(), ErrorModel.class);
            new AlertDialog.Builder(activity)
                    .setTitle(error.getMessage())
                    .setMessage(error.getExceptionMessage())
                    .setPositiveButton("Aceptar", null)
                    .show();
        } catch (IOException e) {
            new AlertDialog.Builder(activity)
                    .setTitle("Error")
                    .setMessage(e.getMessage())
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
    }
    public void SetUpNavigation(AppCompatActivity activity, final DrawerLayout drawerLayout, final Toolbar toolbar) {
        activity.setSupportActionBar(toolbar);
        assert activity.getSupportActionBar() != null;
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);


        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });
    }

}
