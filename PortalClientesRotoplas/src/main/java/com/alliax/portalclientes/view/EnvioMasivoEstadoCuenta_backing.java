/**
 * Backing bean para Envío masivo de Estado de Cuenta
 */
package com.alliax.portalclientes.view;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.alliax.portalclientes.controller.InfoClienteRFC;
import com.alliax.portalclientes.domain.RolUsuario;
import com.alliax.portalclientes.domain.SociedadClienteCorreo;
import com.alliax.portalclientes.domain.Usuario;
import com.alliax.portalclientes.general.formato.Convertidor;
import com.alliax.portalclientes.model.ClienteInfo;
import com.alliax.portalclientes.model.ClienteVendedorCargaMasiva;
import com.alliax.portalclientes.model.SociedadClienteCorreoCargaMasiva;
import com.alliax.portalclientes.service.SociedadClienteCorreoService;
import com.alliax.portalclientes.service.UsuarioService;
import com.alliax.portalclientes.util.EnviaCorreoPDFUtil;

@ManagedBean(name="envioMasivoEdoCta")
@ViewScoped
public class EnvioMasivoEstadoCuenta_backing extends AbstractBackingGen {
			
	private final static Logger logger = Logger.getLogger(EnvioMasivoEstadoCuenta_backing.class);
			
	private String sociedad;
	private String noCliente;
	private List<String> noClienteList;
	private String mensaje;
	private List<SociedadClienteCorreoCargaMasiva> list;
	private boolean checked = true;
	private boolean procesado = false;
	private String carteraVencida;
		

	@PostConstruct
	public void init(){
		this.carteraVencida = "1";
	}
	
	public void listaSociedadClienteCorreo(){
		logger.info("method listaSociedadClienteCorreo");
		this.setList(null);		
		this.setMensaje(null);
		this.noClienteList = leeArchivo(this.noCliente);
		List<SociedadClienteCorreoCargaMasiva> list = new ArrayList();
		for (String noCliente : this.noClienteList) {
			/***********excluye noCliente repetido********/	
			boolean repetido = false;
			for (SociedadClienteCorreoCargaMasiva sccm : list) {
				if(sccm != null ){
					if(sccm.getNoCliente().equals(noCliente)){
						repetido = true;
						break;
					}						
				}
			}
			if(repetido){continue;}
			/*********************************************/
			List<SociedadClienteCorreoCargaMasiva> responseList = new ArrayList();
			responseList = buscaSociedadClienteCorreo(this.sociedad, noCliente);
			
			if(responseList != null && !responseList.isEmpty()){
				if(responseList.get(0) != null){				   		
					list.addAll(responseList);
				}

			}
		}
		this.setList(list);
		this.procesado = false;
	}
	
	
	/**
	 * Metodo para descargar resultados en pdf 
	 */
	public void procesarEnvioEstadoDeCuenta(){
		logger.info("metodo procesarEnvioEstadoDeCuenta...");
				
		EnviaCorreoPDFUtil.generaEstadosDeCuenta(null, null, list, this.getUsuarioLogueado(), this.carteraVencida);
		this.procesado = true;
		this.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,this.getLblMain().getString("procesoEnvioMasivoTerminado"),null));																									
	}
	
	public void seleccionarTodosLosRegistros(){
		this.checked = this.checked ? false : true;
		for (SociedadClienteCorreoCargaMasiva sccm : list) {
			sccm.setChecked(this.checked);
		}
	}

	
	
	
	
	
	
	
	private List<SociedadClienteCorreoCargaMasiva> buscaSociedadClienteCorreo(String sociedad, String noCliente){

		
		if(sociedad == null || sociedad.trim().equals("")){
			//Sociedad requerida
			//this.setMensaje(this.getLblMain().getString("errSociedadRequerida"));
			this.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.getLblMain().getString("errSociedadRequerida"),null));
			return null;
		}
		logger.info("Sociedad: " + sociedad + " - Cliente: " + noCliente);
		
		SociedadClienteCorreoService sociedadClienteCorreoService = this.getSpringContext().getBean("sociedadClienteCorreoService",SociedadClienteCorreoService.class);		
		 logger.info("metodo buscaSociedadClienteCorreo: inician los if");
		if((sociedad != null && !sociedad.isEmpty()) && (noCliente != null && !noCliente.isEmpty()) ){
			logger.info("campo sociedad y campo noCliente: " + sociedad + " - " + noCliente);				
			List<SociedadClienteCorreoCargaMasiva>  l = new ArrayList();
			 l.add(convertSccToSccm(sociedadClienteCorreoService.findBySociedadAndNoCliente(sociedad, noCliente)));
			return l;
		}else if(sociedad != null && !sociedad.isEmpty() ){
			logger.info("campo sociedad: " + sociedad);
			return convertSccListToSccmList(sociedadClienteCorreoService.findBySociedad(sociedad));
		}else if(noCliente != null && !noCliente.isEmpty() ){
			logger.info("campo cliente: " + noCliente);			
			return convertSccListToSccmList(sociedadClienteCorreoService.findByNoCliente(noCliente));
		}else{
			logger.info("findAll SociedadClienteVendedor");
			return null;
		}	
	}
	
	private List<SociedadClienteCorreoCargaMasiva> convertSccListToSccmList(List<SociedadClienteCorreo> sccList){
		List<SociedadClienteCorreoCargaMasiva> sccmList= new ArrayList();
		
		if(sccList != null && !sccList.isEmpty()){
			for (SociedadClienteCorreo scc : sccList) {
				sccmList.add(new SociedadClienteCorreoCargaMasiva(scc.getSociedad(), scc.getNoCliente(), scc.getCorreo(), scc.getNombreCliente(), null, true));
			}
		}
		
		return sccmList;
	}
	private SociedadClienteCorreoCargaMasiva convertSccToSccm(SociedadClienteCorreo scc){
		SociedadClienteCorreoCargaMasiva sccm = null;
		if(scc == null){
			logger.info("convertSccToSccm scc == null");
		}else{
			logger.info("Sociedad " + scc.getSociedad() + " - NoCliente " + scc.getCorreo() + " - NombreCliente " + scc.getNombreCliente() + " - Cartera Vendcida: " + this.carteraVencida);
			sccm = new SociedadClienteCorreoCargaMasiva(scc.getSociedad(), scc.getNoCliente(), scc.getCorreo(), scc.getNombreCliente(), null, true);
		}								
		return sccm;
	}
	
	/**
	 * Metodo para leer archivo y precargar datos
	 * @return
	 */
	private List<String> leeArchivo(String noCliente){
		    List<String> noClienteList = new ArrayList<String>();
			String[] row = noCliente.split("\\r\\n");
			int j = 0;
			while(j < row.length){
				logger.info(row[j]);
				j++;
			}			
			int i = 0;
			while(i < row.length){
				try{
					noClienteList.add( row[i].trim() );										
				} catch(Exception e){
					logger.error("Error al procesar informacion del cliente " + e.getLocalizedMessage(),e);
				} finally {
					i++;
				}
			}												
		return noClienteList;
	}
	
	/****Getters y Setters*****/
	public String getSociedad() {
		return sociedad;
	}

	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}

	public String getNoCliente() {
		return noCliente;
	}

	public void setNoCliente(String noCliente) {
		this.noCliente = noCliente;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<SociedadClienteCorreoCargaMasiva> getList() {
		return list;
	}

	public void setList(List<SociedadClienteCorreoCargaMasiva> list) {
		this.list = list;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isProcesado() {
		return procesado;
	}

	public void setProcesado(boolean procesado) {
		this.procesado = procesado;
	}

	public String getCarteraVencida() {
		return this.carteraVencida;
	}

	public void setcarteraVencida(String carteraVencida) {
		this.carteraVencida = carteraVencida;
	}

	public List<String> getNoClienteList() {
		return noClienteList;
	}

	public void setNoClienteList(List<String> noClienteList) {
		this.noClienteList = noClienteList;
	}


		

	
}
