/**
 * Clase para Informacion de Usuarios que accessan al portal
 * @author saul.ibarra
 * @fecha 27-Ene-2016
 * 
 */
package com.alliax.portalclientes.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="SociedadClienteCorreo")
public class SociedadClienteCorreo implements Serializable{

	private static final long serialVersionUID = 2179690408489449025L;
	
	//Variables
	private int idSociedadClienteCorreo;
	private String sociedad;		
	private String noCliente;
	private String nombreCliente;
	private String correo;	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idSociedadClienteCorreo")
	public int getIdSociedadClienteCorreo() {
		return idSociedadClienteCorreo;
	}
	public void setIdSociedadClienteCorreo(int idSociedadClienteCorreo) {
		this.idSociedadClienteCorreo = idSociedadClienteCorreo;
	}
	
	@Column(name="sociedad")
	public String getSociedad() {
		return sociedad;
	}
	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}
	
	@Column(name="noCliente")
	public String getNoCliente() {
		return noCliente;
	}
	public void setNoCliente(String noCliente) {
		this.noCliente = noCliente;
	}
	
	@Column(name="nombreCliente")
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	
	@Column(name="correo")
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
}


