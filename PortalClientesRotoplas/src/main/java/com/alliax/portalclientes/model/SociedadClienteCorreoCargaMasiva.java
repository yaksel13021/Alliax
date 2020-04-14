package com.alliax.portalclientes.model;

public class SociedadClienteCorreoCargaMasiva {
	
	private String sociedad;
	private String noCliente;
	private String correo;
	private String nombreCliente;
	private String mensaje;
	private boolean checked;
	
	
	public SociedadClienteCorreoCargaMasiva(){
		
	}
	
	public SociedadClienteCorreoCargaMasiva(String sociedad, String noCliente, String correo, String nombreCliente,	String mensaje, boolean checked) {		
		this.sociedad = sociedad;
		this.noCliente = noCliente;
		this.correo = correo;
		this.nombreCliente = nombreCliente;
		this.mensaje = mensaje;
		this.checked = checked;
	}
	public String getSociedad() {
		return sociedad;
	}
	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}
	public String getNoCliente() {
		return noCliente;
	}
	public void setNoCliente(String noCliente) {
		this.noCliente = noCliente;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}


	
	
	

	
	
}
