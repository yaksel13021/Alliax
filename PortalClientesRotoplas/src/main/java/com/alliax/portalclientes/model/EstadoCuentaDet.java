/**
 * Objeto para representar un registro el Estado de Cuenta
 * @author ext.juan.gonzalez
 * @fecha 15-Agosto-2018
 */
package com.alliax.portalclientes.model;

import java.math.BigDecimal;

public class EstadoCuentaDet implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tipoDocumento; //TIPO_DOC
	private String ordenCompra; //OR_COMPRA	
	private String noPedido; //NO_PEDIDO
	private String notaEntrega;  //NTA_ENTREGA
	private String noFactura; //NO_FACTURA
	private String noFactFiscal;  //NO_FACT_FISCAL
	private String fechaFactura; //FECHA_FACT
	private String fechaFacturaSort; //yyyymmdd  solo sirve para ordenar por fecha
	private String fechaVenc; //FECHA_VENC
	private String fechaVencSort; //yyyymmdd solo sirve para ordenar por fecha
	private String diasMora;  //DIAS_MORA
	private BigDecimal importe; //IMPORTE
	private String estatus;  //ESTATUS
	private String entrega; //ENTREGA
	private String facturaRelacionada; //FACTURA_REF
	private String UUIDRelacionado; //FACT_FISCAL_REF
	
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getOrdenCompra() {
		return ordenCompra;
	}
	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}
	public String getNoPedido() {
		return noPedido;
	}
	public void setNoPedido(String noPedido) {
		this.noPedido = noPedido;
	}
	public String getNotaEntrega() {
		return notaEntrega;
	}
	public void setNotaEntrega(String notaEntrega) {
		this.notaEntrega = notaEntrega;
	}
	public String getNoFactura() {
		return noFactura;
	}
	public void setNoFactura(String noFactura) {
		this.noFactura = noFactura;
	}
	public String getNoFactFiscal() {
		return noFactFiscal;
	}
	public void setNoFactFiscal(String noFactFiscal) {
		this.noFactFiscal = noFactFiscal;
	}
	public String getFechaFactura() {
		return fechaFactura;
	}
	public void setFechaFactura(String fechaFactura) {
		this.fechaFactura = fechaFactura;
	}
	public String getFechaVenc() {
		return fechaVenc;
	}
	public void setFechaVenc(String fechaVenc) {
		this.fechaVenc = fechaVenc;
	}
	public String getDiasMora() {
		return diasMora;
	}
	public void setDiasMora(String diasMora) {
		this.diasMora = diasMora;
	}
	public BigDecimal getImporte() {
		return importe;
	}
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getFechaFacturaSort() {
		return fechaFacturaSort;
	}
	public void setFechaFacturaSort(String fechaFacturaSort) {
		this.fechaFacturaSort = fechaFacturaSort;
	}
	public String getFechaVencSort() {
		return fechaVencSort;
	}
	public void setFechaVencSort(String fechaVencSort) {
		this.fechaVencSort = fechaVencSort;
	}
	public String getEntrega() {
		return entrega;
	}
	public void setEntrega(String entrega) {
		this.entrega = entrega;
	}
	public String getFacturaRelacionada() {
		return facturaRelacionada;
	}
	public void setFacturaRelacionada(String facturaRelacionada) {
		this.facturaRelacionada = facturaRelacionada;
	}
	public String getUUIDRelacionado() {
		return UUIDRelacionado;
	}
	public void setUUIDRelacionado(String UUIDRelacionado) {
		this.UUIDRelacionado = UUIDRelacionado;
	}
	@Override
	public String toString() {
		return "EstadoCuentaDet [tipoDocumento=" + tipoDocumento + ", ordenCompra=" + ordenCompra + ", noPedido="
				+ noPedido + ", notaEntrega=" + notaEntrega + ", noFactura=" + noFactura + ", noFactFiscal="
				+ noFactFiscal + ", fechaFactura=" + fechaFactura + ", fechaFacturaSort=" + fechaFacturaSort
				+ ", fechaVenc=" + fechaVenc + ", fechaVencSort=" + fechaVencSort + ", diasMora=" + diasMora
				+ ", importe=" + importe + ", estatus=" + estatus + ", entrega=" + entrega + ", facturaRelacionada="
				+ facturaRelacionada + ", UUIDRelacionado=" + UUIDRelacionado + "]";
	}
	
}
