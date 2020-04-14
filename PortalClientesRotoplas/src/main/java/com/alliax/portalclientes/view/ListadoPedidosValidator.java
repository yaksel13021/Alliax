/**
 * Validador multiple para busqueda de pedidos de clientes
 * @author saul.ibarra
 * @fecha 4-Abril-2016
 */
package com.alliax.portalclientes.view;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;

import com.alliax.portalclientes.general.formato.Fecha;
import com.alliax.portalclientes.general.validaciones.Validaciones;

@ManagedBean
@FacesValidator("listadoPedidosValidator")
public class ListadoPedidosValidator implements Validator {
	
	private final static Logger logger = Logger.getLogger(ListadoPedidosValidator.class);
	
	@ManagedProperty("#{lblMain}")
	private ResourceBundle lblMain;
	
		
	public ResourceBundle getLblMain() {
		return lblMain;
	}

	public void setLblMain(ResourceBundle lblMain) {
		this.lblMain = lblMain;
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		logger.info("validate Rango fechas Pedidos");
		String fechaInicio;
		String fechaFin;
		String rangoDias;
		String tipoDoc;
		
		UIInput uiInputFechaIni = (UIInput)component.getAttributes().get("frm_fechaIni");
		UIInput uiInputFechaFin = (UIInput)component.getAttributes().get("frm_fechaFin");
		UIInput uiInputRangoDias = (UIInput)component.getAttributes().get("frm_rangoDias");		
		UIInput uiInputTipoDoc = (UIInput)component.getAttributes().get("frm_tipoDocumento");
		/*UIInput uiInputFecIni = (UIInput)component.getAttributes().get("frm_fecIni");
		UIInput uiInputFecFin = (UIInput)component.getAttributes().get("frm_fecFin");*/
		
		fechaInicio = (String)uiInputFechaIni.getSubmittedValue();
		fechaFin = (String)uiInputFechaFin.getSubmittedValue();
		rangoDias = (String)uiInputRangoDias.getSubmittedValue();		
		tipoDoc = (String)uiInputTipoDoc.getSubmittedValue();
		/*fecIni = (String)uiInputFecIni.getSubmittedValue();
		fecFin = (String)uiInputFecFin.getSubmittedValue();*/
		
		logger.info("fechaInicio " + fechaInicio);
		logger.info("fechaFin "+ fechaFin);
		logger.info("rangoDias " + rangoDias);
		logger.info("tipoDoc "+ tipoDoc);
		/*logger.info("fecIni "+ fecIni);
		logger.info("fecFin "+ fecFin);*/
		
		if(rangoDias != null && rangoDias.equals("P")) { //Periodo
			//Si las dos fechas estan definidas valida rango..
			if(Validaciones.validaRequerido(fechaInicio) &&
				Validaciones.validaRequerido(fechaFin)){
	
				logger.info("Validando diferencia");
				boolean rango = false;		
				try {
					if(Fecha.diferenciaDias(Fecha.getDate(fechaInicio + " 00:00:01",4), 
						Fecha.getDate(fechaFin + " 23:59:59",4)) > 90){
							rango = true;
					}
				} catch(Exception e){
					logger.error("Error " + e.getLocalizedMessage(),e);
				}
				
				if(rango){
					throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"",
							this.getLblMain().getString("errRangoInvalido")));			
				}
			} else {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"",
						this.getLblMain().getString("errRangoFecRequerido")));				
			}
		} else if(rangoDias != null && rangoDias.equals("F")) { //Fecha
			if(!Validaciones.validaRequerido(fechaInicio)){
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"",
						(new MessageFormat(this.getLblMain().getString("msgRequerido"))).format(
							new Object [] {this.getLblMain().getString("fecha")})));
			}
		}
		logger.info("Fin");
	}

}
