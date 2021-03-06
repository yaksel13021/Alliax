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
		uno.setNoDestinatario("1234567890");
		uno.setNombreSucursal("NombreSucursal1");
		uno.setCalleNumero("CalleNumero1");
		uno.setColonia("Colonia1");
		uno.setCodigoPostal("64610");
		uno.setPoblacion("Poblacion1");
		uno.setSociedad("Socieda1");
		uno.setOrganizacionVentas("9874563210");
		
		DestinatarioMercancia dos = new DestinatarioMercancia();
		dos.setNoDestinatario("1234567891");
		dos.setNombreSucursal("NombreSucursal2");
		dos.setCalleNumero("CalleNumero2");
		dos.setColonia("Colonia2");
		dos.setCodigoPostal("CodigoPostal2");
		dos.setPoblacion("Poblacion2");
		dos.setSociedad("Socieda2");
		dos.setOrganizacionVentas("9874563211");
		
		DestinatarioMercancia tres = new DestinatarioMercancia();
		tres.setNoDestinatario("1234567892");
		tres.setNombreSucursal("NombreSucursal3");
		tres.setCalleNumero("CalleNumero3");
		tres.setColonia("Colonia3");
		tres.setCodigoPostal("CodigoPostal3");
		tres.setPoblacion("Poblacion3");
		tres.setSociedad("Socieda3");
		tres.setOrganizacionVentas("9874563212");
		
		result.add(uno);
		result.add(dos);
		result.add(tres);
		
		return result;
	}
}
