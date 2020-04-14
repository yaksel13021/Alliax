package com.alliax.portalclientes.view;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

@ManagedBean(name="errorHandler")
@RequestScoped
public class ErrorHandler_backing {
	
	private final static Logger logger = Logger.getLogger(ErrorHandler_backing.class);
	
	@PostConstruct
	public void init(){
		logger.error("javax.servlet.error.status_code " + this.getStatusCode());
		logger.error("javax.servlet.error.exception " + this.getException());
		logger.error("javax.servlet.error.request_uri " + this.getRequestURI());
		logger.error("javax.servlet.error.message " + this.getMessage());
		logger.error("javax.servlet.error.exception_type " + this.getExceptionType());
	}

	public String getStatusCode(){
		try {
			String val = String.valueOf((Integer)FacesContext.getCurrentInstance().getExternalContext().
				getRequestMap().get("javax.servlet.error.status_code"));
			return val;
		} catch(Exception e){
			return "50X";
		}
	}

	public String getMessage(){
		try {
			String val =  (String)FacesContext.getCurrentInstance().getExternalContext().
				getRequestMap().get("javax.servlet.error.message");
			return val;
		} catch(Exception e){
			return "";
		}
	}

	public String getExceptionType(){
		try {
			return FacesContext.getCurrentInstance().getExternalContext().
					getRequestMap().get("javax.servlet.error.exception_type").toString();
		} catch(Exception e){
			return "";
		}
	}

	public String getException(){
		try {
			String val =  (String)((Exception)FacesContext.getCurrentInstance().getExternalContext().
				getRequestMap().get("javax.servlet.error.exception")).toString();
			return val;
		} catch(Exception e){
			return "";
		}
	}

	public String getRequestURI(){
		try{
			return (String)FacesContext.getCurrentInstance().getExternalContext().
				getRequestMap().get("javax.servlet.error.request_uri");
		} catch(Exception e){
			return "";
		}
	}

	public String getServletName(){
		try {
			return (String)FacesContext.getCurrentInstance().getExternalContext().
					getRequestMap().get("javax.servlet.error.servlet_name");
		} catch(Exception e){
			return "";
		}
		
	}
}
