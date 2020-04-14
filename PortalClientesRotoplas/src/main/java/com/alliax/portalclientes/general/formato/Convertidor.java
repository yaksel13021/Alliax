package com.alliax.portalclientes.general.formato;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Convertidor {
	
	
	/**
	 * Remueve ceros a la izquierda de un string
	 * @param numero
	 * @return
	 */
    public final static String remueveCerosIzq(String numero){
    	try {	        
            int i = 0;
            boolean continua = true;           
            
            while(continua){
                try{
                    if(numero.substring(i,1).equals("0"))
                        numero = numero.substring(i+1);
                    else
                        continua = false;
                } catch (Exception e) {
                    continua = false;
                }
            }
	        return numero;
    	} catch(Exception e){
    		return numero;
    	}
    }	
	
	
    /**
     * Agrega ceros a la izquierda de un string hasta una longitud determinada.
     * @param numero
     * @param digitos
     * @return
     */
    public final static String agregaCerosIzq(String numero, int digitos){
        if(numero != null && !numero.trim().equals("") && numero.length() < digitos){            
            while(digitos > numero.length()){
                numero = "0" + numero;
            }
        }
        return numero;
    }
    
    
    /**
     * Establece la escala para un numero BigDecimal
     * @param bigDecimal
     * @param EscalaMax
     * @return
     */
    public static BigDecimal setEscala(BigDecimal bigDecimal, int EscalaMax){
    	try{
    		//return NumberFormat.getCurrencyInstance().format(bigDecimal);
    		if(bigDecimal.scale() > EscalaMax)
    			return bigDecimal.setScale(EscalaMax,BigDecimal.ROUND_HALF_EVEN);
    		else if(bigDecimal.scale() < 2)
    			return bigDecimal.setScale(2,BigDecimal.ROUND_HALF_EVEN);
    		else {
				try {
					return bigDecimal.setScale(2,BigDecimal.ROUND_UNNECESSARY);
				}catch (ArithmeticException e){
					return bigDecimal.setScale(bigDecimal.scale());
				}
    		}
    	} catch(NullPointerException npe){
    		return new BigDecimal("0").setScale(2);
    	} catch(NumberFormatException nfe){
    		throw new NumberFormatException(nfe.toString());
    	}    	
    }
    
    
    /**
     * Formatea un BigDecimal a String
     * @param bigDecimal
     * @return
     */
    public static final String BigDecimalToString(BigDecimal bigDecimal){
    	try{
    		DecimalFormat df = new DecimalFormat("#,###.##");
    		return df.format(bigDecimal);
    	} catch(Exception e){
    		return bigDecimal.toString();
    	}
    }
    
    /**
     * Formatea un BigDecimal a String con signo de pesos $
     * @param bigDecimal
     * @return
     */
    public static final String BigDecimalToString$(BigDecimal bigDecimal){
    	try{
    		DecimalFormat df = new DecimalFormat("$#,##0.00");
    		return df.format(bigDecimal);
    	} catch(Exception e){
    		return bigDecimal.toString();
    	}
    }

    /**
     * Formatea un BigDecimal como currency
     * @param monto
     * @return
     * @throws Exception
     */
    public static String bigDecimalToCurrencyFormat(BigDecimal monto) throws Exception{        
        try{
            //Para formatear numeros
        	NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US); 
        	
            return nf.format(monto);
        } catch(Exception e){
        	throw new Exception(e.getLocalizedMessage());
        }            	
    }
    

    
        
}
