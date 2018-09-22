package com.example.carlosgerman.lavendimia.Views.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carlosgerman.lavendimia.DataBase.StoreDB;
import com.example.carlosgerman.lavendimia.Modelos.Articulo;
import com.example.carlosgerman.lavendimia.Modelos.ConfiguracionGeneral;
import com.example.carlosgerman.lavendimia.R;
import com.example.carlosgerman.lavendimia.Utilerias.Validacion;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.example.carlosgerman.lavendimia.R;

public class ConfiguracionActivity extends BaseActivity {
    StoreDB db;
    @BindView(R.id.configuracion_input_enganche)
    EditText configuracion_input_enganche;
    @BindView(R.id.configuracion_input_plazomaximo)
    EditText configuracion_input_plazomaximo;
    @BindView(R.id.configuracion_input_tasafinanciamiento)
    EditText configuracion_input_tasafinanciamiento;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        db = new StoreDB(this);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        CargarConfiguracion();
    }

    void CargarConfiguracion(){
        ConfiguracionGeneral obj = db.GetGeneralConfiguration();
        configuracion_input_enganche.setText(obj.getEnganche()+"");
        configuracion_input_tasafinanciamiento.setText(obj.getTasaFinanciamiento()+"");
        configuracion_input_plazomaximo.setText(obj.getPlazoMaximo()+"");
    }

    @OnClick(R.id.configuracion_img_aceptar)
    void Registrarse(){
        if(hasValidation()) {
            ConfiguracionGeneral cg = new ConfiguracionGeneral();
            cg.setEnganche(Double.parseDouble(configuracion_input_enganche.getText().toString()));
            cg.setPlazoMaximo(Integer.parseInt(configuracion_input_plazomaximo.getText().toString()));
            cg.setTasaFinanciamiento(Double.parseDouble(configuracion_input_tasafinanciamiento.getText().toString()));
            cg.setId("12345");
            db.CreateGeneralConfiguration(cg);
            mostrarMensaje("Configuracion Guardada");
            this.finish();
        }else{
            mostrarMensajeError("Intente de nuevo");
        }
    }
    @OnClick(R.id.configuracion_img_cancelar)
    void Salir(){this.finish();}


    boolean hasValidation() {
        if (!Validacion.hasText(configuracion_input_enganche, "Ingrese Enganche"))
            return false;
        if (!Validacion.hasText(configuracion_input_plazomaximo, "Ingrese Plazo Maximo"))
            return false;
        if (!Validacion.hasText(configuracion_input_tasafinanciamiento, "Ingrese Tasa de Financiamiento"))
            return false;
        return true;

    }
}
