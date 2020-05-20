package com.alliax.portalclientes.model;

public class PedidoPartidas {
    private String posicion;
    private String nroMaterial;
    private String cantidad;
    private String unidadMedida;
    private String monto;

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getNroMaterial() {
        return nroMaterial;
    }

    public void setNroMaterial(String nroMaterial) {
        this.nroMaterial = nroMaterial;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "PedidoPartidas{" +
                "posicion='" + posicion + '\'' +
                ", nroMaterial='" + nroMaterial + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                '}';
    }
}
