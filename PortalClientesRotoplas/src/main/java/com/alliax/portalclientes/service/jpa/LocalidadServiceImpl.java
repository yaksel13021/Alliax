package com.alliax.portalclientes.service.jpa;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alliax.portalclientes.domain.Localidad;
import com.alliax.portalclientes.repository.LocalidadRepository;
import com.alliax.portalclientes.service.LocalidadService;
import com.google.common.collect.Lists;

@Service("localidadService")
@Repository
@Transactional
public class LocalidadServiceImpl implements LocalidadService {

	private final static Logger logger = Logger.getLogger(LocalidadServiceImpl.class);
	
	@Autowired
	private LocalidadRepository localidadRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public List<Localidad> findAll() {
		return Lists.newArrayList(this.localidadRepository.findAll());
	}

	@Override
	public Map<Integer, String> findAllByEstadoSet(int idEstado) {
		logger.debug("findAllByEstadoSet");
		
		Map<Integer,String> localidades = new LinkedHashMap<Integer,String>();
		
		TypedQuery<Localidad> query = em.createNamedQuery("Localidad.findByEstado",Localidad.class);
		query.setParameter("idEstado", idEstado);
		
		List<Localidad> resultados = query.getResultList();
		//localidades.put(0, "--Selecciona uno--");
		
		for(Localidad localidad : resultados){
			logger.trace("Localidad id " + localidad.getIdLocalidad() + " - " + localidad.getDescripcion());
			localidades.put(localidad.getIdLocalidad(), localidad.getDescripcion());
			//logger.info("Total Plantas " + localidad.getPlantas().size());
		}
		
		return localidades;
	}

	@Override
	public Localidad findById(int idLocalidad) {
		return this.localidadRepository.findOne(idLocalidad);
	}

	@Override
	public Localidad save(Localidad localidad) {
		return this.localidadRepository.save(localidad);
	}
}
