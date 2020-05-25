package com.alliax.portalclientes.controller;

import com.alliax.portalclientes.model.PrecioMaterial;

import java.math.BigDecimal;

public class PrecioMaterialConfig {

    public PrecioMaterial obtenerPrecioMaterial(){

        PrecioMaterial precioMaterial = new PrecioMaterial();

        precioMaterial.setPrecioNeto(new BigDecimal(200.25));
        precioMaterial.setFechaEntrega("01/01/2021");
        precioMaterial.setMoneda("MXN");
        precioMaterial.setIva("5.56");
        precioMaterial.setMonto(new BigDecimal(15.85));
        precioMaterial.setTotalPartida(new BigDecimal(8932.25));
        precioMaterial.setCodigoError("0");
        precioMaterial.setMensajeError("");


        return precioMaterial;
    }
}
