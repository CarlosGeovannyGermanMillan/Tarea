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
import com.example.carlosgerman.lavendimia.Modelos.Articulo;
import com.example.carlosgerman.lavendimia.Modelos.Cliente;
import com.example.carlosgerman.lavendimia.R;
import com.example.carlosgerman.lavendimia.Utilerias.ItemClickSupport;
import com.example.carlosgerman.lavendimia.Views.Activities.NuevoArticuloActivity;
import com.example.carlosgerman.lavendimia.Views.Activities.NuevoClienteActivity;
import com.example.carlosgerman.lavendimia.Views.Adapters.articuloAdapter;
import com.example.carlosgerman.lavendimia.Views.Adapters.clienteAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticulosFragment extends Fragment {
    private ArticulosFragment.OnFragmentInteractionListener mListener;
    public List<Articulo> listArticulos;
    private RecyclerView.Adapter ArticlesAdapter;
    public ProgressDialog mDialog;
    private RecyclerView.Adapter MyAdapter;
    StoreDB db;


    public @BindView(R.id.swipeRefreshLayout_Articulos) SwipeRefreshLayout mSwipeRefreshLayout;
    public @BindView(R.id.Fragment_rcv_Articulos) RecyclerView arti;

    public ArticulosFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ArticulosFragment newInstance(String param1, String param2) {
        ArticulosFragment fragment = new ArticulosFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @OnClick(R.id.fragmentArticulos_img_nuevo)
    void click_nuevo() {

        Intent intent = new Intent(getActivity(), NuevoArticuloActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_articulos, container, false);
        View view = inflater.inflate(R.layout.fragment_articulos, container, false);
        ButterKnife.bind(this,view);

        db = new StoreDB(getActivity());
        preferences.getInstance().Initialize(getActivity());

        this.listArticulos = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        arti.setHasFixedSize(true);
        arti.setLayoutManager(layoutManager);
        mostrarCargando("Cargando las Solicitudes del Usuario, espere.....", "Waiting......");
        cargarArticulos();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                cargarArticulos();
            }
        });

        return view;
    }
    public void cargarArticulos(){
        mSwipeRefreshLayout.setRefreshing(false);
        // int usr = Integer.parseInt(preferences.getInstance().getUserid());
        listArticulos = db.GetAllArticles();

        if(listArticulos.isEmpty()){
            // No existe historial para este usuario, mostrar boton solicitud
            //nohistorial.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
        }else {
            //nohistorial.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            MyAdapter = new articuloAdapter(listArticulos);
            arti.setAdapter(MyAdapter);

            ItemClickSupport.addTo(arti).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Articulo artic = listArticulos.get(position);
                    Intent intent = new Intent(getActivity(), ClientesFragment.class);

                    intent.putExtra("Clave",artic.getClave()+"");
                    intent.putExtra("Descripcion",artic.getDescripcion());
                    intent.putExtra("Modelo",artic.getModelo());
                    intent.putExtra("Precio",artic.getPrecio()+"");
                    intent.putExtra("Existencia",artic.getExistencia()+"");

                    startActivity(intent);
                }
            });
        }

        if (mDialog.isShowing())
            mDialog.dismiss();
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onResume() {
        super.onResume();
        cargarArticulos();
    }
}
