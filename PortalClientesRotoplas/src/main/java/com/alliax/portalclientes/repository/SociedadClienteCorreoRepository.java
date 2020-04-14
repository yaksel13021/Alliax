package com.alliax.portalclientes.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.alliax.portalclientes.domain.SociedadClienteCorreo;

public interface SociedadClienteCorreoRepository extends CrudRepository<SociedadClienteCorreo,Integer> {
	
	List<SociedadClienteCorreo> findByNoCliente(String noCliente);
	
	List<SociedadClienteCorreo> findBySociedad(String noCliente);
	
	SociedadClienteCorreo findBySociedadAndNoCliente(String sociedad, String noCliente);
	
	
	
}
