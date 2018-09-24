package com.example.carlosgerman.lavendimia.Views.Adapters;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.carlosgerman.lavendimia.DataBase.StoreDB;

import com.example.carlosgerman.lavendimia.Modelos.DetalleVenta;
import com.example.carlosgerman.lavendimia.R;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class detalleVentaAdapter extends RecyclerView.Adapter<detalleVentaAdapter.MyDetalleVentaAdapterViewHolder>{
    private List<DetalleVenta> ventaItemList;
    public detalleVentaAdapter(List items){
        ventaItemList = items;
    }

    @NonNull
    @Override
    public detalleVentaAdapter.MyDetalleVentaAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detalleventa,viewGroup,false);
        return new detalleVentaAdapter.MyDetalleVentaAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final detalleVentaAdapter.MyDetalleVentaAdapterViewHolder Holder, int i) {
        final DetalleVenta ventas = ventaItemList.get(i);
        //Holder.itemCliente_txt_nombre.setText(ventas.getNombre());
        Holder.itemDetalleventa_txt_cantidad.setText(ventas.getCantidad()+"");
        Holder.itemDetalleventa_txt_clavearticulo.setText(ventas.getClaveArticulo()+"");
    }


    @Override
    public int getItemCount() {
        return ventaItemList.size();
    }

    public static class MyDetalleVentaAdapterViewHolder extends RecyclerView.ViewHolder {

        public @BindView(R.id.itemDetalleventa_txt_clavearticulo) TextView itemDetalleventa_txt_clavearticulo;
        public @BindView(R.id.itemDetalleventa_txt_cantidad) TextView itemDetalleventa_txt_cantidad;
        //public @BindView(R.id.itemDetalleventa_txt_precioarticulo) TextView itemDetalleventa_txt_precioarticulo;


        StoreDB db;
        public MyDetalleVentaAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            db = new StoreDB(itemView.getContext());
        }
    }
}
