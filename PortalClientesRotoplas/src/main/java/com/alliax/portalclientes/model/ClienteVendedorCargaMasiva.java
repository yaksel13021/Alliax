package com.alliax.portalclientes.model;

public class ClienteVendedorCargaMasiva {
	
	private String noCliente;
	private String nombreCliente;
	private String vendedor;
	private String mensaje;
	
	
	
	public String getNoCliente() {
		return noCliente;
	}
	public void setNoCliente(String cliente) {
		this.noCliente = cliente;
	}
	public String getVendedor() {
		return vendedor;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}	
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
