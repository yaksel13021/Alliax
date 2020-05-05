package com.alliax.portalclientes.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alliax.portalclientes.general.sap.SAPConexionPool;
import com.sap.mw.jco.JCO;

@Service("cancelaPedidoRFC")
public class CancelaPedidoRFC {
	
	private final static Logger logger = Logger.getLogger(CancelaPedidoRFC.class);
	 
	@Autowired
    private SAPConexionPool sapCon;

	private JCO.ParameterList input;    
	private JCO.ParameterList output; 
	    
    public String cancelaPedido(String nroPedido) throws Exception {
        try{
            this.initModule();
            this.initParamVariables();
            this.setParamValues(nroPedido);
            return this.getResultTables();

        } catch(Exception e){
            logger.error("Error al Cancelar Pedido NRO PEDIDO: " + nroPedido);
            throw new Exception("Error al Cancelar Pedido");
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
            logger.info("Asigna ZCL_CANCELA_PEDIDO");
            this.sapCon.doSetFunction("ZCL_CANCELA_PEDIDO");

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

    private void setParamValues(String nroPedido) throws Exception  {
    	try{			
			logger.info("NRO_PEDIDO: " + nroPedido);
			this.input.setValue(nroPedido, "P_VBELN");
            this.sapCon.doCallFunction();
		} catch(Exception e){
        	logger.fatal("Error setParamValues " + e.getLocalizedMessage(),e);            
            throw new Exception (e.getLocalizedMessage(),e);			
		}
    }

    public String getResultTables() throws ClassNotFoundException, Exception {
    	try{
    		logger.info("getResultTables");
    		String resultado = this.output.getString("P_RETURN");	
    		return resultado;
    	} catch(Exception e){
    		logger.error("Error en getResultTables " + e.getLocalizedMessage(),e);
    		throw new Exception("Error al obtener listado de pedidos " + e.getLocalizedMessage(),e);    		
    	}
    }

}
