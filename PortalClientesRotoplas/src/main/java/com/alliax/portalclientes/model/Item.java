/**
 * Objeto para representar una posicion del pedido
 * @author saul.ibarra
 * @fecha 31-Enero-2016
 */
package com.alliax.portalclientes.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import com.alliax.portalclientes.general.formato.Convertidor;

public class Item {
	
	private String documentoComercial; //DOC_NUMBER
	private int posicion;  //ITM_NUMBER
	private Date fecha; //DOC_DATE
	private String bloqueoEntrega; //? //DLV_BLOCK
	private String noMaterial; //MATERIAL
	private String descripcion;  //SHORT_TEXT
	private Date fechaReparto; //?  //REQ_DATE
	private BigDecimal cantidad;  //REQ_QTY
	private BigDecimal cantidadConfirmada;  //CUM_CF_QTY
	private String unidadMedida; //SALES_UNIT
	private BigDecimal monto; //NET_VALUE
	private String moneda; //CURRENCY 
	private BigDecimal precioNeto; //NET_PRICE
	private BigDecimal cantidadBase; //?  //COND_P_UNT
	private String unidadMedidaCondicion; //?  //COND_UNIT
	private String noEntrega;  //DELIV_NUMB
	private int posicionEntrega; //DELIV_ITEM 
	private Date fechaEntrega;  //DELIV_DATE
	private BigDecimal cantidadEntregada;  //DLV_QTY
	private BigDecimal cantidadRefUmb;  //?  REF_QTY
	private String unidadEntrega;  // ? S_UNIT_DLV
	private String unidadMedidaISO;  //DLV_UNIT_ISO
	private String motivoRechazo; //REA_FOR_RE
	private String pedidoCliente; //PURCH_NO_C
	private String estatus;  //DLV_STAT_I
	private String estatusDes;  //DLV_STAT_I_D
	private String estatusGeneral; //GRL_STAT_I
	private String estatusGeneralDes; //GRL_STAT_I_D 
	
	public String getDocumentoComercial() {
		return documentoComercial;
	}
	public void setDocumentoComercial(String documentoComercial) {
		this.documentoComercial = documentoComercial;
	}
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getBloqueoEntrega() {
		return bloqueoEntrega;
	}
	public void setBloqueoEntrega(String bloqueoEntrega) {
		this.bloqueoEntrega = bloqueoEntrega;
	}
	public String getNoMaterial() {
		return noMaterial;
	}
	public void setNoMaterial(String noMaterial) {
		this.noMaterial = noMaterial;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaReparto() {
		return fechaReparto;
	}
	public void setFechaReparto(Date fechaReparto) {
		this.fechaReparto = fechaReparto;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}
	
	public String getCantidadStr(){
		return Convertidor.BigDecimalToString(this.cantidad);
	}
	
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getCantidadConfirmada() {
		return cantidadConfirmada;
	}
	public void setCantidadConfirmada(BigDecimal cantidadConfirmada) {
		this.cantidadConfirmada = cantidadConfirmada;
	}
	public String getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public BigDecimal getPrecioNeto() {
		return precioNeto;
	}
	public void setPrecioNeto(BigDecimal precioNeto) {
		this.precioNeto = precioNeto;
	}
	public BigDecimal getCantidadBase() {
		return cantidadBase;
	}
	public void setCantidadBase(BigDecimal cantidadBase) {
		this.cantidadBase = cantidadBase;
	}
	public String getUnidadMedidaCondicion() {
		return unidadMedidaCondicion;
	}
	public void setUnidadMedidaCondicion(String unidadMedidaCondicion) {
		this.unidadMedidaCondicion = unidadMedidaCondicion;
	}
	public String getNoEntrega() {
		return noEntrega;
	}
	public void setNoEntrega(String noEntrega) {
		this.noEntrega = noEntrega;
	}
	public int getPosicionEntrega() {
		return posicionEntrega;
	}
	public void setPosicionEntrega(int posicionEntrega) {
		this.posicionEntrega = posicionEntrega;
	}
	public Date getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public BigDecimal getCantidadEntregada() {
		return cantidadEntregada;
	}
	
	public String getCantidadEntregadaStr() {
		return Convertidor.BigDecimalToString(cantidadEntregada);
	}	
	
	public void setCantidadEntregada(BigDecimal cantidadEntregada) {
		this.cantidadEntregada = cantidadEntregada;
	}
	public BigDecimal getCantidadRefUmb() {
		return cantidadRefUmb;
	}
	public void setCantidadRefUmb(BigDecimal cantidadRefUmb) {
		this.cantidadRefUmb = cantidadRefUmb;
	}
	public String getUnidadEntrega() {
		return unidadEntrega;
	}
	public void setUnidadEntrega(String unidadEntrega) {
		this.unidadEntrega = unidadEntrega;
	}
	public String getUnidadMedidaISO() {
		return unidadMedidaISO;
	}
	public void setUnidadMedidaISO(String unidadMedidaISO) {
		this.unidadMedidaISO = unidadMedidaISO;
	}
	public String getMotivoRechazo() {
		return motivoRechazo;
	}
	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}
	public String getPedidoCliente() {
		return pedidoCliente;
	}
	public void setPedidoCliente(String pedidoCliente) {
		this.pedidoCliente = pedidoCliente;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getEstatusDes() {
		return estatusDes;
	}
	public void setEstatusDes(String estatusDes) {
		this.estatusDes = estatusDes;
	}
	public String getEstatusGeneral() {
		return estatusGeneral;
	}
	public void setEstatusGeneral(String estatusGeneral) {
		this.estatusGeneral = estatusGeneral;
	}
	public String getEstatusGeneralDes() {
		return estatusGeneralDes;
	}
	public void setEstatusGeneralDes(String estatusGeneralDes) {
		this.estatusGeneralDes = estatusGeneralDes;
	}
	@Override
	public String toString() {
		return "Item [documentoComercial=" + documentoComercial + ", posicion=" + posicion + ", fecha=" + fecha
				+ ", bloqueoEntrega=" + bloqueoEntrega + ", noMaterial=" + noMaterial + ", descripcion=" + descripcion
				+ ", fechaReparto=" + fechaReparto + ", cantidad=" + cantidad + ", cantidadConfirmada="
				+ cantidadConfirmada + ", unidadMedida=" + unidadMedida + ", monto=" + monto + ", moneda=" + moneda
				+ ", precioNeto=" + precioNeto + ", cantidadBase=" + cantidadBase + ", unidadMedidaCondicion="
				+ unidadMedidaCondicion + ", noEntrega=" + noEntrega + ", posicionEntrega=" + posicionEntrega
				+ ", fechaEntrega=" + fechaEntrega + ", cantidadEntregada=" + cantidadEntregada + ", cantidadRefUmb="
				+ cantidadRefUmb + ", unidadEntrega=" + unidadEntrega + ", unidadMedidaISO=" + unidadMedidaISO
				+ ", motivoRechazo=" + motivoRechazo + ", pedidoCliente=" + pedidoCliente + ", estatus=" + estatus
				+ ", estatusDes=" + estatusDes + ", estatusGeneral=" + estatusGeneral + ", estatusGeneralDes="
				+ estatusGeneralDes + "]";
	}
}
