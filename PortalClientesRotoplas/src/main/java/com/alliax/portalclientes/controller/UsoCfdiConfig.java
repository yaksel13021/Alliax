package com.alliax.portalclientes.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;

import com.alliax.portalclientes.model.UsoCFDI;
import com.alliax.portalclientes.model.UsoCFDIDetalle;

@Configuration
public class UsoCfdiConfig {
	
	private final static Logger logger = Logger.getLogger(UsoCfdiConfig.class);
	
	public UsoCFDI usoCFDI(){
		logger.info("UsoCfdiConfig - usoCFDI");
		UsoCFDI resultado = new UsoCFDI();
		resultado.setResultCode("0");	
		List<UsoCFDIDetalle> detalles = new ArrayList<UsoCFDIDetalle>();
		
		UsoCFDIDetalle uno = new UsoCFDIDetalle();
		uno.setClaveUsoCFDI("CLAVE1");
		uno.setDescripcionClaveUsoCFDI("CLAVE1_DESCRIPCION");
		
		UsoCFDIDetalle dos = new UsoCFDIDetalle();
		dos.setClaveUsoCFDI("CLAVE2");
		dos.setDescripcionClaveUsoCFDI("CLAVE2_DESCRIPCION");
		
		UsoCFDIDetalle tres = new UsoCFDIDetalle();
		tres.setClaveUsoCFDI("CLAVE3");
		tres.setDescripcionClaveUsoCFDI("CLAVE3_DESCRIPCION");
		
		detalles.add(uno);  
		detalles.add(dos);  
		detalles.add(tres);  
		resultado.setDetalles(detalles);
		return resultado;
	}

}
