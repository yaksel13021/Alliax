package com.alliax.portalclientes.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alliax.portalclientes.general.sap.SAPConexionPool;
import com.alliax.portalclientes.model.UsoCFDI;
import com.alliax.portalclientes.model.UsoCFDIDetalle;
import com.sap.mw.jco.JCO;

@Service("usoCfdiRFC")
public class UsoCfdiRFC {
	
	private final static Logger logger = Logger.getLogger(UsoCfdiRFC.class);	
	
	@Autowired
	private SAPConexionPool sapCon;	
	
	private JCO.Table table;
    //private JCO.ParameterList input;    
    private JCO.ParameterList output;
    
    public UsoCFDI usoCFDI() throws Exception {
		try{
			this.initModule();
			this.initParamVariables();
			this.setParamValues();
			return this.getResultTables();
			
		} catch(Exception e){
			logger.error("Error al buscar usoCFDI");
			throw new Exception("Error al usoCFDI");
		} finally {
			try{
				this.sapCon.sendToSleep();
			} catch(Exception e){}
		}
	}
    
    private void initModule() throws Exception {
        logger.info("initModule");
        try {
        	this.sapCon.creaConexion();        	
            logger.info("Asigna ZCL_GET_USO_CFDI");
            this.sapCon.doSetFunction("ZCL_GET_USO_CFDI");
            logger.info("Termina Modulo de conexion");
        } catch (Exception e) {
        	logger.fatal("Error en initModule - " + e.getLocalizedMessage(),e);
            throw new Exception ("Error en initModule - " + e.getLocalizedMessage(),e);
        }
	}
    
    private void initParamVariables() throws Exception  {
        try {
        	logger.info("initParamVariables");


        	this.table = this.sapCon.getFunction().getTableParameterList().getTable("T_USOCFDI");
            //this.input = this.sapCon.getFunction().getImportParameterList();           
            this.output = this.sapCon.getFunction().getExportParameterList();
        } catch(Exception e) {
            logger.fatal("Error al inicializar tablas " + e.getLocalizedMessage(),e);            
            throw new Exception (e.getLocalizedMessage(),e);
        }
	}	
    
    private void setParamValues() throws Exception  {
		try{			
            this.sapCon.doCallFunction();
		} catch(Exception e){
        	logger.fatal("Error setParamValues " + e.getLocalizedMessage(),e);            
            throw new Exception (e.getLocalizedMessage(),e);			
		}
	}
    
    public UsoCFDI getResultTables() throws ClassNotFoundException, Exception {
    	try{
    		logger.info("getResultTables");
    		UsoCFDI resultado = new UsoCFDI();
    		resultado.setResultCode(this.output.getString("P_RETURN"));	
    		List<UsoCFDIDetalle> detalles = new ArrayList<UsoCFDIDetalle>();
    			for (int i = 0; i < this.table.getNumRows(); i++) {
        			this.table.setRow(i);
        			UsoCFDIDetalle item = new UsoCFDIDetalle();
        			item.setClaveUsoCFDI(this.table.getString("CVECDFI"));
        			item.setDescripcionClaveUsoCFDI(this.table.getString("DESCCFDI"));
        			detalles.add(item);    			
        		}
    		resultado.setDetalles(detalles);
    		return resultado;
    	} catch(Exception e){
    		logger.error("Error en getResultTables " + e.getLocalizedMessage(),e);
    		throw new Exception("Error al obtener el Metodo de Pago CFDI " + e.getLocalizedMessage(),e);    		
    	}
    }

}
