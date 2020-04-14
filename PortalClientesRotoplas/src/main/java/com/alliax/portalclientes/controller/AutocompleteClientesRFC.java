/**
 * Clase para cargar informaicion del Estado de Cuenta
 */
package com.alliax.portalclientes.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alliax.portalclientes.general.formato.Convertidor;
import com.alliax.portalclientes.general.formato.Fecha;
import com.alliax.portalclientes.general.sap.SAPConexionPool;
import com.alliax.portalclientes.model.EstadoCuenta;
import com.alliax.portalclientes.model.EstadoCuentaDet;
import com.alliax.portalclientes.util.Helper;
import com.alliax.portalclientes.util.JSONCliente;
import com.sap.mw.jco.JCO;

@Service("autocompleteClienteRFC")
public class AutocompleteClientesRFC {
	
	private final static Logger logger = Logger.getLogger(AutocompleteClientesRFC.class);	
	
	@Autowired
	private SAPConexionPool sapCon;		
	
    //Variables de RFC
    private JCO.Table table;
    private JCO.ParameterList input;    
    private JCO.ParameterList output;
    private JCO.Structure structure;

	/**
	 * Obtiene el detalle del pedido
	 * @param cliente
	 * @return
	 * @throws Exception 
	 */
	public List<JSONCliente> getClientesList(String customerName, String customerCountry) throws Exception{
		try {
			this.initModule();
			this.initParamVariables();
			this.setParamValues(customerName, customerCountry );
			
			return this.getResultTables();
			
			
		} catch(Exception e){
			logger.error("Error al obtener lista de clientes: " + customerName);
			throw new  Exception("Error al obtener lista de clientes");
		} finally {
			try{
				this.sapCon.sendToSleep();
			} catch(Exception e){}
		}

	}
	
	/**
	 * Inicializa conexion
	 * @throws Exception
	 */
	private void initModule() throws Exception {
        // inicializa la Conexion con SAP
        logger.info("initModule");
        try {
        	//Valida y Crea conexion a SAP
        	this.sapCon.creaConexion();
        	
            logger.info("Asigna ZCRM_GET_CUSTOMER_LIST");
            this.sapCon.doSetFunction("ZCRM_GET_CUSTOMER_LIST");

            logger.info("Termina Modulo de conexion");
        } catch (Exception e) {
        	logger.fatal("Error en initModule - " + e.getLocalizedMessage(),e);
            throw new Exception ("Error en initModule - " + e.getLocalizedMessage(),e);
        }
	}		
	
	/**
	 * Inicializa tablas 
	 * @throws Exception
	 */
	private void initParamVariables() throws Exception  {
        try {
        	this.table = this.sapCon.getFunction().getTableParameterList().getTable("CUSTOMER_LIST");
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
	private void setParamValues(String customerName, String customerCountry) throws Exception  {
		try{
			//Input
			logger.info("setParamValues");
			logger.info("CUSTOMER_NAME " + customerName);
			logger.info("CUSTOMER_COUNTRY" + customerCountry);
						
			this.input.setValue(customerName, "CUSTOMER_NAME");
			this.input.setValue(customerCountry, "CUSTOMER_COUNTRY");			
			
            //Ejecuta la funcion
            this.sapCon.doCallFunction();			
			
		} catch(Exception e){
        	logger.fatal("Error setParamValues " + e.getLocalizedMessage(),e);            
            throw new Exception (e.getLocalizedMessage(),e);
		}
	}
	
	
	/**
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
    public List<JSONCliente> getResultTables() throws ClassNotFoundException, Exception {
    	try{
    		logger.info("getResultTables");    		    		    		    		    		    		        		    		
    		logger.info("Total Resultados " + this.table.getNumRows());

    		List<JSONCliente> list = new ArrayList<>();
        	for (int i = 0; i < this.table.getNumRows(); i++) {
        		this.table.setRow(i);
        			list.add(new JSONCliente(this.table.getString("CUSTOMER"),
        						             this.table.getString("NAME") + "" + this.table.getString("NAME_2"), 
        						             this.table.getString("COUNTRY")));        
    		}      
//        	JSONArray( list ).toString()
    		return list;
    	} catch(Exception e){
    		logger.error("Error en getResultTables " + e.getLocalizedMessage(),e);
    		throw new Exception("Error al obtener listado de pedidos " + e.getLocalizedMessage(),e);    		
    	}
    }
}
