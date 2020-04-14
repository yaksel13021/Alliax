package com.alliax.portalclientes.service;

import java.util.List;

import com.alliax.portalclientes.domain.ClienteVendedor;
import com.alliax.portalclientes.domain.SociedadClienteCorreo;

public interface SociedadClienteCorreoService {
			
	public List<SociedadClienteCorreo> findByNoCliente(String noCliente);

	public List<SociedadClienteCorreo> findBySociedad(String sociedad);
	
	public SociedadClienteCorreo findBySociedadAndNoCliente(String sociedad, String noCliente);
	
	public SociedadClienteCorreo save(SociedadClienteCorreo sociedadClienteCorreo);
	
	public void delete(String sociedad, String noCliente);
	
	public List<SociedadClienteCorreo> findAll();
}
