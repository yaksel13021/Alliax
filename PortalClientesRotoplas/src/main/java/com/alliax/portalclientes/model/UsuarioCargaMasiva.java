package com.alliax.portalclientes.model;

import com.alliax.portalclientes.domain.Usuario;

public class UsuarioCargaMasiva {
	private Usuario usuario;
	private ClienteInfo clienteInfo;
	private String mensaje;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public ClienteInfo getClienteInfo() {
		return clienteInfo;
	}
	public void setClienteInfo(ClienteInfo clienteInfo) {
		this.clienteInfo = clienteInfo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
