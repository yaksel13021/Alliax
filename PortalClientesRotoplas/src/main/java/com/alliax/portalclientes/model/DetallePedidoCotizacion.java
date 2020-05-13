package com.alliax.portalclientes.model;

public class DetallePedidoCotizacion {
    private String noPedido;
    private String posicion;
    private String noMaterial;
    private String descripcion;
    private String cantidad;
    private String cantEnt;
    private String unidadMedida;
    private String monto;
    private String precioNeto;
    private String fechaEnt;
    private String estatus;
    private String moneda;

    public String getNoPedido() {
        return noPedido;
    }

    public void setNoPedido(String noPedido) {
        this.noPedido = noPedido;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getNoMaterial() {
        return noMaterial;
    }

    public void setNoMaterial(String noMaterial) {
        this.noMaterial = noMaterial;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCantEnt() {
        return cantEnt;
    }

    public void setCantEnt(String cantEnt) {
        this.cantEnt = cantEnt;
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

    public String getPrecioNeto() {
        return precioNeto;
    }

    public void setPrecioNeto(String precioNeto) {
        this.precioNeto = precioNeto;
    }

    public String getFechaEnt() {
        return fechaEnt;
    }

    public void setFechaEnt(String fechaEnt) {
        this.fechaEnt = fechaEnt;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getMoneda() {
    	return moneda;
    }
    
    public void setMoneda(String moneda) {
    	this.moneda = moneda;
    }
}
