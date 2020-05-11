package com.alliax.portalclientes.service.jpa;

import com.alliax.portalclientes.domain.Pedido;
import com.alliax.portalclientes.repository.PedidoRepository;
import com.alliax.portalclientes.service.PedidoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("pedidoService")
@Repository
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final static Logger logger = Logger.getLogger(PedidoServiceImpl.class);

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    @Transactional(readOnly=true)
    public List<Pedido> findAll() {
        return (List<Pedido>)pedidoRepository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
    public Pedido findById(long idPedido) {
        return pedidoRepository.findOne(idPedido);
    }

    @Override
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }
}
