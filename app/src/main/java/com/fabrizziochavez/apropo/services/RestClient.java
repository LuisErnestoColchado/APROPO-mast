package com.fabrizziochavez.apropo.services;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import com.fabrizziochavez.apropo.BuildConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RestClient {

    public static void getError(Response response, AppCompatActivity activity) {
        Gson gson = new GsonBuilder().create();
        try {
            RestErrorObject error = gson.fromJson(response.errorBody().string(), RestErrorObject.class);
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

    private static void errorMessage(AppCompatActivity activity, String message) {
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

    public static RestInterface build() {
        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(interceptor);
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
        client.setWriteTimeout(30, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.URL_SERVIDOR)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RestInterface.class);
    }
}

