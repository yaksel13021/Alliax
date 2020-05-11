package com.alliax.portalclientes.controller;

import com.alliax.portalclientes.general.sap.SAPConexionPool;
import com.alliax.portalclientes.model.PrecioMaterial;
import com.sap.mw.jco.JCO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("precioMaterialRFC")
public class PrecioMaterialRFC {
    @Autowired
    private SAPConexionPool sapCon;

    private final static Logger logger = Logger.getLogger(PrecioMaterialRFC.class);

    //Variables de RFC
    private JCO.Table table;
    private JCO.ParameterList input;
    private JCO.ParameterList output;

    public PrecioMaterial obtienePrecioMaterial(String clasePedido, String organizacionVenta, String canalDistribucion,
                                             String sector, String segmento, String nroMaterial, String cantidadMaterial,
                                             String unidadMedida, String nroCliente, String nroDestinatarioMCIAS) throws Exception {
        try{
            this.initModule();
            this.initParamVariables();
            this.setParamValues(clasePedido, organizacionVenta, canalDistribucion, sector, segmento, nroMaterial, cantidadMaterial,
                    unidadMedida, nroCliente, nroDestinatarioMCIAS);

            return this.getResultTables();

            //return this.resultados;
        } catch(Exception e){
            logger.error("Error al obtener precio de material CLASE_PEDIDO: " + clasePedido + " ORGANIZACION_VENTA: " + organizacionVenta +
                            " CANAL_DISTRIBUCION: " +canalDistribucion + " SECTOR: " + sector + " SEGMENTO: " + segmento +
                            " NUMERO_MATERIAL: " + nroMaterial + " CANTIDAD_MATERIAL: " + cantidadMaterial + " UNIDAD_MEDIDA: " + unidadMedida +
                            " NUMERO_CLIENTE: " + nroCliente +  " NUMERO_DESTINATARIO_MCIAS: " + nroDestinatarioMCIAS);
            throw new Exception("Error al obtener precio de material");
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

            logger.info("Asigna ZCL_GET_PRECIO_MATERIAL");
            this.sapCon.doSetFunction("ZCL_GET_PRECIO_MATERIAL");

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

    private void setParamValues(String clasePedido, String organizacionVenta, String canalDistribucion,
                                String sector, String segmento, String nroMaterial, String cantidadMaterial,
                                String unidadMedida, String nroCliente, String nroDestinatarioMCIAS) throws Exception  {
        try{

            logger.info("CLASE_PEDIDO: " + clasePedido);
            logger.info("ORGANIZACION_VENTA: " + organizacionVenta);
            logger.info("CANAL_DISTRIBUCION: " + canalDistribucion);
            logger.info("SECTOR: " + sector);
            logger.info("SEGMENTO: " + segmento);
            logger.info("NUMERO_MATERIAL: " + nroMaterial);
            logger.info("CANTIDAD_MATERIAL: " + cantidadMaterial);
            logger.info("UNIDAD_MEDIDA: " + unidadMedida);
            logger.info("NUMERO_CLIENTE: " + nroCliente);
            logger.info("NUMERO_DESTINATARIO_MCIAS: " + nroDestinatarioMCIAS);

            this.input.setValue(clasePedido, "P_AUART");
            this.input.setValue(organizacionVenta, "P_VKORG");
            this.input.setValue(canalDistribucion, "P_VTWEG");
            this.input.setValue(sector, "P_SPART");
            this.input.setValue(segmento, "P_KVGR4");
            this.input.setValue(nroMaterial, "P_MATNR");
            this.input.setValue(cantidadMaterial, "P_KWMENG");
            this.input.setValue(unidadMedida, "P_VRKME");
            this.input.setValue(nroCliente, "P_KUNNR");
            this.input.setValue(nroDestinatarioMCIAS, "P_KUNN2");

            //Ejecuta la funcion
            this.sapCon.doCallFunction();
        } catch(Exception e){
            logger.fatal("Error setParamValues " + e.getLocalizedMessage(),e);
            throw new Exception (e.getLocalizedMessage(),e);
        }
    }

    public PrecioMaterial getResultTables() throws ClassNotFoundException, Exception {
        try{
            PrecioMaterial precioMaterial = new PrecioMaterial();

            precioMaterial.setCodigoError(this.output.getString("P_RETURN"));
            precioMaterial.setMensajeError(this.output.getString("P_MESSAGE"));
            if(precioMaterial.getCodigoError().equals("0")){
                precioMaterial.setMonto(this.output.getBigDecimal("P_NETPR"));
                precioMaterial.setPrecioNeto(this.output.getBigDecimal("P_NETWR"));
                precioMaterial.setIva(this.output.getString("P_IMPIVA"));
                precioMaterial.setTotalPartida(this.output.getBigDecimal("P_TNETWR"));
                precioMaterial.setMoneda(this.output.getString("P_WAERS"));
                precioMaterial.setFechaEntrega(this.output.getString("P_DELDAT"));
            }

            return precioMaterial;
        } catch(Exception e){
            logger.info("Error getResultTables " + e.toString(),e);
            throw new Exception("Error getResultados: " + e.getLocalizedMessage(),e);
        }
    }
}
