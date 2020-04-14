package com.alliax.portalclientes.domain;

import java.util.ArrayList;
import java.util.Collection;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Planta")
@NamedQueries({
	@NamedQuery(name="Planta.findByLocalidad",
		query = "select p from Planta p inner join p.localidad l where l.idLocalidad = :idLocalidad order by p.descripcion ASC")
		//query = "select p from Planta p where p.idPlanta = :idLocalidad")
})
public class Planta {
	
	private String idPlanta;
	//private String code;
	private String descripcion;
	private boolean activo;
	
	private List<Localidad> localidad = new ArrayList<Localidad>();
	
	@Id
	@Column(name="idPlanta")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public String getIdPlanta() {
		return idPlanta;
	}
	public void setIdPlanta(String idPlanta) {
		this.idPlanta = idPlanta;
	}
		
	/*@Column(name="code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}*/
	
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
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="LocalidadPlantaStr",
		joinColumns = @JoinColumn(name="idPlanta"), 
		inverseJoinColumns = @JoinColumn(name="idLocalidad"))
	public List<Localidad> getLocalidad() {
		return localidad;
	}
	public void setLocalidad(List<Localidad> localidad) {
		this.localidad = localidad;
	}
	
	
	
	
}
