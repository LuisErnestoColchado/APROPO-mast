package com.fabrizziochavez.apropo.services;

import com.fabrizziochavez.apropo.model.MensajeModel;
import com.fabrizziochavez.apropo.model.PuntoVenta;
import com.fabrizziochavez.apropo.model.RespuestaLogin;
import com.fabrizziochavez.apropo.model.UsuarioModel;

import java.util.List;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit2.http.FormUrlEncoded;

/**
 * Created by fabri on 9/04/2017.
 */

public interface RestInterface {
    @retrofit.http.FormUrlEncoded
    @POST("cuenta/login")
    Call<RespuestaLogin> Login(@Field("user") String user, @Field("clave") String clave);

    @GET("puntosventa/get/{COD}")
    Call<List<PuntoVenta>> ListarPuntos(@Path("COD") int COD);



}
