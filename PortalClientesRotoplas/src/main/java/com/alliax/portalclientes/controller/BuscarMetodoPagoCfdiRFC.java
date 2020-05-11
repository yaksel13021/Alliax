package com.alliax.portalclientes.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alliax.portalclientes.general.sap.SAPConexionPool;
import com.alliax.portalclientes.model.MetodoPagoCFDI;
import com.sap.mw.jco.JCO;

@Service("buscarMetodoPagoCfdiRDC")
public class BuscarMetodoPagoCfdiRFC {

	private final static Logger logger = Logger.getLogger(BuscarMetodoPagoCfdiRFC.class);	
	
	@Autowired
	private SAPConexionPool sapCon;	
	
    private JCO.ParameterList input;    
    private JCO.ParameterList output;  
    
    public MetodoPagoCFDI buscarMetodoPagoCFDI(String nroCliente) throws Exception {
		try{
			this.initModule();
			this.initParamVariables();
			this.setParamValues(nroCliente);
			return this.getResultTables();
			
		} catch(Exception e){
			logger.error("Error al buscarMetodoPagoCFDI, nroCliente: " + nroCliente);
			throw new Exception("Error al buscarMetodoPagoCFDI");
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
            logger.info("Asigna ZCL_GET_METODO_PAGO_CFDI");
            this.sapCon.doSetFunction("ZCL_GET_METODO_PAGO_CFDI");
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
        } catch(Exception e) {
            logger.fatal("Error al inicializar tablas " + e.getLocalizedMessage(),e);            
            throw new Exception (e.getLocalizedMessage(),e);
        }
	}	
    
    private void setParamValues(String nroCliente) throws Exception  {
		try{			
			logger.info("NRO_CLIENTE: " + nroCliente);
			this.input.setValue(nroCliente, "P_KUNNR");
            this.sapCon.doCallFunction();
		} catch(Exception e){
        	logger.fatal("Error setParamValues " + e.getLocalizedMessage(),e);            
            throw new Exception (e.getLocalizedMessage(),e);			
		}
	}
    
    public MetodoPagoCFDI getResultTables() throws ClassNotFoundException, Exception {
    	try{
    		logger.info("getResultTables");
    		MetodoPagoCFDI resultado = new MetodoPagoCFDI();
    		resultado.setClaveMetodoPago(this.output.getString("P_BRSCH"));	
    		resultado.setResultCode(this.output.getString("P_RETURN"));	
    		return resultado;
    	} catch(Exception e){
    		logger.error("Error en getResultTables " + e.getLocalizedMessage(),e);
    		throw new Exception("Error al obtener el Metodo de Pago CFDI " + e.getLocalizedMessage(),e);    		
    	}
    }
}
