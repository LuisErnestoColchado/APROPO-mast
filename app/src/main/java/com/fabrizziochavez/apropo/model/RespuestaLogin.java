package com.fabrizziochavez.apropo.model;

/**
 * Created by fabri on 2/05/2017.
 */

public class RespuestaLogin {
    private int coderror;
    private String mensaje;
    private  UsuarioModel usuario;
    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }


    public int getCoderror() {
        return coderror;
    }

    public void setCoderror(int coderror) {
        this.coderror = coderror;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
