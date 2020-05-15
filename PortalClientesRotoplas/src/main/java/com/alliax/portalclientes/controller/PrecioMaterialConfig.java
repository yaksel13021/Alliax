package com.alliax.portalclientes.controller;

import com.alliax.portalclientes.model.PrecioMaterial;

import java.math.BigDecimal;

public class PrecioMaterialConfig {

    public PrecioMaterial obtenerPrecioMaterial(){

        PrecioMaterial precioMaterial = new PrecioMaterial();

        precioMaterial.setPrecioNeto(new BigDecimal(10));
        precioMaterial.setFechaEntrega("01/01/2021");
        precioMaterial.setMoneda("MXN");
        precioMaterial.setIva("5");
        precioMaterial.setMonto(new BigDecimal(15));
        precioMaterial.setTotalPartida(new BigDecimal(30));
        precioMaterial.setCodigoError("0");
        precioMaterial.setMensajeError("");


        return precioMaterial;
    }
}
