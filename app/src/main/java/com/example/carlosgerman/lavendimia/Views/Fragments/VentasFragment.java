package com.example.carlosgerman.lavendimia.Views.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carlosgerman.lavendimia.Aplication.preferences;
import com.example.carlosgerman.lavendimia.DataBase.StoreDB;
import com.example.carlosgerman.lavendimia.Modelos.Cliente;
import com.example.carlosgerman.lavendimia.Modelos.Venta;
import com.example.carlosgerman.lavendimia.R;
import com.example.carlosgerman.lavendimia.Utilerias.ItemClickSupport;
import com.example.carlosgerman.lavendimia.Views.Activities.NuevaVentaActivity;
import com.example.carlosgerman.lavendimia.Views.Adapters.clienteAdapter;
import com.example.carlosgerman.lavendimia.Views.Adapters.ventaAdapter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VentasFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    public List<Venta> listVentas;
    private RecyclerView.Adapter ventaAdapter;
    public ProgressDialog mDialog;
    private RecyclerView.Adapter MyAdapter;
    StoreDB db;

    public @BindView(R.id.swipeRefreshLayout_Ventas) SwipeRefreshLayout mSwipeRefreshLayout;
    public @BindView(R.id.Fragment_rcv_Ventas) RecyclerView ven;

    public VentasFragment() {
        // Required empty public constructor
    }

    public static VentasFragment newInstance(String param1, String param2) {
        VentasFragment fragment = new VentasFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ventas, container, false);
        ButterKnife.bind(this,view);

        db = new StoreDB(getActivity());
        preferences.getInstance().Initialize(getActivity());

        this.listVentas = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ven.setHasFixedSize(true);
        ven.setLayoutManager(layoutManager);
        //mostrarCargando("Cargando clientes, espere.....", "Waiting......");
        cargarVentas();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                cargarVentas();
            }
        });

        return view;
    }
    public void mostrarCargando(String title, String message) {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
        mDialog = new ProgressDialog(getActivity(), R.style.Theme_MyDialog);
        mDialog.setTitle(title);
        mDialog.setMessage(message);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
    }

    public void cargarVentas(){
        mSwipeRefreshLayout.setRefreshing(false);
        listVentas = db.GetAllVentas();

        if(listVentas.isEmpty()){

            //nohistorial.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
        }else {
            //nohistorial.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            MyAdapter = new ventaAdapter(listVentas);
            ven.setAdapter(MyAdapter);

            ItemClickSupport.addTo(ven).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Venta vent = listVentas.get(position);
                    Intent intent = new Intent(getActivity(), VentasFragment.class);

                    intent.putExtra("Folio",vent.getFolio());

                    startActivity(intent);
                }
            });
        }

       // if (mDialog.isShowing())
         //   mDialog.dismiss();
    }

    @OnClick(R.id.fragmentVentas_img_nuevo)
    void click_nuevo() {

        Intent intent = new Intent(getActivity(), NuevaVentaActivity.class);
        startActivity(intent);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarVentas();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
