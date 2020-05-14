/**
 * Clase para Informacion de Usuarios que accessan al portal
 * @author saul.ibarra
 * @fecha 27-Ene-2016
 * 
 */
package com.alliax.portalclientes.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.alliax.portalclientes.general.formato.Fecha;

@Entity
@Table(name="Usuario")
@NamedQueries({
	@NamedQuery(name="Usuario.findByUsuario",
			query="select u from Usuario u where u.usuario = :usuario"),
	@NamedQuery(name="Usuario.findByUsrPwd",
			query="select u from Usuario u where u.usuario = :usuario and u.password = :password"),
	@NamedQuery(name="Usuario.findByNoCliente",
			query="select u from Usuario u where u.usuario like :usuario")
})
public class Usuario {

	//Variables
	private int idUsuario;
	private String noCliente;		
	private String email;	
	private String usuario;	
	private String password;
	private String passwordStr;
	private DateTime fechaEntrada;	
	private int intentosFallidos;
	private DateTime fechaAlta;
	//private String rol;
	private String estatus; /* A:Activo, F:Bloqueado x Intentos Fallidos, N: Bloquedo x Inactivo, B:Bloqueado Administrador, I:Password Inicial, C:Password Caducado */
	private String pais;
	private String parent;
		

	private Set<RolUsuario> roles = new HashSet<RolUsuario>();
	
	
	/**
	 * Seccion para Pruebas - Remover en ambientes PRD //SIL 8Abril15
	 */
	/*@PostConstruct
	public void init(){
		this.setIdUsuario(1);
		this.setIdEmpresa(0);
		this.setRFC("IALS850402QW0");
		this.setNombre("Saul");
		this.setApellidos("Ibarra");
		this.setEmail("saul.ibarra@alliax.com");
		this.setUsuario("IALS850402QW0");
		this.setAdmin(1);
		this.setMergeXml(false);
		this.setEstatus("A");
	}*/
	/**
	 * Fin de Seccion de Prueba
	 */
	
	
	@Id
	@Column(name="idUsuario")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	@Column(name="noCliente")
	public String getNoCliente() {
		return noCliente;
	}
	public void setNoCliente(String noCliente) {
		this.noCliente = noCliente;
	}
	
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name="usuario")
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Transient
	public String getPasswordStr() {
		return passwordStr;
	}
	public void setPasswordStr(String passwordStr) {
		this.passwordStr = passwordStr;
	}
	
	@Column(name="fechaEntrada")	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	//@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public DateTime getFechaEntrada() {		
		//DateTimeZone.setDefault(DateTimeZone.forTimeZone(TimeZone.getTimeZone("America/Mexico_City")));
		return fechaEntrada;
	}
	public void setFechaEntrada(DateTime fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}
	
	@Column(name="intentosFallidos")
	public int getIntentosFallidos() {
		return intentosFallidos;
	}
	public void setIntentosFallidos(int intentosFallidos) {
		this.intentosFallidos = intentosFallidos;
	}
		
	@Column(name="fechaAlta")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")	
	public DateTime getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(DateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	/*@Column(name="rol")
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}*/
	
	@Column(name="estatus")
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	@Column(name="pais")
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	@Column(name="parent")
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
		
	//@OneToMany(fetch = FetchType.EAGER, mappedBy="usuario", cascade=CascadeType.ALL, orphanRemoval=true)
	@OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="idUsuario")
	public Set<RolUsuario> getRoles() {
		return roles;
	}
	public void setRoles(Set<RolUsuario> roles) {
		this.roles = roles;
	}
	
	public void setRol(RolUsuario rol){
		try{
			this.roles.add(rol);
		} catch(Exception e){
			
		}
	}
	
 }
