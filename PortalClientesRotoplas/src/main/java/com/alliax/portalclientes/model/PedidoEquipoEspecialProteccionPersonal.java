package com.alliax.portalclientes.model;

public class PedidoEquipoEspecialProteccionPersonal {
    private int secuencia;
    private String lineaTexto;

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public String getLineaTexto() {
        return lineaTexto;
    }

    public void setLineaTexto(String lineaTexto) {
        this.lineaTexto = lineaTexto;
    }

    @Override
    public String toString() {
        return "PedidoEquipoEspecialProteccionPersonal{" +
                "secuencia=" + secuencia +
                ", lineaTexto='" + lineaTexto + '\'' +
                '}';
    }
}
