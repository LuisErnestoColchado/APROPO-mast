package com.fabrizziochavez.apropo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fabrizziochavez.apropo.model.PuntoVenta;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DB_Version = 1;
    private static final String DB_Name = "db_apropo";

    //tablas
    public static final String TB_Puntos = "tb_puntos";

    public DatabaseHandler(Context context){
        super(context, DB_Name, null, DB_Version);
    }

    public void truncateTabla(String table_name, String condicion){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table_name + condicion);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TB_Puntos + "(" +
                "CodigoCliente INTEGER," +
                "Nombre TEXT," +
                "Documento TEXT," +
                "Direccion TEXT," +
                "CodigoPunto  INTEGER," +
                "Latitud REAL," +
                "Longitud REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TB_Puntos);
        onCreate(db);
    }
    //AÑADIR Puntos
     public void addPunto(PuntoVenta pv){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("CodigoCliente", pv.getCodigoCliente());
        values.put("Nombre",pv.getNombre());
        values.put("Documento", pv.getDocumento());
        values.put("Direccion",pv.getDireccion());
        values.put("CodigoPunto",pv.getCodigoPunto());
        values.put("Latitud",pv.getLatitud());
        values.put("Longitud",pv.getLongitud());
        try {
            db.insert(TB_Puntos, null, values);
            }catch (Throwable t){
                Log.e("Error", t.getMessage());
            }
    }

    public List<PuntoVenta> listPV(){
        List<PuntoVenta> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_Puntos, null);
        if(cursor.moveToFirst()){
            do{
                PuntoVenta pc = new PuntoVenta(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getDouble(5),
                        cursor.getDouble(6)
                );
                data.add(pc);
            }while (cursor.moveToNext());
        }
        return data;

    }

}
