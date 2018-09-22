package com.example.carlosgerman.lavendimia.Views.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlosgerman.lavendimia.DataBase.StoreDB;
import com.example.carlosgerman.lavendimia.Modelos.Articulo;
import com.example.carlosgerman.lavendimia.Modelos.Cliente;
import com.example.carlosgerman.lavendimia.R;
import com.example.carlosgerman.lavendimia.Utilerias.ItemClickSupport;
import com.example.carlosgerman.lavendimia.Utilerias.Validacion;
import com.example.carlosgerman.lavendimia.Views.Adapters.articuloAdapter;
import com.example.carlosgerman.lavendimia.Views.Adapters.carritoAdapter;
import com.example.carlosgerman.lavendimia.Views.Fragments.ArticulosFragment;
import com.example.carlosgerman.lavendimia.Views.Fragments.ClientesFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NuevaVentaActivity extends BaseActivity {
    public List<Object> list;
    private RecyclerView.Adapter mAdapter;
    public ProgressDialog mDialog;


    public @BindView(R.id.swipeRefreshLayout_Ventas)
    SwipeRefreshLayout mSwipeRefreshLayout;
    public @BindView(R.id.Fragment_rcv_Ventas)
    RecyclerView carrito;


    StoreDB db;
    //@BindView(R.id.nuevav) TextView nuevaventa_txt_folio;
    @BindView(R.id.nuevaventa_txt_folio)
    TextView nuevavente_txt_folio;
    @BindView(R.id.nuevocliente_input_nombre)
    AutoCompleteTextView nuevocliente_input_nombre;
    @BindView(R.id.nuevocliente_input_articulo)
    AutoCompleteTextView nuevocliente_input_articulo;
    @BindView(R.id.ventaCliente_ly_cliente)
    LinearLayout ventaCliente_ly_cliente;
    @BindView(R.id.ventaCliente_txt_clave)
    TextView ventaCliente_txt_clave;
    @BindView(R.id.ventaCliente_txt_nombre)
    TextView ventaCliente_txt_nombre;
    @BindView(R.id.ventaCliente_txt_apepaterno)
    TextView ventaCliente_txt_apepaterno;
    @BindView(R.id.ventaCliente_txt_apepamaterno)
    TextView ventaCliente_txt_apepamaterno;
    @BindView(R.id.ventaCliente_txt_rfc)
    TextView ventaCliente_txt_rfc;


    @BindView(R.id.ventaArticulos_ly_articulos)
    LinearLayout ventaArticulos_ly_articulos;
    @BindView(R.id.ventaArticulos_txt_enganche)
    TextView ventaArticulos_txt_enganche;
    @BindView(R.id.ventaArticulos_txt_bonificacion)
    TextView ventaArticulos_txt_bonificacion;
    @BindView(R.id.ventaArticulos_txt_total)
    TextView ventaArticulos_txt_total;

    @BindView(R.id.ventaPlazo_ly_plazo)
    LinearLayout ventaPlazo_ly_plazo;


    @BindView(R.id.nuevaventa_img_cliente)
    ImageView nuevaventa_img_cliente;
    @BindView(R.id.nuevaventa_img_venta)
    ImageView nuevaventa_img_venta;
    @BindView(R.id.nuevaventa_img_plazo)
    ImageView nuevaventa_img_plazo;
    @BindView(R.id.nuevaventa_ly_buscar)
    LinearLayout nuevaventa_ly_buscar;


    public NuevaVentaActivity() {
        // Required empty public constructor
    }


    //String[] a = {"Carlos","Cesar","Carrillo","Peres","Josefina","Jose"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_venta);

        ButterKnife.bind(this);
        db = new StoreDB(this);

        this.list = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        carrito.setHasFixedSize(true);
        carrito.setLayoutManager(layoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
        ConsultaCarrito();

        int c = db.ReturnMaxVenta() + 1;
        nuevavente_txt_folio.setText(c + "");

    }

    @Override
    public void onResume() {
        super.onResume();
        String[] nombres = RegresaClientes();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.select_dialog_item, nombres);
        nuevocliente_input_nombre.setThreshold(1);
        nuevocliente_input_nombre.setAdapter(adapter);


        String[] articu = RegresaArticulos();
        ArrayAdapter adapterArti = new ArrayAdapter(this, android.R.layout.select_dialog_item, articu);
        nuevocliente_input_articulo.setThreshold(1);
        nuevocliente_input_articulo.setAdapter(adapterArti);

        ConsultaCarrito();
    }

    String[] RegresaClientes() {
        List<String> c = db.GetAllNameClients();
        int n = c.size();
        int i = 0;
        String[] name = new String[n];
        for (String cl : c) {
            name[i] = cl;
            i++;
        }
        return name;
    }

    String[] RegresaArticulos() {
        List<String> c = db.GetAllNameArticles();
        int n = c.size();
        int i = 0;
        String[] name = new String[n];
        for (String cl : c) {
            name[i] = cl;
            i++;
        }
        return name;
    }

    @SuppressLint("ResourceAsColor")
    @OnClick(R.id.nuevaventa_img_cliente)
    void ClickCliente() {
        ventaCliente_ly_cliente.setVisibility(LinearLayout.VISIBLE);
        ventaArticulos_ly_articulos.setVisibility(LinearLayout.GONE);
        ventaPlazo_ly_plazo.setVisibility(LinearLayout.GONE);
        nuevaventa_ly_buscar.setVisibility(LinearLayout.VISIBLE);
        nuevaventa_img_cliente.setBackgroundResource(android.R.color.holo_green_dark);
        nuevaventa_img_venta.setBackgroundResource(android.R.color.white);
        nuevaventa_img_plazo.setBackgroundResource(android.R.color.white);
    }

    @SuppressLint("ResourceAsColor")
    @OnClick(R.id.nuevaventa_img_venta)
    void ClickArticulos() {
        ventaCliente_ly_cliente.setVisibility(LinearLayout.GONE);
        ventaArticulos_ly_articulos.setVisibility(LinearLayout.VISIBLE);
        ventaPlazo_ly_plazo.setVisibility(LinearLayout.GONE);
        nuevaventa_ly_buscar.setVisibility(LinearLayout.VISIBLE);
        nuevaventa_img_cliente.setBackgroundResource(android.R.color.white);
        nuevaventa_img_venta.setBackgroundResource(android.R.color.holo_green_dark);
        nuevaventa_img_plazo.setBackgroundResource(android.R.color.white);
    }

    @SuppressLint("ResourceAsColor")
    @OnClick(R.id.nuevaventa_img_plazo)
    void ClickPlazo() {
        ventaCliente_ly_cliente.setVisibility(LinearLayout.GONE);
        ventaArticulos_ly_articulos.setVisibility(LinearLayout.GONE);
        ventaPlazo_ly_plazo.setVisibility(LinearLayout.VISIBLE);
        nuevaventa_ly_buscar.setVisibility(LinearLayout.GONE);
        nuevaventa_img_cliente.setBackgroundResource(android.R.color.white);
        nuevaventa_img_venta.setBackgroundResource(android.R.color.white);
        nuevaventa_img_plazo.setBackgroundResource(android.R.color.holo_green_dark);
    }

    @OnClick(R.id.nuevaventa_img_buscararticulo)
    void ClickBuscarArticulo() {
        if (hasValidation()) {
            Articulo a = db.GetArticleName(nuevocliente_input_articulo.getText().toString());
            db.CreateArticuloCarrito(a);

        } else

        {
            mostrarMensajeError("Intente de nuevo");
        }

    }

    void ConsultaCarrito() {
        List<Articulo> array_list = new ArrayList();

        int a = db.numberOfRows();
        array_list = db.GetAllArticlesCarrito();
        mAdapter = new carritoAdapter(array_list);
        double sum = 0;
        double total = 0;
        for (Articulo articulo : array_list) {
            sum = articulo.getExistencia() * articulo.getPrecio();
            total += sum;
            sum = 0;
        }

        ventaArticulos_txt_total.setText(total + "");
        carrito.setAdapter(mAdapter);
        DefaultItemAnimator animator = new DefaultItemAnimator() {
            @Override
            public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                return true;
            }
        };
        carrito.setItemAnimator(animator);

    }
    void refreshItems() {
        List<Articulo> array_list = new ArrayList();

        int a = db.numberOfRows();
        array_list = db.GetAllArticlesCarrito();
        mAdapter = new carritoAdapter(array_list);
        double sum = 0;
        double total = 0;
        for (Articulo articulo : array_list) {
            sum = articulo.getExistencia() * articulo.getPrecio();
            total += sum;
            sum = 0;
        }

        ventaArticulos_txt_total.setText(total + "");
        carrito.setAdapter(mAdapter);
        DefaultItemAnimator animator = new DefaultItemAnimator() {
            @Override
            public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                return true;
            }
        };
        carrito.setItemAnimator(animator);
        mSwipeRefreshLayout.setRefreshing(false);

    }


    @OnClick(R.id.nuevaventa_img_buscarcliente)
    void ClickBuscarCliente() {
        if (hasValidation()) {
            Cliente c = db.GetClientName(nuevocliente_input_nombre.getText().toString());
            ventaCliente_txt_nombre.setText(c.getNombre());
            ventaCliente_txt_clave.setText(c.getPrimary() + "");
            ventaCliente_txt_apepaterno.setText(c.getApellidoPaterno());
            ventaCliente_txt_apepamaterno.setText(c.getApellidoMaterno());
            ventaCliente_txt_rfc.setText(c.getRFC());

        } else

        {
            mostrarMensajeError("Intente de nuevo");
        }

    }


    boolean hasValidation() {
        if (!Validacion.hasText(nuevocliente_input_nombre, "Ingrese Cliente"))
            return false;
        if (!Validacion.hasText(nuevocliente_input_articulo, "Ingrese Articulo"))
            return false;
        return true;

    }
}
