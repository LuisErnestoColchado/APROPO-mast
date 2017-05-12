package com.fabrizziochavez.apropo.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by luis on 08/05/17.
 */

public class PuntoVenta {

    private int CodigoCliente;
    private String Nombre;
    private String Documento;
    private String Direccion;
    private int CodigoPunto;
    private double Latitud;
    private double Longitud;

    public PuntoVenta(){

    }
    public PuntoVenta(int pCodigoCliente,String pNombre,String pDocumento,String pDireccion,int pCodigoPunto,double pLatitud,double pLongitud){
        this.CodigoCliente = pCodigoCliente;
        this.Nombre = pNombre;
        this.Documento = pDocumento;
        this.Direccion = pDireccion;
        this.CodigoPunto = pCodigoPunto;
        this.Latitud = pLatitud;
        this.Longitud = pLongitud;

    }
    public int getCodigoCliente() {
        return CodigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        CodigoCliente = codigoCliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String documento) {
        Documento = documento;
    }

    public int getCodigoPunto() {
        return CodigoPunto;
    }

    public void setCodigoPunto(int codigoPunto) {
        CodigoPunto = codigoPunto;
    }

    public double getLatitud() {
        return Latitud;
    }

    public void setLatitud(double latitud) {
        this.Latitud = latitud;
    }

    public double getLongitud() {
        return Longitud;
    }

    public void setLongitud(double longitud) {
        this.Longitud = longitud;
    }

    public String   getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }
}
