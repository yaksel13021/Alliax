/**
 * Clase para validar datos
 * @author saul.ibarra
 * @fecha 1-Julio-2013
 */
package com.alliax.portalclientes.general.validaciones;

import java.math.BigDecimal;
import java.util.Date;


public class Validaciones {

			
	/**
	 * Valida que se trate de un correo electroico
	 * @param email
	 * @return
	 */
	public static boolean validaEmail(String email){
		try{
			if(email.trim().matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,4}$"))
				return true;
			
			return false;
						
		} catch(Exception e){
			return false;
		}
	}
	
	
	/**
	 * Valida que un password cumpla con las politicas las cuales son:
	 * 		Al menos un caracter en Mayusculas
	 * 		Al menos un numero
	 * 		Al menos un simbolo $ % & / = +
	 * 		Estar formada por 8 caracteres minimo y 12 maximo
	 * 
	 * @param password
	 * @return
	 */
	public static boolean validaPassword(String password){
		try{			
			String ch = "[\\w,$,%,&amp;,=,(,),(+)]";		
			if(password.matches("^((?=" + ch + "*[A-Z,Ñ])(?=" + ch + "*[a-z,ñ])(?=" + ch + "*[0-9])(?=" + ch + "*[$%(&amp;)(,)=+])" + ch + "{8,12}$)"))
				return true;
		} catch(NullPointerException npe){
			return false;
		}
		return false;
	}
			
	
	/**
	 * Valida que el importe sea un numero decimal positivo
	 * @param importe
	 * @return
	 */
	public static boolean validaBigDecimal(String bigDecimal){
		try{					
			//Valida que la cantidad sea mayor a 0
			if( (new BigDecimal(bigDecimal).compareTo(new BigDecimal("0")) >= 0) && (new BigDecimal(bigDecimal).compareTo(new BigDecimal("9999999999.999999")) <= 0))
				return true;
			
			return false;
		} catch (Exception e){
			return false;
		}
	}
	

	/**
	 * Reemplaza Caracteres especiales y si la cadena esta vacia regresa null
	 * @param cadena
	 * @return
	 */
	public static String validaTexto(String cadena){
        try {
        	String temp = cadena;
        	
        	if(cadena.trim().length() == 0)
        		throw new NullPointerException("Cadena Vacia");


        	//Remueve comillas sencillas y dobles y cambia \s (espacio) por espacio
        	temp = temp.replace("'","").trim();        	
        	temp = temp.replace("\"", "");
        	temp = temp.replaceAll("\\s", " ");
                
            return temp;
        } catch (NullPointerException e) {
        	return null;
        }
	}
		
	
	/**
	 * Los campos requeridos deben pasar esta validacion
	 * @param cadena
	 * @return
	 */
	public static boolean validaRequerido(String cadena){
		try {
			if(cadena.trim().length() > 0)
				return true;
			
			return false;
		} catch (Exception e){
			return false;
		}
	}
	
	/**
	 * Valida que la cadena cumpla con la longitud minima y maxima requerida
	 * Si maxLenght = 0  quiere decir que no tiene limite
	 * @param str
	 * @param minLenght
	 * @param maxLenght
	 * @return
	 */
	public static boolean validaLongitud(String str, int minLenght, int maxLenght) {
		try{			
			str = str.trim();
			
			if(str.length() >= minLenght){
				if(maxLenght != 0){
					if(str.length() <= maxLenght)
						return true;
					else 
						return false;
				} else //Si no hay longitud maxima..									
					return true;
			}
			return false;			
		} catch (Exception e){
			return false;
		}
	}
	

	/**
	 * Valida que el campo string sea de tipo Long
	 * @param numeroLong
	 * @return
	 */
	public static boolean validaLong(String numeroLong){
		try{
			Long.parseLong(numeroLong);
			return true;
		} catch (NumberFormatException e){
			e.getLocalizedMessage();
			return false;
		}
	}
	
	/**
	 * Valida que el campo String sea de tipo Integer
	 * @param numero
	 * @return
	 */
	public static boolean validaInteger(String numero){
		try {
			Integer.parseInt(numero);
			return true;
		} catch (Exception e){
			return false;
		}
	}
	
	/**
	 * Valida que el campo String contenga solo digitos
	 * @param numero
	 * @return
	 */
	public static boolean isNumber(String numero){
		try {
			return numero.matches("[0-9]+");
				
		} catch (Exception e){
			return false;
		}
	}	    
    
    /**
     * Valida el valor de un dato boolean On, Off
     * @param valor
     * @return
     */
    public static boolean validaBoolean(String valor){
    	try{
    		if(valor.equalsIgnoreCase("on"))
    			return true;
    		else
    			return false;
    	} catch(NullPointerException e){
    		return false;
    	}
    }
    
    
    /**
     * Valida que una fecha sea mayor a otra.
     * @param fecha
     * @param fechaLimite
     * @return
     * @throws Exception
     */
    public static boolean isDateGreaterThan(Date fecha, Date fechaLimite) throws Exception{
    	try {
	    	if(fecha.after(fechaLimite))
	    		return true;
	    	else 
	    		return false;    
    	} catch (Exception e) {
    		throw new Exception("Error al comparar fechas " + e.getLocalizedMessage());
    	}
    }
    
    
    /**
     * Valida que una fecha este dentro de un rango.
     * @param fecha
     * @param limiteInferior
     * @param limiteSuperior
     * @return
     * @throws Exception 
     */
    public static boolean isDateInRange(Date fecha, Date limiteInferior, Date limiteSuperior) throws Exception{
    	try {
	    	if(fecha.after(limiteInferior) && fecha.before(limiteSuperior))
	    		return true;
	    	else 
	    		return false;
    	} catch (Exception e) {
    		throw new Exception("Error al comparar rangos de fechas " + e.getLocalizedMessage());
    	}
    } 
}
