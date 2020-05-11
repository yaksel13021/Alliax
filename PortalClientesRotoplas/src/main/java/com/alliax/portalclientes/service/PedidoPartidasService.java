package com.alliax.portalclientes.service;

import com.alliax.portalclientes.domain.PedidoPartidas;
import com.alliax.portalclientes.domain.PedidoPartidasPK;

import java.util.List;

public interface PedidoPartidasService {
	
	public List<PedidoPartidas> findAll();

	public PedidoPartidas findById(PedidoPartidasPK idPedidoPartidas);
	
	public PedidoPartidas save(PedidoPartidas pedido);

}
