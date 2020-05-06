package com.alliax.portalclientes.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;


import com.alliax.portalclientes.controller.InfoClienteConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.alliax.portalclientes.controller.InfoClienteRFC;
import com.alliax.portalclientes.domain.RolUsuario;
import com.alliax.portalclientes.domain.Usuario;
import com.alliax.portalclientes.general.formato.Fecha;
import com.alliax.portalclientes.model.ClienteInfo;
import com.alliax.portalclientes.model.CustomUserDetails;
import com.alliax.portalclientes.service.jpa.UsuarioServiceImpl;
import com.alliax.portalclientes.util.Helper;

public class SQLUserDetailsService implements UserDetailsService {

	private final static Logger logger = Logger.getLogger(SQLUserDetailsService.class);
	
//	@Autowired
//	public UsuarioService usuarioService;
	@Autowired
	AutowireCapableBeanFactory factory;		
	@Autowired
	private InfoClienteRFC infoClienteRfc;
	
	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException, CredentialsExpiredException {
		logger.info("Validando usr: " + arg0);
		UsuarioService usuarioService = factory.createBean(UsuarioServiceImpl.class);
		Usuario usr = usuarioService.findByUserName(arg0);
		
		if(usr == null){
			logger.error("No existe usuario");
			throw new UsernameNotFoundException("Usuario no existe");
		}
		
		logger.info("NoCliente " + usr.getNoCliente());
		logger.info("Email " + usr.getEmail());
		logger.info("Total Roles " + usr.getRoles().size());				
		logger.info("Estatus " + usr.getEstatus());
		
		if(usr.getEstatus().equalsIgnoreCase("F")){
			logger.error("Usuario Bloqueado por Intentos Fallidos");
			throw new LockedException("Usuario Bloqueado por Intentos Fallidos");
		} else if(usr.getEstatus().equalsIgnoreCase("B")){
			logger.error("Usuario Bloqueado por Admin");
			throw new DisabledException("Usuario deshabilitado");		
		}
							

		//Consulta SAP para ver
		ClienteInfo clienteInfo = null;
		try{
			clienteInfo = infoClienteRfc.obtieneInfoCliente(arg0);
		}catch(Exception e){
			logger.error("Error getResultTables " + e.toString(),e);
			InfoClienteConfig config = new InfoClienteConfig();
			clienteInfo = config.informacionCliente();
		}

		if(clienteInfo != null && clienteInfo.getStatusSAP() != null && clienteInfo.getStatusSAP().equals("C")){
			logger.error("Usuario Bloqueado en SAP");
			throw new DisabledException("Usuario deshabilitado");
		}
		
				
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(RolUsuario rol : usr.getRoles()){
			logger.info("Rol " + (String)rol.getRol());
			authorities.add(new SimpleGrantedAuthority((String)rol.getRol()));
		}
		String pais = Helper.getPaisFromRoles( usr.getRoles() ); //Toma el país del Rol
		
		//Filter
		//Si es primer ingreso solicitara cambio de password.
		if(usr.getEstatus().equalsIgnoreCase("I")){
			logger.error("Credencial expirada");
			authorities.add(new SimpleGrantedAuthority("ROLE_PWDCHANGE_MEMBER"));
		}
		
		//Seteando valores .. aqui valida password
		CustomUserDetails usrDetails = new CustomUserDetails(
											(String)usr.getUsuario(), 
												(String)usr.getPassword(), 
													authorities, 
														(String)usr.getNoCliente(),
															(String)clienteInfo.getEmail(),
															   clienteInfo.getPais() == null || clienteInfo.getPais().equals("") ? pais : clienteInfo.getPais());
		
		
							
		logger.info("After user details.");
		
		
		return usrDetails;
	}
	
	


}
