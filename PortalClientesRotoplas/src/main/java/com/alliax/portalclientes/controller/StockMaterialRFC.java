package com.alliax.portalclientes.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alliax.portalclientes.general.formato.Convertidor;
import com.alliax.portalclientes.general.sap.SAPConexionPool;
import com.alliax.portalclientes.model.StockMaterial;
import com.alliax.portalclientes.service.StockMaterialService;
import com.sap.mw.jco.JCO;

@Service("stockMaterialImpl")
public class StockMaterialRFC {

	@Autowired
	private SAPConexionPool sapCon;

	private final static Logger logger = Logger.getLogger(StockMaterialRFC.class);

    //Variables de RFC
    private JCO.Table table;
    private JCO.Table tblPlanta;
    private JCO.ParameterList input;    
    private JCO.ParameterList output;  	
    private JCO.Structure structure;
	
	/*@Resource(name="stockMaterial")
	private List<StockMaterial> resultados;*/
	
	/*@Override
	public List<StockMaterial> getResultado() {
		return this.resultados;
	}*/

	public List<StockMaterial> obtieneStock(String noMaterial, String planta, String lang) throws Exception {
		try{
			this.initModule();
			this.initParamVariables();
			this.setParamValues(noMaterial,planta,lang);
			
			return this.getResultTables();
			
			//return this.resultados;
		} catch(Exception e){
			logger.error("Error al obtener stock de material: " + noMaterial);
			throw new Exception("Error al obtener listado de pedidos");
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
        	
            logger.info("Asigna ZCRM_GET_STOCK_PT");
            this.sapCon.doSetFunction("ZCRM_GET_STOCK_PT");

            logger.info("Termina Modulo de conexion");
        } catch (Exception e) {
        	logger.fatal("Error en initModule - " + e.getLocalizedMessage(),e);
            throw new Exception ("Error en initModule - " + e.getLocalizedMessage(),e);
        }
	}	
	
	
	private void initParamVariables() throws Exception  {
        try {
        	logger.info("initParamVariables");
        	
        	this.table = this.sapCon.getFunction().getTableParameterList().getTable("ET_STOCK_PT");
        	this.tblPlanta = this.sapCon.getFunction().getImportParameterList().getTable("I_WERKS");
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
	private void setParamValues(String noMaterial, String planta, String lang) throws Exception  {
		try{
			
			logger.info("I_MATNR: " + noMaterial);
			logger.info("I_WERKS: " + planta);
			logger.info("I_LANGU: " + lang);
			
			//Crea tabla de planta
			this.tblPlanta.appendRow();
			this.tblPlanta.setRow(0);
			this.tblPlanta.setValue(planta, "WERKS");			
			
			this.input.setValue(noMaterial, "I_MATNR"); //18Posiciones
			this.input.setValue(this.tblPlanta, "I_WERKS"); //Planta
			this.input.setValue(lang, "I_LANGU");
			

			
			
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
    public List<StockMaterial> getResultTables() throws ClassNotFoundException, Exception {	
    	try{
    		logger.info("getResultTables");
    		List<StockMaterial> resultados = new ArrayList<StockMaterial>();
    		StockMaterial stock = null;
    		
    		logger.info("Total de resultados " + this.table.getNumRows());
    		
    		DecimalFormat df = new DecimalFormat("#,###.00");
    		
    		for (int i = 0; i < this.table.getNumRows(); i++) {
    			this.table.setRow(i);
    			
    			stock = new StockMaterial();
    			stock.setNoMaterial(Convertidor.remueveCerosIzq(this.table.getString("MATNR")));
    			stock.setDescripcion(this.table.getString("MAKTX"));
    			stock.setNoPlanta(this.table.getString("WERKS"));
    			stock.setPlanta(this.table.getString("NAME1"));
    			stock.setAlmacen(this.table.getString("LGORT"));
   				stock.setStock(this.table.getBigDecimal("LABST"));
    			stock.setUmb(this.table.getString("MEINS"));
    			
    			resultados.add(stock);
    		}
    		
    		return resultados;
    	} catch(Exception e){
    		logger.info("Error getResultTables " + e.toString(),e);
			throw new Exception("Error getResultados: " + e.getLocalizedMessage(),e);    		
    	}
    }
}
