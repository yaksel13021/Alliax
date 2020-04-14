/**
 * Objeto para extraer el Company Code del cliente desde SAP.
 * @author ext.juan.gonzalez
 * @fecha 16-Ago-2018
 */
package com.alliax.portalclientes.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alliax.portalclientes.general.formato.Convertidor;
import com.alliax.portalclientes.general.sap.SAPConexionPool;
import com.alliax.portalclientes.model.ClienteCompany;
import com.sap.mw.jco.JCO;

@Service("clienteCompanyRfc")
public class ClienteCompanyRFC {

	@Autowired
	private SAPConexionPool sapCon;

	private final static Logger logger = Logger.getLogger(ClienteCompanyRFC.class);	
	
    //Variables de RFC
    private JCO.ParameterList input;
    private JCO.ParameterList output; 
    private JCO.Table table;
	
	/*@Resource
	private ClienteInfo resultado;*/
    
    
	public List<ClienteCompany> obtieneCompanyCliente(String noCliente) throws Exception {
		try{
			logger.info("obtieneCompanyCliente");
			this.initModule();
			this.initParamVariables();
			this.setParamValues(noCliente);
			
			return this.getResultTables();
			
			//return this.resultado;
		} catch(Exception e){
			logger.error("Error al obtener informacion del cliente: " + noCliente);
			throw new Exception("Error al obtener informacion del cliente");
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
        	
            logger.info("Asigna ZCRM_CUSTOMER_COCODE");
            this.sapCon.doSetFunction("ZCRM_CUSTOMER_COCODE");

            logger.info("Termina Modulo de conexion");
        } catch (Exception e) {
        	logger.fatal("Error en initModule - " + e.getLocalizedMessage(),e);
            throw new Exception ("Error en initModule - " + e.getLocalizedMessage(),e);
        }
	}
	
	
	private void initParamVariables() throws Exception  {
        try {
        	logger.info("initParamVariables");
        	        	
            this.input = this.sapCon.getFunction().getImportParameterList();           
            this.output = this.sapCon.getFunction().getExportParameterList();
            this.table = this.sapCon.getFunction().getTableParameterList().getTable("CUSTOMER_COCODE");
            
        } catch(Exception e) {
            logger.error("Error al inicializar tablas " + e.getLocalizedMessage(),e);            
            throw new Exception (e.getLocalizedMessage(),e);
        }
	}
	
	private void setParamValues(String noCliente) throws Exception  {
		try{
			
			logger.info("CUSTOMER_NUMBER: " + noCliente);
			
			this.input.setValue(noCliente, "CUSTOMER_NUMBER"); //18Posiciones			
			
            //Ejecuta la funcion
            this.sapCon.doCallFunction();
		} catch(Exception e){
        	logger.error("Error setParamValues " + e.getLocalizedMessage(),e);            
            throw new Exception (e.getLocalizedMessage(),e);			
		}
	}
	
	
    public List<ClienteCompany> getResultTables() throws ClassNotFoundException, Exception {	
    	try{
    		logger.info("getResultTables");
    		List<ClienteCompany> list = new ArrayList<ClienteCompany>();
    		logger.info("Registros : " + this.table.getNumRows());
    		for (int i = 0; i < this.table.getNumRows(); i++) {
    			this.table.setRow(i);
    			ClienteCompany company = new ClienteCompany();
    			logger.info("cliente : " + this.table.getString("CUSTOMER"));
    			logger.info("company : " + this.table.getString("COMPANY"));
    			logger.info("company_name : " + this.table.getString("COMPANY_NAME"));
    			company.setNoCliente(Convertidor.remueveCerosIzq(this.table.getString("CUSTOMER")));
    			company.setCompanyCode(this.table.getString("COMPANY"));
    			company.setCompanyName(this.table.getString("COMPANY_NAME"));
    			list.add(company);

    		}
    		logger.info("Total registros : " + list.size());
			return list;   		

    	} catch(Exception e){
    		logger.error("Error getResultTables " + e.toString(),e);
			throw new Exception("Error getResultados: " + e.getLocalizedMessage(),e);     		
    	}
    }    			
}

