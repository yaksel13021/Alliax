package com.alliax.portalclientes.domain;

import java.util.Date;

public class HistoriaPwd {

	private int idHistoriaPwd;
	private int idUsuario;
	private byte[] password;
	private Date fechaCambio;
	
	
	public int getIdHistoriaPwd() {
		return idHistoriaPwd;
	}
	public void setIdHistoriaPwd(int idHistoriaPwd) {
		this.idHistoriaPwd = idHistoriaPwd;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public byte[] getPassword() {
		return password;
	}
	public void setPassword(byte[] password) {
		this.password = password;
	}
	public Date getFechaCambio() {
		return fechaCambio;
	}
	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}			
}
