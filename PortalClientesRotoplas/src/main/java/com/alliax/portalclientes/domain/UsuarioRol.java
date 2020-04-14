/**
 * Entidad para representar la tabla UsuarioRol
 * @author saul.ibarra
 * @fecha 24-Febrero-2016
 */
package com.alliax.portalclientes.domain;



public class UsuarioRol {

	private int idRol;
	private int idUsuario;
	private String rol;	
	
	//private Usuario usuario;
	

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public int getIdRol() {
		return idRol;
	}	
	
	
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}	
	
	public String getRol() {
		return rol;
	}
	
	/*@ManyToOne
	@JoinColumn(name="idUsuario")
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}*/
}
