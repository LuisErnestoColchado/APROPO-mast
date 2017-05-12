package com.fabrizziochavez.apropo.services;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fabrizziochavez.apropo.data.DatabaseHandler;
import com.fabrizziochavez.apropo.model.PuntoVenta;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SyncService extends IntentService {

    private static DatabaseHandler dbh;
    private static SessionManager session;
    public static AppCompatActivity activity;
    public static final String actionFinish = "FINISH";
    public static final String actionError = "ERROR";
    public static boolean newLogin=false;

    private int counter;
    private int counter_helper;

    public SyncService() {
        super("SyncService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        int actionType = intent.getIntExtra("actionType", 0);
        switch (actionType){
            case 1://Sync
                sync_puntos(actionType,"Puntos sincronizados");
                break;
            case 2:
                sync_ejemplo(actionType,"sincronizados");
                break;
            case 3:

                break;
        }
    }
    //private SessionManager  sesion;

    public void sync_puntos(int actionType,String message){

        session = new SessionManager(getApplicationContext());
        HashMap<String, Object> user = session.getUserDetails();
        RestInterface api = SharedTools.call();
        PuntoVenta oPv;
        String CodigoZona = user.get(SessionManager.KEY_ZONAID).toString();
        Call<List<PuntoVenta>> call = api.ListarPuntos(Integer.parseInt(CodigoZona));
        try{

            Response<List<PuntoVenta>> puntos = call.execute();
            if(puntos.body().size() == 0)
            {
                sendActionBC(actionFinish, "No se encontraron", actionType);
            }
            if(puntos.isSuccess())
            {
                dbh = new DatabaseHandler(activity);

                dbh.truncateTabla(DatabaseHandler.TB_Puntos,"");
                for(int i =0;i<puntos.body().size();i++){
                    int CodCliente = puntos.body().get(i).getCodigoCliente();
                    String Nombre = puntos.body().get(i).getNombre();
                    String Documento = puntos.body().get(i).getDocumento();
                    String Direccion = puntos.body().get(i).getDireccion();
                    int CodPunto = puntos.body().get(i).getCodigoPunto();
                    double Latitud = puntos.body().get(i).getLatitud();
                    double Longitud = puntos.body().get(i).getLongitud();

                    dbh.addPunto(new PuntoVenta(CodCliente,Nombre,Documento,Direccion,CodPunto,Latitud,Longitud));
                }
                sendActionBC(actionFinish,message,actionType);
            }
            else {
                sendActionBC(actionError, "Error", actionType);
            }
        }
        catch (IOException e){
            Log.e("Error","IOException");
            sendActionBC(actionError,e.getMessage(),actionType);
        }

    }
    public void sync_ejemplo(int actionType, String message){
        if (true){
            sendActionBC(actionFinish, message, actionType);
        }
        else{
            sendActionBC(actionError, "Response error", actionType);
        }


    }
    private void sendActionBC(String action, String message, int actionType){
        Intent bcIntent = new Intent();
        bcIntent.setAction(action);
        bcIntent.putExtra("message", message);
        bcIntent.putExtra("actionType", actionType);
        sendBroadcast(bcIntent);
    }
}
