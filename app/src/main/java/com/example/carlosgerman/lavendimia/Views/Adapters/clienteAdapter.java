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
import com.example.carlosgerman.lavendimia.Modelos.Cliente;
import com.example.carlosgerman.lavendimia.R;
import com.example.carlosgerman.lavendimia.Views.Activities.NuevoClienteActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class clienteAdapter extends RecyclerView.Adapter<clienteAdapter.MyClientsAdapterViewHolder>{

    private List<Cliente> clientItemList;
    public clienteAdapter(List items){
        clientItemList = items;
    }

    @NonNull
    @Override
    public MyClientsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cliente,viewGroup,false);
        return new clienteAdapter.MyClientsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyClientsAdapterViewHolder Holder, int i) {
        final Cliente clientes = clientItemList.get(i);
        Holder.itemCliente_txt_clave.setText(clientes.getPrimary()+"");
        Holder.itemCliente_txt_nombre.setText(clientes.getNombre());
        Holder.itemCliente_txt_apepaterno.setText(clientes.getApellidoPaterno());
        Holder.itemCliente_txt_apematerno.setText(clientes.getApellidoMaterno());
        Holder.itemCliente_txt_rfc.setText(clientes.getRFC());

        Holder.imgEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NuevoClienteActivity.class);

                intent.putExtra("Clave",Holder.itemCliente_txt_clave.getText());
                intent.putExtra("Nombre",Holder.itemCliente_txt_nombre.getText());
                intent.putExtra("ApellidoPaterno",Holder.itemCliente_txt_apepaterno.getText());
                intent.putExtra("ApellidoMaterno",Holder.itemCliente_txt_apematerno.getText());
                intent.putExtra("RFC",Holder.itemCliente_txt_rfc.getText());
                v.getContext().startActivity(intent);
                //startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clientItemList.size();
    }

    public static class MyClientsAdapterViewHolder extends RecyclerView.ViewHolder {
        public @BindView(R.id.itemCliente_txt_clave) TextView itemCliente_txt_clave;
        public @BindView(R.id.itemCliente_txt_nombre) TextView itemCliente_txt_nombre;
        public @BindView(R.id.itemCliente_txt_apepaterno) TextView itemCliente_txt_apepaterno;
        public @BindView(R.id.itemCliente_txt_apematerno) TextView itemCliente_txt_apematerno;
        public @BindView(R.id.itemCliente_txt_rfc) TextView itemCliente_txt_rfc;
        public @BindView(R.id.itemCliente_imgv_edit) ImageView imgEditar;

        StoreDB db;
        public MyClientsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            // BASE DE DATOS
            db = new StoreDB(itemView.getContext());
        }
    }
}
