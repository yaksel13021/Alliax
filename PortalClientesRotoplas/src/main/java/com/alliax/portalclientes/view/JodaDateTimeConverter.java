/**
 * Converter para Joda Date time
 * @author saul.ibarra
 * @fecha 22-Abril-2016
 */
package com.alliax.portalclientes.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

@FacesConverter("jodaDateTimeConverter")
public class JodaDateTimeConverter implements Converter {

	private final String PATTERN = "dd-MM-yyyy HH:mm:ss";
	
	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String value) {
		return DateTimeFormat.forPattern(PATTERN).parseDateTime(value);
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object value) {
		DateTime dateTime = (DateTime)value;
		
		return DateTimeFormat.forPattern(PATTERN).print(dateTime);
	}
}
