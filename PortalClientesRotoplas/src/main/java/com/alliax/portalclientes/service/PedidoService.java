package com.alliax.portalclientes.service;

import com.alliax.portalclientes.domain.Pedido;

import java.util.List;

public interface PedidoService {
	
	public List<Pedido> findAll();

	public Pedido findById(long idPedido);
	
	public Pedido save(Pedido pedido);

}
