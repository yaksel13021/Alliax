/**
 * Clase para crear un pool de conexion es en R3 SAP
 * 
 * @author saul.ibarra
 * @fecha 24-Mayo-14
 */
package com.alliax.portalclientes.general.sap;

import java.io.*;
import java.util.*;
import com.sap.mw.jco.*;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

//@Service("sapConexionPool2")
public class SAPConexionPool2 {

	private JCO.Pool connPool;
	private JCO.Client mConnection;
	private IRepository mRepository;
	private JCO.Function function;
        
    private static final Logger logger = Logger.getLogger(SAPConexionPool2.class);
    
    private final String SAP_POOL = "EdoCtaSapPool";
            
    private final int maxConPooled = 10;
                        
    /**
     * *****Constructor
     * Crea un pool de conexiones
     * @destino
     * 
     */
	public SAPConexionPool2() throws Exception {
        logger.info("Claves______SAP_ConexionPool");
        
        //Crea conexion
        this.creaConexion();
	}
	
	public void creaConexion() throws Exception{
        logger.info("creaConexion");
        try{
            logger.info("Sacando la conexion del pool de srm");
            this.getConnectionPool();
            
            //Valida si existe el pool
            if (this.connPool == null) {
                //this.log.writeMes("Agregando cliente al pool de " + destino);
                this.addClientPool();
            }
            
            logger.info("Sacando la cliente del pool");
            //Establece el cliente de conexion
            this.getCliente();
        } catch(Exception e){
            logger.error("Error en la conexion con SAP " + e.getLocalizedMessage(),e);
            throw new Exception("Error en la conexion con SAP " + e.getLocalizedMessage());                
        }		
	}
        
        
    /**
     * Regresa la funcion que se esta ejecutando
     */
	public JCO.Function getFunction() {
		return this.function;
	}
        
        
    /**
     * Extae la informacion del pool de Conexiones del destino especificado
     * @destino 
     */
    private void getConnectionPool() throws Exception{
        try{
           logger.info("Conectando al srm pool");
            
           this.connPool = JCO.getClientPoolManager().getPool(SAP_POOL);
        } catch(Exception e){
            logger.error("Error en getConnectionPool " + e.getLocalizedMessage(),e);
            throw new Exception("Error en getConnectionPool " + e.getLocalizedMessage());
        }
    }
        
        
    /**
     * Agrega una nueva conexion al pool de conexiones del destino especificado
     * @destino 
     */
    private void addClientPool() throws ClassNotFoundException, Exception{
        try {            
            logger.info("++++++++++++++++++++++Añadiendo al pool srm");
            
            //Extrae datos de Conexion del archivo properties
            Properties sapData = this.getPropertie("SAPPRD");  
            
            logger.debug("Datos conexion " + sapData);
            
            logger.info("Datos conexion");
            logger.info("client" + sapData.getProperty("jco.client.client"));
            logger.info("user" + sapData.getProperty("jco.client.user"));
            logger.info("passwd" + sapData.getProperty("jco.client.passwd"));
            logger.info("langu" + sapData.getProperty("jco.client.langu"));
            logger.info("ashost" + sapData.getProperty("jco.client.ashost"));
            logger.info("sysnr" + sapData.getProperty("jco.client.sysnr"));
            //logger.info("r3name" + sapData.getProperty("jco.client.r3name"));
            //logger.info("group" + sapData.getProperty("jco.client.group"));
            
            
            //Usando archivo propertie
            JCO.addClientPool(SAP_POOL,maxConPooled,sapData);
            /*JCO.addClientPool(SAP_POOL,
            					maxConPooled,
            					sapData.getProperty("jco.client.client"),
            					sapData.getProperty("jco.client.user"),
            					sapData.getProperty("jco.client.passwd"),
            					sapData.getProperty("jco.client.langu"),
            					sapData.getProperty("jco.client.mshost"),
            					sapData.getProperty("jco.client.r3name"),
            					sapData.getProperty("jco.client.group"));*/
            
        } catch(Exception e){
            logger.error("Error en addClientPool " + e.getLocalizedMessage(),e);
            //throw new Exception("Error en addClientPool " + e.toString());
        }
    }
        
        
    /**
     * Extrae el cliente solicitado e inicializa el repositorio
     * @destino
     *
     */
    private void getCliente() throws Exception{
        try {
           
        	this.mConnection = JCO.getClient(SAP_POOL);
            
            this.mRepository = new JCO.Repository("AribaExtract", this.mConnection);
            logger.info("Conexion con Pool SRM OK");
            
        } catch (Exception e) {
            logger.error("Error en getCliente " + e.getLocalizedMessage(),e);
            throw new Exception(e.toString());
        }
    }
                
        
    /**
     * Crea la funcion que se ejecutara
     */
	private JCO.Function createFunction(String name) throws Exception  {
        try {
            logger.info("Creando funcion " + name);
            return this.mRepository.getFunctionTemplate(name.toUpperCase()).getFunction();
        } catch (Exception ex) {
            logger.error("Error al crear funcion:" + name + " " + ex.getLocalizedMessage(),ex);
            throw new Exception("Error al crear funcion " + name +  "" + ex.getLocalizedMessage() );
        }
	}

    /**
     * Establece la RFC que se ejecutara
     * @functionName
     */
	public void doSetFunction(String functionName) throws Exception {
        try {
            this.function = this.createFunction(functionName);
            if (this.function == null) {
                logger.info("No se encontró " + functionName + " en SAP.");
                throw new Exception("No se encontró " + functionName + " en SAP.");
            }
            logger.debug("Se creo la funcion" + functionName + " en SAP.");
        } catch (Exception ex) {
            logger.error("Ocurrio un error al crear la funcion " + functionName + " en SAP. " + ex.getLocalizedMessage(),ex);
            throw new Exception("Ocurrio un error al crear la funcion " + functionName + " en SAP. " + ex.getLocalizedMessage());
        }
	}

    /**
     * Ejecuta la RFC solicitada y libera el cliente
     * 
     */
	public void doCallFunction() throws Exception{
        try {
            logger.info("Llamando a la funcion " + this.function.getName());
            
            if(this.mConnection.isAlive())
            	logger.info("Conexion Alive");
            else
            	throw new Exception("Conexion Ended");
            
            this.mConnection.execute(this.function);
        }catch (Exception ex) {
           logger.error("Error al llamar la función: "+ ex.getLocalizedMessage(),ex);
        }
	}
        
        
    /**
     * Libera el cliente de conexion
     */
    public void sendToSleep(){
        //Libera conexion
        try{
            JCO.releaseClient(this.mConnection);
        } catch (Exception e){
            logger.error("Error liberar pool sendToSleep: "+ e.getLocalizedMessage(),e);
        }
    }
        
                
    /**
     * Lee el archivo properties con datos de la conexion
     * @dest nombre del archivo properties que leera
     */
	private Properties getPropertie(String dest) throws ClassNotFoundException, Exception {
		String cfgFile = dest + "-ConnPool.properties";
        logger.info("Archivo:  " + cfgFile);
        Properties prop = new Properties();
        InputStream fis = null;
        try {
            fis = SAPConexionPool2.class.getResourceAsStream("/META-INF/"+ cfgFile);
            prop.load(fis);
           
        } catch(FileNotFoundException e) {
        	logger.fatal("Error al obtener configuracion de conexion:  " + e.getLocalizedMessage(),e);
            throw new FileNotFoundException(e.toString());
        } finally {
        	try {
        		fis.close();
        	}catch(Exception e){        		
        	}
        }
        return prop;
    }        
}