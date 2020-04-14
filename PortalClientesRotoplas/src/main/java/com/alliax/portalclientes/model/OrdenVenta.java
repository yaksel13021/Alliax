/**
 * Objeto para representar la informcion de una Orden de Venta
 * @author saul.ibarra
 * @fecha 27-Ene-2016
 */
package com.alliax.portalclientes.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.text.WordUtils;

public class OrdenVenta {

	private String documentoComercial; //SD_DOC	
	private String pedidoCliente; //PURCH_NO_C
	private Date fechaCreacion;  //CREATION_DATE
	private long horaCreacion; //CREATION_TIME
	private BigDecimal monto; //NET_VAL_HD   Valor neto de pedido
	private String moneda;  //CURRENCY
	private String destinatario; //SHIP_TO	
	private String estatusGlobal; //PRC_STAT_H
	private String estatusGlobalDes; //PRC_STAT_H_D
	private String estatusCredito;  //CRE_STAT_H
	private String estatusCreditoDes; //CRE_STAT_H_D
	private String estatusEntrega; //DLV_STAT_H
	private String estatusEntregaDes; //DLV_STAT_H_D
	
	private String estatusGeneral; //GRL_STAT_H
	private String estatusGeneralDes; //GRL_STAT_H_D
	private String razonBloqueo; //REASON_BLOCK
	
	
	public String getDocumentoComercial() {
		return documentoComercial;
	}
	public void setDocumentoComercial(String documentoComercial) {
		this.documentoComercial = documentoComercial;
	}
	public String getPedidoCliente() {
		return pedidoCliente;
	}
	public void setPedidoCliente(String pedidoCliente) {
		this.pedidoCliente = pedidoCliente;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public long getHoraCreacion() {
		return horaCreacion;
	}
	public void setHoraCreacion(long horaCreacion) {
		this.horaCreacion = horaCreacion;
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
	public String getDestinatario() {
		try {									
			return WordUtils.capitalize(destinatario.toLowerCase());
		} catch(Exception e){
			return destinatario;
		}
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public String getEstatusGlobal() {
		return estatusGlobal;
	}
	public void setEstatusGlobal(String estatusGlobal) {
		this.estatusGlobal = estatusGlobal;
	}
	public String getEstatusGlobalDes() {
		return estatusGlobalDes;
	}
	public void setEstatusGlobalDes(String estatusGlobalDes) {
		this.estatusGlobalDes = estatusGlobalDes;
	}
	public String getEstatusCredito() {
		return estatusCredito;
	}
	public void setEstatusCredito(String estatusCredito) {
		this.estatusCredito = estatusCredito;
	}
	public String getEstatusCreditoDes() {
		return estatusCreditoDes;
	}
	public void setEstatusCreditoDes(String estatusCreditoDes) {
		this.estatusCreditoDes = estatusCreditoDes;
	}
	public String getEstatusEntrega() {
		return estatusEntrega;
	}
	public void setEstatusEntrega(String estatusEntrega) {
		this.estatusEntrega = estatusEntrega;
	}
	public String getEstatusEntregaDes() {
		return estatusEntregaDes;
	}
	public void setEstatusEntregaDes(String estatusEntregaDes) {
		this.estatusEntregaDes = estatusEntregaDes;
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
	public String getRazonBloqueo() {
		return razonBloqueo;
	}
	public void setRazonBloqueo(String razonBloqueo) {
		this.razonBloqueo = razonBloqueo;
	}
}
