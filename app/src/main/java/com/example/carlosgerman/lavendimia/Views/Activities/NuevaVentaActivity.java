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
import android.widget.RadioButton;
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



    // Binding natural para opcion Tab3 (Ventas)
    @BindView(R.id.nuevaventa_rbtn3_venta)
    RadioButton rbtn3venta;
    @BindView(R.id.nuevaventa_rbtn6_venta)
    RadioButton rbtn6venta;
    @BindView(R.id.nuevaventa_rbtn9_venta)
    RadioButton rbtn9venta;
    @BindView(R.id.nuevaventa_rbtn12_venta)
    RadioButton rbtn12venta;

    @BindView(R.id.nuevaventa_txt3_ahorro)
    TextView txt3_ahorro;
    @BindView(R.id.nuevaventa_txt6_ahorro)
    TextView txt6_ahorro;
    @BindView(R.id.nuevaventa_txt9_ahorro)
    TextView txt9_ahorro;
    @BindView(R.id.nuevaventa_txt12_ahorro)
    TextView txt12_ahorro;

    @BindView(R.id.nuevaventa_txt3_saldo)
    TextView txt3_saldo;
    @BindView(R.id.nuevaventa_txt6_saldo)
    TextView txt6_saldo;
    @BindView(R.id.nuevaventa_txt9_saldo)
    TextView txt9_saldo;
    @BindView(R.id.nuevaventa_txt12_saldo)
    TextView txt12_saldo;

    @BindView(R.id.nuevaventa_txt3_total)
    TextView txt3_total;
    @BindView(R.id.nuevaventa_txt6_total)
    TextView txt6_total;
    @BindView(R.id.nuevaventa_txt9_total)
    TextView txt9_total;
    @BindView(R.id.nuevaventa_txt12_total)
    TextView txt12_total;



    public NuevaVentaActivity() {
        // Required empty public constructor
    }


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
        String[] nombres = RegresaClientes();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.select_dialog_item, nombres);
        nuevocliente_input_nombre.setThreshold(1);
        nuevocliente_input_nombre.setAdapter(adapter);


        String[] articu = RegresaArticulos();
        ArrayAdapter adapterArti = new ArrayAdapter(this, android.R.layout.select_dialog_item, articu);
        nuevocliente_input_articulo.setThreshold(1);
        nuevocliente_input_articulo.setAdapter(adapterArti);


        //Articulo cant = db.GetArticleName(nuevocliente_input_articulo.getText().toString());
    }

    @Override
    public void onResume() {
        super.onResume();


        ConsultaCarrito();
        refreshItems();
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
        if (Validacion.hasText(nuevocliente_input_nombre, "Ingrese Cliente")) {
            ventaCliente_ly_cliente.setVisibility(LinearLayout.GONE);
            ventaArticulos_ly_articulos.setVisibility(LinearLayout.VISIBLE);
            ventaPlazo_ly_plazo.setVisibility(LinearLayout.GONE);
            nuevaventa_ly_buscar.setVisibility(LinearLayout.VISIBLE);
            nuevaventa_img_cliente.setBackgroundResource(android.R.color.white);
            nuevaventa_img_venta.setBackgroundResource(android.R.color.holo_green_dark);
            nuevaventa_img_plazo.setBackgroundResource(android.R.color.white);
        }
    }

    @SuppressLint("ResourceAsColor")
    @OnClick(R.id.nuevaventa_img_plazo)
    void ClickPlazo() {
        if (Validacion.hasText(nuevocliente_input_articulo, "Ingrese Articulo")) {
            ventaCliente_ly_cliente.setVisibility(LinearLayout.GONE);
            ventaArticulos_ly_articulos.setVisibility(LinearLayout.GONE);
            ventaPlazo_ly_plazo.setVisibility(LinearLayout.VISIBLE);
            nuevaventa_ly_buscar.setVisibility(LinearLayout.GONE);
            nuevaventa_img_cliente.setBackgroundResource(android.R.color.white);
            nuevaventa_img_venta.setBackgroundResource(android.R.color.white);
            nuevaventa_img_plazo.setBackgroundResource(android.R.color.holo_green_dark);

            calculoPlazos();
        }
    }

    @OnClick(R.id.nuevaventa_img_buscararticulo)
    void ClickBuscarArticulo() {

        if (Validacion.hasText(nuevocliente_input_articulo, "Ingrese Articulo")) {
            Articulo a = db.GetArticleName(nuevocliente_input_articulo.getText().toString());
            Articulo e = db.GetArticleNameCarrito(nuevocliente_input_articulo.getText().toString());


            if (e.getClave() == -1){
                a.setExistencia(1);
                db.CreateArticuloCarrito(a);
                mostrarMensajeError("Articulo Agregado");
           }else{
                if (a.getExistencia() >= (e.getExistencia()+1)){
                    e.setExistencia(e.getExistencia()+1);
                    db.UpdateArticleExistencia(e);
                    mostrarMensajeError("Articulo Agregado");
                }else{
                    mostrarMensajeError("Articulos Insuficientes");
                }
            }

            refreshItems();
        } else

        {
            mostrarMensajeError("Intente de nuevo");
        }

    }



    void ConsultaCarrito() {
        List<Articulo> array_list = new ArrayList();
        nuevocliente_input_articulo.clearFocus();
        nuevocliente_input_articulo.clearListSelection();

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
        ventaArticulos_txt_enganche.setText(ActualizaEnganche(total)+"");
        ventaArticulos_txt_bonificacion.setText(RegresaBonificacion(ActualizaEnganche(total))+"");
        carrito.setAdapter(mAdapter);

    }

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

        double totalAdeudo = regresaaTotalAdeudo(total,ActualizaEnganche(total),RegresaBonificacion(total));

        ventaArticulos_txt_total.setText(totalAdeudo + "");
        carrito.setAdapter(mAdapter);
        ventaArticulos_txt_enganche.setText(ActualizaEnganche(total)+"");
        ventaArticulos_txt_bonificacion.setText(RegresaBonificacion(ActualizaEnganche(total))+"");
        mSwipeRefreshLayout.setRefreshing(false);

    }

    double regresaaTotalAdeudo(double importe, double Enganche, double Bonificacion){
        double totalAdeudo = 0.0;

        totalAdeudo = importe - Enganche - Bonificacion ;

        return totalAdeudo;
    }


    @OnClick(R.id.nuevaventa_img_buscarcliente)
    void ClickBuscarCliente() {
        if (Validacion.hasText(nuevocliente_input_nombre, "Ingrese Cliente")) {
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

    void LimpiarRadioButtons(){
        rbtn3venta.setSelected(false);
        rbtn6venta.setSelected(false);
        rbtn9venta.setSelected(false);
        rbtn12venta.setSelected(false);

    }

    double regresaImporte(){
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
    double regresaPrecioContado(){
        double precioContado = 0.0;
        double totalAdeu= regresaaTotalAdeudo(regresaImporte(),ActualizaEnganche(regresaImporte()),RegresaBonificacion(regresaImporte()));

        precioContado = totalAdeu / (1 + ((db.GetGeneralConfiguration().getTasaFinanciamiento() * db.GetGeneralConfiguration().getPlazoMaximo())/100));
        return precioContado;
    }

    void calculoPlazos(){
        // Aqui hacemos el calculo de cada uno de los formatos
        double tasa = db.GetGeneralConfiguration().getTasaFinanciamiento();


        double totalPagar3 = regresaPrecioContado() * (1+(tasa*3)/100);
        double totalPagar6 = regresaPrecioContado() * (1+(tasa*6)/100);
        double totalPagar9 = regresaPrecioContado() * (1+(tasa*9)/100);
        double totalPagar12 = regresaPrecioContado() * (1+(tasa*12)/100);

        double importeAbono3 = totalPagar3 / 3;
        double importeAbono6 = totalPagar6 / 6;
        double importeAbono9 = totalPagar9 / 9;
        double importeAbono12 = totalPagar12 / 12;

        double totalAdeudo = regresaaTotalAdeudo(regresaImporte(),ActualizaEnganche(regresaImporte()),RegresaBonificacion(regresaImporte()));

        double importeAhorro3 = totalAdeudo - totalPagar3;
        double importeAhorro6 = totalAdeudo - totalPagar6;
        double importeAhorro9 = totalAdeudo - totalPagar9;
        double importeAhorro12 = totalAdeudo - totalPagar12;


        //double importeAhorro3 = totalPagar3 - t

        PantallaPlazo(importeAbono3,totalPagar3,importeAhorro3,importeAbono6,totalPagar6,importeAhorro6,importeAbono9,totalPagar9,importeAhorro9,importeAbono12,totalPagar12,importeAhorro12);
    }

    void PantallaPlazo(double saldo3, double total3,double ahorro3, double saldo6, double total6,double ahorro6,double saldo9, double total9,double ahorro9, double saldo12, double total12,double ahorro12){
        // 3 meses
        txt3_saldo.setText(saldo3+"");
        txt3_ahorro.setText(ahorro3+"");
        txt3_total.setText(total3+"");
        // 6 meses
        txt6_saldo.setText(saldo6+"");
        txt6_ahorro.setText(ahorro6+"");
        txt6_total.setText(total6+"");
        // 9 meses
        txt9_saldo.setText(saldo9+"");
        txt9_ahorro.setText(ahorro9+"");
        txt9_total.setText(total9+"");
        // 12 meses
        txt12_saldo.setText(saldo12+"");
        txt12_ahorro.setText(ahorro12+"");
        txt12_total.setText(total12+"");
    }
}
