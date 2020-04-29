package com.alliax.portalclientes.model;

import java.util.List;

public class UsoCFDI {
	
	private String resultCode;
	private List<UsoCFDIDetalle> detalles;
	
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public List<UsoCFDIDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<UsoCFDIDetalle> detalles) {
		this.detalles = detalles;
	}
	
	
	

}
