package com.alliax.portalclientes.model;

public class AltaMaterial {
    private String sku;
    private String tipoMaterial;
    private String descripcion;
    private String unidadMedida;
    private String urlFoto;

    public AltaMaterial() {
    }

    public AltaMaterial(String sku, String tipoMaterial, String descripcion, String unidadMedida, String urlFoto) {
        this.sku = sku;
        this.tipoMaterial = tipoMaterial;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.urlFoto = urlFoto;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
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

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
