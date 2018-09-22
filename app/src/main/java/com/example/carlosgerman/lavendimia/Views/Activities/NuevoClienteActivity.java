package com.example.carlosgerman.lavendimia.Views.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carlosgerman.lavendimia.DataBase.StoreDB;
import com.example.carlosgerman.lavendimia.Modelos.Cliente;
import com.example.carlosgerman.lavendimia.R;
import com.example.carlosgerman.lavendimia.Utilerias.Validacion;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NuevoClienteActivity extends BaseActivity {

    StoreDB db;

    @BindView(R.id.nuevocliente_txt_clave)
    TextView nuevocliente_txt_clave;
    @BindView(R.id.nuevocliente_input_nombre)
    EditText nuevocliente_input_nombre;
    @BindView(R.id.nuevocliente_input_apepaterno)
    EditText nuevocliente_input_apepaterno;
    @BindView(R.id.nuevocliente_input_apematerno)
    EditText nuevocliente_input_apematerno;
    @BindView(R.id.nuevocliente_input_rfc)
    EditText nuevocliente_input_rfc;


    boolean detalle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        detalle=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_cliente);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        db = new StoreDB(this);
        if (extras!=null){
            nuevocliente_txt_clave.setText(extras.getString("Clave"));
            nuevocliente_input_nombre.setText(extras.getString("Nombre"));
            nuevocliente_input_apepaterno.setText(extras.getString("ApellidoPaterno"));
            nuevocliente_input_apematerno.setText(extras.getString("ApellidoMaterno"));
            nuevocliente_input_rfc.setText(extras.getString("RFC"));
            detalle=true;
        }else{
            int c = db.ReturnMaxClient()+1;
            nuevocliente_txt_clave.setText(c+"");
        }
    }


    @OnClick(R.id.nuevocliente_img_aceptar)
    void ClickAceptar(){
        if (detalle){
            Actualiza();
        }else{
            Registrarse();
        }
    }
    void Registrarse(){
        if(hasValidation()) {
            Cliente cliente = new Cliente();
            cliente.setNombre(nuevocliente_input_nombre.getText().toString());
            cliente.setApellidoPaterno(nuevocliente_input_apepaterno.getText().toString());
            cliente.setApellidoMaterno(nuevocliente_input_apematerno.getText().toString());
            cliente.setRFC(nuevocliente_input_rfc.getText().toString());
            db.CreateCliente(cliente);
            mostrarMensaje("Cliente Creado");
            this.finish();
        }else{
            mostrarMensajeError("Intente de nuevo");
        }
    }
    void Actualiza(){
        if(hasValidation()) {
            Cliente cliente = new Cliente();
            cliente.setPrimary(Integer.parseInt(nuevocliente_txt_clave.getText().toString()));
            cliente.setNombre(nuevocliente_input_nombre.getText().toString());
            cliente.setApellidoPaterno(nuevocliente_input_apepaterno.getText().toString());
            cliente.setApellidoMaterno(nuevocliente_input_apematerno.getText().toString());
            cliente.setRFC(nuevocliente_input_rfc.getText().toString());
            db.UpdateClient(cliente);
            mostrarMensaje("Cliente Actualizado");
            this.finish();
        }else{
            mostrarMensajeError("Intente de nuevo");
        }
    }
    @OnClick(R.id.nuevocliente_img_cancelar)
    void Cancelar(){
        nuevocliente_input_apepaterno.setText("");
        nuevocliente_input_nombre.setText("");
        nuevocliente_input_apematerno.setText("");
        nuevocliente_input_rfc.setText("");
        nuevocliente_input_nombre.setFocusable(true);
    }
    boolean hasValidation() {
        if (!Validacion.hasText(nuevocliente_input_nombre, "Ingrese Nombre"))
            return false;
        if (!Validacion.hasText(nuevocliente_input_apepaterno, "Ingrese Apellido Paterno"))
            return false;
        if (!Validacion.hasText(nuevocliente_input_apematerno, "Ingrese Apellido Materno"))
            return false;
        if (!Validacion.hasText(nuevocliente_input_rfc, "Ingrese RFC"))
            return false;
        return true;

    }


}
