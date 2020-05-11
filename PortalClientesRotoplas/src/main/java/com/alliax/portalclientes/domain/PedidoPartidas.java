package com.alliax.portalclientes.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="PedidoPartidas")
public class PedidoPartidas {
    @EmbeddedId
    private PedidoPartidasPK id;

    @Column(name = "cantidad")
    private String cantidad;
    @Column(name = "precioNeto")
    private String precioNeto;
    @Column(name = "monto")
    private String monto;
    @Column(name = "iva")
    private String iva;
    @Column(name = "totalPartida")
    private String totalPartida;
    @Column(name = "moneda")
    private String moneda;
    @Column(name = "fechaEntrega")
    private String fechaEntrega;
    @Column(name = "mensajeError")
    private String mensajeError;
    @Column(name = "codigoError")
    private String codigoError;
    @Column(name = "flete")
    private Boolean flete;

    public PedidoPartidasPK getId() {
        return id;
    }

    public void setId(PedidoPartidasPK id) {
        this.id = id;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecioNeto() {
        return precioNeto;
    }

    public void setPrecioNeto(String precioNeto) {
        this.precioNeto = precioNeto;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getTotalPartida() {
        return totalPartida;
    }

    public void setTotalPartida(String totalPartida) {
        this.totalPartida = totalPartida;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }

    public Boolean getFlete() {
        return flete;
    }

    public void setFlete(Boolean flete) {
        this.flete = flete;
    }
}
