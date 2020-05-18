package com.alliax.portalclientes.general.formato;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.xml.datatype.DatatypeFactory;

import org.apache.log4j.Logger;

public class Fecha {
	
	private final static Logger logger = Logger.getLogger(Fecha.class);
			
	private final static TimeZone timeZone = TimeZone.getTimeZone("America/Mexico_City");
	private final static DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(
															TimeZone.getTimeZone("America/Mexico_City"));	
	
	/**
	 * Clase para gestionar la fecha con timesaving etc etc
	 * @return
	 */
	public final static GregorianCalendar getCalendario(long timeStamp){

        //Definiendo uso horario
        /*String[] ids = TimeZone.getAvailableIDs(-6 * 60 * 60 * 1000);
        SimpleTimeZone CST = new SimpleTimeZone(-6 * 60 * 60 * 1000, ids[0]);
        CST.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
        CST.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);*/		
		
		
        //TimeZone.setDefault(TimeZone.getTimeZone("America/NewYorkCity"));
       // TimeZone.setDefault(TimeZone.getTimeZone(TimeZone.getTimeZone("GMT-5:00").getID()));
        
        //TimeZone.setDefault(TimeZone.getTimeZone("GMT-6:00"));
		
		GregorianCalendar cal = new GregorianCalendar(timeZone);
		
		if(timeStamp != 0)
			cal.setTimeInMillis(timeStamp);
		
		
		return cal;
	}
	
	public final static long getTimeStampActual(){
		return getCalendario(0).getTimeInMillis();
	}
	
	
	public final static java.sql.Timestamp getSQLTimeStampActual(){
		return new java.sql.Timestamp(getCalendario(0).getTimeInMillis());
	}
	
	public final static java.sql.Timestamp getSQLTimeStamp(Date fecha){
		return new java.sql.Timestamp(getCalendario(fecha.getTime()).getTimeInMillis());
	}
	
	public final static java.sql.Timestamp getSQLTimeStamp(Long timestamp){
		return new java.sql.Timestamp(timestamp);
	}	
	
	
	
	/**
	 * Extrae la fecha actual en el formato 1: aaaa-mm-ddThh:mm:ss tipo dato xs:dateTime
	 * @return
	 */
    public final static String getFechaActual(){
    	GregorianCalendar cal = getCalendario(0);
        
        //Time Actual ****Definir horarios de verano
        long timme = cal.getTimeInMillis();
                
        try {
            return getFechaFormateada(timme, 1);
        } catch(Exception e){
            return "";
        }
    }
    
    /**
     * Regresa un objeto Date con la fecha actual
     * @return
     */
    public final static java.util.Date getDateActual(){
    	return new Date(getTimeStampActual());
    }
    
    
    public final static DateTime getDateTimeActual(){
    	//return new DateTime(getTimeStampActual()).minusHours(5);
    	return new DateTime(new Date(getTimeStampActual()));
    }
    
    public final static void setDefaultTimeZone(){
    	DateTimeZone.setDefault(dateTimeZone);
    }
    
    
    public final static java.util.Date sqlTimeStampToDate(java.sql.Timestamp timestamp){
    	return new Date(timestamp.getTime());
    }
    
    
    /**
     * Convierte un String en Fecha
     * @param fecha
     * @return
     */
    public final static Date getDate(String fecha){
    	try {
    		return getFormatoFecha(2).parse(fecha);
    	} catch(Exception e){
    		return null;
    	}
    }
    
    /**
     * Convierte un String en Fecha
     * @param fecha
     * @return
     */
    public final static Date getDate(String fecha, int formato){
    	try {
    		return getFormatoFecha(formato).parse(fecha);
    	} catch(Exception e){
    		return null;
    	}
    }    
   
    
    /**
     * Regresa un vector conteniendo fechaInicial y fechaFinal de acuerdo a las opciones del select en filtros
     * @param dias Diferencia de los rangos de fecha en dias
     * @param formato de fecha de retorno
     * @return
     * @throws Exception
     */
    public final static List<String> getRangosFechas(int dias, int formato) throws Exception{        
        String fechaFinal = null;
        List<String> rangoFecha = new LinkedList<String>();
        
        GregorianCalendar fecha = getCalendario(0);
        
        fechaFinal = getFechaFormateada(fecha.getTimeInMillis(), 6);
               
        //Calculando el Rango final        
        fecha.add(Calendar.DATE, -dias);
        
        //Agregando el Rango Inicial
        rangoFecha.add(getFechaFormateada(fecha.getTimeInMillis(),6));
        
        //Agregando Fecha Final
        rangoFecha.add(fechaFinal);
                                     
        return rangoFecha;        
    }    
    
    
    
    
    /**
     * Convierte una Fecha de java.util.Date a java.sql.Date
     * @param fecha
     * @return
     */
    public final static java.sql.Date getSQLDate(java.util.Date fecha){    	
    	GregorianCalendar cal = getCalendario(fecha.getTime());
    	        	    	    	
    	return new java.sql.Date(cal.getTime().getTime());    	
    }
    
    
    
    private final static SimpleDateFormat getFormatoFecha(int formato) {
		if(formato == 1)
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		else if(formato == 2)
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		else if(formato == 3)
			return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		else if(formato == 4)
			return new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		else if(formato == 7)
			return new SimpleDateFormat("dd/MM/yyyy");
		else
			return new SimpleDateFormat("yyyy-MM-dd"); 
    }
    
    
    /**
     * Regresa la fecha Formateada
     * @param fecha
     * @param formato 
 
     * @return
     * @throws Exception
     */
    public final static String getFechaFormateada(Date fecha, int formato) throws Exception{    
    	try{
    		return getFechaFormateada(fecha.getTime(),formato);
		} catch (NullPointerException npe){
			return null;
		} catch (Exception e){
			throw new Exception (e.toString());
		}    
    }
    
    /**
     * Genera Fechas en diferentes formatos
     * @param timme
     * @param formato 
     * 		1: aaaa-mm-ddThh:mm:ss Tipo dato xs:dateTime
     * 		2  aaaa-mm-dd			Tipo Dato xs:date
     * 		3  HH:MM:SS
     *      4 DD.MM.YYYY
     *      5 MM.DD.YYYY
     *      6 YYYYMMDD <-- Formato para SAP
     * @return
     * @throws Exception
     */
    public static String getFechaFormateada(long timme, int formato) throws Exception{
		//Fecha Inicial del mes seleccionado
		SimpleDateFormat sdf = null;
						
		if(formato == 2)
			sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");			
		else if(formato == 2)
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		else if(formato == 3)
			sdf = new SimpleDateFormat("HH:mm:ss");
		else if(formato == 4)
			sdf = new SimpleDateFormat("dd.MM.yyyy");	
		else if(formato == 5)
			sdf = new SimpleDateFormat("MM.dd.yyyy");
		else if(formato == 6)
			sdf = new SimpleDateFormat("yyyyMMdd");
		else if(formato == 7)
			sdf = new SimpleDateFormat("dd/MM/yyyy");			
		else
			return "yyyy-MM-dd";
		
		//Setting time zone
		sdf.setTimeZone(timeZone);
			
		return sdf.format(timme);
    }
    
    /**
     * Determina el numero de dias de diferencia entre 2 fechas
     * @param fechaInicio
     * @param fechaFin
     * @return
     * @throws Exception
     */
    public final static long diferenciaDias(Date fechaInicio, Date fechaFin) throws Exception {
    	try {
	        long diferencia = 0;
	        long dias = 0;        
	
	        diferencia = fechaFin.getTime() - fechaInicio.getTime();
	
	        //Calcula el numero de dias de diferencia entre las fechas
	        dias = (diferencia / (24 * 60 * 60 * 1000));
	        
	        logger.info("Dias Diferencia " + dias);
	        
	        if(dias < 0)
	        	throw new Exception("La Fecha Final no puede ser mayor a la fecha Inicial");
	        
	        return dias;
    	} catch(NullPointerException npe){
    		throw new Exception ("La fecha de Inicio o fecha final son invalidas" + npe.toString());
    	} catch(ArithmeticException ae){
    		throw new Exception("Error al calcular los dias de diferencia entre las fechas" + ae.toString());
    	} catch (Exception e){
    		throw new Exception(e.toString());
    	}
    }
    
    /**
     * Determina el numero de dias de diferencia entre 2 fechas
     * @param fechaInicio
     * @param fechaFin
     * @return
     * @throws Exception
     */
    public final static long diferenciaHoras(Date fechaInicio, Date fechaFin) throws Exception {
    	try {
	        long diferencia = 0;
	        long horas = 0;        
	
	        diferencia = fechaFin.getTime() - fechaInicio.getTime();
	
	        //Calcula el numero de dias de diferencia entre las fechas
	        horas = (diferencia / (60 * 60 * 1000));
	        
	        /*if(horas < 0)
	        	throw new Exception("La Fecha Final no puede ser mayor a la fecha Inicial");*/
	        
	        return horas;
    	
    	} catch (Exception e){
    		throw new Exception("Ocurri&oacute; un error al validar diferencia de fechas " + e.getLocalizedMessage());
    	}
    }
    
    public static void main(String[] args) {
		try {
//			System.out.println( getFechaFormateada(new Date(), 7) );
//			System.out.println( getFechaFormateada(new Date().getTime(), 7) );
//			System.out.println( new Date().toString() );
			
			System.out.println( getDate("28-01-2018",3));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public static boolean validarFechayyyyMMdd(String fecha) {
        boolean correcto = false;

        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
            formatoFecha.parse(fecha);
            correcto = true;
        } catch (ParseException e) {
            correcto = false;
        }

        return correcto;
    }

	public static String getFechaFormateadaStringToString(String fecha,int formatoEntrada,int formatoSalida){
    	String resultado=null;
    	try {
			if (fecha != null && fecha.length() > 0) {
				Date date = Fecha.getDate(fecha, formatoEntrada);
				return getFechaFormateada(date, formatoSalida);
			}
		}catch(Exception e){
    		return null;
		}
    	return resultado;
	}
}
