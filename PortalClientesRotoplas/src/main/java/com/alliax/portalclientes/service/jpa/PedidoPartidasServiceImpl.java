package com.alliax.portalclientes.service.jpa;

import com.alliax.portalclientes.domain.PedidoPartidas;
import com.alliax.portalclientes.domain.PedidoPartidasPK;
import com.alliax.portalclientes.repository.PedidoPartidasRepository;
import com.alliax.portalclientes.repository.PedidoRepository;
import com.alliax.portalclientes.service.PedidoPartidasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("pedidoPartidasService")
@Repository
@Transactional
public class PedidoPartidasServiceImpl implements PedidoPartidasService {

    @Autowired
    private PedidoPartidasRepository pedidoPartidasRepository;

    @Override
    public List<PedidoPartidas> findAll() {
        return (List<PedidoPartidas>)pedidoPartidasRepository.findAll();
    }

    @Override
    public PedidoPartidas findById(PedidoPartidasPK idPedidoPartidas) {
        return  pedidoPartidasRepository.findOne(idPedidoPartidas);
    }

    @Override
    public PedidoPartidas save(PedidoPartidas pedido) {
        return pedidoPartidasRepository.save(pedido);
    }
}
