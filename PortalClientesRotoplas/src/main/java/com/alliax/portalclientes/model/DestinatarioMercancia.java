package com.alliax.portalclientes.model;

public class DestinatarioMercancia {

	private String noDestinatario;
	private String nombreSucursal;
	private String calleNumero;
	private String colonia;
	private String codigoPostal;
	private String poblacion;
	private String sociedad;
	private String organizacionVentas;
	
	public String getNoDestinatario() {
		return noDestinatario;
	}
	public void setNoDestinatario(String noDestinatario) {
		this.noDestinatario = noDestinatario;
	}
	public String getNombreSucursal() {
		return nombreSucursal;
	}
	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}
	public String getCalleNumero() {
		return calleNumero;
	}
	public void setCalleNumero(String calleNumero) {
		this.calleNumero = calleNumero;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getPoblacion() {
		return poblacion;
	}
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}
	public String getSociedad() {
		return sociedad;
	}
	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}
	public String getOrganizacionVentas() {
		return organizacionVentas;
	}
	public void setOrganizacionVentas(String organizacionVentas) {
		this.organizacionVentas = organizacionVentas;
	}

	@Override
	public String toString() {
		return "DestinatarioMercancia[" +
				"noDestinatario='" + noDestinatario +
				", nombreSucursal='" + nombreSucursal +
				", calleNumero='" + calleNumero +
				", colonia='" + colonia +
				", codigoPostal='" + codigoPostal +
				", poblacion='" + poblacion +
				", sociedad='" + sociedad +
				", organizacionVentas='" + organizacionVentas +
				']';
	}
}
