package com.alliax.portalclientes.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;

import com.alliax.portalclientes.model.DestinatarioMercancia;

@Configuration
public class BuscarDestinatariosMercanciasConfig {
	
	private final static Logger logger = Logger.getLogger(BuscarDestinatariosMercanciasConfig.class);
	
	public List<DestinatarioMercancia> buscarDestinatariosMercancias(String noCliente){
		logger.info("BuscarDestinatariosMercanciasConfig - buscarDestinatarios, noCliente: " + noCliente);
		List<DestinatarioMercancia> result = new ArrayList<DestinatarioMercancia>();
		DestinatarioMercancia uno = new DestinatarioMercancia();
		uno.setNoDestinatario("NoDestinatario");
		uno.setNombreSucursal("NombreSucursal");
		uno.setCalleNumero("CalleNumero");
		uno.setColonia("Colonia");
		uno.setCodigoPostal("CodigoPostal");
		uno.setPoblacion("Poblacion");
		uno.setSociedad("Socieda");
		uno.setOrganizacionVentas("OrganizacionVentas");
		
		DestinatarioMercancia dos = new DestinatarioMercancia();
		dos.setNoDestinatario("NoDestinatario");
		dos.setNombreSucursal("NombreSucursal");
		dos.setCalleNumero("CalleNumero");
		dos.setColonia("Colonia");
		dos.setCodigoPostal("CodigoPostal");
		dos.setPoblacion("Poblacion");
		dos.setSociedad("Socieda");
		dos.setOrganizacionVentas("OrganizacionVentas");
		
		DestinatarioMercancia tres = new DestinatarioMercancia();
		tres.setNoDestinatario("NoDestinatario");
		tres.setNombreSucursal("NombreSucursal");
		tres.setCalleNumero("CalleNumero");
		tres.setColonia("Colonia");
		tres.setCodigoPostal("CodigoPostal");
		tres.setPoblacion("Poblacion");
		tres.setSociedad("Socieda");
		tres.setOrganizacionVentas("OrganizacionVentas");
		
		result.add(uno);
		result.add(dos);
		result.add(tres);
		
		return result;
	}
}
