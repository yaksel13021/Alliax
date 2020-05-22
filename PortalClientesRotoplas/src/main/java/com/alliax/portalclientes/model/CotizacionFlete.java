package com.alliax.portalclientes.model;


import java.math.BigDecimal;

public class CotizacionFlete {

    public static final String idMatFlete = "400068";
   
    public static final String descFlete = "Flete";
    public static final String unidadMed = "UN";
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
    private BigDecimal precioNeto;
    private BigDecimal monto;


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

    public BigDecimal getPrecioNeto() {
        return precioNeto;
    }

    public void setPrecioNeto(BigDecimal precioNeto) {
        this.precioNeto = precioNeto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
    	return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
    	this.descripcion = descripcion;
    }
}