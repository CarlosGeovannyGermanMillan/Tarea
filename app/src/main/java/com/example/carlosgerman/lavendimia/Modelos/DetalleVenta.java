package com.example.carlosgerman.lavendimia.Modelos;

public class DetalleVenta {
    int primary;
    int ClaveVenta;
    int ClaveArticulo;
    int Cantidad;

    public DetalleVenta(){

    }

    public DetalleVenta(int primary, int claveVenta, int claveArticulo, int cantidad) {
        this.primary = primary;
        ClaveVenta = claveVenta;
        ClaveArticulo = claveArticulo;
        Cantidad = cantidad;
    }

    public int getPrimary() {
        return primary;
    }

    public void setPrimary(int primary) {
        this.primary = primary;
    }

    public int getClaveVenta() {
        return ClaveVenta;
    }

    public void setClaveVenta(int claveVenta) {
        ClaveVenta = claveVenta;
    }

    public int getClaveArticulo() {
        return ClaveArticulo;
    }

    public void setClaveArticulo(int claveArticulo) {
        ClaveArticulo = claveArticulo;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }
}
