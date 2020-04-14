package com.alliax.portalclientes.service.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.event.ListSelectionEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alliax.portalclientes.domain.Pais;
import com.alliax.portalclientes.repository.PaisRepository;
import com.alliax.portalclientes.service.PaisService;
import com.google.common.collect.Lists;

@Service("paisService")
@Repository
@Transactional
public class PaisServiceImpl implements PaisService {

	private final static Logger logger = Logger.getLogger(PaisServiceImpl.class);
	
	@Autowired
	private PaisRepository paisRepository;
	
	
	@Override
	@Transactional(readOnly=true)
	public List<Pais> findAll() {
		return Lists.newArrayList(this.paisRepository.findAll());						
	}
	
	@Transactional(readOnly=true)
	public Map<Integer,String> findAllSet(){
		Map<Integer,String> paises = new HashMap<Integer,String>();
		for(Pais pais : this.paisRepository.findAll() ){
			paises.put(pais.getIdPais(), pais.getDescripcion());
		}
		
		return paises;
	}
	

	@Override
	@Transactional(readOnly=true)
	public Pais findById(int idPais) {
		return this.paisRepository.findOne(idPais);
	}

	@Override
	public Pais save(Pais pais) {
		return this.paisRepository.save(pais);
	}
}
