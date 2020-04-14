/**
 * Objeto para representar la informacion del cliente
 * @author saul.ibarra
 * @fecha 29-Feb-2016
 */
package com.alliax.portalclientes.model;

public class ClienteInfo {
	private String noCliente;
	private String nombre;
	private String calle;
	private String codigoPostal;
	private String ciudad;
	private String region;
	private String pais;
	private String email;
	private String fax;
	private String telefono;
	private String telefono2;
	private String lang;
	private String statusSAP; //A Desbloqueado  //B Desbloqueado  //C  Bloquead0
	
	public String getNoCliente() {
		return noCliente;
	}
	public void setNoCliente(String noCliente) {
		this.noCliente = noCliente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getTelefono2() {
		return telefono2;
	}
	public void setTelefono2(String telefonos2) {
		this.telefono2 = telefonos2;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getStatusSAP() {
		return statusSAP;
	}
	public void setStatusSAP(String statusSAP) {
		this.statusSAP = statusSAP;
	}	
}
