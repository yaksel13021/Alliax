package com.alliax.portalclientes.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alliax.portalclientes.domain.ClienteVendedor;
import com.alliax.portalclientes.repository.ClienteVendedorRepository;
import com.alliax.portalclientes.service.ClienteVendedorService;

@Service("clienteVendedorService")
@Repository
@Transactional
public class ClienteVendedorServiceImpl implements ClienteVendedorService {
	
	private final static Logger logger = Logger.getLogger(ClienteVendedorServiceImpl.class);
		
	@Autowired
	public ClienteVendedorRepository usuarioRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<ClienteVendedor> findByNoCliente(String noCliente) {
		return this.usuarioRepository.findByNoCliente(noCliente);		
	}

	@Override
	public List<ClienteVendedor> findByNoClienteList(String vendedor) { 
		return this.usuarioRepository.findByVendedor(vendedor);
	}
	
	@Override
	public ClienteVendedor save(ClienteVendedor clienteVendedor) {
		ClienteVendedor cv; 
		//= this.usuarioRepository.findByNoCliente(clienteVendedor.getNoCliente());
		logger.info("ClienteVendedor method : " + clienteVendedor.getNoCliente() + " " + clienteVendedor.getVendedor());
		Query query1 = em.createNativeQuery("SELECT * FROM ClienteVendedor cv WHERE cv.vendedor = :vendedor AND cv.noCliente = :noCliente");
		 query1.setParameter("vendedor", clienteVendedor.getVendedor());
		 query1.setParameter("noCliente", clienteVendedor.getNoCliente());
		Object obj;
		try{ obj = query1.getSingleResult(); }catch(Exception e){ obj = null;
		}
		 
		if(obj == null){
			logger.info("BeforeSave: ");
			cv = this.usuarioRepository.save(clienteVendedor);
		}else{
//			Query query2 = em.createQuery("UPDATE ClienteVendedor cv SET cv.vendedor = :vendedor WHERE cv.noCliente = :noCliente");
//			 query2.setParameter("vendedor", clienteVendedor.getVendedor());
//			 query2.setParameter("noCliente", clienteVendedor.getNoCliente());
//			query2.executeUpdate();
			cv = clienteVendedor;
		}
		return cv;
	}
	
	@Override
	public void delete(String noCliente, String vendedor){
		Query query = em.createQuery("DELETE ClienteVendedor cv WHERE cv.vendedor = :vendedor AND cv.noCliente = :noCliente");
		 query.setParameter("vendedor", vendedor);
		 query.setParameter("noCliente", noCliente);
		query.executeUpdate();
	}
	
	@Override
	public List<ClienteVendedor> findAll(){
		return (List<ClienteVendedor>) this.usuarioRepository.findAll();
	}
}
