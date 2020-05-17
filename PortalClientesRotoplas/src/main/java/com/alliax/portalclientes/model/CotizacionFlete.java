package com.alliax.portalclientes.model;


public class CotizacionFlete {

    public static final String idMatFlete = "400068";
   
    public static final String descFlete = "Flete";
    public static final String unidadMed = "SVO";
    public static final String estadoCaptura= "En Captura";
    public static final String estadoFin = "Finalizado";
    public static final String estadoOrdenado = "Ordenado";

    private String nroPedido;
    private String noCotizacion;
    private String material;
    private String cantidad;
    private String descripcion;
    private String uM;
    private String estado;
    private String precioNeto;
    private String monto;


    public String getNroPedido() {
        return nroPedido;
    }

    public void setNroPedido(String nroPedido) {
        this.nroPedido = nroPedido;
    }

    public CotizacionFlete() {
    }

    public String getNoCotizacion() {
        return noCotizacion;
    }

    public void setNoCotizacion(String noCotizacion) {
        this.noCotizacion = noCotizacion;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getuM() {
        return uM;
    }

    public void setuM(String uM) {
        this.uM = uM;
    }
    
    public String getPrecioNeto() {
    	return precioNeto;
    }
    
    public void setPrecioNeto(String precioNeto) {
    	this.precioNeto = precioNeto;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }
    
    public String getDescripcion() {
    	return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
    	this.descripcion = descripcion;
    }
}