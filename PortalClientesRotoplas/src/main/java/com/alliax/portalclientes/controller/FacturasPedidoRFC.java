/**
 * Clase para obtener el listado de facturas dado un pedido
 * @author saul.ibarra
 * @fecha 17-Febrero-2016
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
import com.alliax.portalclientes.model.Factura;
import com.sap.mw.jco.JCO;

@Service("facturasPedido")
public class FacturasPedidoRFC {

	private final static Logger logger = Logger.getLogger(FacturasPedidoRFC.class);	
	
	@Autowired
	private SAPConexionPool sapCon;
	
    //Variables de RFC
    private JCO.Table table;
    private JCO.ParameterList input;
    private JCO.ParameterList output;
    
    /*@Resource(name="facturas")
    private List<Factura> resultados;*/
	
    
    /**
     * Extrae el listado de facturas dado un pedido
     * @param noPedido
     * @return
     * @throws Exception
     */
	public List<Factura> busquedaFacturas(String noPedido) throws Exception{
		try {
			this.initModule();			
			this.initParamVariables();
			this.setParamValues(noPedido);
			
			return this.getResultTables();
			
			//return this.resultados;
						
		} catch(Exception e){
			logger.error("Error al obtener listado de facturas pedido: " + noPedido);
			throw new  Exception("Error al obtener listado de facturas pedido");
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
        	
            logger.info("Asigna ZCRM_BILLINGDOC_GETLIST");
            this.sapCon.doSetFunction("ZCRM_BILLINGDOC_GETLIST");

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
        	this.table = this.sapCon.getFunction().getTableParameterList().getTable("BILLINGDOC_LIST");
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
	private void setParamValues(String noPedido) throws Exception  {
		try{
			//Input
			logger.info("PURCHASE_ORDER: " + noPedido);
			
			this.input.setValue(Convertidor.agregaCerosIzq(noPedido,10), "PURCHASE_ORDER");
			
            //Ejecuta la funcion
            this.sapCon.doCallFunction();			
		} catch (Exception e){
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
    public List<Factura> getResultTables() throws ClassNotFoundException, Exception {
    	try{
    		logger.info("getResultTables");
    		
    		List<Factura> resultados = new ArrayList<Factura>();
    		Factura factura = null;
    		
    		logger.info("Total Facturas " + this.table.getNumRows());
    		
    		for (int i = 0; i < this.table.getNumRows(); i++) {
    			this.table.setRow(i);
    			factura = new Factura();
    			
    			factura.setDocFactura(this.table.getString("BILLINGDOC"));
    			factura.setFechaFactura(this.table.getDate("BILL_DATE"));
    			factura.setMontoImp(this.table.getBigDecimal("TAX_VALUE"));
    			factura.setMontoNeto(this.table.getBigDecimal("NET_VALUE"));
    			factura.setMoneda(this.table.getString("CURRENCY"));
    			factura.setEstatusContabilidad(this.table.getString("ACCTSTATUS"));
    			
    			resultados.add(factura);    			
    		}
    		
    		return resultados;
    		
    	} catch(Exception e){
    		logger.error("Error en getResultTables " + e.getLocalizedMessage(),e);
    		throw new Exception("Error al obtener listado de pedidos " + e.getLocalizedMessage(),e);    		
    	}
    }
}
