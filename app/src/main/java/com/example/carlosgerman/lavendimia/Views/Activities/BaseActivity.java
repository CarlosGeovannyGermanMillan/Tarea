package com.example.carlosgerman.lavendimia.Views.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.carlosgerman.lavendimia.Interfaces.IBaseView;
import com.example.carlosgerman.lavendimia.Presentadores.BasePresenter;
import com.example.carlosgerman.lavendimia.R;


public class BaseActivity extends AppCompatActivity implements IBaseView {
    public ProgressDialog mDialog;
    public Context mContext;
    public BasePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getApplicationContext();
        this.presenter = new BasePresenter();
    }

    @Override
    public void mostrarCargando(String title, String message) {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
        mDialog = new ProgressDialog(this, R.style.Theme_MyDialog);
        mDialog.setTitle(title);
        mDialog.setMessage(message);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    @Override
    public void mostrarMensajeError(String mensaje) {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
        Toast.makeText(mContext, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void mostrarMensaje(String msg){
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
        Toast.makeText(mContext, msg,
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.cancelarObservables();
    }



//Flecha Back de cada Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
