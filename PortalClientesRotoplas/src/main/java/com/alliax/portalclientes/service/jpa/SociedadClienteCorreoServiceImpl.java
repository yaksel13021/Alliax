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
import com.alliax.portalclientes.domain.SociedadClienteCorreo;
import com.alliax.portalclientes.repository.ClienteVendedorRepository;
import com.alliax.portalclientes.repository.SociedadClienteCorreoRepository;
import com.alliax.portalclientes.service.ClienteVendedorService;
import com.alliax.portalclientes.service.SociedadClienteCorreoService;

@Service("sociedadClienteCorreoService")
@Repository
@Transactional
public class SociedadClienteCorreoServiceImpl implements SociedadClienteCorreoService {
	
	private final static Logger logger = Logger.getLogger(SociedadClienteCorreoServiceImpl.class);
		
	@Autowired
	public SociedadClienteCorreoRepository usuarioRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<SociedadClienteCorreo> findByNoCliente(String noCliente) {
		return this.usuarioRepository.findByNoCliente(noCliente);		
	}

	@Override
	public List<SociedadClienteCorreo> findBySociedad(String sociedad) { 
		return this.usuarioRepository.findBySociedad(sociedad);
	}
	
	@Override
	public SociedadClienteCorreo findBySociedadAndNoCliente(String sociedad, String noCliente) { 
		return this.usuarioRepository.findBySociedadAndNoCliente(sociedad, noCliente);
	}
	
	
	@Override
	public SociedadClienteCorreo save(SociedadClienteCorreo sociedadClienteCorreo) {
		SociedadClienteCorreo cv = null; 
		//= this.usuarioRepository.findByNoCliente(clienteVendedor.getNoCliente());
		logger.info("SociedadClienteCorreo method : " + sociedadClienteCorreo.getSociedad() + " " + sociedadClienteCorreo.getNoCliente());
		Query query1 = em.createQuery("FROM SociedadClienteCorreo scc WHERE scc.sociedad = :sociedad AND scc.noCliente = :noCliente");
		 query1.setParameter("sociedad", sociedadClienteCorreo.getSociedad());
		 query1.setParameter("noCliente", sociedadClienteCorreo.getNoCliente());

		Object obj;
		try{ obj = query1.getSingleResult(); }catch(Exception e){ logger.error("error al leer de base de datos 1 row... ", e); obj = null;}
		 
		if(obj == null){
			logger.info("Save SociedadClienteCorreo to Database: ");
			cv = this.usuarioRepository.save(sociedadClienteCorreo);
		}else{
			logger.info("UPDATE SociedadClienteCorreo to Database: ");
			try{
				if(obj instanceof SociedadClienteCorreo){ logger.info("instanceof SociedadClienteCorreo"); }				
				SociedadClienteCorreo scc = (SociedadClienteCorreo)obj;
				logger.info(scc.getIdSociedadClienteCorreo());
				logger.info(scc.getSociedad());
				logger.info(scc.getNoCliente());
				logger.info(sociedadClienteCorreo.getCorreo());
				
				Query query2 = em.createQuery("UPDATE SociedadClienteCorreo scc SET scc.correo = :correo WHERE scc.idSociedadClienteCorreo = :idSociedadClienteCorreo");
				 query2.setParameter("idSociedadClienteCorreo", scc.getIdSociedadClienteCorreo());
				 query2.setParameter("correo", sociedadClienteCorreo.getCorreo());
				query2.executeUpdate();
				
				cv = sociedadClienteCorreo;
//				if(sociedadClienteCorreo != null && !obj.getCorreo().equals(sociedadClienteCorreo.getCorreo())){
//					obj.setCorreo(sociedadClienteCorreo.getCorreo());
//					this.usuarioRepository.save(obj);				
//				}		
			}catch(Exception e){
				logger.error("",e);
			}
			
		}
		return cv;
	}
	
	@Override
	public void delete(String sociedad, String noCliente){
		Query query = em.createQuery("DELETE SociedadClienteCorreo scc WHERE scc.sociedad = :sociedad AND scc.noCliente = :noCliente");
		 query.setParameter("sociedad", sociedad);
		 query.setParameter("noCliente", noCliente);
		query.executeUpdate();
	}
	
	@Override
	public List<SociedadClienteCorreo> findAll(){
		return (List<SociedadClienteCorreo>) this.usuarioRepository.findAll();
	}
}
