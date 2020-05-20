package com.alliax.portalclientes.controller;

import com.alliax.portalclientes.general.sap.SAPConexionPool;
import com.alliax.portalclientes.model.Pedido;
import com.alliax.portalclientes.model.PedidoCapacidadesTransporteEspecial;
import com.alliax.portalclientes.model.PedidoEquipoEspecialProteccionPersonal;
import com.alliax.portalclientes.model.PedidoPartidas;
import com.alliax.portalclientes.model.PedidoProductoAlmacenar;
import com.alliax.portalclientes.model.PedidoReferenciaUbicacion;
import com.alliax.portalclientes.model.PedidoResultado;
import com.alliax.portalclientes.model.PrecioMaterial;
import com.alliax.portalclientes.model.CotizacionFlete;
import com.alliax.portalclientes.util.Helper;
import com.sap.mw.jco.JCO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service("crearPedidoRFC")
public class CrearPedidoRFC {
    @Autowired
    private SAPConexionPool sapCon;

    private final static Logger logger = Logger.getLogger(CrearPedidoRFC.class);

    //Variables de RFC
    private JCO.Table tblItems;
    private JCO.Table tblRefUbic;
    private JCO.Table tblProdAlm;
    private JCO.Table tblCapTran;
    private JCO.Table tblEqupProt;
    private JCO.Structure structure;
    private JCO.ParameterList input;
    private JCO.ParameterList output;

    public PedidoResultado crearPedido(Pedido pedido) throws Exception {
        try{
            this.initModule();
            this.initParamVariables();
            this.setParamValues(pedido);

            return this.getResultTables();

            //return this.resultados;
        } catch(Exception e){
            logger.error("Error al Crear Pedido PEDIDO: " + pedido);
            throw new Exception("Error al Crear Pedido");
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

            logger.info("Asigna ZCL_CREA_PEDIDO");
            this.sapCon.doSetFunction("ZCL_CREA_PEDIDO");

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

            this.structure = this.sapCon.getFunction().getImportParameterList().getStructure("E_HEADER");
            this.tblItems = this.sapCon.getFunction().getTableParameterList().getTable("T_ITEMS");
            this.tblRefUbic = this.sapCon.getFunction().getTableParameterList().getTable("T_REFUBIC");
            this.tblProdAlm = this.sapCon.getFunction().getTableParameterList().getTable("T_PRODALM");
            this.tblCapTran = this.sapCon.getFunction().getTableParameterList().getTable("T_CAPTRAN");
            this.tblEqupProt = this.sapCon.getFunction().getTableParameterList().getTable("T_EQUPROT");

        } catch(Exception e) {
            logger.fatal("Error al inicializar tablas " + e.getLocalizedMessage(),e);
            throw new Exception (e.getLocalizedMessage(),e);
        }
    }

    private void setParamValues(Pedido pedido) throws Exception  {
        try{

            logger.info("PEDIDO: " + pedido);
            //Llenar parametros
            this.input.setValue(pedido.getNombreCliente(),"P_NAME");
            this.input.setValue(pedido.getNroTeleofno(),"P_TELF1");
            this.input.setValue(pedido.getNroTelefonoFijo(),"P_TELF2");
            this.input.setValue(pedido.getHorarioRecepcion(),"P_SCHED");

            //Llenar estructura
            this.structure.setValue(pedido.getPedidoEncabezado().getNroCliente(),"KUNNR");
            this.structure.setValue(pedido.getPedidoEncabezado().getNroDestinatarioMercancias(),"KUNN2");
            this.structure.setValue(pedido.getPedidoEncabezado().getClasePedido(),"DOC_TYPE");
            this.structure.setValue(pedido.getPedidoEncabezado().getOrganizacionVenta(),"SALES_ORG");
            this.structure.setValue(pedido.getPedidoEncabezado().getCanalDistribucion(),"DISTR_CHAN");
            this.structure.setValue(pedido.getPedidoEncabezado().getSector(),"DIVISION");
            this.structure.setValue(pedido.getPedidoEncabezado().getMotivoPedido(),"ORD_REASON");
            this.structure.setValue(pedido.getPedidoEncabezado().getSegmento(),"CUST_GRP4");
            this.structure.setValue(pedido.getPedidoEncabezado().getNroPedidoCliente(),"PURCH_NO_C");
            this.structure.setValue(pedido.getPedidoEncabezado().getSociedad(),"COMPANY");
            this.structure.setValue(pedido.getPedidoEncabezado().getMoneda(),"CURRENCY");
            this.structure.setValue(pedido.getPedidoEncabezado().getMetodoPago(),"MET_PAGO");
            this.structure.setValue(pedido.getPedidoEncabezado().getUsoCFDI(),"USO_CFDI");

            //Llenar tabla de partidas
            PedidoPartidas pedidoPartidas = null;
            for(int i = 0; i < pedido.getPedidoPartidas().size(); i++){
                pedidoPartidas = pedido.getPedidoPartidas().get(i);
                logger.info("partida : " + i + " : " + pedidoPartidas);

                this.tblItems.appendRow();
                this.tblItems.setRow(i);

                this.tblItems.setValue(pedidoPartidas.getPosicion(),"ITM_NUMBER");
                this.tblItems.setValue(pedidoPartidas.getNroMaterial(),"MATERIAL");
                this.tblItems.setValue(pedidoPartidas.getCantidad(),"REQ_QTY");
                this.tblItems.setValue(pedidoPartidas.getUnidadMedida(),"SALES_UNIT");
                if(pedidoPartidas.getNroMaterial().equals(Helper.lpad(CotizacionFlete.idMatFlete,18,"0"))){
                    this.tblItems.setValue(pedidoPartidas.getMonto(), "SERV_PRICE");
                }
                this.tblItems.nextRow();
            }

            //Llenar tabla Referencia de Ubicacion
            PedidoReferenciaUbicacion pedidoReferenciaUbicacion = null;
            for(int i = 0; i < pedido.getPedidoReferenciaUbicacion().size(); i++){
                pedidoReferenciaUbicacion = pedido.getPedidoReferenciaUbicacion().get(i);

                this.tblRefUbic.appendRow();
                this.tblRefUbic.setRow(i);

                this.tblRefUbic.setValue(pedidoReferenciaUbicacion.getSecuencia(), "SEC");
                this.tblRefUbic.setValue(pedidoReferenciaUbicacion.getLineaTexto(), "TDLINE");

            }

            //Llenar tabla Producto a Almacenar
            PedidoProductoAlmacenar pedidoProductoAlmacenar = null;
            for(int i = 0; i < pedido.getPedidoProductoAlmacenar().size(); i++){
                pedidoProductoAlmacenar = pedido.getPedidoProductoAlmacenar().get(i);

                this.tblProdAlm.appendRow();
                this.tblProdAlm.setRow(i);

                this.tblProdAlm.setValue(pedidoProductoAlmacenar.getSecuencia(), "SEC");
                this.tblProdAlm.setValue(pedidoProductoAlmacenar.getLineaTexto(), "TDLINE");

            }

            //Llenar tabla Capacidades de transporte especiales
            PedidoCapacidadesTransporteEspecial pedidoCapacidadesTransporteEspecial = null;
            for(int i = 0; i < pedido.getPedidoCapacidadesTransporteEspecial().size(); i++){
                pedidoCapacidadesTransporteEspecial = pedido.getPedidoCapacidadesTransporteEspecial().get(i);

                this.tblCapTran.appendRow();
                this.tblCapTran.setRow(i);

                this.tblCapTran.setValue(pedidoCapacidadesTransporteEspecial.getSecuencia(), "SEC");
                this.tblCapTran.setValue(pedidoCapacidadesTransporteEspecial.getLineaTexto(), "TDLINE");

            }

            //Llenar tabla Equipo Especial de Proteccion Personal
            PedidoEquipoEspecialProteccionPersonal pedidoEquipoEspecialProteccionPersonal = null;
            for(int i = 0; i < pedido.getPedidoEquipoEspecialProteccionPersonal().size(); i++){
                pedidoEquipoEspecialProteccionPersonal = pedido.getPedidoEquipoEspecialProteccionPersonal().get(i);

                this.tblEqupProt.appendRow();
                this.tblEqupProt.setRow(i);

                this.tblEqupProt.setValue(pedidoEquipoEspecialProteccionPersonal.getSecuencia(), "SEC");
                this.tblEqupProt.setValue(pedidoEquipoEspecialProteccionPersonal.getLineaTexto(), "TDLINE");

            }

            //Asignamos la estructura
            this.input.setValue(this.structure,"E_HEADER");
            /*
            //Aignamos las tabla al objeto input
            this.input.setValue(this.tblItems, "T_ITEMS"); //Partidas
            this.input.setValue(this.tblRefUbic, "T_REFUBIC"); //Referencia de Ubicacion
            this.input.setValue(this.tblProdAlm, "T_PRODALM"); //Producto a Almacenar
            this.input.setValue(this.tblCapTran, "T_CAPTRAN"); //Capacidades de transporte especiales
            this.input.setValue(this.tblEqupProt, "T_EQUPROT"); //Equipo Especial de Proteccion Personal
            */
            //Ejecuta la funcion
            this.sapCon.doCallFunction();
        } catch(Exception e){
            logger.fatal("Error setParamValues " + e.getLocalizedMessage(),e);
            throw new Exception (e.getLocalizedMessage(),e);
        }
    }

    public PedidoResultado getResultTables() throws ClassNotFoundException, Exception {
        try{
            logger.info("getResultTables");
            PedidoResultado pedidoResultado = new PedidoResultado();

            pedidoResultado.setMensajeError(this.output.getString("P_MESSAGE"));
            pedidoResultado.setActualizoFacturacion(this.output.getString("C_RETURN"));
            pedidoResultado.setDocumentoVenta(this.output.getString("P_DOCUMENT"));
            pedidoResultado.setGeneroDocumentoVenta(this.output.getString("P_RETURN"));
            logger.info(pedidoResultado);

           return pedidoResultado;
        } catch(Exception e){
            logger.info("Error getResultTables " + e.toString(),e);
            throw new Exception("Error getResultados: " + e.getLocalizedMessage(),e);
        }
    }
}
