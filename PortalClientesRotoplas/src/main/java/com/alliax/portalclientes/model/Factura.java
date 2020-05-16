/**
 * Objeto para representar Facturas
 */
package com.alliax.portalclientes.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Factura {

	private String docFactura; //BILLINGDOC
	private Date fechaFactura; //BILL_DATE
	private BigDecimal montoNeto; //NET_VALUE
	private BigDecimal montoImp; //Impuesto
	private String moneda; //CURRENCY
	private String estatusContabilidad; //ACCTSTATUS
	private String cancelado; //??

	
	//Render del detalle
	private boolean muestraDetalle;
	
	//Detalle de la Factura
	private List<ItemFactura> detalleFactura;
	
	public boolean isMuestraDetalle() {
		return muestraDetalle;
	}

	public void setMuestraDetalle(boolean muestraDetalle) {
		this.muestraDetalle = muestraDetalle;
	}

	public String getDocFactura() {
		return docFactura;
	}

	public void setDocFactura(String docFactura) {
		this.docFactura = docFactura;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public String getEstatusContabilidad() {
		return estatusContabilidad;
	}

	public void setEstatusContabilidad(String estatusContabilidad) {
		this.estatusContabilidad = estatusContabilidad;
	}

	public BigDecimal getMontoNeto() {
		return montoNeto;
	}

	public void setMontoNeto(BigDecimal montoNeto) {
		this.montoNeto = montoNeto;
	}

	public BigDecimal getMontoImp() {
		return montoImp;
	}

	public void setMontoImp(BigDecimal montoImp) {
		this.montoImp = montoImp;
	}

	public String getCancelado() {
		return cancelado;
	}

	public void setCancelado(String cancelado) {
		this.cancelado = cancelado;
	}

	public List<ItemFactura> getDetalleFactura() {
		return detalleFactura;
	}

	public void setDetalleFactura(List<ItemFactura> detalleFactura) {
		this.detalleFactura = detalleFactura;
	}

	public boolean cambiaMuestraDetalle(){
		this.muestraDetalle = !this.muestraDetalle;
		return muestraDetalle;
	}


}
