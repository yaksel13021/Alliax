package com.alliax.portalclientes.model;

public class PedidoMaterial {
    private String sku;
    private String cantidad;
    private String posicion;
    private String descripcion;
    private String unidadMedida;
    private String monto;
    private String precioNeto;
    private String moneda;
    private String iva;
    private String fechaEntrega;
    private String codigoError;
    private String mensajeError;
    private String leyendaError;
    private String descripcionError;
    private String urlFoto;
    private String estatus;
    private String totalPartida;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getLeyendaError() {
        return leyendaError;
    }

    public void setLeyendaError(String leyendaError) {
        this.leyendaError = leyendaError;
    }

    public String getDescripcionError() {
        return descripcionError;
    }

    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getTotalPartida() {
        return totalPartida;
    }

    public void setTotalPartida(String totalPartida) {
        this.totalPartida = totalPartida;
    }

    @Override
    public String toString() {
        return "PedidoMaterial{" +
                "sku='" + sku + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", posicion='" + posicion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", monto='" + monto + '\'' +
                ", precioNeto='" + precioNeto + '\'' +
                ", moneda='" + moneda + '\'' +
                ", iva='" + iva + '\'' +
                ", fechaEntrega='" + fechaEntrega + '\'' +
                ", codigoError='" + codigoError + '\'' +
                ", mensajeError='" + mensajeError + '\'' +
                ", leyendaError='" + leyendaError + '\'' +
                ", descripcionError='" + descripcionError + '\'' +
                ", urlFoto='" + urlFoto + '\'' +
                ", estatus='" + estatus + '\'' +
                ", totalPartida='" + totalPartida + '\'' +
                '}';
    }
}
