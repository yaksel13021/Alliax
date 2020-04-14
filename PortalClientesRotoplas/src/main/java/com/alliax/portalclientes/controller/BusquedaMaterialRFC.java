/**
 * Clase para buscar manteriales en SAP
 * @author saul.ibarra
 */
package com.alliax.portalclientes.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alliax.portalclientes.general.formato.Convertidor;
import com.alliax.portalclientes.general.sap.SAPConexionPool;
import com.alliax.portalclientes.model.Material;
import com.sap.mw.jco.JCO;

@Service("busquedaMaterial")
public class BusquedaMaterialRFC {
	
	@Autowired
	private SAPConexionPool sapCon;

	private final static Logger logger = Logger.getLogger(BusquedaMaterialRFC.class);

    //Variables de RFC
    private JCO.Table table;
    private JCO.ParameterList input;    
    private JCO.ParameterList output;  
    
    /*@Resource(name="busquedaMateriales")
    private List<Material> resultados;*/
        
	public List<Material> buscaMaterial(String noMaterial, String descripcion, String planta, String lang) throws Exception {
		try{
			this.initModule();
			this.initParamVariables();
			this.setParamValues(noMaterial,descripcion,planta,lang);
			
			return this.getResultTables();
			
			//return this.resultados;
			
		} catch(Exception e){
			logger.error("Error al obtener stock de material: " + noMaterial);
			throw new Exception("Error al obtener listado de pedidos");
		} finally {
			try{
				this.sapCon.sendToSleep();
			} catch(Exception e){}
		}
	}
	
	
	private void initModule() throws Exception {
        // inicializa la Conexion con SAP
        logger.info("initModule");
        try {
        	//Valida y Crea conexion a SAP
        	this.sapCon.creaConexion();        	
        	
            logger.info("Asigna ZCRM_MATERIAL_SEARCH");
            this.sapCon.doSetFunction("ZCRM_MATERIAL_SEARCH");

            logger.info("Termina Modulo de conexion");
        } catch (Exception e) {
        	logger.fatal("Error en initModule - " + e.getLocalizedMessage(),e);
            throw new Exception ("Error en initModule - " + e.getLocalizedMessage(),e);
        }
	}
	

	private void initParamVariables() throws Exception  {
        try {
        	logger.info("initParamVariables");
        	
        	this.table = this.sapCon.getFunction().getTableParameterList().getTable("MATERIAL_LIST");
            this.input = this.sapCon.getFunction().getImportParameterList();           
            this.output = this.sapCon.getFunction().getExportParameterList();
            
        } catch(Exception e) {
            logger.fatal("Error al inicializar tablas " + e.getLocalizedMessage(),e);            
            throw new Exception (e.getLocalizedMessage(),e);
        }
	}	
	
	
    /**
     * Establece parametros de entrada
     */
	private void setParamValues(String noMaterial, String descripcion, String planta, String lang) throws Exception  {
		try{			
			logger.info("MATERIAL: " + noMaterial);
			logger.info("MATL_DESC: %" + descripcion + "%");
			logger.info("PLANT: " + planta);
			logger.info("LANGU: " + lang);
												
			if(descripcion != null){
				this.input.setValue("%" + descripcion + "%", "MATL_DESC");
				this.input.setValue("", "MATERIAL"); //18Posiciones
			} else { 
				this.input.setValue(noMaterial, "MATERIAL"); //18Posiciones
				this.input.setValue("", "MATL_DESC");
			}
						
			this.input.setValue(planta, "PLANT");
			this.input.setValue(lang, "LANGU");
			
            //Ejecuta la funcion
            this.sapCon.doCallFunction();
		} catch(Exception e){
        	logger.fatal("Error setParamValues " + e.getLocalizedMessage(),e);            
            throw new Exception (e.getLocalizedMessage(),e);			
		}
	}
	
	/*
	 * 
	 */
    public List<Material> getResultTables() throws ClassNotFoundException, Exception {	
    	try{
    		logger.info("getResultTables");
    		List<Material> resultados = new ArrayList<Material>();
    		Material stock = null;
    		
    		logger.info("Total de resultados " + this.table.getNumRows());
    		
    		for (int i = 0; i < this.table.getNumRows(); i++) {
    			this.table.setRow(i);
    			
    			stock = new Material();
    			stock.setNoMaterial(Convertidor.remueveCerosIzq(this.table.getString("MATNR")));
    			stock.setDescripcion(this.table.getString("MAKTX"));
    			resultados.add(stock);
    		}
    		
    		return resultados;
    	} catch(Exception e){
    		logger.info("Error getResultTables " + e.toString(),e);
			throw new Exception("Error getResultados: " + e.getLocalizedMessage(),e);    		
    	}
    }
}
