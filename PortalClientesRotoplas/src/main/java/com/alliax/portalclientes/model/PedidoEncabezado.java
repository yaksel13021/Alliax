package com.alliax.portalclientes.model;

public class PedidoEncabezado {
    private String nroCliente;
    private String nroDestinatarioMercancias;
    private String clasePedido;
    private String organizacionVenta;
    private String canalDistribucion;
    private String sector;
    private String motivoPedido;
    private String segmento;
    private String nroPedidoCliente;
    private String sociedad;
    private String moneda;
    private String metodoPago;
    private String usoCFDI;

    public String getNroCliente() {
        return nroCliente;
    }

    public void setNroCliente(String nroCliente) {
        this.nroCliente = nroCliente;
    }

    public String getNroDestinatarioMercancias() {
        return nroDestinatarioMercancias;
    }

    public void setNroDestinatarioMercancias(String nroDestinatarioMercancias) {
        this.nroDestinatarioMercancias = nroDestinatarioMercancias;
    }

    public String getClasePedido() {
        return clasePedido;
    }

    public void setClasePedido(String clasePedido) {
        this.clasePedido = clasePedido;
    }

    public String getOrganizacionVenta() {
        return organizacionVenta;
    }

    public void setOrganizacionVenta(String organizacionVenta) {
        this.organizacionVenta = organizacionVenta;
    }

    public String getCanalDistribucion() {
        return canalDistribucion;
    }

    public void setCanalDistribucion(String canalDistribucion) {
        this.canalDistribucion = canalDistribucion;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getMotivoPedido() {
        return motivoPedido;
    }

    public void setMotivoPedido(String motivoPedido) {
        this.motivoPedido = motivoPedido;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getNroPedidoCliente() {
        return nroPedidoCliente;
    }

    public void setNroPedidoCliente(String nroPedidoCliente) {
        this.nroPedidoCliente = nroPedidoCliente;
    }

    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getUsoCFDI() {
        return usoCFDI;
    }

    public void setUsoCFDI(String usoCFDI) {
        this.usoCFDI = usoCFDI;
    }

    @Override
    public String toString() {
        return "PedidoEncabezado{" +
                "nroCliente='" + nroCliente + '\'' +
                ", nroDestinatarioMercancias='" + nroDestinatarioMercancias + '\'' +
                ", clasePedido='" + clasePedido + '\'' +
                ", organizacionVenta='" + organizacionVenta + '\'' +
                ", canalDistribucion='" + canalDistribucion + '\'' +
                ", sector='" + sector + '\'' +
                ", motivoPedido='" + motivoPedido + '\'' +
                ", segmento='" + segmento + '\'' +
                ", nroPedidoCliente='" + nroPedidoCliente + '\'' +
                ", sociedad='" + sociedad + '\'' +
                ", moneda='" + moneda + '\'' +
                ", metodoPago='" + metodoPago + '\'' +
                ", usoCFDI='" + usoCFDI + '\'' +
                '}';
    }
}
