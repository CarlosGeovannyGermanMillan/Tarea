package com.example.carlosgerman.lavendimia.Modelos;

public class Venta {
    int Folio;
    int ClaveCliente;
    double Total;
    String Fecha;
    int Plazo;
    String Estatus;

    public Venta() {
    }

    public Venta(int folio, int claveCliente, double total, String fecha, int plazo, String estatus) {
        Folio = folio;
        ClaveCliente = claveCliente;
        Total = total;
        Fecha = fecha;
        Plazo = plazo;
        Estatus = estatus;
    }

    public int getFolio() {
        return Folio;
    }

    public void setFolio(int folio) {
        Folio = folio;
    }

    public int getClaveCliente() {
        return ClaveCliente;
    }

    public void setClaveCliente(int claveCliente) {
        ClaveCliente = claveCliente;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public int getPlazo() {
        return Plazo;
    }

    public void setPlazo(int plazo) {
        Plazo = plazo;
    }

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String estatus) {
        Estatus = estatus;
    }
}
