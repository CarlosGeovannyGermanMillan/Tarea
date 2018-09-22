package com.example.carlosgerman.lavendimia.Modelos;

public class Articulo {
    int Clave;
    String Descripcion;
    String Modelo;
    Double Precio;
    int Existencia;

    public Articulo() {
    }

    public Articulo(int clave, String descripcion, String modelo, Double precio, int existencia) {
        Clave = clave;
        Descripcion = descripcion;
        Modelo = modelo;
        Precio = precio;
        Existencia = existencia;
    }

    public int getClave() {
        return Clave;
    }

    public void setClave(int clave) {
        Clave = clave;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }

    public int getExistencia() {
        return Existencia;
    }

    public void setExistencia(int existencia) {
        Existencia = existencia;
    }
}
