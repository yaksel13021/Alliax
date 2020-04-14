/**
 * Objeto para extraer la informacion del cliente desde SAP.
 * @author saul.ibarra
 * @fecha 29-Feb-2016
 */
package com.alliax.portalclientes.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alliax.portalclientes.general.formato.Convertidor;
import com.alliax.portalclientes.general.sap.SAPConexionPool;
import com.alliax.portalclientes.model.ClienteInfo;
import com.alliax.portalclientes.model.StockMaterial;
import com.sap.mw.jco.JCO;

@Service("infoClienteRfc")
public class InfoClienteRFC {

	@Autowired
	private SAPConexionPool sapCon;

	private final static Logger logger = Logger.getLogger(InfoClienteRFC.class);	
	
    //Variables de RFC
    private JCO.ParameterList input;
    private JCO.ParameterList output;  	
    private JCO.Structure structure;
	
	/*@Resource
	private ClienteInfo resultado;*/
    public boolean completeEmail = false;
    
	public ClienteInfo obtieneInfoCliente(String noCliente) throws Exception {
		try{
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
        	
            logger.info("Asigna ZCRM_GET_CUSTOMER");
            this.sapCon.doSetFunction("ZCRM_GET_CUSTOMER");

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
            this.structure = this.sapCon.getFunction().getExportParameterList().getStructure("CUSTOMER_DETAIL");
            
        } catch(Exception e) {
            logger.fatal("Error al inicializar tablas " + e.getLocalizedMessage(),e);            
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
        	logger.fatal("Error setParamValues " + e.getLocalizedMessage(),e);            
            throw new Exception (e.getLocalizedMessage(),e);			
		}
	}
	
	
    public ClienteInfo getResultTables() throws ClassNotFoundException, Exception {	
    	try{
    		logger.info("getResultTables");
    		ClienteInfo info = null;
    		if(this.structure != null){
    			info = new ClienteInfo();
    			info.setNoCliente(Convertidor.remueveCerosIzq(this.structure.getString("CUSTOMER")));
    			info.setNombre(this.structure.getString("NAME"));
    			if(this.structure.getString("NAME_2") != null)
    				info.setNombre(info.getNombre() + " " + this.structure.getString("NAME_2"));
    			if(this.structure.getString("NAME_3") != null)
    				info.setNombre(info.getNombre() + " " + this.structure.getString("NAME_3"));
    			if(this.structure.getString("NAME_4") != null)
    				info.setNombre(info.getNombre() + " " + this.structure.getString("NAME_4"));
    			info.setCalle(this.structure.getString("STREET"));
    			info.setCodigoPostal(this.structure.getString("POSTL_CODE"));
    			info.setCiudad(this.structure.getString("CITY"));
    			info.setRegion(this.structure.getString("REGION"));
    			info.setPais(this.structure.getString("COUNTRY"));
    			try{
    				if(completeEmail){
    					info.setEmail(this.structure.getString("EMAIL"));
    				}else{
    					info.setEmail(this.structure.getString("EMAIL").substring(0,this.structure.getString("EMAIL").indexOf(";")));    				
    				}
    			} catch(Exception e){
    				info.setEmail(this.structure.getString("EMAIL"));
    			}
    			info.setFax(this.structure.getString("FAX_NUMBER"));
    			info.setTelefono(this.structure.getString("TELEPHONE"));
    			info.setTelefono2(this.structure.getString("TELEPHONE2"));
    			info.setLang(this.structure.getString("LANGU"));
    			info.setStatusSAP(this.structure.getString("RISK_CAT"));
    			
    			return info;
    		}
    		
    		throw new Exception("Cliente no encontrado");
    	} catch(Exception e){
    		logger.info("Error getResultTables " + e.toString(),e);
			throw new Exception("Error getResultados: " + e.getLocalizedMessage(),e);     		
    	}
    }    			
}

