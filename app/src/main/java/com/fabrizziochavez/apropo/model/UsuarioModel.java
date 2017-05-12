package com.fabrizziochavez.apropo.model;

/**
 * Created by fabri on 10/04/2017.
 */

public class UsuarioModel {
    private String codigo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getZonaid() {
        return zonaid;
    }

    public void setZonaid(int zonaid) {
        this.zonaid = zonaid;
    }

    public String getZonanombre() {
        return zonanombre;
    }

    public void setZonanombre(String zonanombre) {
        this.zonanombre = zonanombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String nombre;
    private int zonaid;
    private String zonanombre;
    private String email;

}
