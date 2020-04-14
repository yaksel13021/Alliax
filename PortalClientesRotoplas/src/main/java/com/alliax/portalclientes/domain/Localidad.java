package com.alliax.portalclientes.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="Localidad")
@NamedQueries({
	@NamedQuery(name="Localidad.findByEstado",
			query="select l from Localidad l where l.idEstado = :idEstado order by l.descripcion asc"),
	@NamedQuery(name="Localidad.findByLocalidad",
			query="select l from Localidad l where l.idLocalidad = :idLocalidad order by l.descripcion asc")
})
public class Localidad {
	private int idLocalidad;
	private String descripcion;
	private int idEstado;
	private boolean activo;	
	//private List<Planta> plantas = new ArrayList<Planta>();
	
	@Id
	@Column(name="idLocalidad")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getIdLocalidad() {
		return idLocalidad;
	}
	public void setIdLocalidad(int idLocalidad) {
		this.idLocalidad = idLocalidad;
	}
	
	@Column(name="descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="idEstado")
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
	
	@Column(name="activo")
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	/*@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="LocalidadPlanta",
		joinColumns = @JoinColumn(name="idLocalidad"),
		inverseJoinColumns = @JoinColumn(name="idPlanta"))
	public List<Planta> getPlantas() {
		return plantas;
	}
	public void setPlantas(List<Planta> plantas) {
		this.plantas = plantas;
	}*/

}
