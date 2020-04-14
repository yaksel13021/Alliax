/**
 * Clase para cargar informaicion del Estado de Cuenta
 */
package com.alliax.portalclientes.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.alliax.portalclientes.general.formato.Convertidor;
import com.alliax.portalclientes.general.formato.Fecha;
import com.alliax.portalclientes.general.sap.SAPConexionPool;
import com.alliax.portalclientes.model.EstadoCuenta;
import com.alliax.portalclientes.model.EstadoCuentaDet;
import com.alliax.portalclientes.util.Helper;
import com.sap.mw.jco.JCO;

@Service("estadoCuentaDetRFC")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE,proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EstadoCuentaDetRFC {
	
	private final static Logger logger = Logger.getLogger(EstadoCuentaDetRFC.class);	
	
	@Autowired
	private SAPConexionPool sapCon;		
	
    //Variables de RFC
    private JCO.Table table;
    private JCO.ParameterList input;    
    private JCO.ParameterList output;
    private JCO.Structure structure;

	/**
	 * Obtiene el detalle del pedido
	 * @param cliente
	 * @return
	 * @throws Exception 
	 */
	public EstadoCuenta detalleEstadoCuenta(String cliente, String company, String fecha, String lang) throws Exception{
		try {
			this.initModule();
			this.initParamVariables();
			this.setParamValues(cliente, company, fecha, lang);
			
			return this.getResultTables();
			
			
		} catch(Exception e){
			logger.error("Error al obtener detalle de estado de cuenta: " + cliente);
			throw new  Exception("Error el detalle de estado de cuenta");
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
        	
            logger.info("Asigna ZCRM_CUSTOMER_EDOCTA");
            this.sapCon.doSetFunction("ZCRM_CUSTOMER_EDOCTA");

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
        	this.table = this.sapCon.getFunction().getTableParameterList().getTable("EDOCTA_DETAIL");
            this.input = this.sapCon.getFunction().getImportParameterList();           
            this.output = this.sapCon.getFunction().getExportParameterList();
            this.structure = this.sapCon.getFunction().getExportParameterList().getStructure("EDOCTA_HEADER");
        } catch(Exception e) {
            logger.fatal("Error al inicializar tablas " + e.getLocalizedMessage(),e);            
            throw new Exception (e.getLocalizedMessage(),e);
        }
	}		
	
    /**
     * Establece parametros de entrada
     */
	private void setParamValues(String noCliente, String company, String fecha, String lang) throws Exception  {
		try{
			//Input
			logger.info("setParamValues");
			logger.info("CUSTOMER_NUMBER " + noCliente);
			logger.info("CUST_COMPANY" + company);
			logger.info("OPEN_DATE" + fecha);			
			logger.info("LANGU: " + lang);
			
			this.input.setValue(Convertidor.agregaCerosIzq(noCliente,10), "CUSTOMER_NUMBER");
			this.input.setValue(company, "CUST_COMPANY");
			this.input.setValue(fecha, "OPEN_DATE");
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
    public EstadoCuenta getResultTables() throws ClassNotFoundException, Exception {
    	try{
    		logger.info("getResultTables");
    		
    		EstadoCuenta resultado = new EstadoCuenta();
    		
    		if(this.structure != null){
    			resultado.setNumCliente(this.structure.getString("CUSTOMER"));		
    			resultado.setNombre(this.structure.getString("NAME"));			
    			resultado.setNombre2(this.structure.getString("NAME_2"));	
    			resultado.setNombre3(this.structure.getString("NAME_3"));    						
    			resultado.setNombre4(this.structure.getString("NAME_4"));        						
    			resultado.setRfc(this.structure.getString("RFC"));  
    			resultado.setTel(this.structure.getString("TELEPHONE"));  
    			resultado.setTel2(this.structure.getString("TELEPHONE2")); 
    			resultado.seteMail(this.structure.getString("EMAIL"));
    			resultado.setMoneda(this.structure.getString("CURRENCY"));  
    			resultado.setLimiteCredito(BigDecimal.valueOf(Double.parseDouble(this.structure.getString("CREDIT_LIMIT").replace(",", ""))));  
    			resultado.setSaldoVencido(BigDecimal.valueOf(Double.parseDouble(this.structure.getString("VENCIDO_VALUE").replace(",", ""))));  
    			resultado.setSaldoVencer(BigDecimal.valueOf(Double.parseDouble(this.structure.getString("AVENCER_VALUE").replace(",", ""))));  
    			resultado.setTotal(BigDecimal.valueOf(Double.parseDouble(this.structure.getString("TOTAL_VALUE").replace(",", ""))));  
    			resultado.setCreditoDisponible(BigDecimal.valueOf(Double.parseDouble(this.structure.getString("CREDIT_AVAILABLE").replace(",", ""))));  
    			resultado.setFechaCorte(this.structure.getDate("CORTE_DATE"));
    			resultado.setPais( this.structure.getString("COUNTRY") );
    			resultado.setSignoPesos( Helper.getSignoPesos(resultado.getPais()) );
    		}
    		
    		List<EstadoCuentaDet> detalle = new ArrayList<EstadoCuentaDet>();
    		EstadoCuentaDet item = null;
    		
    		logger.info("Total Resultaos " + this.table.getNumRows());
    		
    		for (int i = 0; i < this.table.getNumRows(); i++) {
    			this.table.setRow(i);
    			
    			item = new EstadoCuentaDet();
    			item.setTipoDocumento(this.table.getString("TIPO_DOC"));
    			item.setOrdenCompra(this.table.getString("OR_COMPRA"));
    			item.setNoPedido(this.table.getString("NO_PEDIDO"));
    			item.setNotaEntrega(this.table.getString("NTA_ENTREGA"));
    			item.setNoFactura(this.table.getString("NO_FACTURA"));
    			item.setNoFactFiscal(this.table.getString("NO_FACT_FISCAL"));
    			item.setFechaFactura(this.table.getString("FECHA_FACT"));
    			item.setFechaVenc(this.table.getString("FECHA_VENC"));
    			item.setDiasMora(this.table.getString("DIAS_MORA"));
    			item.setImporte(BigDecimal.valueOf(Double.parseDouble(this.table.getString("IMPORTE").replace(",", ""))));
    			item.setEstatus(this.table.getString("ESTATUS"));
    			item.setEntrega(this.table.getString("ENTREGA"));
    			item.setFacturaRelacionada(this.table.getString("FACTURA_REF"));
    			item.setUUIDRelacionado(this.table.getString("FACT_FISCAL_REF"));
    			
    			detalle.add(item);    			
    		}
    		resultado.setDetalle(detalle);
    		return resultado;
    	} catch(Exception e){
    		logger.error("Error en getResultTables " + e.getLocalizedMessage(),e);
    		throw new Exception("Error al obtener listado de pedidos " + e.getLocalizedMessage(),e);    		
    	}
    }
}
