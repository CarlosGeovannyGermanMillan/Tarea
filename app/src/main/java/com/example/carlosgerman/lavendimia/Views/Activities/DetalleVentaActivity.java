package com.example.carlosgerman.lavendimia.Views.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.carlosgerman.lavendimia.DataBase.StoreDB;
import com.example.carlosgerman.lavendimia.Modelos.Articulo;
import com.example.carlosgerman.lavendimia.Modelos.DetalleVenta;
import com.example.carlosgerman.lavendimia.R;
import com.example.carlosgerman.lavendimia.Utilerias.ItemClickSupport;
import com.example.carlosgerman.lavendimia.Views.Adapters.articuloAdapter;
import com.example.carlosgerman.lavendimia.Views.Adapters.detalleVentaAdapter;
import com.example.carlosgerman.lavendimia.Views.Fragments.ArticulosFragment;
import com.example.carlosgerman.lavendimia.Views.Fragments.ClientesFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalleVentaActivity extends AppCompatActivity {

    private ArticulosFragment.OnFragmentInteractionListener mListener;
    public List<DetalleVenta> listArticulos;
    public ProgressDialog mDialog;
    private RecyclerView.Adapter MyAdapter;

    StoreDB db;
    int claveVenta;

    public @BindView(R.id.swipeRefreshLayout_DetalleVenta)
    SwipeRefreshLayout mSwipeRefreshLayout;
    public @BindView(R.id.DetalleVenta_rcv_DetalleVenta) RecyclerView arti;
    public @BindView(R.id.DetalleVenta_txt_folio)
    TextView DetalleVenta_txt_folio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_venta);
        ButterKnife.bind(this);
        db = new StoreDB(this);
        Bundle extras = getIntent().getExtras();
        claveVenta = Integer.parseInt(extras.getString("Folio"));
        DetalleVenta_txt_folio.setText(claveVenta+"");
        cargarArticulos();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarArticulos();
            }
        });
    }

    public void cargarArticulos(){
        mSwipeRefreshLayout.setRefreshing(false);
        listArticulos = db.ReturnDetails(claveVenta);

        if(listArticulos.isEmpty()){
            mSwipeRefreshLayout.setVisibility(View.GONE);
        }else {
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            MyAdapter = new detalleVentaAdapter(listArticulos);
            arti.setAdapter(MyAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarArticulos();
    }
}
