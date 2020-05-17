/**
 * Clase para cargar informaicion del pedido
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
import com.alliax.portalclientes.model.Item;
import com.alliax.portalclientes.model.OrdenVenta;
import com.sap.mw.jco.JCO;

@Service("detallePedido")
public class DetallePedidoRFC {
	
	private final static Logger logger = Logger.getLogger(DetallePedidoRFC.class);	
	
	@Autowired
	private SAPConexionPool sapCon;		
		
	private List<Factura> listaFacturas;
	
	/*@Resource(name="partidas")
	private List<Item> partidas;*/	
	
    //Variables de RFC
    private JCO.Table table;
    private JCO.ParameterList input;    
    private JCO.ParameterList output;  	

	
	public List<Factura> getListaFacturas() {
		return listaFacturas;
	}

	public void setListaFacturas(List<Factura> listaFacturas) {
		this.listaFacturas = listaFacturas;
	}

	/**
	 * Obtiene el detalle del pedido
	 * @param pedido
	 * @return
	 * @throws Exception 
	 */
	public List<Item> detallePedido(String pedido, String lang) throws Exception{
		try {
			this.initModule();
			this.initParamVariables();
			this.setParamValues(pedido, lang);
			
			return this.getResultTables();
			
			//return this.partidas;
			
		} catch(Exception e){
			logger.error("Error al obtener detalle de pedido: " + pedido);
			throw new  Exception("Error el detalle de pedido");
		} finally {
			try{
				this.sapCon.sendToSleep();
			} catch(Exception e){}
		}
		//return this.getResultado();
	}
	
	public List<Factura> detalleFacturas(){
		return this.getListaFacturas();
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
        	
            logger.info("Asigna ZCRM_SALES_ORDER_GETITEMLIST");
            this.sapCon.doSetFunction("ZCRM_SALES_ORDER_GETITEMLIST");

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
        	this.table = this.sapCon.getFunction().getTableParameterList().getTable("SALES_ORDER_ITEMS");
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
	private void setParamValues(String noPedido, String lang) throws Exception  {
		try{
			//Input
			logger.info("setParamValues");
			logger.info("PURCHASE_ORDER " + noPedido);
			logger.info("LANGU: " + lang);
			
			this.input.setValue(Convertidor.agregaCerosIzq(noPedido,10), "PURCHASE_ORDER");
			this.input.setValue(lang, "LANGU");
			
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
    public List<Item> getResultTables() throws ClassNotFoundException, Exception {
    	try{
    		logger.info("getResultTables");
    		
    		List<Item> resultados = new ArrayList<Item>();
    		Item item = null;
    		
    		logger.info("Total Resultaos " + this.table.getNumRows());
    		
    		for (int i = 0; i < this.table.getNumRows(); i++) {
    			this.table.setRow(i);
    			
    			item = new Item();
    			item.setDocumentoComercial(this.table.getString("DOC_NUMBER"));
    			item.setPosicion(this.table.getInt("ITM_NUMBER"));
    			//item.setFecha(this.table.getDate("DOC_DATE"));
    			item.setBloqueoEntrega(this.table.getString("DLV_BLOCK"));
    			item.setNoMaterial(Convertidor.remueveCerosIzq(this.table.getString("MATERIAL")));
    			item.setDescripcion(this.table.getString("SHORT_TEXT"));
    			item.setFechaReparto(this.table.getDate("REQ_DATE"));
    			item.setCantidad(this.table.getBigDecimal("REQ_QTY"));
    			//item.setCantidadConfirmada(this.table.getBigDecimal("CUM_CF_QTY"));
    			item.setUnidadMedida(this.table.getString("SALES_UNIT"));
    			item.setMonto(this.table.getBigDecimal("NET_VALUE"));
    			item.setMoneda(this.table.getString("CURRENCY"));
    			item.setPrecioNeto(this.table.getBigDecimal("NET_PRICE"));
    			item.setNoEntrega(this.table.getString("DELIV_NUMB"));
    			item.setPosicionEntrega(this.table.getInt("DELIV_ITEM"));
    			item.setFechaEntrega(this.table.getDate("DELIV_DATE"));
    			item.setCantidadEntregada(this.table.getBigDecimal("DLV_QTY"));
    			item.setMotivoRechazo(this.table.getString("REA_FOR_RE"));
    			item.setPedidoCliente(this.table.getString("PURCH_NO_C"));
    			item.setEstatus(this.table.getString("DLV_STAT_I"));
    			item.setEstatusDes(this.table.getString("DLV_STAT_I_D"));
    			item.setEstatusGeneral(this.table.getString("GRL_STAT_I"));
    			item.setEstatusGeneralDes(this.table.getString("GRL_STAT_I_D"));
    			
    			logger.info("GRL_STAT_I_D SAP :: " + this.table.getString("GRL_STAT_I_D"));
    			logger.info("Item SAP :: " + item.toString());
    			resultados.add(item);    			
    		}
    		
    		return resultados;
    	} catch(Exception e){
    		logger.error("Error en getResultTables " + e.getLocalizedMessage(),e);
    		throw new Exception("Error al obtener listado de pedidos " + e.getLocalizedMessage(),e);    		
    	}
    }
}
