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
import com.example.carlosgerman.lavendimia.Modelos.Articulo;
import com.example.carlosgerman.lavendimia.R;
import com.example.carlosgerman.lavendimia.Views.Activities.NuevoArticuloActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class articuloAdapter extends RecyclerView.Adapter<articuloAdapter.MyArticuloAdapterViewHolder>{
    private List<Articulo> articleItemList;
    public articuloAdapter(List items){
        articleItemList = items;
    }


    @Override
    public articuloAdapter.MyArticuloAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_articulo,viewGroup,false);
        return new articuloAdapter.MyArticuloAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyArticuloAdapterViewHolder Holder, int i) {
        final Articulo articulos = articleItemList.get(i);
        Holder.itemArticulo_txt_clave.setText(articulos.getClave()+"");
        Holder.itemArticulo_txt_descripcion.setText(articulos.getDescripcion());
        Holder.itemArticulo_txt_existencia.setText(articulos.getExistencia()+"");
        Holder.itemArticulo_txt_precio.setText(articulos.getPrecio().toString()+"");

        Holder.imgEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NuevoArticuloActivity.class);
                intent.putExtra("Clave",Holder.itemArticulo_txt_clave.getText());
                intent.putExtra("Descripcion",Holder.itemArticulo_txt_descripcion.getText());
                intent.putExtra("Precio",Holder.itemArticulo_txt_precio.getText());
                intent.putExtra("Existencia",Holder.itemArticulo_txt_existencia.getText());
                intent.putExtra("Modelo",Holder.itemArticulo_txt_modelo.getText());
                v.getContext().startActivity(intent);
                //startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articleItemList.size();
    }

    public static class MyArticuloAdapterViewHolder extends RecyclerView.ViewHolder {

        public @BindView(R.id.itemArticulo_txt_descripcion) TextView itemArticulo_txt_descripcion;
        public @BindView(R.id.itemArticulo_txt_clave) TextView itemArticulo_txt_clave;
        public @BindView(R.id.itemArticulo_txt_existencia) TextView itemArticulo_txt_existencia;
        public @BindView(R.id.itemArticulo_txt_precio) TextView itemArticulo_txt_precio;
        public @BindView(R.id.itemArticulo_txt_modelo) TextView itemArticulo_txt_modelo;
        public @BindView(R.id.itemArticulo_imgv_edit) ImageView imgEditar;

        StoreDB db;

        public MyArticuloAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            db = new StoreDB(itemView.getContext());
        }
    }
}
