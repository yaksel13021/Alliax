/**
 * Objeto para representar una posicion relacionada a una factura en particular.
 * @author saul.ibarra
 * @fecha 17-Febrero-2017
 */
package com.alliax.portalclientes.model;

import java.math.BigDecimal;

import com.alliax.portalclientes.general.formato.Convertidor;

public class ItemFactura {
	
	//Variables
	private String docFactura;
	private int posicion;
	private String material;
	private String descripcion;
	private BigDecimal cantidad;
	private String unidadMedida;
	private BigDecimal montoNeto;
	private String moneda;
	private BigDecimal impuesto;	
	
	
	public String getDocFactura() {
		return docFactura;
	}
	public void setDocFactura(String docFactura) {
		this.docFactura = docFactura;
	}
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getCantidad() {
		return cantidad;
	}
	public String getCantidadStr() {
		return Convertidor.BigDecimalToString(cantidad);
	}	
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	public String getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	public BigDecimal getMontoNeto() {
		return montoNeto;
	}
	public void setMontoNeto(BigDecimal montoNeto) {
		this.montoNeto = montoNeto;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public BigDecimal getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(BigDecimal impuesto) {
		this.impuesto = impuesto;
	}
}
