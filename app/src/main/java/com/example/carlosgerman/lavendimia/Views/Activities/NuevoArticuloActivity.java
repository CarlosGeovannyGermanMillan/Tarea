package com.example.carlosgerman.lavendimia.Views.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carlosgerman.lavendimia.DataBase.StoreDB;
import com.example.carlosgerman.lavendimia.Modelos.Articulo;
import com.example.carlosgerman.lavendimia.R;
import com.example.carlosgerman.lavendimia.Utilerias.Validacion;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NuevoArticuloActivity extends BaseActivity {
    StoreDB db;

    @BindView(R.id.nuevoarticulo_txt_clave)
    TextView nuevoarticulo_txt_clave;
    @BindView(R.id.nuevoarticulo_input_descripcion)
    EditText nuevoarticulo_input_descripcion;
    @BindView(R.id.nuevoarticulo_input_existencia)
    EditText nuevoarticulo_input_existencia;
    @BindView(R.id.nuevoarticulo_input_Modelo)
    EditText nuevoarticulo_input_Modelo;
    @BindView(R.id.nuevoarticulo_input_Precio)
    EditText nuevoarticulo_input_Precio;


    boolean detalle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        detalle=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_articulo);
        ButterKnife.bind(this);
        db = new StoreDB(this);
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            nuevoarticulo_txt_clave.setText(extras.getString("Clave"));
            nuevoarticulo_input_descripcion.setText(extras.getString("Descripcion"));
            nuevoarticulo_input_Modelo.setText(extras.getString("Modelo"));
            nuevoarticulo_input_Precio.setText(extras.getString("Precio"));
            nuevoarticulo_input_existencia.setText(extras.getString("Existencia"));
            detalle=true;
        }else{
            int c = db.ReturnMaxArticle()+1;
            nuevoarticulo_txt_clave.setText(c+"");
        }

    }

    @OnClick(R.id.nuevoarticulo_img_aceptar)
    void ClickAceptar(){
        if (detalle){
            Actualiza();
        }else{
            Registrarse();
        }
    }
    void Registrarse(){
        if(hasValidation()) {
            Articulo articulo = new Articulo();
            articulo.setDescripcion(nuevoarticulo_input_descripcion.getText().toString());
            articulo.setModelo(nuevoarticulo_input_Modelo.getText().toString());
            articulo.setPrecio(Double.parseDouble(nuevoarticulo_input_Precio.getText().toString()));
            articulo.setExistencia(Integer.parseInt(nuevoarticulo_input_existencia.getText().toString()));
            db.CreateArticulo(articulo);
            mostrarMensaje("Articulo Creado");
            this.finish();
        }else{
            mostrarMensajeError("Intente de nuevo");
        }
    }
    void Actualiza(){
        if(hasValidation()) {
            Articulo articulo = new Articulo();
            articulo.setClave(Integer.parseInt(nuevoarticulo_txt_clave.getText().toString()));
            articulo.setDescripcion(nuevoarticulo_input_descripcion.getText().toString());
            articulo.setModelo(nuevoarticulo_input_Modelo.getText().toString());
            articulo.setPrecio(Double.parseDouble(nuevoarticulo_input_Precio.getText().toString()));
            articulo.setExistencia(Integer.parseInt(nuevoarticulo_input_existencia.getText().toString()));
            db.UpdateArticle(articulo);
            mostrarMensaje("Articulo Actualizado");
            this.finish();
        }else{
            mostrarMensajeError("Intente de nuevo");
        }
    }
    @OnClick(R.id.nuevoarticulo_img_cancelar)
    void Cancelar(){
        nuevoarticulo_input_descripcion.setText("");
        nuevoarticulo_input_existencia.setText("");
        nuevoarticulo_input_Modelo.setText("");
        nuevoarticulo_input_Precio.setText("");
        nuevoarticulo_input_descripcion.setFocusable(true);
    }
    boolean hasValidation() {
        if (!Validacion.hasText(nuevoarticulo_input_descripcion, "Ingrese Descripcion"))
            return false;
        if (!Validacion.hasText(nuevoarticulo_input_Modelo, "Ingrese Modelo"))
            return false;
        if (!Validacion.hasText(nuevoarticulo_input_Precio, "Ingrese Precio"))
            return false;
        if (!Validacion.hasText(nuevoarticulo_input_existencia, "Ingrese Existencia"))
            return false;
        return true;

    }

}
