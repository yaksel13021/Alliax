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
@Table(name="ClienteVendedor")
public class ClienteVendedor implements Serializable{

	private static final long serialVersionUID = 2179690408489449025L;
	
	//Variables
	private int idClienteVendedor;
	private String noCliente;		
	private String vendedor;				
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idClienteVendedor")
	public int getIdClienteVendedor() {
		return idClienteVendedor;
	}
	public void setIdClienteVendedor(int idClienteVendedor) {
		this.idClienteVendedor = idClienteVendedor;
	}
	
	@Column(name="noCliente")
	public String getNoCliente() {
		return noCliente;
	}
	public void setNoCliente(String noCliente) {
		this.noCliente = noCliente;
	}
	
	@Column(name="vendedor")
	public String getVendedor() {
		return vendedor;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}				
	
}


