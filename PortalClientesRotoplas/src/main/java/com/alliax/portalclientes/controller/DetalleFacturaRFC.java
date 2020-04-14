/**
 * Objeto para obtener el detalle de una factura
 * @author saul.ibarra
 * @fecha 2-Marzo-2016
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
import com.alliax.portalclientes.model.ItemFactura;
import com.sap.mw.jco.JCO;

@Service("detalleFacturaRFC")
public class DetalleFacturaRFC {

	private final static Logger logger = Logger.getLogger(DetalleFacturaRFC.class);	
	
	@Autowired
	private SAPConexionPool sapCon;		

    //Variables de RFC
    private JCO.Table table;
    private JCO.ParameterList input;    
    private JCO.ParameterList output;
    
    /*@Resource(name="detalleFacturas")
    private List<ItemFactura> resultados;*/    
    
    
    /**
     * Extrae el detalle de la factura
     * @param cliente
     * @param fechaIni
     * @param fechaFin
     * @param noPedido
     * @return
     * @throws Exception
     */
	public List<ItemFactura> obtieneDetalleFactura(String noPedido, String factura) throws Exception{
		try {
			this.initModule();			
			this.initParamVariables();
			this.setParamValues(noPedido,factura);
			
			return this.getResultTables();
			
			//Resource
			//return this.resultados;
			
		} catch(Exception e){
			logger.error("Error al obtener detalle de factura : " + factura + " del Pedido " + noPedido);
			throw new  Exception("Error al obtener detalle de la factura");
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

            logger.info("Asigna ZCRM_BILLINGDOC_GETITEMLIST");
            this.sapCon.doSetFunction("ZCRM_BILLINGDOC_GETITEMLIST");

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
        	this.table = this.sapCon.getFunction().getTableParameterList().getTable("BILLINGDOC_ITEM");
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
	private void setParamValues(String noPedido, String factura) throws Exception  {
		try{
			logger.info("setParamValues");
			logger.info("PURCHASE_ORDER:" + noPedido);
			logger.info("BILLINGDOC: " + factura);
				
			//Input			
			this.input.setValue(Convertidor.agregaCerosIzq(noPedido,10), "PURCHASE_ORDER");
			this.input.setValue(Convertidor.agregaCerosIzq(factura,10), "BILLINGDOC");
																	
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
    public List<ItemFactura> getResultTables() throws ClassNotFoundException, Exception {	
    	try{
    		List<ItemFactura> resultados = new ArrayList<ItemFactura>();
    		ItemFactura fact = null;
    		
    		logger.info("getResultTables");
    		logger.info("Total resultados: " + this.table.getNumRows());
    		
    		for (int i = 0; i < this.table.getNumRows(); i++) {
    			this.table.setRow(i);
    			fact = new ItemFactura();
    			
    			fact.setDocFactura(this.table.getString("BILL_DOC"));
    			fact.setPosicion(this.table.getInt("BILL_DOC_ITEM"));
    			fact.setMaterial(Convertidor.remueveCerosIzq(this.table.getString("MATERIAL")));
    			fact.setDescripcion(this.table.getString("SHORT_TEXT"));
    			fact.setCantidad(this.table.getBigDecimal("BILL_QTY"));
    			fact.setUnidadMedida(this.table.getString("BILL_UNIT"));
    			fact.setMontoNeto(this.table.getBigDecimal("NET_VALUE"));
    			fact.setMoneda(this.table.getString("CURRENCY"));
    			fact.setImpuesto(this.table.getBigDecimal("TAX_VALUE"));
    			
    			resultados.add(fact);    			
    		}
    		
    		return resultados;
    		    		
    	} catch(Exception e){
    		logger.error("Error en getResultTables " + e.getLocalizedMessage(),e);
    		throw new Exception("Error al obtener listado de pedidos " + e.getLocalizedMessage(),e);    		
    	}
    }
	
}
