package com.alliax.portalclientes.general.formato;

import java.util.Random;

import org.apache.log4j.Logger;

public class Generales {

	private final static Logger logger = Logger.getLogger(Generales.class);
	
    /**
     * Genera un password aleatoriamente que cumple con las politicas de SAT
     * Politica Aplicable en este metodo
     * 1. Longitud minima de 8 caracteres
     * 2 Debe contener almenos 1 de los siguienres caracteres
     * 2. Mayusculas
     * 3. Minusculas
     * 4. Numeros
     * 5. Simbolos especiales...
     */
    public final static String generaPassword() throws Exception{
    	String metodo = "generaPassword ";
    	try {
	        String strMinusculas = "qwertyuiopasdfghjklzxcvbnm";
	        String strMayusculas = "QWERTYUIOPASDFGHJKLZXCVBNM";
	        String strNumero = "0123456789";
	        String strEspeciales = "$%#=@&?";
	        
	        String  pwd = "";
	
	        String strFullString = strMinusculas + strMayusculas +strNumero + strEspeciales;
	        
	        //Instancia para generar numeros aleatorios
	        Random rand = new Random();
	
	        //Minusculas
	        int rdm = rand.nextInt(strMinusculas.length()-1);
	        pwd += strMinusculas.substring(rdm,rdm+1);
	
	        //Todos
	        rdm = rand.nextInt(strFullString.length()-1);
	        pwd += strFullString.substring(rdm,rdm+1);        
	        
	        //Mayusculas
	        rdm = rand.nextInt(strMayusculas.length()-1);
	        pwd += strMayusculas.substring(rdm,rdm+1);
	        
	        //Todos
	        rdm = rand.nextInt(strFullString.length()-1);
	        pwd += strFullString.substring(rdm,rdm+1);        
	        
	        //Numeros
	        rdm = rand.nextInt(strNumero.length()-1);
	        pwd += strNumero.substring(rdm,rdm+1);
	        
	        //Todos
	        rdm = rand.nextInt(strFullString.length()-1);
	        pwd += strFullString.substring(rdm,rdm+1);
	                
	        //Caracteres Especiales
	        rdm = rand.nextInt(strEspeciales.length()-1);
	        pwd += strEspeciales.substring(rdm,rdm+1);
	        
	        //Todos
	        rdm = rand.nextInt(strFullString.length()-1);
	        pwd += strFullString.substring(rdm,rdm+1);        
	
	        return pwd;
    	}catch (Exception e){
    		logger.fatal(metodo + e.getLocalizedMessage(),e);
    		throw new Exception ("Error al generar Password Generico");
    	}
    }	
	
}
