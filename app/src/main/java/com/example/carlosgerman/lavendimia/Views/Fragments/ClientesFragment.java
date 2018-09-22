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
import com.example.carlosgerman.lavendimia.R;
import com.example.carlosgerman.lavendimia.Utilerias.ItemClickSupport;
import com.example.carlosgerman.lavendimia.Views.Activities.NuevoClienteActivity;
import com.example.carlosgerman.lavendimia.Views.Adapters.clienteAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ClientesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    public List<Cliente> listClientes;
    private RecyclerView.Adapter ClientAdapter;
    public ProgressDialog mDialog;
    private RecyclerView.Adapter MyAdapter;
    StoreDB db;


    //@BindView(R.id.)
    public @BindView(R.id.swipeRefreshLayout_Clientes) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.Fragment_rcv_Clientes)
    RecyclerView cli;


    public ClientesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ClientesFragment newInstance(String param1, String param2) {
        ClientesFragment fragment = new ClientesFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    @OnClick(R.id.fragmentClientes_img_nuevo)
    void click_nuevo() {

            Intent intent = new Intent(getActivity(), NuevoClienteActivity.class);
            startActivity(intent);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clientes, container, false);
        ButterKnife.bind(this,view);

        db = new StoreDB(getActivity());
        preferences.getInstance().Initialize(getActivity());

        this.listClientes = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        cli.setHasFixedSize(true);
        cli.setLayoutManager(layoutManager);
        //mostrarCargando("Cargando clientes, espere.....", "Waiting......");
        cargarClientes();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                cargarClientes();
            }
        });

        return view;

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

    public void cargarClientes(){

        mSwipeRefreshLayout.setRefreshing(false);
        listClientes = db.GetAllClients();

        if(listClientes.isEmpty()){

            //nohistorial.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
        }else {
            //nohistorial.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            MyAdapter = new clienteAdapter(listClientes);
            cli.setAdapter(MyAdapter);

            ItemClickSupport.addTo(cli).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Cliente clie = listClientes.get(position);
                    Intent intent = new Intent(getActivity(), ClientesFragment.class);

                    intent.putExtra("Clave",clie.getPrimary());
                    intent.putExtra("Nombre",clie.getNombre());
                    intent.putExtra("ApellidoPaterno",clie.getApellidoPaterno());
                    intent.putExtra("ApellidoMaterno",clie.getApellidoMaterno());
                    intent.putExtra("RFC",clie.getRFC());

                    startActivity(intent);
                }
            });
        }
        //if (mDialog.isShowing())
        //mDialog.dismiss();

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarClientes();
    }
}
