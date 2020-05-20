package com.alliax.portalclientes.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alliax.portalclientes.general.sap.SAPConexionPool;
import com.alliax.portalclientes.model.DestinatarioMercancia;
import com.sap.mw.jco.JCO;

@Service("buscarDestinatarioMercanciaPorPedidoRFC")
public class BuscarDestinatarioMercanciaPorPedidoRFC {

	private final static Logger logger = Logger.getLogger(BuscarDestinatarioMercanciaPorPedidoRFC.class);	
	
	@Autowired
	private SAPConexionPool sapCon;	
	
	private JCO.Table table;
    private JCO.ParameterList input;    
    private JCO.ParameterList output;
    
    public DestinatarioMercancia buscarDestinatarioMercanciaPorPedido(String pedido) throws Exception {
		try{
			this.initModule();
			this.initParamVariables();
			this.setParamValues(pedido);
			return this.getResultTables();
			
		} catch(Exception e){
			logger.error("Error al buscarDestinatarioMercanciaPorPedido, pedido: " + pedido);
			throw new Exception("Error al buscar Destinatario Mercancia Por Pedido");
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
            logger.info("Asigna ZCL_GET_DEST_MERC_PEDIDO");
            this.sapCon.doSetFunction("ZCL_GET_DEST_MERC_PEDIDO");
            logger.info("Termina Modulo de conexion");
        } catch (Exception e) {
        	logger.fatal("Error en initModule - " + e.getLocalizedMessage(),e);
            throw new Exception ("Error en initModule - " + e.getLocalizedMessage(),e);
        }
	}
    
    private void initParamVariables() throws Exception  {
        try {
        	logger.info("initParamVariables");
        	this.table = this.sapCon.getFunction().getTableParameterList().getTable("DEST_MERC"); 
            this.input = this.sapCon.getFunction().getImportParameterList();           
            this.output = this.sapCon.getFunction().getExportParameterList();
        } catch(Exception e) {
            logger.fatal("Error al inicializar tablas " + e.getLocalizedMessage(),e);            
            throw new Exception (e.getLocalizedMessage(),e);
        }
	}	
    
    private void setParamValues(String pedido) throws Exception  {
		try{			
			logger.info("P_VBELN: " + pedido);	
			this.input.setValue(pedido, "P_VBELN");
            this.sapCon.doCallFunction();
		} catch(Exception e){
        	logger.fatal("Error setParamValues " + e.getLocalizedMessage(),e);            
            throw new Exception (e.getLocalizedMessage(),e);			
		}
	}
    
    public DestinatarioMercancia getResultTables() throws ClassNotFoundException, Exception {	
    	try{
    		logger.info("getResultTables");
    		List<DestinatarioMercancia> resultado = new ArrayList<DestinatarioMercancia>();
    		
    		if(this.output.getString("P_RETURN").equals("0")) {
    			for (int i = 0; i < this.table.getNumRows(); i++) {
        			this.table.setRow(i);
        			DestinatarioMercancia item = new DestinatarioMercancia();
        			item.setNoDestinatario(this.table.getString("KUNN2"));
        			item.setNombreSucursal(this.table.getString("SUCURSAL"));
        			item.setCalleNumero(this.table.getString("STREET"));
        			item.setColonia(this.table.getString("CITY2"));
        			item.setCodigoPostal(this.table.getString("POST_CODE"));
        			item.setPoblacion(this.table.getString("CITY1"));
        			item.setSociedad(this.table.getString("BUKRS"));
        			item.setOrganizacionVentas(this.table.getString("VKORG"));
        			return item;
        			//resultado.add(item);    			
        		}
    		}
    		//return resultado;
    	} catch(Exception e){
    		logger.error("Error en getResultTables " + e.getLocalizedMessage(),e);
    		throw new Exception("Error al obtener listado de pedidos " + e.getLocalizedMessage(),e);    		
    	}
		return null;
    }
    
}
