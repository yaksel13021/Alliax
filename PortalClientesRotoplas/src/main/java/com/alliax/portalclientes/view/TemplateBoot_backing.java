package com.alliax.portalclientes.view;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

@ManagedBean(name="template")
@SessionScoped
public class TemplateBoot_backing extends AbstractBacking {
	
	private final static Logger logger = Logger.getLogger(TemplateBoot_backing.class);
	
}
