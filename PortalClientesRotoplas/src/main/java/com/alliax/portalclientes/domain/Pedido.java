package com.alliax.portalclientes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.Date;

@Entity
@Table(name="Pedido")
@NamedQueries({
        @NamedQuery(name="Pedido.findFletes",
                query="select p from Pedido p where p.noCotizacion is not null " +
                        " and (1 = :flagCot or p.noCotizacion = :noCotizacion )" +
                        " and (1 = :flagCliente or p.nroCliente = :nroCliente )" +
                        " and (1 = :flagFecha or day(p.fechaCreacion) = day(:fecha) )" +
                        "order by  p.id asc")
})
public class Pedido {
    @Id
    @Column(name="idPedido")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idPedido;

    @Column(name="destino")
    private String destino;
    @Column(name="nroCliente")
    private String nroCliente;
    @Column(name="nroPedido")
    private String nroPedido;
    @Column(name="destinatario")
    private String destinatario;
    @Column(name="codigoPostal")
    private String codigoPostal;
    @Column(name="organizacionVenta")
    private String organizacionVenta;
    @Column(name="sociedad")
    private String sociedad;
    @Column(name="destinatarioMercancia")
    private String destinatarioMercancia;
    @Column(name="clasePedido")
    private String clasePedido;
    @Column(name="tipoMaterial")
    private String tipoMaterial;
    @Column(name="estatus")
    private String estatus;
    @Column(name="metodoPago")
    private String metodoPago;
    @Column(name="usoCFDI")
    private String usoCFDI;
    @Column(name="comprobanteBancario")
    private String comprobanteBancario;
    @Column(name="datosEntrega")
    private String datosEntrega;
    @Column(name="nombreContacto")
    private String nombreContacto;
    @Column(name="apellidoContacto")
    private String apellidoContacto;
    @Column(name="telefonoContacto")
    private String telefonoContacto;
    @Column(name="telefonoFijoContacto")
    private String telefonoFijoContacto;
    @Column(name="horarioRecepcion")
    private String horarioRecepcion;
    @Column(name="referenciaUbicacion")
    private String referenciaUbicacion;
    @Column(name="productoAlmacenar")
    private String productoAlmacenar;
    @Column(name="capacidadesTransporte")
    private String capacidadesTransporte;
    @Column(name="equipoEspecial")
    private String equipoEspecial;
    @Column(name="noCotizacion")
    private String noCotizacion;
    @Column(name="estatusCotizacion")
    private String estatusCotizacion;
    @Column(name="correoElectronico")
    private String correoElectronico;
    @Column(name="fechaCreacion")
    private Date fechaCreacion;


    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
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
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
