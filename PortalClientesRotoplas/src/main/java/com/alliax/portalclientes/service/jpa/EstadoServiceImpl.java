package com.alliax.portalclientes.service.jpa;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alliax.portalclientes.domain.Estado;
import com.alliax.portalclientes.repository.EstadoRepository;
import com.alliax.portalclientes.service.EstadoService;
import com.google.common.collect.Lists;

@Service("estadoService")
@Repository
@Transactional
public class EstadoServiceImpl implements EstadoService {
	
	private final static Logger logger = Logger.getLogger(EstadoServiceImpl.class);

	@Autowired
	private EstadoRepository estadoRepository;
	
	@PersistenceContext
	private EntityManager em;	
	
	private final String FINDBYPLANTA = "SELECT DISTINCT es.idestado, es.descripcion, es.idPais, es.activo " +
					"FROM LocalidadPlanta lp " +
					"INNER JOIN localidad l on l.idlocalidad = lp.idlocalidad " +
					"INNER JOIN estado es ON es.idestado = l.idestado " +
					"WHERE lp.idPlanta = ? " +
					"ORDER BY es.descripcion ASC";
	
	private final String FINDBYPLANTAStr = "SELECT DISTINCT es.idestado, es.descripcion, es.idPais, es.activo " +
			"FROM LocalidadPlantaStr lp " +
			"INNER JOIN localidad l on l.idlocalidad = lp.idlocalidad " +
			"INNER JOIN estado es ON es.idestado = l.idestado " +
			"WHERE lp.idPlanta = ? " +
			"ORDER BY es.descripcion ASC";	
	
	@Override
	@Transactional(readOnly=true)
	public List<Estado> findAll() {
		return Lists.newArrayList(this.estadoRepository.findAll());
	}

	@Override
	@Transactional(readOnly=true)
	public Map<Integer, String> findAllByPaisSet(int idPais) {
		Map<Integer,String> estados = new LinkedHashMap<Integer,String>();
		TypedQuery<Estado> query = em.createNamedQuery("Estado.findByPais",Estado.class);
		query.setParameter("idPais",idPais);
		
		List<Estado> resultados = query.getResultList();
		//estados.put(0, "--Selecciona uno--");
		
		for(Estado estado : resultados){
			logger.trace("Estado " + estado.getDescripcion());
			estados.put(estado.getIdEstado(), estado.getDescripcion());
		}
		return estados;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Map<Integer,String> findByPlanta(String idPlanta){
		logger.info("findByPlanta " + idPlanta);
		
		logger.info("query " + FINDBYPLANTA);
		
		Map<Integer,String> estados = new LinkedHashMap<Integer,String>();		
		List<Estado> resultado = em.createNativeQuery(FINDBYPLANTAStr,Estado.class).setParameter(1, idPlanta).getResultList();
		
		logger.info("Total estados " + resultado.size());
		
		for(Estado estado : resultado){
			estados.put(estado.getIdEstado(), estado.getDescripcion());
		}
		
		return estados;		
	}
	

	@Override
	@Transactional(readOnly=true)
	public Estado findById(int idEstado) {		
		return this.estadoRepository.findOne(idEstado);
	}

	@Override
	public Estado save(Estado estado) {
		return this.estadoRepository.save(estado);
	}

}
