package com.example.carlosgerman.lavendimia.Modelos;

public class ConfiguracionGeneral {
    String id;
    double TasaFinanciamiento;
    double Enganche;
    int PlazoMaximo;


    public ConfiguracionGeneral() {
    }

    public ConfiguracionGeneral(String id, double tasaFinanciamiento, double enganche, int plazoMaximo) {
        this.id = id;
        TasaFinanciamiento = tasaFinanciamiento;
        Enganche = enganche;
        PlazoMaximo = plazoMaximo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTasaFinanciamiento() {
        return TasaFinanciamiento;
    }

    public void setTasaFinanciamiento(double tasaFinanciamiento) {
        TasaFinanciamiento = tasaFinanciamiento;
    }

    public double getEnganche() {
        return Enganche;
    }

    public void setEnganche(double enganche) {
        Enganche = enganche;
    }

    public int getPlazoMaximo() {
        return PlazoMaximo;
    }

    public void setPlazoMaximo(int plazoMaximo) {
        PlazoMaximo = plazoMaximo;
    }
}
