package com.example.carlosgerman.lavendimia.Views.Adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carlosgerman.lavendimia.DataBase.StoreDB;

import com.example.carlosgerman.lavendimia.Modelos.Venta;
import com.example.carlosgerman.lavendimia.R;
import com.example.carlosgerman.lavendimia.Views.Activities.DetalleVentaActivity;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ventaAdapter extends RecyclerView.Adapter<ventaAdapter.MyVentasAdapterViewHolder>{
    private List<Venta> ventaItemList;
    public ventaAdapter(List items){
        ventaItemList = items;
    }

    @NonNull
    @Override
    public ventaAdapter.MyVentasAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_venta,viewGroup,false);
        return new ventaAdapter.MyVentasAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ventaAdapter.MyVentasAdapterViewHolder Holder, int i) {
        final Venta ventas = ventaItemList.get(i);
        //Holder.itemCliente_txt_nombre.setText(ventas.getNombre());
        Holder.itemVenta_txt_folio.setText(ventas.getFolio()+"");
        Holder.itemVenta_txt_fecha.setText(ventas.getFecha());
        Holder.itemVenta_txt_total.setText(ventas.getTotal()+"");

        Holder.imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetalleVentaActivity.class);

                intent.putExtra("ClaveVenta",Holder.itemVenta_txt_folio.getText());
                v.getContext().startActivity(intent);
                //startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ventaItemList.size();
    }

    public static class MyVentasAdapterViewHolder extends RecyclerView.ViewHolder {

        public @BindView(R.id.itemVenta_txt_folio) TextView itemVenta_txt_folio;
        public @BindView(R.id.itemVenta_txt_nombre) TextView itemVenta_txt_nombre;
        public @BindView(R.id.itemVenta_txt_apepaterno) TextView itemVenta_txt_apepaterno;
        public @BindView(R.id.itemVenta_txt_apematerno) TextView itemVenta_txt_apematerno;
        public @BindView(R.id.itemVenta_txt_fecha) TextView itemVenta_txt_fecha;
        public @BindView(R.id.itemVenta_txt_total) TextView itemVenta_txt_total;
        public @BindView(R.id.itemVenta_imgv_info) ImageView imgInfo;


        StoreDB db;
        public MyVentasAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            // BASE DE DATOS
            db = new StoreDB(itemView.getContext());
        }
    }
}
