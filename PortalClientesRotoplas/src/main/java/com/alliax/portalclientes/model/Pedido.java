package com.alliax.portalclientes.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private String nombreCliente;
    private String nroTeleofno;
    private String nroTelefonoFijo;
    private String horarioRecepcion;

    private PedidoEncabezado pedidoEncabezado;
    private List<PedidoPartidas> pedidoPartidas;
    private List<PedidoReferenciaUbicacion> pedidoReferenciaUbicacion;
    private List<PedidoProductoAlmacenar> pedidoProductoAlmacenar;
    private List<PedidoCapacidadesTransporteEspecial> pedidoCapacidadesTransporteEspecial;
    private List<PedidoEquipoEspecialProteccionPersonal> pedidoEquipoEspecialProteccionPersonal;

    public static final int TEXT_MAX_LENGTH = 132;

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNroTeleofno() {
        return nroTeleofno;
    }

    public void setNroTeleofno(String nroTeleofno) {
        this.nroTeleofno = nroTeleofno;
    }

    public String getNroTelefonoFijo() {
        return nroTelefonoFijo;
    }

    public void setNroTelefonoFijo(String nroTelefonoFijo) {
        this.nroTelefonoFijo = nroTelefonoFijo;
    }

    public String getHorarioRecepcion() {
        return horarioRecepcion;
    }

    public void setHorarioRecepcion(String horarioRecepcion) {
        this.horarioRecepcion = horarioRecepcion;
    }

    public PedidoEncabezado getPedidoEncabezado() {
        return pedidoEncabezado;
    }

    public void setPedidoEncabezado(PedidoEncabezado pedidoEncabezado) {
        this.pedidoEncabezado = pedidoEncabezado;
    }

    public List<PedidoPartidas> getPedidoPartidas() {
        if(pedidoPartidas == null){
            pedidoPartidas = new ArrayList<>();
        }
        return pedidoPartidas;
    }

    public void setPedidoPartidas(List<PedidoPartidas> pedidoPartidas) {
        this.pedidoPartidas = pedidoPartidas;
    }

    public List<PedidoReferenciaUbicacion> getPedidoReferenciaUbicacion() {
        if(pedidoReferenciaUbicacion == null){
            pedidoReferenciaUbicacion = new ArrayList<>();
        }
        return pedidoReferenciaUbicacion;
    }

    public void setPedidoReferenciaUbicacion(List<PedidoReferenciaUbicacion> pedidoReferenciaUbicacion) {
        this.pedidoReferenciaUbicacion = pedidoReferenciaUbicacion;
    }

    public List<PedidoProductoAlmacenar> getPedidoProductoAlmacenar() {
        if(pedidoProductoAlmacenar == null){
            pedidoProductoAlmacenar = new ArrayList<>();
        }
        return pedidoProductoAlmacenar;
    }

    public void setPedidoProductoAlmacenar(List<PedidoProductoAlmacenar> pedidoProductoAlmacenar) {
        this.pedidoProductoAlmacenar = pedidoProductoAlmacenar;
    }

    public List<PedidoCapacidadesTransporteEspecial> getPedidoCapacidadesTransporteEspecial() {
        if(pedidoCapacidadesTransporteEspecial == null){
            pedidoCapacidadesTransporteEspecial = new ArrayList<>();
        }
        return pedidoCapacidadesTransporteEspecial;
    }

    public void setPedidoCapacidadesTransporteEspecial(List<PedidoCapacidadesTransporteEspecial> pedidoCapacidadesTransporteEspecial) {
        this.pedidoCapacidadesTransporteEspecial = pedidoCapacidadesTransporteEspecial;
    }

    public List<PedidoEquipoEspecialProteccionPersonal> getPedidoEquipoEspecialProteccionPersonal() {
        if(pedidoEquipoEspecialProteccionPersonal == null){
            pedidoEquipoEspecialProteccionPersonal = new ArrayList<>();
        }
        return pedidoEquipoEspecialProteccionPersonal;
    }

    public void setPedidoEquipoEspecialProteccionPersonal(List<PedidoEquipoEspecialProteccionPersonal> pedidoEquipoEspecialProteccionPersonal) {
        this.pedidoEquipoEspecialProteccionPersonal = pedidoEquipoEspecialProteccionPersonal;
    }

    public void setReferenciaUbicacion(String referenciaUbicacion){
        List<String> listaTexto = textoALista(referenciaUbicacion);
        PedidoReferenciaUbicacion pedidoReferenciaUbicacion = new PedidoReferenciaUbicacion();
        for(int i = 0; i < listaTexto.size(); i++){
            pedidoReferenciaUbicacion.setSecuencia(i+1);
            pedidoReferenciaUbicacion.setLineaTexto(listaTexto.get(i));
            this.getPedidoReferenciaUbicacion().add(pedidoReferenciaUbicacion);
        }
    }

    public void setPedidoProductoAlmacenar(String productoAlmacenar){
        List<String> listaTexto = textoALista(productoAlmacenar);
        PedidoProductoAlmacenar pedidoProductoAlmacenar = new PedidoProductoAlmacenar();
        for(int i = 0; i < listaTexto.size(); i++){
            pedidoProductoAlmacenar.setSecuencia(i+1);
            pedidoProductoAlmacenar.setLineaTexto(listaTexto.get(i));
            this.getPedidoProductoAlmacenar().add(pedidoProductoAlmacenar);
        }
    }

    public void setPedidoCapacidadesTransporteEspecial(String capacidadesTransporteEspecial){
        List<String> listaTexto = textoALista(capacidadesTransporteEspecial);
        PedidoCapacidadesTransporteEspecial pedidoCapacidadesTransporteEspecial = new PedidoCapacidadesTransporteEspecial();
        for(int i = 0; i < listaTexto.size(); i++){
            pedidoCapacidadesTransporteEspecial.setSecuencia(i+1);
            pedidoCapacidadesTransporteEspecial.setLineaTexto(listaTexto.get(i));
            this.getPedidoCapacidadesTransporteEspecial().add(pedidoCapacidadesTransporteEspecial);
        }
    }

    public void setPedidoEquipoEspecialProteccionPersonal(String equipoEspecialProteccionPersonal){
        List<String> listaTexto = textoALista(equipoEspecialProteccionPersonal);
        PedidoEquipoEspecialProteccionPersonal pedidoEquipoEspecialProteccionPersonal = new PedidoEquipoEspecialProteccionPersonal();
        for(int i = 0; i < listaTexto.size(); i++){
            pedidoEquipoEspecialProteccionPersonal.setSecuencia(i+1);
            pedidoEquipoEspecialProteccionPersonal.setLineaTexto(listaTexto.get(i));
            this.getPedidoEquipoEspecialProteccionPersonal().add(pedidoEquipoEspecialProteccionPersonal);
        }
    }

    public List<String> textoALista(String lineaTexto){
        List<String> listaTexto = new ArrayList<>();
        char[] caracteres =  lineaTexto.toCharArray();
        StringBuffer linea = new StringBuffer();
        for(int i = 1; i <= caracteres.length; i++){
            linea.append(caracteres[i-1]);
            if(i%TEXT_MAX_LENGTH == 0){
                listaTexto.add(linea.toString());
                linea = new StringBuffer();
            }
        }
        if(linea.length()>0) {
            listaTexto.add(linea.toString());
        }
        return listaTexto;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "nombreCliente='" + nombreCliente + '\'' +
                ", nroTeleofno='" + nroTeleofno + '\'' +
                ", nroTelefonoFijo='" + nroTelefonoFijo + '\'' +
                ", horarioRecepcion='" + horarioRecepcion + '\'' +
                ", pedidoEncabezado=" + pedidoEncabezado +
                ", pedidoPartidas=" + pedidoPartidas +
                ", pedidoReferenciaUbicacion=" + pedidoReferenciaUbicacion +
                ", pedidoProductoAlmacenar=" + pedidoProductoAlmacenar +
                ", pedidoCapacidadesTransporteEspecial=" + pedidoCapacidadesTransporteEspecial +
                ", pedidoEquipoEspecialProteccionPersonal=" + pedidoEquipoEspecialProteccionPersonal +
                '}';
    }
}
