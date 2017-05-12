package com.fabrizziochavez.apropo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.fabrizziochavez.apropo.PuntoVentaFragment;
import com.fabrizziochavez.apropo.R;
import com.fabrizziochavez.apropo.data.DatabaseHandler;
import com.fabrizziochavez.apropo.model.PuntoVenta;

/**
 * Created by luis on 10/05/17.
 */

public class ListaPuntoVentaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private final Context ctx;
    private final ImageView inicial;
    private final TextView texto1;
    private final TextView texto2;
    private final TextView texto3;

    private PuntoVenta pdv;
    private final DatabaseHandler dbh;
    private final PuntoVentaFragment fh;

    public ListaPuntoVentaViewHolder(View itemView,Context context,PuntoVentaFragment fragment)
    {
        super(itemView);

        ctx = context;
        fh = fragment;

        inicial = (ImageView) itemView.findViewById(R.id.img_estado);
        texto1 = (TextView) itemView.findViewById(R.id.id);
        texto2 = (TextView) itemView.findViewById(R.id.content);
        texto3 = (TextView) itemView.findViewById(R.id.content2);

        dbh = new DatabaseHandler(ctx);

        itemView.setOnClickListener(this);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        //programar un activity para detalles
    }

    public boolean onLongClick(View view){
        if(pdv.getCodigoPunto()!=0){
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            popup.inflate(R.menu.main);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    /*switch (item.getItemId()) {
                        case R.id.pc_glosa:
                            Log.e("Menu", "Glosa");
                            glosa();
                            return true;
                        case R.id.pc_eliminar:
                            Log.e("Menu", "Eliminar");
                            validar_eliminar();
                            return true;
                    }*/
                    return true;
                }
            });
            popup.show();
        }
        return true;
    }

    public void bind(PuntoVenta punto){
        pdv = punto;

        int color;
        color = Color.parseColor("#A860A0");
        TextDrawable drawable = TextDrawable.builder().buildRound(String.valueOf(punto.getNombre().substring(0,1)), color);
        inicial.setImageDrawable(drawable);
        texto1.setText(punto.getNombre());
        texto2.setText("RUC: " + punto.getDocumento());
        texto3.setText(" " + punto.getDireccion());

    }
}
