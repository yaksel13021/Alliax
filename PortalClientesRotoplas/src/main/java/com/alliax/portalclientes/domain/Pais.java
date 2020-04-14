/**
 * Objeto para representar la tabla Pais
 * @author saul.ibarra
 * @fecha 11-Febrero-16
 */
package com.alliax.portalclientes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Pais")
public class Pais {
	
	private int idPais;
	private String descripcion;
	private boolean activo;
	
	@Id
	@Column(name="idPais")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getIdPais() {
		return idPais;
	}
	public void setIdPais(int idPais) {
		this.idPais = idPais;
	}

	@Column(name="descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="activo")
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
