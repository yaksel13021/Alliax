package com.alliax.portalclientes.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alliax.portalclientes.general.sap.SAPConexionPool;
import com.alliax.portalclientes.model.ClasePedido;
import com.sap.mw.jco.JCO;

@Service("buscarClasePedidoRFC")
public class BuscarClasePedidoRFC {
	
	private final static Logger logger = Logger.getLogger(BuscarClasePedidoRFC.class);	
	
	@Autowired
	private SAPConexionPool sapCon;	
	
    private JCO.ParameterList input;    
    private JCO.ParameterList output;  
    
    public ClasePedido buscarClasePedido(String organizacionVentas, String codigoPostal) throws Exception {
		try{
			this.initModule();
			this.initParamVariables();
			this.setParamValues(organizacionVentas, codigoPostal);
			return this.getResultTables();
			
		} catch(Exception e){
			logger.error("Error al obtener la clase pedido, organizacionVentas: " + organizacionVentas + ", codigoPostal: " + codigoPostal);
			throw new Exception("Error al obtener clase pedido");
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
            logger.info("Asigna ZCL_GET_CLASE_PEDIDO");
            this.sapCon.doSetFunction("ZCL_GET_CLASE_PEDIDO");
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
    
    private void setParamValues(String organizacionVentas, String codigoPostal) throws Exception  {
		try{			
			logger.info("ORGANIZACION_VENTAS: " + organizacionVentas);
			logger.info("CODIGO_POSTAL: " + codigoPostal );
			this.input.setValue(organizacionVentas, "P_VKORG");
			this.input.setValue(codigoPostal, "P_POST_CODE");
            this.sapCon.doCallFunction();
		} catch(Exception e){
        	logger.fatal("Error setParamValues " + e.getLocalizedMessage(),e);            
            throw new Exception (e.getLocalizedMessage(),e);			
		}
	}
    
    public ClasePedido getResultTables() throws ClassNotFoundException, Exception {
    	try{
    		logger.info("getResultTables");
    		ClasePedido resultado = new ClasePedido();
    		resultado.setClasePedido(this.output.getString("P_AUART"));	
    		resultado.setResultCode(this.output.getString("RETURN"));	
    		return resultado;
    	} catch(Exception e){
    		logger.error("Error en getResultTables " + e.getLocalizedMessage(),e);
    		throw new Exception("Error al obtener listado de pedidos " + e.getLocalizedMessage(),e);    		
    	}
    }

}
