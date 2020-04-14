package com.alliax.portalclientes.service.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alliax.portalclientes.domain.Planta;
import com.alliax.portalclientes.repository.PlantaRepository;
import com.alliax.portalclientes.service.PlantaService;
import com.google.common.collect.Lists;

@Service("plantaService")
@Repository
@Transactional
public class PlantaServiceImpl implements PlantaService {
	@Autowired
	private PlantaRepository plantaRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Planta> findAll() {
		return Lists.newArrayList(this.plantaRepository.findAll());
	}
	
	@Override
	public Map<String,String> findAllMap() {
		Map<String,String> plantas = new HashMap<String,String>();
		List<Planta> resultados = this.findAll();
		
		for(Planta planta : resultados){
			plantas.put(planta.getIdPlanta(), planta.getDescripcion());
		}
		
		return plantas;		
	}	

	@Override
	public Map<String, String> findByLocalidad(int idLocalidad) {
		Map<String,String> plantas = new HashMap<String,String>();
		
		TypedQuery<Planta> query = em.createNamedQuery("Planta.findByLocalidad",Planta.class);
		query.setParameter("idLocalidad", idLocalidad);
		
		List<Planta> resultados = query.getResultList();
		
		for(Planta planta : resultados){
			plantas.put(planta.getIdPlanta(), planta.getDescripcion());
		}
		
		return plantas;
	}
	
	@Override
	public Planta findByIdLocalidad(int idLocalidad) {
		TypedQuery<Planta> query = em.createNamedQuery("Planta.findByLocalidad",Planta.class);
		query.setParameter("idLocalidad", idLocalidad);
		
		return query.getSingleResult();
	}	
	

	@Override
	public Planta findById(int idPlanta) {
		return this.plantaRepository.findOne(idPlanta);
	}

	@Override
	public Planta save(Planta planta) {
		return this.plantaRepository.save(planta);
	}



}
