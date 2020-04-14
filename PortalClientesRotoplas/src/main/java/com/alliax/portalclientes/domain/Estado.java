package com.alliax.portalclientes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="Estado")
@NamedQueries({
	@NamedQuery(name="Estado.findByPais",
			query="select e from Estado e where e.idPais = :idPais order by e.descripcion asc")
})
public class Estado {

	private int idEstado;
	private String descripcion;
	private int idPais;
	private boolean activo;

	
	@Id
	@Column(name="idEstado")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
	
	@Column(name="descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="idPais")
	public int getIdPais() {
		return idPais;
	}
	public void setIdPais(int idPais) {
		this.idPais = idPais;
	}
	
	@Column(name="activo")
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
