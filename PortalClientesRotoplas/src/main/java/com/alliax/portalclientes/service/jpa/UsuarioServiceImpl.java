package com.alliax.portalclientes.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alliax.portalclientes.domain.Usuario;
import com.alliax.portalclientes.repository.UsuarioRepository;
import com.alliax.portalclientes.service.UsuarioService;

@Service("usuarioService")
@Repository
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
	
	private final static Logger logger = Logger.getLogger(UsuarioServiceImpl.class);
		
	@Autowired
	public UsuarioRepository usuarioRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly=true)
	public Usuario findByUserName(String usuario) {
		try {			
			//this.em.getEntityManagerFactory().getCache().evictAll();
			TypedQuery<Usuario> query = this.em.createNamedQuery("Usuario.findByUsuario",Usuario.class);
			query.setParameter("usuario", usuario);
			
			logger.debug("Despues de setear parametos");
			
			return query.getSingleResult();
		} catch(NoResultException nre){
			logger.error("No existe el usuario " + nre.getLocalizedMessage());
			return null;
		}
	}
	
	
	@Override
	public Usuario findByUsrPwd(String usuario, String password) {
		TypedQuery<Usuario> query = this.em.createNamedQuery("Usuario.findByUsrPwd",Usuario.class);
		query.setParameter("usuario", usuario);
		query.setParameter("password", password);
		
		return query.getSingleResult();
	}


	@Override
	public List<Usuario> findByClientNumber(String noCliente) {
		TypedQuery<Usuario> query = this.em.createNamedQuery("Usuario.findByNoCliente",Usuario.class)
										.setMaxResults(10);
		query.setParameter("usuario", noCliente + "%");
		return query.getResultList();
	}
	

	@Override
	public Usuario findById(int idUsuario) {
		return this.usuarioRepository.findOne(idUsuario);
	}


	@Override
	public Usuario save(Usuario usuario) {
		logger.info("Grabando usuario " + usuario.getUsuario());
		
		logger.info("Intentos fallidos " + usuario.getIntentosFallidos());
		
		return this.usuarioRepository.save(usuario);
	}


	@Override
	public List<Usuario> findAll() {				
		return (List<Usuario>) this.usuarioRepository.findAll();
	}
}
