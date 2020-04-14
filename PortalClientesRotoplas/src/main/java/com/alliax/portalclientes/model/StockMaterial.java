/**
 * Objeto para representar el stock de un material
 * @author saul.ibarra
 * @fecha 13-Febrero-2016
 */

package com.alliax.portalclientes.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.alliax.portalclientes.general.formato.Convertidor;

public class StockMaterial {
	
	private String noMaterial;
	private String descripcion;
	private String noPlanta;
	private String planta;
	private String almacen;
	private BigDecimal stock;
	private String umb;
	
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
	public String getNoPlanta() {
		return noPlanta;
	}
	public void setNoPlanta(String noPlanta) {
		this.noPlanta = noPlanta;
	}
	public String getPlanta() {
		return planta;
	}
	public void setPlanta(String planta) {
		this.planta = planta;
	}
	public String getAlmacen() {
		return almacen;
	}
	public void setAlmacen(String almacen) {
		this.almacen = almacen;
	}	
	public BigDecimal getStock() {
		return stock;
	}
	public String getStockStr(){
		return Convertidor.BigDecimalToString(this.stock);
	}	
	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}
	public String getUmb() {
		return umb;
	}
	public void setUmb(String umb) {
		this.umb = umb;
	}
}
