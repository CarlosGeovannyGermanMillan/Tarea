package com.example.carlosgerman.lavendimia.Views.Adapters;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlosgerman.lavendimia.DataBase.StoreDB;
import com.example.carlosgerman.lavendimia.Modelos.Articulo;
import com.example.carlosgerman.lavendimia.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class carritoAdapter extends RecyclerView.Adapter<carritoAdapter.MiCarritoAdapterViewHolder>{

    private List<Articulo> items;

    public carritoAdapter(List carrito) { items = carrito; }

    @NonNull
    @Override
    public MiCarritoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrito, parent, false);

        return new carritoAdapter.MiCarritoAdapterViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final MiCarritoAdapterViewHolder holder, final int position) {
        final Articulo produ = items.get(position);

        holder.precio.setText(produ.getPrecio()+"");
        holder.descripcion.setText(produ.getDescripcion());
        holder.cantidad.setText( produ.getExistencia() + "");
        holder.importe.setText((produ.getExistencia() * produ.getPrecio()) +"");

        holder.imgMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Articulo a = holder.db.GetArticleName(produ.getDescripcion());
                if (a.getExistencia() >= produ.getExistencia()+1){
                    produ.setExistencia(produ.getExistencia() + 1);
                    holder.db.UpdateArticleExistencia(produ);
                    notifyDataSetChanged();
                }
            }
        });
        holder.imgMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produ.setExistencia(produ.getExistencia() - 1);
                if (produ.getExistencia() > 0) {
                    holder.db.UpdateArticleExistencia(produ);
                    notifyDataSetChanged();
                } else {
                    holder.db.deleteArticleCarrito(produ.getClave()+"");
                    items.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, items.size());
                }
            }
        });

    }
    StoreDB db;
    double ActualizaEnganche(double importe){
        double porcentajeE = db.GetGeneralConfiguration().getEnganche();
        double Enganche =0.0;
        Enganche = (porcentajeE / 100) * importe;
        return Enganche;
    }
    double RegresaBonificacion(double enganche){
        double tasa= db.GetGeneralConfiguration().getTasaFinanciamiento();
        int plazo = db.GetGeneralConfiguration().getPlazoMaximo();

        double RBonificacion =  enganche * ((tasa*plazo)/100);
        return RBonificacion;
    }
    double total(){
        List<Articulo> array_list = new ArrayList();

        int a = db.numberOfRows();
        array_list = db.GetAllArticlesCarrito();
        double sum = 0;
        double total = 0;
        for (Articulo articulo : array_list) {
            sum = articulo.getExistencia() * articulo.getPrecio();
            total += sum;
            sum = 0;
        }
        return total;
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class MiCarritoAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemCarrito_imgv_plus) ImageView imgMas;
        @BindView(R.id.itemCarrito_imgv_minus) ImageView imgMenos;
        public @BindView(R.id.itemCarrito_txt_precio) TextView precio;
        public @BindView(R.id.itemCarrito_txt_descripcion) TextView descripcion;
        @BindView(R.id.itemCarrito_txt_cantidad) TextView cantidad;
        @BindView(R.id.itemCarrito_txt_importe) TextView importe;

        StoreDB db;


        public MiCarritoAdapterViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            db = new StoreDB(v.getContext());

        }

    }
}
