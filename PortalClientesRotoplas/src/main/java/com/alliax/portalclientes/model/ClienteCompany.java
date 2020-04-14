/**
 * Objeto para representar la información de la compañia del cliente
 * @author ext.juan.gonzalez
 * @fecha 16-Ago-2018
 */
package com.alliax.portalclientes.model;

public class ClienteCompany {

	private String noCliente;
	private String companyCode;
	private String companyName;
	
	public String getNoCliente() {
		return noCliente;
	}
	public void setNoCliente(String noCliente) {
		this.noCliente = noCliente;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
