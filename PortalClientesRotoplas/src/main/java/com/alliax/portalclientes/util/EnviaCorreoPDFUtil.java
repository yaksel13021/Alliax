package com.alliax.portalclientes.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alliax.portalclientes.controller.ConstructEmail;
import com.alliax.portalclientes.controller.EstadoCuentaDetRFC;
import com.alliax.portalclientes.controller.EstadoCuentaPdf;
import com.alliax.portalclientes.domain.SociedadClienteCorreo;
import com.alliax.portalclientes.model.CustomUserDetails;
import com.alliax.portalclientes.model.EstadoCuenta;
import com.alliax.portalclientes.model.EstadoCuentaDet;
import com.alliax.portalclientes.model.SociedadClienteCorreoCargaMasiva;
import com.alliax.portalclientes.service.SociedadClienteCorreoService;

public class EnviaCorreoPDFUtil {
	
	private final static Logger logger = Logger.getLogger(EnviaCorreoPDFUtil.class);
	
	public static void generaEstadosDeCuenta(String sociedad, String noCliente, List<SociedadClienteCorreoCargaMasiva> list, CustomUserDetails usuarioLogueado, String carteraVencida){
		 logger.info("method generaEstadosDeCuenta");
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		
		SociedadClienteCorreoService sociedadClienteCorreoService = ctx.getBean("sociedadClienteCorreoService",SociedadClienteCorreoService.class);
		EstadoCuentaDetRFC edoCtaRFC = ctx.getBean("estadoCuentaDetRFC",EstadoCuentaDetRFC.class);
		EstadoCuentaPdf comppdf = ctx.getBean("estadoCuentaPdf",EstadoCuentaPdf.class);
		ConstructEmail mail = ctx.getBean("constructEmail",ConstructEmail.class);
		
		StringBuilder sbLog = new StringBuilder();
		
		List<SociedadClienteCorreoCargaMasiva> sccmList = new ArrayList();
		if(list == null){
			logger.info("list == null");
			//Obtiene la lista de clientes a los que se debe enviar el estado de cuenta			
			if((sociedad != null && !sociedad.isEmpty()) && (noCliente != null && !noCliente.isEmpty()) ){
				 logger.info("campo sociedad y campo noCliente: " + sociedad + " - " + noCliente);					
				sccmList.add(convertSccToSccm(sociedadClienteCorreoService.findBySociedadAndNoCliente(sociedad, noCliente)));
			}else if(sociedad != null && !sociedad.isEmpty() ){
				 logger.info("campo sociedad: " + sociedad);
				sccmList.addAll(convertSccListToSccmList(sociedadClienteCorreoService.findBySociedad(sociedad)));
			}else if(noCliente != null && !noCliente.isEmpty() ){
				 logger.info("campo cliente: " + noCliente);
				sccmList.addAll( convertSccListToSccmList(sociedadClienteCorreoService.findByNoCliente(noCliente)) );			
			}else{						
				//logger.info("findAll SociedadClienteVendedor");
				//this.setList(sociedadClienteCorreoService.findAll());
			}	
		}else{
			logger.info("list != null");
			sccmList = list;
		}

		
		
		//Itera cada uno de los clientes y genera su correspondiente PDF
		for (SociedadClienteCorreoCargaMasiva sccm : sccmList) {
			if(sccm.isChecked() == false){continue;}
			String fechaCorte = new SimpleDateFormat("yyyyMMdd").format(new Date());
			try{												
				EstadoCuenta edoCta = edoCtaRFC.detalleEstadoCuenta(sccm.getNoCliente(), sccm.getSociedad(), fechaCorte, "ES");				

				String locale = "";
				if(edoCta.getPais() != null && edoCta.getPais().equals("BR")){
					locale = "pt";
				}else{
					locale = "es";
				}
				logger.info("locale: " + locale);
				String baseName = "com.alliax.portalclientes.locale.Labels";
				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				ResourceBundle rb = ResourceBundle.getBundle(baseName, new Locale(locale), loader);	
				
				//Envia por correo este archivo binario a la dirección de correo de scc.getCorreo();			
//			      try {
//			          FileOutputStream fileOut =
//			          new FileOutputStream("D:\\ComercioExterior\\TomcatCluster\\Tomcat7.0.42_RtpN1\\webapps\\EstadoCuenta.ser");
//			          ObjectOutputStream out = new ObjectOutputStream(fileOut);
//			          out.writeObject(edoCta);
//			          out.close();
//			          fileOut.close();
//			          System.out.printf("Serialized data is saved in D:\\ComercioExterior\\TomcatCluster\\Tomcat7.0.42_RtpN1\\webapps\\EstadoCuenta.ser");
//			       } catch (IOException i) {
//			    	  logger.error("Error: ", i);
//			          i.printStackTrace();
//			       }
//				 logger.info(":::::::::::::::::Generating pdf "+edoCta.getNumCliente()+"::::::::::::::::::::");
//				 logger.info("Rb: " + rb);
//				 logger.info("rb.getString(estadoCuenta) - " + rb.getString("estadoCuenta"));
//				 logger.info("edoCta - " + edoCta);
//				 logger.info("edoCta.getDtalle - " + edoCta.getDetalle());
//				 logger.info("edoCta.getMail - " + edoCta.geteMail());
//				 logger.info("edoCta.getMoneda - " + edoCta.getMoneda());
//				 logger.info("edoCta.getNombre - " + edoCta.getNombre());
//				 logger.info("edoCta.getNombre2 - " + edoCta.getNombre2());
//				 logger.info("edoCta.getNombre3 - " + edoCta.getNombre3());
//				 logger.info("edoCta.getNombre4 - " + edoCta.getNombre4());
//				 logger.info("edoCta.getNumCliente - " + edoCta.getNumCliente());
//				 logger.info("edoCta.getPais - " + edoCta.getPais());
//				 logger.info("edoCta.getRfc - " + edoCta.getRfc());
//				 logger.info("edoCta.getSignoPesos - " + edoCta.getSignoPesos());
//				 logger.info("edoCta.getTel - " + edoCta.getTel());
//				 logger.info("edoCta.getCreditoDisponible - " + edoCta.getCreditoDisponible());
//				 logger.info("edoCta.getFechaCorte - " + edoCta.getFechaCorte());
//				 logger.info("edoCta.getLimiteCredito - " + edoCta.getLimiteCredito());
//				 logger.info("edoCta.getSaldoVencer - " + edoCta.getSaldoVencer());
//				 logger.info("edoCta.getSaldoVencido - " + edoCta.getSaldoVencido());
//				 logger.info("edoCta.getTotal - " + edoCta.getTotal());
//				 if(edoCta.getDetalle() != null){
//					 for(EstadoCuentaDet dt : edoCta.getDetalle()){
//						 logger.info("edoCta.getDetalle.getDiasMora - " + dt.getDiasMora());
//						 logger.info("edoCta.getDetalle.getEntrega - " + dt.getEntrega());
//						 logger.info("edoCta.getDetalle.getEstatus - " + dt.getEstatus());
//						 logger.info("edoCta.getDetalle.getFacturaRelacionada - " + dt.getFacturaRelacionada());
//						 logger.info("edoCta.getDetalle.getFechaFactura - " + dt.getFechaFactura());
//						 logger.info("edoCta.getDetalle.getFechaFacturaSort - " + dt.getFechaFacturaSort());
//						 logger.info("edoCta.getDetalle.getFechaVenc - " + dt.getFechaVenc());
//						 logger.info("edoCta.getDetalle.getFechaVencSort - " + dt.getFechaVencSort());
//						 logger.info("edoCta.getDetalle.getNoFactFiscal - " + dt.getNoFactFiscal());
//						 logger.info("edoCta.getDetalle.getNoFactura - " + dt.getNoFactura());
//						 logger.info("edoCta.getDetalle.getNoPedido - " + dt.getNoPedido());
//						 logger.info("edoCta.getDetalle.getNotaEntrega - " + dt.getNotaEntrega());
//						 logger.info("edoCta.getDetalle.getOrdenCompra - " + dt.getOrdenCompra());
//						 logger.info("edoCta.getDetalle.getTipoDocumento - " + dt.getTipoDocumento());
//						 logger.info("edoCta.getDetalle.getUUIDRelacionado - " + dt.getUUIDRelacionado());
//						 logger.info("edoCta.getDetalle.getImporte - " + dt.getImporte());							 						
//					 }
//				 }
				 
				ByteArrayOutputStream baos = comppdf.GenerarPdf(edoCta, rb).getPdfOutput();
//				FileUtils.writeByteArrayToFile(new File("D:\\ComercioExterior\\TomcatCluster\\Tomcat7.0.42_RtpN1\\webapps\\EstadoCuenta_"+edoCta.getNumCliente()+".pdf"), baos.toByteArray());
				
				mail.enviaEstadoCuenta(baos.toByteArray(), sccm, edoCta.getPais(), carteraVencida);
				sccm.setMensaje("ok");
				
				
			}catch(Exception e){
				//Manejar error 
				logger.error("Reporte de error", e);
				sccm.setMensaje(e.getMessage());
			}
			 
			 sbLog.append("Sociedad: " + sccm.getSociedad() + 
					      " | NoCliente: " + sccm.getNoCliente() + 
					      " | FechaCorte: " + fechaCorte +
					      " | NombreCliente: " + sccm.getNombreCliente() + 	
	                      " | CorreoDestino: " + sccm.getCorreo() + 
	                      " | CarteraVencida: " + carteraVencida +
	                      " | Enviado: " + sccm.getMensaje() +
	                      "\n");			 

		}
		//Envía log por correo
		mail.enviaLogEstadoCuentaMasivo(sbLog.toString(), usuarioLogueado);
	}
	
	
	
	
	
	
	private static List<SociedadClienteCorreoCargaMasiva> convertSccListToSccmList(List<SociedadClienteCorreo> sccList){
		List<SociedadClienteCorreoCargaMasiva> sccmList= new ArrayList();
		
		if(sccList != null && !sccList.isEmpty()){
			for (SociedadClienteCorreo scc : sccList) {
				sccmList.add(new SociedadClienteCorreoCargaMasiva(scc.getSociedad(), scc.getNoCliente(), scc.getCorreo(), scc.getNombreCliente(), "", true));
			}
		}
		
		return sccmList;
	}
	private static SociedadClienteCorreoCargaMasiva convertSccToSccm(SociedadClienteCorreo scc){
		SociedadClienteCorreoCargaMasiva sccm = new SociedadClienteCorreoCargaMasiva(scc.getSociedad(), scc.getNoCliente(), scc.getCorreo(), scc.getNombreCliente(), "", true);						
		return sccm;
	}
	
}
