package com.fabrizziochavez.apropo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.fabrizziochavez.apropo.PuntoVentaFragment.OnListFragmentInteractionListener;
import com.fabrizziochavez.apropo.dummy.DummyContent.DummyItem;
import com.fabrizziochavez.apropo.model.PuntoVenta;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PuntoVentaRecyclerViewAdapter extends RecyclerView.Adapter<ListaPuntoVentaViewHolder> {

    //private final List<DummyItem> mValues;
    private List<PuntoVenta> mModels;
    private LayoutInflater inflater;
    private Context ctx;
    private PuntoVentaFragment fh;

    public PuntoVentaRecyclerViewAdapter(Context context,List<PuntoVenta> models,PuntoVentaFragment fragment)
    {
        inflater = LayoutInflater.from(context);
        mModels = new ArrayList<>(models);
        ctx = context;
        fh = fragment;
    }

    @Override
    public ListaPuntoVentaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_puntoventa,parent,false);
        return new ListaPuntoVentaViewHolder(view,ctx,fh);
    }

    @Override
    public void onBindViewHolder(ListaPuntoVentaViewHolder holder, int position) {
        PuntoVenta punto = mModels.get(position);
        holder.bind(punto);
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public void animateTo(List<PuntoVenta> models){
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }
    private void applyAndAnimateRemovals(List<PuntoVenta> newModels) {
        for (int i = mModels.size() - 1; i >= 0; i--) {
            final PuntoVenta model = mModels.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<PuntoVenta> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final PuntoVenta model = newModels.get(i);
            if (!mModels.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<PuntoVenta> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final PuntoVenta model = newModels.get(toPosition);
            final int fromPosition = mModels.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    private void removeItem(int position) {
        mModels.remove(position);
        notifyItemRemoved(position);
    }

    private void addItem(int position, PuntoVenta model) {
        mModels.add(position, model);
        notifyItemInserted(position);
    }

    private void moveItem(int fromPosition, int toPosition) {
        final PuntoVenta model = mModels.remove(fromPosition);
        mModels.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
    //  private final OnListFragmentInteractionListener mListener;

  //  public PuntoVentaRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
    //    mValues = items;
      //  mListener = listener;
//    }






/*
    @Override


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mContentView2;
        public final ImageView mImage;
        //public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mContentView2 = (TextView) view.findViewById(R.id.content2);
            mImage = (ImageView)view.findViewById(R.id.imageView);
        }


        }
*/

        //@Override
        //public String toString() {
          //  return super.toString() + " '" + mContentView.getText() + "'";
        //}
   // }
}
