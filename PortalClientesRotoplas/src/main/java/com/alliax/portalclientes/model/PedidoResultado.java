package com.alliax.portalclientes.model;

public class PedidoResultado {
    private String documentoVenta;
    private String mensajeError;
    private String actualizoFacturacion;
    private String generoDocumentoVenta;

    public String getDocumentoVenta() {
        return documentoVenta;
    }

    public void setDocumentoVenta(String documentoVenta) {
        this.documentoVenta = documentoVenta;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getActualizoFacturacion() {
        return actualizoFacturacion;
    }

    public void setActualizoFacturacion(String actualizoFacturacion) {
        this.actualizoFacturacion = actualizoFacturacion;
    }

    public String getGeneroDocumentoVenta() {
        return generoDocumentoVenta;
    }

    public void setGeneroDocumentoVenta(String generoDocumentoVenta) {
        this.generoDocumentoVenta = generoDocumentoVenta;
    }
}
