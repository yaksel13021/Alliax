package com.alliax.portalclientes.view;

import com.alliax.portalclientes.controller.BuscarDestinatariosMercanciasRFC;
import com.alliax.portalclientes.model.DestinatarioMercancia;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean(name="pedido")
@SessionScoped
public class Pedido_backing extends AbstractBackingGen {

    @Autowired
    BuscarDestinatariosMercanciasRFC buscarDestinatariosMercanciasRFC;

    private String idPedido;

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

    private List<DestinatarioMercancia> destinatarioMercancias;

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

    public List<DestinatarioMercancia> getDestinatarioMercancias() {
        return destinatarioMercancias;
    }

    public void setDestinatarioMercancias(List<DestinatarioMercancia> destinatarioMercancias) {
        this.destinatarioMercancias = destinatarioMercancias;
    }

    public String buscarDestinatarioMercancias(){

        return "";
    }

}
