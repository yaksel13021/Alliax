package com.alliax.portalclientes.view;

import com.alliax.portalclientes.controller.ConstructEmail;
import com.alliax.portalclientes.controller.InfoClienteRFC;
import com.alliax.portalclientes.domain.RolUsuario;
import com.alliax.portalclientes.domain.Usuario;
import com.alliax.portalclientes.general.formato.Convertidor;
import com.alliax.portalclientes.general.formato.Fecha;
import com.alliax.portalclientes.general.formato.Generales;
import com.alliax.portalclientes.model.ClienteInfo;
import com.alliax.portalclientes.service.UsuarioService;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name="colaboradores")
@ViewScoped
public class AltaColaboradores_backing extends AbstractBacking {

    private final static Logger logger = Logger.getLogger(AltaColaboradores_backing.class);

    public String email;
    public String actividad;
    public String activo;
    private static String RC = "RC";
    private static String RS = "RS";
    private static String RE = "RE";
    private static String ROLE_PEDIDOS ="ROLE_PEDIDOS";
    private static String ROLE_SEGUIMIENTO_PEDIDOS ="ROLE_SEGUIMIENTO_PEDIDOS";
    private static String ROLE_ESTADO_CUENTA = "ROLE_ESTADO_CUENTA";
    private static String TRUE = "true";
    private ClienteInfo clienteInfo;
    private int status;


    /**
     * Metodo para dar de alta usuario colaborador
     * @return
     * @throws Exception
     */
    public String altaColaborador() throws Exception{
        cargaInfoCliente();
        Map<String, String> map = new HashMap<String, String>();
        map.put(RC, ROLE_PEDIDOS);
        map.put(RS, ROLE_SEGUIMIENTO_PEDIDOS);
        map.put(RE, ROLE_ESTADO_CUENTA);
        try {
            UsuarioService usrServ = this.getSpringContext().getBean("usuarioService",UsuarioService.class);
            logger.info("Usuario: " + this.getUsuarioLogueado().getUsername());

            Usuario usuario = usrServ.findByUserName(this.getUsuarioLogueado().getUsername());
            Usuario colaborador = usrServ.findByUserName(this.getActividad()+usuario.getNoCliente());

            if(colaborador == null){
                colaborador = new Usuario();
                colaborador.setUsuario(this.getActividad()+usuario.getNoCliente());
                colaborador.setPasswordStr(usuario.getPasswordStr());
                colaborador.setPassword(usuario.getPassword());
                colaborador.setNoCliente(this.getActividad()+usuario.getNoCliente());
                colaborador.setEmail(this.getEmail());
                colaborador.setFechaEntrada(usuario.getFechaEntrada());
                colaborador.setIntentosFallidos(0);
                colaborador.setFechaAlta(Fecha.getDateTimeActual());
                colaborador.setEstatus(this.getActivo().equals(TRUE) ? "A" : "I");
                colaborador.setPais(usuario.getPais());
                colaborador.setParent(usuario.getNoCliente());
                
               
	            RolUsuario rolUsr = new RolUsuario();
	            rolUsr.setRol(map.get(this.getActividad()));
	            colaborador.setRol(rolUsr);
                status = 1;
            } else{
            	colaborador.setEmail(this.getEmail());
                colaborador.setEstatus(this.getActivo().equals(TRUE) ? "A" : "I");
                status = 2;
            }
            
            colaborador = usrServ.save(colaborador);
            logger.info("Mensaje save");

            ConstructEmail mail = this.getSpringContext().getBean("constructEmail",ConstructEmail.class);
            mail.enviaCorreoAlta(usuario,this.getClienteInfo());

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getActivo() {
        return activo;
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
