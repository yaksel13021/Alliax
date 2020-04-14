package com.alliax.portalclientes.service;

import java.util.List;

import com.alliax.portalclientes.domain.ClienteVendedor;

public interface ClienteVendedorService {
			
	public List<ClienteVendedor> findByNoCliente(String noCliente);
	
	public List<ClienteVendedor> findByNoClienteList(String vendedor);	
	
	public ClienteVendedor save(ClienteVendedor clienteVendedor);
	
	public void delete(String noCliente, String vendedor);
	
	public List<ClienteVendedor> findAll();
}
