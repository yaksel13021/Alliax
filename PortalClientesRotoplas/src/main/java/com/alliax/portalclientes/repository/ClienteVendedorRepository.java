package com.alliax.portalclientes.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.alliax.portalclientes.domain.ClienteVendedor;

public interface ClienteVendedorRepository extends CrudRepository<ClienteVendedor,Integer> {
	
	List<ClienteVendedor> findByNoCliente(String noCliente);
	
	List<ClienteVendedor> findByVendedor(String vendedor);
	
}
