package com.example.carlosgerman.lavendimia.Views.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.carlosgerman.lavendimia.DataBase.StoreDB;
import com.example.carlosgerman.lavendimia.Modelos.Cliente;
import com.example.carlosgerman.lavendimia.Modelos.ConfiguracionGeneral;
import com.example.carlosgerman.lavendimia.R;
import com.example.carlosgerman.lavendimia.Views.Fragments.ArticulosFragment;
import com.example.carlosgerman.lavendimia.Views.Fragments.ClientesFragment;
import com.example.carlosgerman.lavendimia.Views.Fragments.VentasFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, VentasFragment.OnFragmentInteractionListener {

    StoreDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        VentasFragment fragment = new VentasFragment();
        iniciarFragmento(fragment,"Ventas");
        db = new StoreDB(this);
        ConfiguracionGeneral cg = new ConfiguracionGeneral();
        cg.setPlazoMaximo(12);
        cg.setEnganche(20);
        cg.setTasaFinanciamiento(2.8);
        db.CreateGeneralConfiguration(cg);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        if (id == R.id.nav_Ventas) {
            fragment = new VentasFragment();
            title = "Ventas";
            cambiarFragmento(fragment,title);

        } else if (id == R.id.nav_Clientes) {
            fragment = new ClientesFragment();
            title = "Clientes";
            cambiarFragmento(fragment,title);

        } else if (id == R.id.nav_Articulos) {
            fragment = new ArticulosFragment();
            title = "Articulos";
            cambiarFragmento(fragment,title);

        } else if (id == R.id.nav_Configuracion) {
            Intent intent = new Intent(this,ConfiguracionActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cambiarFragmento(Fragment fragmento, String tag){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.Fragment_Content,fragmento,tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void iniciarFragmento(Fragment fragmento, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.Fragment_Content,fragmento,tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
