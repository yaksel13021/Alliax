package com.alliax.portalclientes.view;

import com.alliax.portalclientes.controller.BuscarClasePedidoConfig;
import com.alliax.portalclientes.controller.BuscarClasePedidoRFC;
import com.alliax.portalclientes.controller.BuscarDestinatariosMercanciasConfig;
import com.alliax.portalclientes.controller.BuscarDestinatariosMercanciasRFC;
import com.alliax.portalclientes.controller.BuscarMetodoPagoCfdiConfig;
import com.alliax.portalclientes.controller.BuscarMetodoPagoCfdiRFC;
import com.alliax.portalclientes.controller.CrearPedidoRFC;
import com.alliax.portalclientes.controller.PrecioMaterialConfig;
import com.alliax.portalclientes.controller.PrecioMaterialRFC;
import com.alliax.portalclientes.controller.UsoCfdiConfig;
import com.alliax.portalclientes.controller.UsoCfdiRFC;
import com.alliax.portalclientes.domain.Material;
import com.alliax.portalclientes.domain.PedidoPartidasPK;
import com.alliax.portalclientes.model.ClasePedido;
import com.alliax.portalclientes.model.DestinatarioMercancia;
import com.alliax.portalclientes.model.MetodoPagoCFDI;
import com.alliax.portalclientes.model.Pedido;
import com.alliax.portalclientes.model.PedidoMaterial;
import com.alliax.portalclientes.model.PedidoPartidas;
import com.alliax.portalclientes.model.PedidoResultado;
import com.alliax.portalclientes.model.PrecioMaterial;
import com.alliax.portalclientes.model.UsoCFDI;
import com.alliax.portalclientes.model.UsoCFDIDetalle;
import com.alliax.portalclientes.service.MaterialService;
import com.alliax.portalclientes.service.PedidoPartidasService;
import com.alliax.portalclientes.service.PedidoService;
import com.alliax.portalclientes.util.Helper;
import com.alliax.portalclientes.util.KeyGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ManagedBean(name="crearPedido")
@ViewScoped
public class CrearPedido_backing extends AbstractBackingGen {

    private final static Logger logger = Logger.getLogger(CrearPedido_backing.class);

    BuscarDestinatariosMercanciasRFC buscarDestinatariosMercanciasRFC;
    BuscarClasePedidoRFC buscarClasePedidoRFC;
    PrecioMaterialRFC precioMaterialRFC;
    UsoCfdiRFC usoCfdiRFC;
    BuscarMetodoPagoCfdiRFC buscarMetodoPagoCfdiRFC;

    com.alliax.portalclientes.domain.Pedido pedidoBd;
    List<com.alliax.portalclientes.domain.PedidoPartidas> pedidoPartidas;

    MaterialService materialService;
    PedidoService pedidoService;
    PedidoPartidasService pedidoPartidasService;

    private String idPedido;

    private String nroPedidoCliente;
    private String destino;
    private String nroCliente;
    private String nroPedido;
    private String destinatario;
    private String codigoPostal;
    private String organizacionVenta;
    private String sociedad;
    private String destinatarioMercancia;
    private String clasePedido;
    private String tipoMaterial;
    private String estatus;
    private String metodoPago;
    private String usoCFDI;
    private String comprobanteBancario;
    private String datosEntrega;
    private String nombreContacto;
    private String apellidoContacto;
    private String telefonoContacto;
    private String telefonoFijoContacto;
    private String horarioRecepcion;
    private String referenciaUbicacion;
    private String productoAlmacenar;
    private String capacidadesTransporte;
    private String equipoEspecial;
    private String noCotizacion;
    private String estatusCotizacion;
    private String correoElectronico;
    private String segmento;
    private String mensajeError;

    private String emailCotizarFlete;


    private String materialSeleccionadoJson;

    private List<DestinatarioMercancia> destinatarioMercancias;
    private DestinatarioMercancia destinatarioMercanciaSel;
    private String destinatarioMercanciasJson;
    private String materialesJson;
    private List<PedidoMaterial> materiales;
    private List<UsoCFDIDetalle> usoCFDIDetalles;
    private String usoCFDIDetallesJson;

    private String descripcionDestinatario;


    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getNroCliente() {
        return nroCliente;
    }

    public void setNroCliente(String nroCliente) {
        this.nroCliente = nroCliente;
    }

    public String getNroPedido() {
        return nroPedido;
    }

    public void setNroPedido(String nroPedido) {
        this.nroPedido = nroPedido;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getOrganizacionVenta() {
        return organizacionVenta;
    }

    public void setOrganizacionVenta(String organizacionVenta) {
        this.organizacionVenta = organizacionVenta;
    }

    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    public String getDestinatarioMercancia() {
        return destinatarioMercancia;
    }

    public void setDestinatarioMercancia(String destinatarioMercancia) {
        this.destinatarioMercancia = destinatarioMercancia;
    }

    public String getClasePedido() {
        return clasePedido;
    }

    public void setClasePedido(String clasePedido) {
        this.clasePedido = clasePedido;
    }

    public String getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getUsoCFDI() {
        return usoCFDI;
    }

    public void setUsoCFDI(String usoCFDI) {
        this.usoCFDI = usoCFDI;
    }

    public String getComprobanteBancario() {
        return comprobanteBancario;
    }

    public void setComprobanteBancario(String comprobanteBancario) {
        this.comprobanteBancario = comprobanteBancario;
    }

    public String getDatosEntrega() {
        return datosEntrega;
    }

    public void setDatosEntrega(String datosEntrega) {
        this.datosEntrega = datosEntrega;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getApellidoContacto() {
        return apellidoContacto;
    }

    public void setApellidoContacto(String apellidoContacto) {
        this.apellidoContacto = apellidoContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getTelefonoFijoContacto() {
        return telefonoFijoContacto;
    }

    public void setTelefonoFijoContacto(String telefonoFijoContacto) {
        this.telefonoFijoContacto = telefonoFijoContacto;
    }

    public String getHorarioRecepcion() {
        return horarioRecepcion;
    }

    public void setHorarioRecepcion(String horarioRecepcion) {
        this.horarioRecepcion = horarioRecepcion;
    }

    public String getReferenciaUbicacion() {
        return referenciaUbicacion;
    }

    public void setReferenciaUbicacion(String referenciaUbicacion) {
        this.referenciaUbicacion = referenciaUbicacion;
    }

    public String getProductoAlmacenar() {
        return productoAlmacenar;
    }

    public void setProductoAlmacenar(String productoAlmacenar) {
        this.productoAlmacenar = productoAlmacenar;
    }

    public String getCapacidadesTransporte() {
        return capacidadesTransporte;
    }

    public void setCapacidadesTransporte(String capacidadesTransporte) {
        this.capacidadesTransporte = capacidadesTransporte;
    }

    public String getEquipoEspecial() {
        return equipoEspecial;
    }

    public void setEquipoEspecial(String equipoEspecial) {
        this.equipoEspecial = equipoEspecial;
    }

    public String getNoCotizacion() {
        return noCotizacion;
    }

    public void setNoCotizacion(String noCotizacion) {
        this.noCotizacion = noCotizacion;
    }

    public String getEstatusCotizacion() {
        return estatusCotizacion;
    }

    public void setEstatusCotizacion(String estatusCotizacion) {
        this.estatusCotizacion = estatusCotizacion;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDescripcionDestinatario() {
        descripcionDestinatario = "";
        if(destinatarioMercanciaSel!=null){
            descripcionDestinatario = destinatarioMercanciaSel.getNombreSucursal() + "/" + destinatarioMercanciaSel.getCalleNumero() + " " + destinatarioMercanciaSel.getColonia();
        }
        return descripcionDestinatario;
    }

    public void setDescripcionDestinatario(String descripcionDestinatario) {
        this.descripcionDestinatario = descripcionDestinatario;
    }

    public List<DestinatarioMercancia> getDestinatarioMercancias() {
        if (destinatarioMercancias == null){
            try {
                buscarDestinatariosMercanciasRFC = this.getSpringContext().getBean("buscarDestinatariosMercanciasRFC", BuscarDestinatariosMercanciasRFC.class);
                logger.info("RFC " + buscarDestinatariosMercanciasRFC);
                setDestinatarioMercancias(buscarDestinatariosMercanciasRFC.buscarDestinatariosMercancias(this.getUsuarioLogueado().getNoCliente()));
            } catch (Exception e) {
                logger.error("Error al desplegar listado de pedidos " + e.getLocalizedMessage());
                logger.error(e);
                //setDestinatarioMercancias(new BuscarDestinatariosMercanciasConfig().buscarDestinatariosMercancias(this.getUsuarioLogueado().getNoCliente()));
            }
        }
        return destinatarioMercancias;
    }

    public void setDestinatarioMercancias(List<DestinatarioMercancia> destinatarioMercancias) {
        this.destinatarioMercancias = destinatarioMercancias;
    }

    public void setDestinatarioMercanciasJson(String destinatarioMercanciasJson) {
        this.destinatarioMercanciasJson = destinatarioMercanciasJson;
    }

    public String getDestinatarioMercanciasJson(){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            destinatarioMercanciasJson = objectMapper.writeValueAsString(getDestinatarioMercancias());
            logger.info("destinatarioMercanciasJson::::" + destinatarioMercanciasJson);

        }catch (Exception e){
            logger.error(e);
            destinatarioMercanciasJson = "";
        }
        return destinatarioMercanciasJson;
    }

    public void obtenerDestinatarioMercancia(){
        logger.info("obtenerDestinatarioMercancia " + destinatarioMercancias.size());
        for(int i = 0;i < destinatarioMercancias.size(); i++ ){
            logger.info("obtenerDestinatarioMercancia COmpare" + destinatarioMercancias.get(i).getNoDestinatario() + " " + getDestinatarioMercancia());
            if(destinatarioMercancias.get(i).getNoDestinatario().equals(getDestinatarioMercancia())){
                destinatarioMercanciaSel = destinatarioMercancias.get(i);
                break;
            }
        }
        logger.info("obtenerDestinatarioMercancia ::::::" + destinatarioMercanciaSel);
    }





    public void continuaCompra(){
        setMaterialesJson(materiales);
    }

    public String asignaProducto(){

        return "";
    }

    public String getMaterialesJson() {
        return materialesJson;
    }

    public void setMaterialesJson(String materialesJson) {
        this.materialesJson = materialesJson;
    }

    public void setMaterialesJson(List<PedidoMaterial> materiales) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            materialesJson = objectMapper.writeValueAsString(materiales);
            logger.info("setMaterialesJson::::" + materialesJson);

        }catch (Exception e){
            logger.error(e);
        }
    }
    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getMaterialSeleccionadoJson() {
        List<PedidoMaterial> seleccionados = new ArrayList<>();
        PedidoMaterial pedidoMaterial = null;
        try{
            if(materiales != null && materiales.size() > 0) {
                for (int i = 0; i < materiales.size(); i++) {
                    pedidoMaterial = materiales.get(i);
                    if ((pedidoMaterial.getCantidad() != null && Integer.valueOf(pedidoMaterial.getCantidad()) > 0)) {
                        seleccionados.add(pedidoMaterial);
                    }
                }

                ObjectMapper objectMapper = new ObjectMapper();
                materialSeleccionadoJson = objectMapper.writeValueAsString(seleccionados);
            }
        }catch(Exception e){
            materialSeleccionadoJson = "";
        }
        return materialSeleccionadoJson;
    }

    public void setMaterialSeleccionadoJson(String materialSeleccionadoJson) {
        this.materialSeleccionadoJson = materialSeleccionadoJson;
    }

    public DestinatarioMercancia getDestinatarioMercanciaSel() {
        return destinatarioMercanciaSel;
    }

    public void setDestinatarioMercanciaSel(DestinatarioMercancia destinatarioMercanciaSel) {
        this.destinatarioMercanciaSel = destinatarioMercanciaSel;
    }

    public List<PedidoMaterial> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<PedidoMaterial> materiales) {
        this.materiales = materiales;
    }



    public List<UsoCFDIDetalle> getUsoCFDIDetalles() {
        return usoCFDIDetalles;
    }

    public void setUsoCFDIDetalles(List<UsoCFDIDetalle> usoCFDIDetalles) {
        this.usoCFDIDetalles = usoCFDIDetalles;
    }

    public String getUsoCFDIDetallesJson() {
        return usoCFDIDetallesJson;
    }

    public void setUsoCFDIDetallesJson(String usoCFDIDetallesJson) {
        this.usoCFDIDetallesJson = usoCFDIDetallesJson;
    }

    public void preparaFacturacion(){
        logger.info("preparaFacturacion");


    }

    public String getNroPedidoCliente() {
        return nroPedidoCliente;
    }

    public void setNroPedidoCliente(String nroPedidoCliente) {
        this.nroPedidoCliente = nroPedidoCliente;
    }

    public String getEmailCotizarFlete() {
        return emailCotizarFlete;
    }

    public void setEmailCotizarFlete(String emailCotizarFlete) {
        this.emailCotizarFlete = emailCotizarFlete;
    }


    @Override
    public String toString() {
        return "CrearPedido_backing{" +
                "buscarDestinatariosMercanciasRFC=" + buscarDestinatariosMercanciasRFC +
                ", buscarClasePedidoRFC=" + buscarClasePedidoRFC +
                ", precioMaterialRFC=" + precioMaterialRFC +
                ", usoCfdiRFC=" + usoCfdiRFC +
                ", buscarMetodoPagoCfdiRFC=" + buscarMetodoPagoCfdiRFC +
                ", pedidoBd=" + pedidoBd +
                ", pedidoPartidas=" + pedidoPartidas +
                ", materialService=" + materialService +
                ", pedidoService=" + pedidoService +
                ", pedidoPartidasService=" + pedidoPartidasService +
                ", idPedido='" + idPedido + '\'' +
                ", nroPedidoCliente='" + nroPedidoCliente + '\'' +
                ", destino='" + destino + '\'' +
                ", nroCliente='" + nroCliente + '\'' +
                ", nroPedido='" + nroPedido + '\'' +
                ", destinatario='" + destinatario + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", organizacionVenta='" + organizacionVenta + '\'' +
                ", sociedad='" + sociedad + '\'' +
                ", destinatarioMercancia='" + destinatarioMercancia + '\'' +
                ", clasePedido='" + clasePedido + '\'' +
                ", tipoMaterial='" + tipoMaterial + '\'' +
                ", estatus='" + estatus + '\'' +
                ", metodoPago='" + metodoPago + '\'' +
                ", usoCFDI='" + usoCFDI + '\'' +
                ", comprobanteBancario='" + comprobanteBancario + '\'' +
                ", datosEntrega='" + datosEntrega + '\'' +
                ", nombreContacto='" + nombreContacto + '\'' +
                ", apellidoContacto='" + apellidoContacto + '\'' +
                ", telefonoContacto='" + telefonoContacto + '\'' +
                ", telefonoFijoContacto='" + telefonoFijoContacto + '\'' +
                ", horarioRecepcion='" + horarioRecepcion + '\'' +
                ", referenciaUbicacion='" + referenciaUbicacion + '\'' +
                ", productoAlmacenar='" + productoAlmacenar + '\'' +
                ", capacidadesTransporte='" + capacidadesTransporte + '\'' +
                ", equipoEspecial='" + equipoEspecial + '\'' +
                ", noCotizacion='" + noCotizacion + '\'' +
                ", estatusCotizacion='" + estatusCotizacion + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", segmento='" + segmento + '\'' +
                ", mensajeError='" + mensajeError + '\'' +
                ", emailCotizarFlete='" + emailCotizarFlete + '\'' +
                ", materialSeleccionadoJson='" + materialSeleccionadoJson + '\'' +
                ", destinatarioMercancias=" + destinatarioMercancias +
                ", destinatarioMercanciaSel=" + destinatarioMercanciaSel +
                ", destinatarioMercanciasJson='" + destinatarioMercanciasJson + '\'' +
                ", materialesJson='" + materialesJson + '\'' +
                ", materiales=" + materiales +
                ", usoCFDIDetalles=" + usoCFDIDetalles +
                ", usoCFDIDetallesJson='" + usoCFDIDetallesJson + '\'' +
                ", descripcionDestinatario='" + descripcionDestinatario + '\'' +
                '}';
    }

    public String generaPedido(){
        logger.info("Genera Pedido");
        try{
            Pedido pedido = new Pedido();
            CrearPedidoRFC crearPedidoRFC = this.getSpringContext().getBean("crearPedidoRFC", CrearPedidoRFC.class);
            try {
                fillPedido(pedido);

                PedidoResultado pedidoResultado =  crearPedidoRFC.crearPedido(pedido);
                logger.info("Recibiendo Respuesta " + pedidoResultado);
                if(!pedidoResultado.getGeneroDocumentoVenta().equals("0")){
                    /*getFacesContext().getMessageList().add(new FacesMessage("Error"));
                    logger.info("Respuesta invalida de RFC");*/
                }
            }catch(Exception e){
                logger.error(e);
            }
        }catch (Exception e){
            logger.error(e);
            getFacesContext().getMessageList().add(new FacesMessage("Error"));
        }
        logger.info("fin Genera Pedido");
        return "/pedidos/listado";
    }

    public String finalizar(){
        logger.info("Finalizar");
        return "/pedidos/listado";
    }

    public void cotizarFlete(){
        pedidoBd.setCorreoElectronico(getCorreoElectronico());
        pedidoBd.setNoCotizacion(String.valueOf(KeyGenerator.getKey()));
        pedidoBd.setEstatusCotizacion("En Captura");

        setNoCotizacion(pedidoBd.getNoCotizacion());
        pedidoService.save(pedidoBd);

    }

    public void fillPedido(Pedido pedido){
        pedido.setNombreCliente(getNombreContacto() + " " + getApellidoContacto());
        pedido.setNroTelefonoFijo(getTelefonoFijoContacto());
        pedido.setHorarioRecepcion(getHorarioRecepcion());
        //pedido.setNroTeleofno();

        //HEADER
        pedido.getPedidoEncabezado().setCanalDistribucion("20");
        pedido.getPedidoEncabezado().setClasePedido(getClasePedido());
        pedido.getPedidoEncabezado().setMetodoPago(getMetodoPago());
        pedido.getPedidoEncabezado().setMoneda("MXN");
        pedido.getPedidoEncabezado().setMotivoPedido("166");
        pedido.getPedidoEncabezado().setNroCliente(Helper.lpad(getUsuarioLogueado().getNoCliente(),10,"0"));
        pedido.getPedidoEncabezado().setNroDestinatarioMercancias(Helper.lpad(getDestinatarioMercanciaSel().getNoDestinatario(),10,"0"));
        pedido.getPedidoEncabezado().setOrganizacionVenta(destinatarioMercanciaSel.getOrganizacionVentas());
        pedido.getPedidoEncabezado().setSector("02");
        pedido.getPedidoEncabezado().setSegmento(getSegmento());
        pedido.getPedidoEncabezado().setUsoCFDI(getUsoCFDI());
        pedido.getPedidoEncabezado().setNroPedidoCliente(getNroPedidoCliente());
        pedido.getPedidoEncabezado().setSociedad(destinatarioMercanciaSel.getSociedad());

        //ITEMS
        PedidoMaterial pedidoMaterial = null;
        PedidoPartidas pedidoPartidas = null;
        for(int i = 0; i < materiales.size(); i++){
            try{
                pedidoMaterial = materiales.get(i);
                if(Integer.valueOf(pedidoMaterial.getCantidad()) > 0){
                    pedidoPartidas = new PedidoPartidas();
                    pedidoPartidas.setCantidad(Helper.lpad(pedidoMaterial.getCantidad(),13,"0"));
                    pedidoPartidas.setNroMaterial(Helper.lpad(pedidoMaterial.getSku(),18,"0"));
                    pedidoPartidas.setPosicion(Helper.lpad(pedidoMaterial.getPosicion(),6,"0"));
                    pedidoPartidas.setUnidadMedida(pedidoMaterial.getUnidadMedida());

                    pedido.getPedidoPartidas().add(pedidoPartidas);
                }
            }catch (NumberFormatException e){

            }
        }
        pedido.setReferenciaUbicacion(getReferenciaUbicacion());
        pedido.setPedidoProductoAlmacenar(getProductoAlmacenar());
        pedido.setPedidoCapacidadesTransporteEspecial(getCapacidadesTransporte());
        pedido.setPedidoEquipoEspecialProteccionPersonal(getEquipoEspecial());

    }

    public void setDestinatarioAndNroPedido(){
        logger.info("setDestinatarioAndNroPedido::::::" + getDestinatarioMercancia() + " " + getNroPedidoCliente() );
        try {
            obtenerDestinatarioMercancia();
            buscarClasePedidoRFC = this.getSpringContext().getBean("buscarClasePedidoRFC", BuscarClasePedidoRFC.class);
            ClasePedido clasePedido = buscarClasePedidoRFC.buscarClasePedido(destinatarioMercanciaSel.getOrganizacionVentas(), destinatarioMercanciaSel.getCodigoPostal());
            logger.info("CLASE PEDIDO " + clasePedido);
            if(clasePedido.getResultCode().equals("0")) {
                setClasePedido(clasePedido.getClasePedido());


            }else{
                setClasePedido("");
                setMensajeError("Favor de contactarnos Correo servicioaclientes@rotoplas.com o al Teléfono 800 506 3000");
            }
        }catch (Exception e){
            logger.error(e);
            //setClasePedido(new BuscarClasePedidoConfig().buscarClasePedido().getClasePedido());
            //setMensajeError("Favor de contactarnos Correo servicioaclientes@rotoplas.com o al Teléfono 800 506 3000");

        }

        if(getClasePedido() != null & !getClasePedido().isEmpty()) {
            logger.info("getNroPedidoCliente:::" + getNroPedidoCliente());
            if (pedidoBd == null) {
                pedidoBd = new com.alliax.portalclientes.domain.Pedido();
                pedidoBd.setClasePedido(getClasePedido());
                pedidoBd.setNroPedido(getNroPedidoCliente());
                pedidoBd.setDestinatarioMercancia(getDestinatarioMercancia());
                pedidoBd.setCodigoPostal(destinatarioMercanciaSel.getCodigoPostal());
                pedidoBd.setOrganizacionVenta(destinatarioMercanciaSel.getOrganizacionVentas());
                pedidoBd.setSociedad(destinatarioMercanciaSel.getSociedad());
                pedidoBd.setEstatus("PEN");
                pedidoBd.setNroCliente(getUsuarioLogueado().getNoCliente());
                pedidoBd.setFechaCreacion(Calendar.getInstance().getTime());
            }
            logger.info(pedidoBd);
            pedidoService = this.getSpringContext().getBean("pedidoService", PedidoService.class);
            pedidoPartidasService = this.getSpringContext().getBean("pedidoPartidasService", PedidoPartidasService.class);

            pedidoService.save(pedidoBd);
        }
        logger.info(this);
    }

    public String asignaPedidoSegmento(){
        logger.info("asignaPedidoSegmento::::::" + getSegmento() );

        if(getSegmento() != null){
            pedidoBd.setTipoMaterial(getSegmento());
            pedidoService.save(pedidoBd);
        }

        materialService = this.getSpringContext().getBean("materialService",MaterialService.class);

        List<Material> materialesDb = materialService.findByTipoMaterial((getSegmento().equals("10")?getSegmento():"11"));
        materiales = new ArrayList<>();
        Material material = null;
        PedidoMaterial pedidoMaterial = null;

        for(int i = 0; i < materialesDb.size() ; i++){
            material = materialesDb.get(i);
            pedidoMaterial = new PedidoMaterial();

            pedidoMaterial.setDescripcion(material.getDescripcion()==null?"":material.getDescripcion().trim());
            pedidoMaterial.setSku(material.getSku()==null?"":material.getSku().trim());
            pedidoMaterial.setUnidadMedida(material.getUnidadMedida()==null?"":material.getUnidadMedida().trim());
            pedidoMaterial.setUrlFoto(material.getUrlFoto()==null?"":material.getUrlFoto().trim());
            materiales.add(pedidoMaterial);
        }
        setMaterialesJson(materiales);

        return "";
    }

    public void asignaPedidoMaterial(){
        logger.info("asignaPedidoMaterial::::::" +materialSeleccionadoJson);
        ObjectMapper objectMapper = new ObjectMapper();
        PedidoMaterial pedidoMaterial = null;
        PedidoMaterial pedidoMaterial2 = null;
        PrecioMaterial precioMaterial = null;
        PedidoMaterial[] materialPedido = null;

        com.alliax.portalclientes.domain.PedidoPartidas pedidoPartida = null;
        PedidoPartidasPK pedidoPartidasPK = null;

        int count =1;
        try {
            if(materialSeleccionadoJson != null) {
                String json = materialSeleccionadoJson;

                materialPedido = objectMapper.readValue(json, PedidoMaterial[].class);

                precioMaterialRFC = this.getSpringContext().getBean("precioMaterialRFC", PrecioMaterialRFC.class);

                if(materialPedido.length > 0){
                    if(pedidoPartida == null) {
                        pedidoPartidas = new ArrayList<>();
                    }
                }

                for (int i = 0; i < materialPedido.length; i++) {
                    pedidoMaterial = materialPedido[i];

                    pedidoPartida = new com.alliax.portalclientes.domain.PedidoPartidas();
                    pedidoPartidasPK = new PedidoPartidasPK();

                    pedidoPartidasPK.setIdPedido(pedidoBd.getIdPedido());
                    pedidoPartidasPK.setSku(pedidoMaterial.getSku());

                    pedidoPartida.setId(pedidoPartidasPK);

                    for(int j = 0; j < materiales.size(); j++){
                        pedidoMaterial2 = materiales.get(j);
                        try {
                            if (pedidoMaterial.getSku().equals(pedidoMaterial2.getSku()) && (Integer.valueOf(pedidoMaterial.getCantidad()) > 0)) {
                                pedidoMaterial2.setCantidad(pedidoMaterial.getCantidad());
                                pedidoMaterial2.setPosicion(String.valueOf(count++));

                                try {
                                    precioMaterial = precioMaterialRFC.obtienePrecioMaterial(getClasePedido(), destinatarioMercanciaSel.getOrganizacionVentas(),
                                            "20", "02", getSegmento(), Helper.lpad(pedidoMaterial2.getSku(), 18, "0"), pedidoMaterial2.getCantidad(),
                                            pedidoMaterial2.getUnidadMedida(), Helper.lpad(getUsuarioLogueado().getNoCliente(), 10, "0"), Helper.lpad(getDestinatarioMercanciaSel().getNoDestinatario(), 10, "0"));
                                    logger.info("Respuesta RFC " + precioMaterial);
                                } catch (Exception e) {
                                    logger.error(e);
                                    //precioMaterial = new PrecioMaterialConfig().obtenerPrecioMaterial();
                                }
                                //no asignar el valor del error para los pedidos de industrial y
                                //que sean error de fecha de entrega (codigo error 5)
                                /*if (!((getSegmento().equals("13") || getSegmento().equals("14")) && precioMaterial.getCodigoError().equals("5"))){
                                    pedidoMaterial2.setCodigoError(precioMaterial.getCodigoError());
                                    pedidoMaterial2.setMensajeError(precioMaterial.getMensajeError());
                                }

                                pedidoMaterial2.setCodigoError("1");
                                pedidoMaterial2.setMensajeError("Ejemplo error");*/

                                pedidoMaterial2.setCodigoError(precioMaterial.getCodigoError());
                                pedidoMaterial2.setMensajeError(precioMaterial.getMensajeError());

                                pedidoMaterial2.setFechaEntrega(precioMaterial.getFechaEntrega());
                                pedidoMaterial2.setIva(precioMaterial.getIva());
                                pedidoMaterial2.setMoneda(precioMaterial.getMoneda());
                                pedidoMaterial2.setPrecioNeto(String.valueOf(precioMaterial.getPrecioNeto()));
                                pedidoMaterial2.setMonto(String.valueOf(precioMaterial.getMonto()));

                                //save to DB
                                pedidoPartida.setPosicion(pedidoMaterial2.getPosicion());
                                pedidoPartida.setCantidad(pedidoMaterial.getCantidad());
                                pedidoPartida.setCodigoError(pedidoMaterial2.getCodigoError());
                                pedidoPartida.setMensajeError(pedidoMaterial2.getMensajeError());

                                pedidoPartida.setFechaEntrega(pedidoMaterial2.getFechaEntrega());
                                pedidoPartida.setIva(pedidoMaterial2.getIva());
                                pedidoPartida.setMoneda(pedidoMaterial2.getMoneda());
                                pedidoPartida.setPrecioNeto(String.valueOf(pedidoMaterial2.getPrecioNeto()));
                                pedidoPartida.setMonto(String.valueOf(pedidoMaterial2.getMonto()));

                                pedidoPartidas.add(pedidoPartida);

                                break;
                            }
                        }catch (Exception e){
                            logger.error(e);
                        }
                    }
                }
            }

            if(pedidoPartidas != null && pedidoPartidas.size()>0){
                pedidoPartidasService.save(pedidoPartidas);
            }

            MetodoPagoCFDI metodoPagoCFDI = null;
            UsoCFDI usoCFDI = null;
            logger.info("CFDI");
            try{
                usoCfdiRFC = this.getSpringContext().getBean("usoCfdiRFC", UsoCfdiRFC.class);
                usoCFDI = ((UsoCfdiRFC) usoCfdiRFC).usoCFDI();

                if(!"0".equals(usoCFDI.getResultCode())){

                }else{
                    setUsoCFDIDetalles(usoCFDI.getDetalles());
                    objectMapper = new ObjectMapper();
                    setUsoCFDIDetallesJson(objectMapper.writeValueAsString(getUsoCFDIDetalles()));
                }
            }catch (Exception e){
                logger.error(e);
                /*
                try {
                    UsoCfdiConfig usoCfdiConfig = new UsoCfdiConfig();
                    objectMapper = new ObjectMapper();
                    setUsoCFDIDetallesJson(objectMapper.writeValueAsString(usoCfdiConfig.usoCFDI().getDetalles()));
                }catch (Exception e1){
                    logger.error(e1);
                }
                */
            }

            logger.info("METODO");
            try{
                buscarMetodoPagoCfdiRFC = this.getSpringContext().getBean("buscarMetodoPagoCfdiRDC", BuscarMetodoPagoCfdiRFC.class);
                metodoPagoCFDI =  buscarMetodoPagoCfdiRFC.buscarMetodoPagoCFDI(this.getUsuarioLogueado().getNoCliente());

                if(!"0".equals(metodoPagoCFDI.getResultCode())){

                }else{
                    setMetodoPago(metodoPagoCFDI.getClaveMetodoPago());
                }
            }catch (Exception e){
               /*
                try{
                    BuscarMetodoPagoCfdiConfig buscarMetodoPagoCfdiConfig = new BuscarMetodoPagoCfdiConfig();
                    setMetodoPago(buscarMetodoPagoCfdiConfig.buscarMetodoPagoCFDI(this.getUsuarioLogueado().getNoCliente()).getClaveMetodoPago());
                }catch(Exception e1){

                }
                */
                logger.error(e);
            }
        }catch(Exception e){
            logger.error(e);
        }
    }

    public void asignaFacturacion(){
        logger.info("asignaFacturacion" + this);
        pedidoBd.setUsoCFDI(getUsoCFDI());
        pedidoBd.setMetodoPago(getMetodoPago());

        pedidoService.save(pedidoBd);
    }

    public void saveComentarios(){
        logger.info("SaveComentarios " + this);

        pedidoBd.setNombreContacto(getNombreContacto());
        pedidoBd.setApellidoContacto(getApellidoContacto());
        pedidoBd.setTelefonoContacto(getTelefonoContacto());
        pedidoBd.setHorarioRecepcion(getHorarioRecepcion());
        pedidoBd.setReferenciaUbicacion(getReferenciaUbicacion());
        pedidoBd.setProductoAlmacenar(getProductoAlmacenar());
        pedidoBd.setCapacidadesTransporte(getCapacidadesTransporte());
        pedidoBd.setEquipoEspecial(getEquipoEspecial());

        pedidoService.save(pedidoBd);

    }
}
