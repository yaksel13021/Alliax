/**
 * Backing bean para vista de consulta de existencias
 * @author saul.ibarra
 * @fecha 11-Febrero-16
 */
package com.alliax.portalclientes.view;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.validation.ValidationException;

import com.alliax.portalclientes.controller.BusquedaMaterialConfig;
import com.alliax.portalclientes.controller.StockMaterialConfig;
import org.apache.log4j.Logger;

import com.alliax.portalclientes.controller.BusquedaMaterialRFC;
import com.alliax.portalclientes.controller.StockMaterialRFC;
import com.alliax.portalclientes.domain.Localidad;
import com.alliax.portalclientes.domain.Planta;
import com.alliax.portalclientes.model.Material;
import com.alliax.portalclientes.model.StockMaterial;
import com.alliax.portalclientes.service.EstadoService;
import com.alliax.portalclientes.service.LocalidadService;
import com.alliax.portalclientes.service.PaisService;
import com.alliax.portalclientes.service.PlantaService;
import com.alliax.portalclientes.util.Helper;

@ManagedBean(name="busqueda")
@ViewScoped
public class ConsultaExistencias_backing extends AbstractBacking {
	
	private final static Logger logger = Logger.getLogger(ConsultaExistencias_backing.class);
	
	private int tipoBusqueda;
	private int idPais;
	private int idEstado;
	private int idLocalidad;
	private String idPlanta;
	private String plantaDescripcion;
	private String noMaterial;
	
//	@ManagedProperty(value="#{tipoBusquedaExMap}")
	private Map<Integer,String> catalogoBusqueda;
	
	private Map<Integer,String> catalogoPais;
	private Map<Integer,String> catalogoEstado;
	private Map<Integer,String> catalogoLocalidad;
	private Map<String,String> catalogoPlanta;
	
	private List<StockMaterial> resultados;
	private List<Material> resultadosMatBusqueda;
	private int tipoMensa;
	
	
	public ConsultaExistencias_backing(){
		//Carga catalogo de Paises
		PaisService ps = this.getSpringContext().getBean("paisService",PaisService.class);		
		this.setCatalogoPais(ps.findAllSet());				
	}

	public int getTipoMensa() {
		return tipoMensa;
	}

	public void setTipoMensa(int tipoMensa) {
		this.tipoMensa = tipoMensa;
	}

	public int getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(int tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public int getIdPais() {
		return idPais;
	}

	public void setIdPais(int idPais) {
		this.idPais = idPais;
	}

	public int getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}

	public int getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(int idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public String getIdPlanta() {
		return idPlanta;
	}
	
	public int getIdPlantaInt(){
		return Integer.valueOf(idPlanta);
	}

	public void setIdPlanta(String idPlanta) {
		this.idPlanta = idPlanta;
	}	
	
	public String getPlantaDescripcion() {
		return plantaDescripcion;
	}


	public void setPlantaDescripcion(String plantaDescripcion) {
		this.plantaDescripcion = plantaDescripcion;
	}


	public Map<Integer, String> getCatalogoBusqueda() {
		setCatalogoBusqueda( Helper.getTipoBusquedaExMap(this.getLblMain()) );
		return catalogoBusqueda;
	}

	public Set<Entry<Integer,String>> getCatalogoBusquedaSet(){
		if(catalogoBusqueda == null)
			setCatalogoBusqueda( Helper.getTipoBusquedaExMap(this.getLblMain()) );
		return catalogoBusqueda.entrySet();
	}
	
	public void setCatalogoBusqueda(Map<Integer, String> catalogoBusqueda) {
		this.catalogoBusqueda = catalogoBusqueda;
	}


	public Map<Integer, String> getCatalogoPais() {
		return catalogoPais;
	}
	
	public Set<Entry<Integer,String>> getCatalogoPaisSet(){
		return catalogoPais.entrySet();
	}

	private void setCatalogoPais(Map<Integer, String> catalogoPais) {
		this.catalogoPais = catalogoPais;
	}


	public Map<Integer, String> getCatalogoEstado() {
		return catalogoEstado;
	}

	public Set<Entry<Integer,String>> getCatalogoEstadoSet(){
		try{ 
			return this.catalogoEstado.entrySet();
		} catch(NullPointerException e){
			return null;
		}
	}

	public void setCatalogoEstado(Map<Integer, String> catalogoEstado) {
		this.catalogoEstado = catalogoEstado;
	}


	public Map<Integer, String> getCatalogoLocalidad() {
		return this.catalogoLocalidad;
		/*LocalidadService localidadService = this.getSpringContext().getBean("localidadService",LocalidadService.class);
		
		try {
			if(this.getIdEstado() != 0){
				 this.setCatalogoLocalidad(localidadService.findAllByEstadoSet(this.getIdEstado()));
				 return this.catalogoLocalidad;
			} else
				return null;
		} catch(NullPointerException npe){
			return null;
		}*/
	}
	
	public Set<Entry<Integer,String>> getCatalogoLocalidadSet(){
		try{
			return this.getCatalogoLocalidad().entrySet();
		} catch(NullPointerException npe){
			return null;
		}
	}

	public void setCatalogoLocalidad(Map<Integer, String> catalogoLocalidad) {
		this.catalogoLocalidad = catalogoLocalidad;
	}


	public Map<String, String> getCatalogoPlanta() {
		return this.catalogoPlanta;
		/*try{
			PlantaService plantaService = this.getSpringContext().getBean("plantaService",PlantaService.class);
			if(this.getIdLocalidad() != 0){
				this.setCatalogoPlanta(plantaService.findByLocalidad(this.getIdLocalidad()));
				return this.catalogoPlanta;
			} else
				return null;
		} catch(NullPointerException e){
			return null;
		}*/
	}
	
	public Set<Entry<String,String>> getCatalogoPlantaSet(){
		try{
			return this.getCatalogoPlanta().entrySet();
		} catch(NullPointerException e){
			return null;
		}
	}

	public void setCatalogoPlanta(Map<String, String> catalogoPlanta) {
		this.catalogoPlanta = catalogoPlanta;
	}


	public String getNoMaterial() {
		return noMaterial;
	}


	public void setNoMaterial(String noMaterial) {
		this.noMaterial = noMaterial;
	}
	

	public List<StockMaterial> getResultados() {
		return resultados;
	}

	public void setResultados(List<StockMaterial> resultados) {
		this.resultados = resultados;
	}

	public List<Material> getResultadosMatBusqueda() {
		return resultadosMatBusqueda;
	}


	public void setResultadosMatBusqueda(List<Material> resultadosMatBusqueda) {
		this.resultadosMatBusqueda = resultadosMatBusqueda;
	}

	/**
	 * Valida combo localidad 
	 * @param context
	 * @param component
	 * @param value
	 */
	public void validaLocalidad(FacesContext context, UIComponent component, Object value){
		try{
			String localidad = (String)value;
			
			logger.info("validaLocalidad " + value);
			
			if(localidad.trim().equals("0"))
				throw new Exception("Localidad Invalida");
						
		} catch(Exception e){
			logger.error("validaLocalidad "+ e.getLocalizedMessage());
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"",
						this.getLblMain().getString("errSelectLocalidad")));
		}
	}
	
	/**
	 * Valida combo estado
	 * @param context
	 * @param component
	 * @param value
	 */
	public void validaEstado(FacesContext context, UIComponent component, Object value){
		try{
			String estado = (String)value;
			
			logger.info("validaEstado " + value);
			
			if(estado.trim().equals("0"))
				throw new Exception("Estado invalido");
		} catch(Exception e){
			logger.error("validaEstado "+ e.getLocalizedMessage());
			throw new ValidatorException(
				new FacesMessage(FacesMessage.SEVERITY_ERROR,"",
					this.getLblMain().getString("errSelectEstado")));
		}
	}
	

	/**
	 * Extrae el Stock para un material dado una planta
	 * @return
	 */
	public String busquedaExistencia(){
		try {
			String descripcion = null;
			String noMaterial = null;
			
			logger.info("No. Material " + this.getNoMaterial());
			
			//Borra resultados de materiales y existencias
			this.setResultados(null);
			this.setResultadosMatBusqueda(null);
			
			//Busqueda por localidad .. Estado y Localidad obligatorios
			if(this.getTipoBusqueda() == 1){
				if(this.getIdEstado() == 0)
					throw new ValidationException(this.getLblMain().getString("errSelectEstado"));
				else if(this.getIdLocalidad() == 0)
					throw new ValidationException(this.getLblMain().getString("errSelectLocalidad"));
			}
			
			if(this.getNoMaterial().trim().matches("[0-9]+")){
				noMaterial = this.getNoMaterial();
			}else if(this.getNoMaterial().trim().length() >= 5)
				descripcion = this.getNoMaterial();
			
			if(noMaterial != null){
				logger.info("Buscando Stock de no de material.");
				StockMaterialRFC stock = this.getSpringContext().getBean("stockMaterialImpl",StockMaterialRFC.class);
				this.setResultados(
							stock.obtieneStock(this.getNoMaterial(),
													this.getIdPlanta(),
														this.getUsuarioLogueado().getLanguage()));
			} else if(descripcion != null) {
				logger.info("Buscando x descripcion materiales");
				BusquedaMaterialRFC busquedaMat = this.getSpringContext().getBean("busquedaMaterial", BusquedaMaterialRFC.class);
				this.setResultadosMatBusqueda(
							busquedaMat.buscaMaterial(noMaterial,
														descripcion,
															this.getIdPlanta(),
																this.getUsuarioLogueado().getLanguage()));
			} else {
				this.getFacesContext().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,"Error",this.getLblMain().getString("errDescripcionMat")));	
			}	
		} catch(ValidationException ve){
			this.getFacesContext().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"",
						ve.getLocalizedMessage()));
		} catch(Exception e){
			logger.error("Error al obtener listado de existencias " + e.getLocalizedMessage());
			this.getFacesContext().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,"Error","Error al desplegar listado de existencias"));			
		}
		return "";
	}
	
	/**
	 * Busca el material a partir de la tabla de Busqueda de Materiales.
	 * @return
	 */
	public String busquedaExistenciaMaterial(){
		try{
			Material mat = (Material)this.getFlash().get("materialSel");
			
			this.setNoMaterial(mat.getNoMaterial());
			this.setResultadosMatBusqueda(null);
			this.setResultados(null);
			tipoMensa = 1;
			return this.busquedaExistencia();
			
		} catch(Exception e){
			logger.error("Error al obtener listado de existencias de Material " + e.getLocalizedMessage());
			this.getFacesContext().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,"Error","Error al desplegar listado de existencias."));			
		}
		return "";
	}
	
	
	/**
	 * Extrae Listado de todas las Plantas
	 * @param ae
	 */
	public void onTipoBusquedaChange(AjaxBehaviorEvent ae){
		try{			
			PlantaService plantaService = this.getSpringContext().getBean("plantaService",PlantaService.class);
			if(this.getTipoBusqueda() == 1){ //Localidad				
				
				EstadoService es = this.getSpringContext().getBean("estadoService",EstadoService.class);								
				this.setCatalogoEstado(es.findAllByPaisSet(1)); //MX Default
				
				//Localidad nullk
				this.setCatalogoLocalidad(null);
				
				//Plantas null
				this.setCatalogoPlanta(null);
				
				//Resetea valores
				this.setIdEstado(0);
				this.setIdLocalidad(0);
				this.setIdPlanta(null);
				
			} else { //Planta
				this.setCatalogoPlanta(plantaService.findAllMap());
			}
		} catch(NullPointerException e){
			logger.error("Error getListadoPlantasTodas " + e.getLocalizedMessage(),e);
		}
	}
	
	public void onEstadoChange(AjaxBehaviorEvent ae){
		LocalidadService localidadService = this.getSpringContext().getBean("localidadService",LocalidadService.class);
		
		logger.info("onEstadoChange " + this.getIdEstado());
		
		try {
			if(this.getIdEstado() != 0){
				 this.setCatalogoLocalidad(localidadService.findAllByEstadoSet(this.getIdEstado()));				 
			} else
				this.setCatalogoLocalidad(null);
		} catch(NullPointerException npe){
			this.setCatalogoLocalidad(null);
		}
	}
	
	/**
	 * Evento que actualiza listado de plantas al cambiar de localidad
	 * @param abe
	 */
	public void onLocalidadChange(AjaxBehaviorEvent abe){
		try {
			PlantaService plantaService = this.getSpringContext().getBean("plantaService",PlantaService.class);
			if(this.getIdLocalidad() != 0){
				logger.info("idLocalidad " + this.getIdLocalidad());
				this.setCatalogoPlanta(plantaService.findByLocalidad(this.getIdLocalidad()));
				Planta planta =  plantaService.findByIdLocalidad(this.getIdLocalidad());
				this.setIdPlanta(planta.getIdPlanta());
				this.setPlantaDescripcion(planta.getDescripcion());				
			}else {
				throw new Exception("No se ha seleccionado una localidad");
			}
		} catch(Exception e){
			logger.error(e.getLocalizedMessage(),e);
			this.setCatalogoPlanta(null);
		}				
	}

	
	/**
	 * Get Listado de Estados por Planta
	 * @param ae
	 */
	public void onPlantaAllChange(AjaxBehaviorEvent ae){
		try{
			EstadoService es = this.getSpringContext().getBean("estadoService",EstadoService.class);									
			if(!this.getIdPlanta().equals(""))
				this.setCatalogoEstado(es.findByPlanta(this.getIdPlanta()));
			else
				throw new Exception("No se ha seleccionad ninguna planta");
		} catch(Exception e){
			logger.error(e.getLocalizedMessage(),e);
			this.setCatalogoEstado(null);
		}
	}



}
