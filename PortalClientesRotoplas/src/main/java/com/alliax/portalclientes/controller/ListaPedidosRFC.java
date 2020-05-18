/**
 * Extrae lista de Pedidos de SRM dado un cliente y fechas
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
import com.alliax.portalclientes.model.OrdenVenta;
import com.alliax.portalclientes.model.StockMaterial;
import com.sap.mw.jco.JCO;

@Service("listaPedidosRfc")
public class ListaPedidosRFC {

	private final static Logger logger = Logger.getLogger(ListaPedidosRFC.class);	
	
	@Autowired
	private SAPConexionPool sapCon;	
	
	/*@Resource(name="pedidos")
	private List<OrdenVenta> resultados;*/

    //Variables de RFC
    private JCO.Table table;
    private JCO.Table tblEstatus;
    private JCO.ParameterList input;
    private JCO.ParameterList output;


	/**
	 * Metodo para buscar pedidos
	 * @return
	 * @throws Exception
	 */
	public List<OrdenVenta> busquedaPedidos(String cliente, String fechaIni, String fechaFin, String noPedido, String docComercial, List<String> estatus, String lang) throws Exception{
		try {
			this.initModule();			
			this.initParamVariables();
			this.setParamValues(cliente,fechaIni,fechaFin,noPedido,docComercial,estatus,lang);
			
			return this.getResultTables();
			
			//Resource
			//return this.resultados;
			
		} catch(Exception e){
			logger.error("Error al obtener listado de pedidos cliente: " + cliente);
			throw new  Exception("Error al obtener listado de pedidos");
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

            logger.info("Asigna ZCRM_SALES_ORDER_GETLIST");
            this.sapCon.doSetFunction("ZCRM_SALES_ORDER_GETLIST");

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
        	this.table = this.sapCon.getFunction().getTableParameterList().getTable("SALES_ORDERS");
        	this.tblEstatus = this.sapCon.getFunction().getImportParameterList().getTable("PRC_STAT_H");
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
	private void setParamValues(String cliente, String fechaIni,String fechaFin, String noPedido, String docComercial, List<String> estatus, String lang) throws Exception  {
		try{
			logger.info("setParamValues");
			logger.info("CUSTOMER_NUMBER:" + cliente);
			logger.info("DOCUMENT_DATE_FROM " + fechaIni);
			logger.info("DOCUMENT_DATE_TO " + fechaFin);
			logger.info("CUSTOMER_PURC_ORDER " + noPedido);//PO
			logger.info("PURCHASE_ORDER " + docComercial ); //SD_DOC 			
			logger.info("LANGU: " + lang);			
			
									
			//Crea tabla de Estatus
			logger.info("Total estatus " + estatus.size());
						
			int s = 0;
			while(s < estatus.size()){
				logger.info("Estatus " + estatus.get(s));
				this.tblEstatus.appendRow();
				this.tblEstatus.setRow(s);
				this.tblEstatus.setValue(estatus.get(s), "GBSTK");
				s++;
			}
			
			//Input			
			this.input.setValue(cliente, "CUSTOMER_NUMBER"); //A 10 digitos
			this.input.setValue(fechaIni, "DOCUMENT_DATE_FROM");
			this.input.setValue(fechaFin, "DOCUMENT_DATE_TO");			
			this.input.setValue(Convertidor.agregaCerosIzq(docComercial, 10), "PURCHASE_ORDER"); // 10 digitos
			this.input.setValue(noPedido, "CUSTOMER_PURC_ORDER");
			
			if(this.tblEstatus != null && estatus.size() > 0){
				logger.info("Seteando tblestatus");
				this.input.setValue(this.tblEstatus, "PRC_STAT_H");
			}
			
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
    public List<OrdenVenta> getResultTables() throws ClassNotFoundException, Exception {	
    	try{
    		List<OrdenVenta> resultados = new ArrayList<OrdenVenta>();
    		OrdenVenta orden = null;
    		
    		logger.info("getResultTables");
    		logger.info("Total resultados: " + this.table.getNumRows());
    		
    		for (int i = 0; i < this.table.getNumRows(); i++) {
    			this.table.setRow(i);
    			orden = new OrdenVenta();
    			
    			orden.setDocumentoComercial(Convertidor.remueveCerosIzq(this.table.getString("SD_DOC")));
    			orden.setPedidoCliente(this.table.getString("PURCH_NO_C"));
    			orden.setFechaCreacion(this.table.getDate("CREATION_DATE"));
    			orden.setHoraCreacion(this.table.getDate("CREATION_TIME").getTime());
    			orden.setMonto(this.table.getBigDecimal("NET_VAL_HD"));
    			orden.setMoneda(this.table.getString("CURRENCY"));
    			orden.setDestinatario(this.table.getString("SHIP_TO"));
    			orden.setEstatusGlobal(this.table.getString("PRC_STAT_H"));
    			orden.setEstatusGlobalDes(this.table.getString("PRC_STAT_H_D"));
    			orden.setEstatusCredito(this.table.getString("CRE_STAT_H"));
    			orden.setEstatusCreditoDes(this.table.getString("CRE_STAT_H_D"));
    			orden.setEstatusEntrega(this.table.getString("DLV_STAT_H"));
    			orden.setEstatusEntregaDes(this.table.getString("DLV_STAT_H_D"));
    			orden.setEstatusGeneral(this.table.getString("GRL_STAT_H"));
    			orden.setEstatusGeneralDes(this.table.getString("GRL_STAT_H_D"));
    			try {
    				logger.info("Inicio buscar KVGR4");
    				String seg = this.table.getString("KVGR4");
    				orden.setSegmento(seg);
    				logger.info("KVGR4 encontrado: " + seg);
    			}catch (Exception e) {
    				logger.info("KVGR4 no encontrado");
    				logger.info(e.getMessage());
				}
    			//logger.info("GRL_STAT_I_D SAP :: " + this.table.getString("GRL_STAT_I_D"));
    			logger.info("Orden Venta SAP :: " + orden.toString());
    			
    			orden.setRazonBloqueo(this.table.getString("REASON_BLOCK"));
    			
    			resultados.add(orden);
    		}
    		
    		return resultados;
    		
    	} catch(Exception e){
    		logger.error("Error en getResultTables " + e.getLocalizedMessage(),e);
    		throw new Exception("Error al obtener listado de pedidos " + e.getLocalizedMessage(),e);
    	}
    }
}