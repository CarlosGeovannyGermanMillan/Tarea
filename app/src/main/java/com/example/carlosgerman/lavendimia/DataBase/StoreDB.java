package com.example.carlosgerman.lavendimia.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.carlosgerman.lavendimia.Modelos.Articulo;
import com.example.carlosgerman.lavendimia.Modelos.Cliente;
import com.example.carlosgerman.lavendimia.Modelos.ConfiguracionGeneral;
import com.example.carlosgerman.lavendimia.Modelos.DetalleVenta;
import com.example.carlosgerman.lavendimia.Modelos.Venta;

import java.util.ArrayList;
import java.util.List;

public class StoreDB extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Store.db";
    public static final String Venta_TABLE_NAME = "Venta";
    public static final String Cliente_TABLE_NAME = "Cliente";
    public static final String Articulo_TABLE_NAME = "Articulo";
    public static final String DetalleVenta_TABLE_NAME = "DetalleVenta";
    public static final String ConfiguracionGeneral_TABLE_NAME = "ConfiguracionGeneral";
    public static final String Carrito_TABLE_NAME = "Carrito";


    public StoreDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + ConfiguracionGeneral_TABLE_NAME + "(id string primary key, TasaFinanciamiento double, Enganche double, PlazoMaximo int)"
        );
        db.execSQL(
                "create table " + Cliente_TABLE_NAME + " (Clave INTEGER primary key AUTOINCREMENT, Nombre string, ApellidoPaterno string, ApellidoMaterno string, RFC string)"
        );
        db.execSQL(
                "create table " + Articulo_TABLE_NAME + " (Clave INTEGER primary key AUTOINCREMENT, Descripcion string, Modelo string, Precio double, Existencia int)"
        );
        db.execSQL(
                "create table " + Venta_TABLE_NAME + "(Folio INTEGER primary key AUTOINCREMENT, ClaveCliente int, Total double,Fecha string, Plazo int, Estatus string)"
        );
        db.execSQL(
                "create table " + DetalleVenta_TABLE_NAME + " (ClaveDetalleVenta INTEGER primary key AUTOINCREMENT, ClaveVenta int, ClaveArticulo int, Cantidad int, FOREIGN KEY(ClaveVenta) REFERENCES Venta(Folio), FOREIGN KEY(ClaveArticulo) REFERENCES Articulo(Clave))"
        );
        db.execSQL(
                "create table " + Carrito_TABLE_NAME + " (Clave INTEGER primary key AUTOINCREMENT, Descripcion string, Modelo string, Precio double, Existencia int)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Store");
        onCreate(db);
    }
    public boolean CreateVenta(Venta venta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues obj = new ContentValues();
        obj.put("ClaveCliente",venta.getClaveCliente());
        obj.put("Total",venta.getTotal());
        obj.put("Fecha",venta.getFecha());
        obj.put("Plazo",venta.getPlazo());
        obj.put("Estatus",venta.getEstatus());
        db.insert("Venta", null,obj );
        return true;
    }
    public List<DetalleVenta> ReturnDetails(int ventaid) {
        List<DetalleVenta> array_list = new ArrayList();
        DetalleVenta solicitud = new DetalleVenta();

        SQLiteDatabase db = this.getReadableDatabase();
       /* String query = "select * from " + DetalleVenta_TABLE_NAME + " INNER JOIN "+Articulo_TABLE_NAME +
                " ON Articulo.Clave = DetalleVenta.ClaveArticulo "+
                " where ClaveVenta = "+ ventaid;*/
       String consulta = "select * from "+DetalleVenta_TABLE_NAME;
        try {
            Cursor res = db.rawQuery(consulta, null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                solicitud = new DetalleVenta();
                solicitud.setClaveVenta(res.getInt(0));
                solicitud.setClaveArticulo(res.getInt(1));
                solicitud.setCantidad(res.getInt(2));

                array_list.add(solicitud);
                res.moveToNext();
            }
        }
        catch(SQLiteException e) { }
        return array_list;
    }

    public boolean CreateDetail(DetalleVenta detalleVenta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues obj = new ContentValues();
        obj.put("ClaveVenta",detalleVenta.getClaveVenta());
        obj.put("ClaveArticulo",detalleVenta.getClaveArticulo());
        obj.put("Cantidad",detalleVenta.getCantidad());

        db.insert("DetalleVenta", null,obj );
        return true;
    }

    public List<Venta> GetAllVentas(){
        List<Venta> array = new ArrayList();
        Venta venta = new Venta();
        SQLiteDatabase db = this.getReadableDatabase();
        String consulta = "select * from "+Venta_TABLE_NAME;
        try{
            Cursor puntero = db.rawQuery(consulta,null);
            puntero.moveToFirst();
            while(!puntero.isAfterLast()){
                venta = new Venta();
                venta.setFolio(puntero.getInt(0));
                venta.setClaveCliente(puntero.getInt(1));
                venta.setTotal(puntero.getDouble(2));
                venta.setFecha(puntero.getString(3));
                venta.setPlazo(puntero.getInt(4));
                array.add(venta);
                puntero.moveToNext();
            }
        }catch (SQLiteException e){ }
        return array;
    }
    public boolean CreateGeneralConfiguration(ConfiguracionGeneral cg) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues obj = new ContentValues();
        obj.put("Enganche",cg.getEnganche());
        obj.put("PlazoMaximo",cg.getPlazoMaximo());
        obj.put("TasaFinanciamiento",cg.getTasaFinanciamiento());
        db.insert("ConfiguracionGeneral", null,obj );
        return true;
    }
    public ConfiguracionGeneral GetGeneralConfiguration(){
        SQLiteDatabase db = this.getReadableDatabase();
        ConfiguracionGeneral cg= new ConfiguracionGeneral();
        cg.setId("");
        String consulta = "select * from " + ConfiguracionGeneral_TABLE_NAME;
        try{
            Cursor puntero =  db.rawQuery( consulta, null );
            puntero.moveToFirst();
            while (!puntero.isAfterLast()) {
                cg.setId(puntero.getString(0));
                cg.setTasaFinanciamiento(puntero.getDouble(1));
                cg.setEnganche(puntero.getDouble(2));
                cg.setPlazoMaximo(puntero.getInt(3));
                puntero.moveToNext();
            }
        }catch(SQLiteException e) {
            cg.setId("");
        }

        return cg;
    }
    public boolean UpdateGeneralConfiguration (ConfiguracionGeneral config) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",config.getId());
        contentValues.put("TasaFinanciamiento", config.getTasaFinanciamiento());
        contentValues.put("Enganche", config.getEnganche());
        contentValues.put("PlazoMaximo", config.getPlazoMaximo());

        db.update("ConfiguracionGeneral", contentValues, "id = ? ", new String[] { config.getId()+"" } );
        return true;
    }

    public boolean CreateCliente(Cliente cliente){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues obj = new ContentValues();
        obj.put("Nombre",cliente.getNombre());
        obj.put("ApellidoPaterno",cliente.getApellidoPaterno());
        obj.put("ApellidoMaterno",cliente.getApellidoMaterno());
        obj.put("RFC",cliente.getRFC());
        db.insert("Cliente", null,obj );
        return true;
    }
    public Cliente GetClient(int client){
        SQLiteDatabase db = this.getReadableDatabase();
        Cliente c= new Cliente();
        c.setPrimary(-1);
        String query = "select * from " + Cliente_TABLE_NAME +" where Clave='"+client+"'";
        try{
            Cursor puntero =  db.rawQuery( query, null );
            puntero.moveToFirst();
            while (!puntero.isAfterLast()) {
                c.setPrimary(puntero.getInt(0));
                c.setNombre(puntero.getString(1));
                c.setApellidoPaterno(puntero.getString(2));
                c.setApellidoMaterno(puntero.getString(3));
                c.setRFC(puntero.getString(4));
                puntero.moveToNext();
            }
        }catch(SQLiteException e) {
            c.setPrimary(-1);
        }

        return c;
    }
    public Cliente GetClientName(String client){
        SQLiteDatabase db = this.getReadableDatabase();
        Cliente c= new Cliente();
        String x="";
        c.setPrimary(-1);
        String query = "select * from " + Cliente_TABLE_NAME;
        try{
            Cursor puntero =  db.rawQuery( query, null );
            puntero.moveToFirst();
            while (!puntero.isAfterLast()) {
                c.setPrimary(puntero.getInt(0));
                c.setNombre(puntero.getString(1));
                c.setApellidoPaterno(puntero.getString(2));
                c.setApellidoMaterno(puntero.getString(3));
                c.setRFC(puntero.getString(4));
                x = c.getNombre().toString() + " "+c.getApellidoPaterno().toString()+" "+c.getApellidoMaterno().toString();
                if (x == client){
                    return c;
                }
                puntero.moveToNext();
            }
        }catch(SQLiteException e) {
            c.setPrimary(-1);
        }
        return c;
    }

    public int ReturnMaxClient (){
        SQLiteDatabase db = this.getReadableDatabase();
        int idmax=0;
        String consulta = "select MAX(Clave) from " +Cliente_TABLE_NAME+" order by Clave desc";
        Cursor puntero = db.rawQuery(consulta,null);
        puntero.moveToFirst();
        while(!puntero.isAfterLast()){
            idmax = puntero.getInt(0);
            puntero.moveToNext();
        }
        return idmax;
    }
    public int ReturnMaxVenta (){
        SQLiteDatabase db = this.getReadableDatabase();
        int idmax=0;
        String consulta = "select MAX(Folio) from " +Venta_TABLE_NAME+" order by Folio desc";
        Cursor puntero = db.rawQuery(consulta,null);
        puntero.moveToFirst();
        while(!puntero.isAfterLast()){
            idmax = puntero.getInt(0);
            puntero.moveToNext();
        }
        return idmax;
    }
    public int ReturnMaxArticle (){
        SQLiteDatabase db = this.getReadableDatabase();
        int idmax=0;
        String consulta = "select MAX(Clave) from " +Articulo_TABLE_NAME+" order by Clave desc";
        Cursor puntero = db.rawQuery(consulta,null);
        puntero.moveToFirst();
        while(!puntero.isAfterLast()){
            idmax = puntero.getInt(0);
            puntero.moveToNext();
        }
        return idmax;
    }
    public boolean UpdateClient (Cliente client) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Clave",client.getPrimary());
        contentValues.put("Nombre", client.getNombre());
        contentValues.put("ApellidoPaterno", client.getApellidoPaterno());
        contentValues.put("ApellidoMaterno", client.getApellidoMaterno());
        contentValues.put("RFC", client.getRFC());

        db.update("Cliente", contentValues, "Clave = ? ", new String[] { client.getPrimary()+"" } );
        return true;
    }

    public List<Cliente> GetAllClients(){
        List<Cliente> array = new ArrayList();
        Cliente cliente = new Cliente();
        SQLiteDatabase db = this.getReadableDatabase();
        String consulta = "select * from "+Cliente_TABLE_NAME;
        try{
            Cursor puntero = db.rawQuery(consulta,null);
            puntero.moveToFirst();
            while(!puntero.isAfterLast()){
                cliente = new Cliente();
                cliente.setPrimary(puntero.getInt(0));
                cliente.setNombre(puntero.getString(1));
                cliente.setApellidoPaterno(puntero.getString(2));
                cliente.setApellidoMaterno(puntero.getString(3));
                cliente.setRFC(puntero.getString(4));
                array.add(cliente);
                puntero.moveToNext();
            }
        }catch (SQLiteException e){ }
        return array;
    }
    public List<String> GetAllNameClients(){
        List<String> array = new ArrayList();
        String name = "";
        Cliente cliente = new Cliente();
        SQLiteDatabase db = this.getReadableDatabase();
        String consulta = "select Nombre, ApellidoPaterno, ApellidoMaterno from "+Cliente_TABLE_NAME;
        try{
            Cursor puntero = db.rawQuery(consulta,null);
            puntero.moveToFirst();
            while(!puntero.isAfterLast()){
                cliente = new Cliente();
                cliente.setNombre(puntero.getString(0));
                cliente.setApellidoPaterno(puntero.getString(1));
                cliente.setApellidoMaterno(puntero.getString(2));
                name = cliente.getNombre().toString() + " "+cliente.getApellidoPaterno().toString()+" "+cliente.getApellidoMaterno().toString();
                array.add(name);
                puntero.moveToNext();
            }
        }catch (SQLiteException e){ }
        return array;
    }


    public List<String> GetAllNameArticles(){
        List<String> array = new ArrayList();
        String name = "";
        Articulo articulo = new Articulo();
        SQLiteDatabase db = this.getReadableDatabase();
        String consulta = "select Descripcion from "+Articulo_TABLE_NAME;
        try{
            Cursor puntero = db.rawQuery(consulta,null);
            puntero.moveToFirst();
            while(!puntero.isAfterLast()){
                articulo = new Articulo();
                articulo.setDescripcion(puntero.getString(0));
                name=articulo.getDescripcion().toString();
                array.add(name);
                puntero.moveToNext();
            }
        }catch (SQLiteException e){ }
        return array;
    }


    public boolean CreateArticulo(Articulo articulo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues obj = new ContentValues();
        obj.put("Descripcion",articulo.getDescripcion());
        obj.put("Existencia",articulo.getExistencia());
        obj.put("Modelo",articulo.getModelo());
        obj.put("Precio",articulo.getPrecio());
        db.insert("Articulo", null,obj );
        return true;
    }
    public Articulo GetArticle(int article){
        SQLiteDatabase db = this.getReadableDatabase();
        Articulo a= new Articulo();
        a.setClave(-1);
        String query = "select * from " + Articulo_TABLE_NAME +" where Clave='"+article+"'";
        try{
            Cursor puntero =  db.rawQuery( query, null );
            puntero.moveToFirst();
            while (!puntero.isAfterLast()) {
                a.setClave(puntero.getInt(0));
                a.setDescripcion(puntero.getString(1));
                a.setModelo(puntero.getString(2));
                a.setPrecio(puntero.getDouble(3));
                a.setExistencia(puntero.getInt(4));
                puntero.moveToNext();
            }
        }catch(SQLiteException e) {
            a.setClave(-1);
        }

        return a;
    }

    public int CheckCantidad(int article){
        SQLiteDatabase db = this.getReadableDatabase();
        Articulo a= new Articulo();
        int c=-1;
        a.setClave(-1);
        boolean band = false;
        String query = "select Existencia from " + Articulo_TABLE_NAME +" where Clave='"+article+"'";
        try{
            Cursor puntero =  db.rawQuery( query, null );
            puntero.moveToFirst();
            while (!puntero.isAfterLast()) {
                a.setExistencia(puntero.getInt(0));
                c= a.getExistencia();
                puntero.moveToNext();
            }
        }catch(SQLiteException e) {
            a.setClave(-1);
        }

        return c;
    }

    public Articulo GetArticleName(String arti){
        SQLiteDatabase db = this.getReadableDatabase();
        Articulo c= new Articulo();
        c.setClave(-1);
        String query = "select * from " + Articulo_TABLE_NAME;
        try{
            Cursor puntero =  db.rawQuery( query, null );
            puntero.moveToFirst();
            while (!puntero.isAfterLast()) {
                c.setClave(puntero.getInt(0));
                c.setDescripcion(puntero.getString(1));
                c.setModelo(puntero.getString(2));
                c.setPrecio(puntero.getDouble(3));
                c.setExistencia(puntero.getInt(4));

                if (c.getDescripcion().toString() == arti){
                    return c;
                }
                puntero.moveToNext();
            }
        }catch(SQLiteException e) {
            c.setClave(-1);
        }
        return c;
    }
    public boolean UpdateArticle (Articulo article) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Clave",article.getClave());
        contentValues.put("Descripcion", article.getDescripcion());
        contentValues.put("Modelo", article.getModelo());
        contentValues.put("Precio", article.getPrecio());
        contentValues.put("Existencia", article.getExistencia());

        db.update("Articulo", contentValues, "Clave = ? ", new String[] { article.getClave()+"" } );
        return true;
    }

    public List<Articulo> GetAllArticles(){
        List<Articulo> array = new ArrayList();
        Articulo article = new Articulo();
        SQLiteDatabase db = this.getReadableDatabase();
        String consulta = "select * from "+Articulo_TABLE_NAME;
        try{
            Cursor puntero = db.rawQuery(consulta,null);
            puntero.moveToFirst();
            while(!puntero.isAfterLast()){
                article = new Articulo();
                article.setClave(puntero.getInt(0));
                article.setDescripcion(puntero.getString(1));
                article.setModelo(puntero.getString(2));
                article.setPrecio(puntero.getDouble(3));
                article.setExistencia(puntero.getInt(4));
                array.add(article);
                puntero.moveToNext();
            }
        }catch (SQLiteException e){ }
        return array;
    }


    public boolean CreateArticuloCarrito(Articulo articulo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues obj = new ContentValues();
        obj.put("Descripcion",articulo.getDescripcion());
        obj.put("Existencia",articulo.getExistencia());
        obj.put("Modelo",articulo.getModelo());
        obj.put("Precio",articulo.getPrecio());
        db.insert("Carrito", null,obj );
        return true;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, Carrito_TABLE_NAME);
        return numRows;
    }
    public boolean UpdateArticleExistencia (Articulo article) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Clave",article.getClave());
        contentValues.put("Descripcion", article.getDescripcion());
        contentValues.put("Modelo", article.getModelo());
        contentValues.put("Precio", article.getPrecio());
        contentValues.put("Existencia", article.getExistencia());

        db.update("Carrito", contentValues, "Clave = ? ", new String[] { article.getClave()+"" } );
        return true;
    }
    public Articulo GetArticleNameCarrito(String arti){
        SQLiteDatabase db = this.getReadableDatabase();
        Articulo c= new Articulo();
        c.setClave(-1);
        String query = "select * from " + Carrito_TABLE_NAME;
        try{
            Cursor puntero =  db.rawQuery( query, null );
            puntero.moveToFirst();
            while (!puntero.isAfterLast()) {
                c.setClave(puntero.getInt(0));
                c.setDescripcion(puntero.getString(1));
                c.setModelo(puntero.getString(2));
                c.setPrecio(puntero.getDouble(3));
                c.setExistencia(puntero.getInt(4));

                if (c.getDescripcion().toString() == arti){
                    return c;
                }
                puntero.moveToNext();
            }
        }catch(SQLiteException e) {
            c.setClave(-1);
        }
        return c;
    }
    public Integer deleteArticleCarrito (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Carrito",
                "Clave = ? ",
                new String[] { (id) });
    }

    public void deleteAllCarrito () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ Carrito_TABLE_NAME);
    }
    public List<Articulo> GetAllArticlesCarrito(){
        List<Articulo> array = new ArrayList();
        Articulo article = new Articulo();
        SQLiteDatabase db = this.getReadableDatabase();
        String consulta = "select * from "+Carrito_TABLE_NAME;
        try{
            Cursor puntero = db.rawQuery(consulta,null);
            puntero.moveToFirst();
            while(!puntero.isAfterLast()){
                article = new Articulo();
                article.setClave(puntero.getInt(0));
                article.setDescripcion(puntero.getString(1));
                article.setModelo(puntero.getString(2));
                article.setPrecio(puntero.getDouble(3));
                article.setExistencia(puntero.getInt(4));
                array.add(article);
                puntero.moveToNext();
            }
        }catch (SQLiteException e){ }
        return array;
    }
}
