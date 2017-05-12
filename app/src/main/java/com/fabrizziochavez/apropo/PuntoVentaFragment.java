package com.fabrizziochavez.apropo;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fabrizziochavez.apropo.data.DatabaseHandler;
import com.fabrizziochavez.apropo.dummy.DummyContent;
import com.fabrizziochavez.apropo.dummy.DummyContent.DummyItem;
import com.fabrizziochavez.apropo.model.PuntoVenta;
import com.fabrizziochavez.apropo.services.SessionManager;
import com.fabrizziochavez.apropo.services.SharedTools;

import java.util.ArrayList;
import java.util.List;

public class PuntoVentaFragment extends Fragment implements SearchView.OnQueryTextListener{

    private PuntoVentaRecyclerViewAdapter adapter;
    private List<PuntoVenta> mModels;
    private RecyclerView recyclerView;
    private AppCompatActivity activity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DatabaseHandler dbh;
    private SessionManager session;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_puntoventa_list, container, false);

        final Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        final DrawerLayout mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        activity = (AppCompatActivity) getActivity();
        final SharedTools cm = new SharedTools();
        cm.SetUpNavigation(activity, mDrawerLayout, toolbar);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_puntos_venta);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });
        swipeRefreshLayout.setEnabled(false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new Divider(activity);
        recyclerView.addItemDecoration(itemDecoration);

        dbh = new DatabaseHandler(activity);
        SQLiteDatabase db = dbh.getWritableDatabase();
        dbh.onCreate(db);

        session = new SessionManager(activity);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.checkLogin();

                SharedTools.isNuevo=true;
                SharedTools.fromDetalle=false;
                SharedTools.id = 0;

                Intent intent = new Intent(activity, addPuntoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
        });

        return rootView;

    }

    public void reload(){
        mModels.clear();
        swipeRefreshLayout.setRefreshing(true);
        getPuntosVenta();
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mModels = new ArrayList<>();

        getPuntosVenta();
    }

    private void getPuntosVenta(){
        MaterialDialog progress = new MaterialDialog.Builder(activity)
                .title("Cargando Puntos de Venta")
                .content("Espere por favor")
                .progress(true, 0)
                .show();
        List<PuntoVenta> pv = dbh.listPV();
        if(pv.size()>0){
            for (PuntoVenta PV : pv) {
                String log = "Nombre: " + PV.getNombre();
                Log.d("Item: ", log);
                PuntoVenta item = new PuntoVenta(
                        PV.getCodigoCliente(),
                        PV.getNombre(),
                        PV.getDocumento(),
                        PV.getDireccion(),
                        PV.getCodigoPunto(),
                        PV.getLatitud(),
                        PV.getLongitud()
                );
                mModels.add(item);
                adapter = new PuntoVentaRecyclerViewAdapter(activity, mModels,PuntoVentaFragment.this);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
                progress.dismiss();
            }
        }else {
            swipeRefreshLayout.setRefreshing(false);
            progress.dismiss();
            Snackbar.make(recyclerView, "No se encontraron Puntos de Venta", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("Buscar");
        searchView.setOnQueryTextListener(this);
    }

    public boolean onQueryTextChange(String query) {
        final List<PuntoVenta> filteredModelList = filter(mModels, query);
        adapter.animateTo(filteredModelList);
        recyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<PuntoVenta> filter(List<PuntoVenta> models, String query) {
        query = query.toLowerCase();

        final List<PuntoVenta> filteredModelList = new ArrayList<>();
        for (PuntoVenta model : models) {
            String text="";
            text = text + model.getCodigoPunto();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }*/




/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }
/*
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
