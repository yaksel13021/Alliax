/**
 * Objeto para representar Estados de Cuenta
 * @author ext.juan.gonzalez
 * @fecha 15-Agosto-2018 
 */
package com.alliax.portalclientes.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EstadoCuenta implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String numCliente; //CUSTOMER
	private String nombre;
	private String nombre2;
	private String nombre3;
	private String nombre4;
	private String rfc;
	private String tel;
	private String tel2;
	private String eMail;
	private String moneda;
	private BigDecimal limiteCredito; //CREDIT_LIMIT
	private BigDecimal saldoVencido; //VENCIDO_VALUE
	private BigDecimal saldoVencer; //AVENCER_VALUE
	private BigDecimal total;//TOTAL_VALUE
	private BigDecimal creditoDisponible;//CREDIT_AVAILABLE
	private Date fechaCorte; //BCORTE_DATE
	private String pais; //COUNTRY
	private String signoPesos;
	
	//Render del detalle
	private boolean muestraDetalle;
	
	//Detalle del Estado de cuenta
	private List<EstadoCuentaDet> detalle;

	public String getNumCliente() {
		return numCliente;
	}

	public void setNumCliente(String numCliente) {
		this.numCliente = numCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre2() {
		return nombre2;
	}

	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}

	public String getNombre3() {
		return nombre3;
	}

	public void setNombre3(String nombre3) {
		this.nombre3 = nombre3;
	}

	public String getNombre4() {
		return nombre4;
	}

	public void setNombre4(String nombre4) {
		this.nombre4 = nombre4;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public BigDecimal getLimiteCredito() {
		return limiteCredito;
	}

	public void setLimiteCredito(BigDecimal limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	public BigDecimal getSaldoVencido() {
		return saldoVencido;
	}

	public void setSaldoVencido(BigDecimal saldoVencido) {
		this.saldoVencido = saldoVencido;
	}

	public BigDecimal getSaldoVencer() {
		return saldoVencer;
	}

	public void setSaldoVencer(BigDecimal saldoVencer) {
		this.saldoVencer = saldoVencer;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getCreditoDisponible() {
		return creditoDisponible;
	}

	public void setCreditoDisponible(BigDecimal creditoDisponible) {
		this.creditoDisponible = creditoDisponible;
	}

	public Date getFechaCorte() {
		return fechaCorte;
	}

	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	public boolean isMuestraDetalle() {
		return muestraDetalle;
	}

	public void setMuestraDetalle(boolean muestraDetalle) {
		this.muestraDetalle = muestraDetalle;
	}

	public List<EstadoCuentaDet> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<EstadoCuentaDet> detalleFactura) {
		this.detalle = detalleFactura;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getSignoPesos() {
		return signoPesos;
	}

	public void setSignoPesos(String signoPesos) {
		this.signoPesos = signoPesos;
	}
	

}
