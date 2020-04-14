package com.alliax.portalclientes.service.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alliax.portalclientes.domain.RolUsuario;
import com.alliax.portalclientes.repository.RolUsuarioRepository;
import com.alliax.portalclientes.service.RolUsuarioService;



@Service("rolUsuarioService")
@Repository
@Transactional
public class RolUsuarioImpl  implements RolUsuarioService {

	@Autowired
	private RolUsuarioRepository rolRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void delete(RolUsuario rol) {
		this.rolRepository.delete(rol);		
	}
	
}
