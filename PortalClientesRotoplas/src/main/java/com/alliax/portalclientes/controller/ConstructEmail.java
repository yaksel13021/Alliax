/**
 * Componente para construir emails
 * @author saul.ibarra
 * @fecha 14-Mar-16
 */
package com.alliax.portalclientes.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.mail.BodyPart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import com.alliax.portalclientes.model.DetallePedidoCotizacion;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.alliax.portalclientes.domain.RolUsuario;
import com.alliax.portalclientes.domain.SociedadClienteCorreo;
import com.alliax.portalclientes.domain.Usuario;
import com.alliax.portalclientes.model.ClienteInfo;
import com.alliax.portalclientes.model.CustomUserDetails;
import com.alliax.portalclientes.model.SociedadClienteCorreoCargaMasiva;
import com.alliax.portalclientes.util.Helper;
//import com.sun.istack.internal.ByteArrayDataSource;

@Configuration
@PropertySource({
	"classpath:/META-INF/emails/Design_ClientesRtp.properties"
})
@Component("constructEmail")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE,proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ConstructEmail {
	
	private final static Logger logger = Logger.getLogger(ConstructEmail.class);
	
	private String urlPortal = "";//"https://my.rotoplas.com:8443/PortalClientes/";
	private final String FROM = "portalclientes@rotoplas.com";
	
	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;

	@Autowired
	private Environment formato;
	
	
	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	@Autowired
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	public JavaMailSender getMailSender() {
		return mailSender;
	}
	
	@Autowired
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}	
	
	
	/**
	 * Manda correo para Altas de clientes.
	 * @param usuario
	 */
	public void enviaCorreoAlta(final Usuario usuario, final ClienteInfo cliente){
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void prepare(MimeMessage mimeMessage) throws Exception {
										
				if(Helper.isProductionServer){
					urlPortal = formato.getProperty("portalPRD");
				}else{
					urlPortal = formato.getProperty("portalQAS");
				}
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
				
				message.setFrom(FROM);
				message.setSubject("Portal de Clientes Rotoplas");
				message.setSentDate(new Date());
				
				//Destinatario
				message.addTo(usuario.getEmail());
				//message.addTo("saul.ibarra@alliax.com");
				
				//Velodity Parameters
				Map model = new HashMap();
				model.put("usuario", usuario);
				model.put("cliente",cliente);
				model.put("urlPortal", urlPortal);
				model.put("pais", cliente.getPais());
				
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, 
							"com/alliax/portalclientes/velocity/templates/altaUsuario.vm", 
								"UTF-8",
									model);
				
				logger.info("Mensaje " + text);
				
				message.setText(text,true);
				
				//Logo
				String pathLogo = formato.getProperty("logo");
				if(cliente.getPais().equals("BR")){  pathLogo = formato.getProperty("logo_br"); } 
				logger.debug("logo " + pathLogo);
				URL urlLogo = ConstructEmail.class.getResource(pathLogo);
				DataSource fdsLogo = new URLDataSource(urlLogo);
				message.addInline("logo", fdsLogo);
			}
		};
		mailSender.send(preparator);
	}

	/**
	 * Manda correo para Altas de clientes.
	 * @param usuario
	 */
	public void enviaCorreoAlta(final Usuario usuario){
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void prepare(MimeMessage mimeMessage) throws Exception {
										
				if(Helper.isProductionServer){
					urlPortal = formato.getProperty("portalPRD");
				}else{
					urlPortal = formato.getProperty("portalQAS");
				}
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
				
				message.setFrom(FROM);
				message.setSubject("Portal de Clientes Rotoplas");
				message.setSentDate(new Date());
				
				//Destinatario
				message.addTo(usuario.getEmail());
				//message.addTo("saul.ibarra@alliax.com");
				String pais = "MX"; //default México
				pais = Helper.getPaisFromRoles( usuario.getRoles() ); 

				//Velodity Parameters
				Map model = new HashMap();
				model.put("usuario", usuario);
				model.put("urlPortal", urlPortal);
				model.put("pais", pais);
				
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, 
							"com/alliax/portalclientes/velocity/templates/altaUsuarioRoto.vm",
								"UTF-8",
									model);
				
				logger.info("Mensaje " + text);
				
				message.setText(text,true);
				
				//Logo
				String pathLogo = formato.getProperty("logo");
				if(pais.equals("BR")){  pathLogo = formato.getProperty("logo_br"); }				
				logger.debug("logo " + pathLogo);
				URL urlLogo = ConstructEmail.class.getResource(pathLogo);
				DataSource fdsLogo = new URLDataSource(urlLogo);
				message.addInline("logo", fdsLogo);
			}
		};
		mailSender.send(preparator);
	}
	
	/**
	 * Envia correo de resteo de password
	 * @param usuario
	 */
	public void enviaCorreoReseteo(final Usuario usuario, final ClienteInfo clienteInfo){
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void prepare(MimeMessage mimeMessage) throws Exception {
				
				if(Helper.isProductionServer){
					urlPortal = formato.getProperty("portalPRD");
				}else{
					urlPortal = formato.getProperty("portalQAS");
				}
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
				
				message.setFrom(FROM);
				message.setSubject("Reseteo de contraseña Usuario Portal de Clientes Rotoplas");
				message.setSentDate(new Date());
				
				//Destinatario
				message.addTo(usuario.getEmail());
				
				String pais = Helper.getPaisFromRoles( usuario.getRoles() );  //Default México
//				for(RolUsuario rol : usuario.getRoles() ){						
//					if(rol.getRol().contains("_AR")){ pais = "AR"; }
//					if(rol.getRol().contains("_PE")){ pais = "PE"; }
//					if(rol.getRol().contains("_CA")){ pais = "CA"; }
//				}
				if(pais.isEmpty()){ pais = clienteInfo.getPais(); }
				if(pais.isEmpty()){ pais = "MX"; }
				
				
				//Velodity Parameters
				Map model = new HashMap();
				model.put("usuario", usuario);
				model.put("urlPortal", urlPortal);
				model.put("pais", pais);
				
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, 
							"com/alliax/portalclientes/velocity/templates/reseteoPassword.vm", 
								"UTF-8",
									model);
				
				
				message.setText(text,true);
				
				//Logo
				String pathLogo = formato.getProperty("logo");
				if(pais.equals("BR")){  pathLogo = formato.getProperty("logo_br"); }				
				logger.debug("logo " + pathLogo);
				URL urlLogo = ConstructEmail.class.getResource(pathLogo);
				DataSource fdsLogo = new URLDataSource(urlLogo);
				message.addInline("logo", fdsLogo);
			}
		};
		mailSender.send(preparator);		
	}
	
	/**
	 * Manda correo para con Estado de cuenta anexo.
	 * @param usuario
	 */
	public void enviaEstadoCuenta(final byte[] pdfByteArray,final SociedadClienteCorreoCargaMasiva scc,final String pais, final String carteraVencida){
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void prepare(MimeMessage mimeMessage) throws Exception {
										
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
				
				message.setFrom(FROM);
				if(pais.equals("BR")){
					if(carteraVencida != null && carteraVencida.equals("1")){
						message.setSubject("Faturas vencidas Acqualimp ");						
					}else{
						message.setSubject("Faturas a vencer Acqualimp");	
					}					
				}else{
					if(carteraVencida != null && carteraVencida.equals("1")){
						message.setSubject("Estado de Cuenta Rotoplas S.A. de C.V.");						
					}else{
						message.setSubject("Facturas por vencer Rotoplas S.A. de C.V.");
					}
					
				}				
				message.setSentDate(new Date());
				
				//Destinatario								
				message.setTo( InternetAddress.parse(scc.getCorreo().replace(";",",")) );				
				//message.addTo(scc.getCorreo());

				//Velodity Parameters
				Map model = new HashMap();
				model.put("scc", scc);				
				model.put("pais", pais);
				model.put("carteraVencida", carteraVencida);
				
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, 
							"com/alliax/portalclientes/velocity/templates/envioEstadoCuentaAdjunto.vm", 
								"UTF-8",
									model);
				
				//logger.info("Mensaje " + text);
				
				message.setText(text,true);
				
				//Logo
				String pathLogo = formato.getProperty("logo");
				if(pais.equals("BR")){  pathLogo = formato.getProperty("logo_br"); }				
				logger.debug("logo " + pathLogo);
				URL urlLogo = ConstructEmail.class.getResource(pathLogo);
				DataSource fdsLogo = new URLDataSource(urlLogo);
				message.addInline("logo", fdsLogo);												
				
				String fechaCorte = new SimpleDateFormat("yyyyMMdd").format(new Date()); 
				//File pdfFile = new File("EstadoDeCuenta.pdf");
				//FileUtils.writeByteArrayToFile(pdfFile, pdfByteArray);
				//message.addAttachment(scc.getNoCliente()+"_"+fechaCorte+".pdf", pdfFile );
				
				//InputStream is = new ByteArrayInputStream(pdfByteArray);
				//InputStreamSource isc = new InputStreamResource(is);
				//message.addAttachment(scc.getNoCliente()+"_"+fechaCorte+".pdf", isc, "application/pdf");
				
				DataSource pdfDS = null;//new ByteArrayDataSource(pdfByteArray, "application/pdf");
				message.addAttachment(scc.getNoCliente()+"_"+fechaCorte+".pdf", pdfDS);
				
				
			}
		};
		mailSender.send(preparator);
	}
	
	/**
	 * Manda correo con un archivo txt anexo, informando a que clientes se les envió el estado de cuenta y a que dirección de correo.
	 * @param usuario
	 */
	public void enviaLogEstadoCuentaMasivo(final String logString,final CustomUserDetails usuarioLogueado){
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void prepare(MimeMessage mimeMessage) throws Exception {
										
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
				
				message.setFrom(FROM);
				message.setSubject("Portal de Clientes Rotoplas - Log Envío masivo");
				message.setSentDate(new Date());
				
				//Destinatario
				message.addTo(usuarioLogueado.getEmail());
				//message.addTo("saul.ibarra@alliax.com");
				String pais = "MX"; //default México
				/*****obtiene pais de Roles o correo automatico por sociedad*****/				
				Set<RolUsuario> setRolUsuario = new HashSet<>();
				if(usuarioLogueado != null && usuarioLogueado.getAuthorities() != null){
					for (GrantedAuthority ga : usuarioLogueado.getAuthorities()) {
						RolUsuario ru = new RolUsuario();
						 ru.setRol(ga.getAuthority());
						setRolUsuario.add(ru);
					}	
				}
		
				/****************************************************************/
				pais = Helper.getPaisFromRoles( setRolUsuario ); 
				
				//Velodity Parameters
				Map model = new HashMap();
//				model.put("scc", scc);
//				model.put("urlPortal", urlPortal);

				model.put("pais", Helper.getPaisFromRoles(setRolUsuario));
				
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, 
							"com/alliax/portalclientes/velocity/templates/envioLogEstadoCuenta.vm", 
								"UTF-8",
									model);
				
				//logger.info("Mensaje " + text);
				
				message.setText(text,true);
				
				//Logo
				String pathLogo = formato.getProperty("logo");
				if(pais.equals("BR")){  pathLogo = formato.getProperty("logo_br"); }				
				logger.debug("logo " + pathLogo);
				URL urlLogo = ConstructEmail.class.getResource(pathLogo);
				DataSource fdsLogo = new URLDataSource(urlLogo);
				message.addInline("logo", fdsLogo);												
				
				String fechaCorte = new SimpleDateFormat("yyyyMMdd").format(new Date()); 
				File txtFile = new File("txtFile.txt");
				FileUtils.writeByteArrayToFile(txtFile, logString.getBytes());
				message.addAttachment("LogEnvioMasivo_"+fechaCorte+".txt", txtFile );
				
			}
		};
		mailSender.send(preparator);
	}

	public void enviaCorreoCotizacion(final String  email, final ClienteInfo clienteInfo, final String noCotizacion, final List<DetallePedidoCotizacion> partidas, final String total,final String fechaEntrega){
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void prepare(MimeMessage mimeMessage) throws Exception {

				if(Helper.isProductionServer){
					urlPortal = formato.getProperty("portalPRD");
				}else{
					urlPortal = formato.getProperty("portalQAS");
				}
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);

				message.setFrom(FROM);
				message.setSubject("Cotización - "+noCotizacion);
				message.setSentDate(new Date());

				//Destinatario
				message.addTo(email);

				String pais = "";
				// Helper.getPaisFromRoles( usuario.getRoles() );  //Default México
//				for(RolUsuario rol : usuario.getRoles() ){
//					if(rol.getRol().contains("_AR")){ pais = "AR"; }
//					if(rol.getRol().contains("_PE")){ pais = "PE"; }
//					if(rol.getRol().contains("_CA")){ pais = "CA"; }
//				}
				if(pais.isEmpty()){ pais = clienteInfo.getPais(); }
				if(pais.isEmpty()){ pais = "MX"; }


				//Velodity Parameters
				Map model = new HashMap();
				model.put("usuario", null);
				model.put("urlPortal", urlPortal);
				model.put("pais", pais);
				model.put("partidas",partidas);
				model.put("total",total);
				model.put("fechaEntrega",fechaEntrega);

				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine,
						"com/alliax/portalclientes/velocity/templates/envioCotizacion.vm",
						"UTF-8",
						model);


				message.setText(text,true);

				//Logo
				String pathLogo = formato.getProperty("logo");
				if(pais.equals("BR")){  pathLogo = formato.getProperty("logo_br"); }
				logger.debug("logo " + pathLogo);
				URL urlLogo = ConstructEmail.class.getResource(pathLogo);
				DataSource fdsLogo = new URLDataSource(urlLogo);
				message.addInline("logo", fdsLogo);
			}
		};
		mailSender.send(preparator);
	}
}
