package com.alliax.portalclientes.view;

import com.alliax.portalclientes.controller.ConstructEmail;
import com.alliax.portalclientes.controller.InfoClienteRFC;
import com.alliax.portalclientes.domain.RolUsuario;
import com.alliax.portalclientes.domain.Usuario;
import com.alliax.portalclientes.general.formato.Fecha;
import com.alliax.portalclientes.general.formato.Generales;
import com.alliax.portalclientes.model.ClienteInfo;
import com.alliax.portalclientes.service.UsuarioService;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.HashMap;
import java.util.Map;

@ManagedBean(name="colaboradores")
@ViewScoped
public class AltaColaboradores_backing extends AbstractBacking {

    private final static Logger logger = Logger.getLogger(AltaColaboradores_backing.class);
    UsuarioService usrServ = this.getSpringContext().getBean("usuarioService",UsuarioService.class);
    public String email1;
    public String email2;
    public String email3;
    public String actividad;
    public String activo;
    public String activo2;
    public String activo3;

    private static String RC = "RC";
    private static String RS = "RS";
    private static String RE = "RE";
    private static String ROLE_PEDIDOS ="ROLE_PEDIDOS";
    private static String ROLE_SEGUIMIENTO_PEDIDOS ="ROLE_SEGUIMIENTO_PEDIDOS";
    private static String ROLE_ESTADO_CUENTA = "ROLE_ESTADO_CUENTA";
    private static String TRUE = "true";
    private ClienteInfo clienteInfo;
    private int status;
    Usuario usuario = usrServ.findByUserName(this.getUsuarioLogueado().getUsername());

    /**
     * Metodo para dar de alta usuario colaborador
     * @return
     * @throws Exception
     */
    public String altaColaborador() throws Exception{
        cargaInfoCliente();
        Map<String, String> map = new HashMap<>();
        map.put(RC, ROLE_PEDIDOS);
        map.put(RS, ROLE_SEGUIMIENTO_PEDIDOS);
        map.put(RE, ROLE_ESTADO_CUENTA);
        try {
            logger.info("Usuario: " + this.getUsuarioLogueado().getUsername());
            Usuario colaborador = usrServ.findByUserName(this.getActividad()+usuario.getNoCliente());

            if(colaborador == null){
                BCryptPasswordEncoder encoder = this.getSpringContext().getBean("encoder",BCryptPasswordEncoder.class);
                colaborador = new Usuario();
                colaborador.setUsuario(this.getActividad()+usuario.getNoCliente());
                colaborador.setPasswordStr(Generales.generaPassword());
                colaborador.setPassword(encoder.encode(colaborador.getPasswordStr()));
                colaborador.setNoCliente(this.getActividad()+usuario.getNoCliente());
                if(this.getActividad().equals(RC)){
                    colaborador.setEmail(this.email1);
                    colaborador.setEstatus(activo.equals(TRUE) ? "I" : "B");
                }else if (this.getActividad().equals(RS)){
                    colaborador.setEmail(this.email2);
                    colaborador.setEstatus(activo2.equals(TRUE) ? "I" : "B");
                }else if (this.getActividad().equals(RE)){
                    colaborador.setEmail(this.email3);
                    colaborador.setEstatus(activo3.equals(TRUE) ? "I" : "B");
                }

                colaborador.setFechaEntrada(usuario.getFechaEntrada());
                colaborador.setIntentosFallidos(0);
                colaborador.setFechaAlta(Fecha.getDateTimeActual());
                colaborador.setPais(usuario.getPais());
                colaborador.setParent(usuario.getNoCliente());
	            RolUsuario rolUsr = new RolUsuario();
	            rolUsr.setRol(map.get(this.getActividad()));
	            colaborador.setRol(rolUsr);
                status = 1;
            } else{
                if(this.getActividad().equals(RC)){
                    colaborador.setEmail(this.email1);
                    colaborador.setEstatus(activo.equals(TRUE) ? "I" : "B");
                }else if (this.getActividad().equals(RS)){
                    colaborador.setEmail(this.email2);
                    colaborador.setEstatus(activo2.equals(TRUE) ? "I" : "B");
                }else if (this.getActividad().equals(RE)){
                    colaborador.setEmail(this.email3);
                    colaborador.setEstatus(activo3.equals(TRUE) ? "I" : "B");
                }
                status = 2;
            }
            usrServ.save(colaborador);
            logger.info("Mensaje save");

            ConstructEmail mail = this.getSpringContext().getBean("constructEmail",ConstructEmail.class);
            mail.enviaCorreoAlta(colaborador,this.getClienteInfo());

        } catch(Exception e){
            status = 0;
        }
        return "";
    }

    public String cargaInfoCliente(){
        try {
            InfoClienteRFC infoClienteRfc = this.getSpringContext().getBean("infoClienteRfc",InfoClienteRFC.class);

            this.setClienteInfo(
                    infoClienteRfc.obtieneInfoCliente(
                            this.getUsuarioLogueado().getNoCliente()));

        } catch(Exception e){
            logger.error("Error al desplegar informaci�n del cliente. " + e.getLocalizedMessage());
            this.getFacesContext().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,"Error",this.getLblMain().getString("errInfoCliente")));
        }
        return "";
    }

    public String getEmail1() {
        Usuario user = usrServ.findByUserName(RC+usuario.getNoCliente());
        if (user != null){
            this.setActivo(user.getEstatus());
            return user.getEmail();
        }else{
            return email1;
        }
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        Usuario user = usrServ.findByUserName(RS+usuario.getNoCliente());
        if (user != null){
            this.setActivo2(user.getEstatus());
            return user.getEmail();
        }else{
            return email2;
        }
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getEmail3() {
        Usuario user = usrServ.findByUserName(RE+usuario.getNoCliente());
        if (user != null){
            this.setActivo3(user.getEstatus());
            return user.getEmail();
        }else{
            return email3;
        }
    }

    public void setEmail3(String email3) {
        this.email3 = email3;
    }

    public String getActivo2() {
        Usuario user = usrServ.findByUserName(RS+usuario.getNoCliente());
        if (user != null){
            return user.getEstatus();
        }else{
            return activo2;
        }
    }

    public void setActivo2(String activo2) {
        this.activo2 = activo2;
    }

    public String getActivo3() {
        Usuario user = usrServ.findByUserName(RE+usuario.getNoCliente());
        if (user != null){
            return user.getEstatus();
        }else{
            return activo3;
        }
    }

    public void setActivo3(String activo3) {
        this.activo3 = activo3;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getActivo() {
        Usuario user = usrServ.findByUserName(RC+usuario.getNoCliente());
        if (user != null){
            return user.getEstatus();
        }else{
            return activo;
        }
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public ClienteInfo getClienteInfo() {
        return clienteInfo;
    }

    public void setClienteInfo(ClienteInfo clienteInfo) {
        this.clienteInfo = clienteInfo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
