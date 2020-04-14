package com.alliax.portalclientes.util;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;

import org.json.JSONArray;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.alliax.portalclientes.controller.AutocompleteClientesRFC;
import com.alliax.portalclientes.domain.RolUsuario;

/**
 * Utilidades globales del sistema
 * @author daniel.galindo
 *
 */
public class Helper {	
	
	private static final Logger logger = Logger.getLogger(Helper.class);
	private static String pathJSON = "";
	public static boolean isProductionServer;	
	
	static{
		try { isProductionServer = InetAddress.getLocalHost().getHostName().equalsIgnoreCase("sap45") ? true : false;  } catch (UnknownHostException e) { }		
		if(isProductionServer){
			pathJSON = "D:\\PortalClientes\\temp\\json\\";			
		}else{
			pathJSON = "D:\\ComercioExterior\\temp\\json\\";
			if(!new File(pathJSON).exists()){
				pathJSON = "C:\\temp\\json\\";
			}			
		}		
	}
		
	/**
	 * Retorna el signo de pesos dependiendo del pais por ejemplo: PE MX AR
	 * @param pais
	 * @return
	 */
	public static String getSignoPesos(String pais){
		String signoPesos = "";
		switch(pais){
			case "PE":
				signoPesos = "S/";
				break;
			case "GT":
			case "SV":
			case "HN":
			case "NI":
			case "CR":
				signoPesos = "";
				break;
			case "BR":
				signoPesos = "R$ ";
				break;				
			default:
				signoPesos = "$";
		}
		return signoPesos;
	}
	
	public static String getRFCLabel(String pais){
		if(pais == null) pais = "";
        if(pais.equals("AR")){
        	return "CUIT";
        }else if(pais.equals("PE")){
        	return "RUC";
        }else if((pais.equals("GT") || pais.equals("SV")) ){
        	return "NIT";
        }else if(pais.equals("HN")){
        	return "RTN";
        }else if(pais.equals("NI")){
        	return "RUC";
        }else if(pais.equals("CR")){
        	return "Cedula Jurídica";
        }else if(pais.equals("BR")){
        	return "CNPJ";                	
        }else{        	
        	return "RFC";        
        }
        
	}
	
	/**
	 * Obtiene el monto con su signo de moneda correspondientes
	 * @param m  El monto sin formato
	 * @param pais El país al que pertenece
	 * @return Monto con formato por ejemplo: input -5,000   output  -$5,000.00
	 */
	public static String getMontoFormateado(Object m, String pais){
		String mf = "";
		DecimalFormat df;
		switch(pais){
			case "BR":
				DecimalFormatSymbols dfs = new DecimalFormatSymbols();
				 dfs.setDecimalSeparator(',');
				 dfs.setGroupingSeparator('.');
				df = new DecimalFormat( getSignoPesos(pais) + "#,##0.00", dfs);
				break;
			default: 
				df = new DecimalFormat( getSignoPesos(pais) + "#,##0.00" );
		}
		
		if(m instanceof BigDecimal){
			mf = df.format(m);
		}else if(m instanceof Integer){
			mf = df.format(m);
		}else if(m instanceof String){
			mf = df.format(m);
		}else{
			mf = df.format(m);
		}
		
		return mf;
	}
	
	public static String getJSONClientes(AutocompleteClientesRFC autocompleteClienteRFC, String pais) throws Exception{
		if(pais.equals("ALL")){
			List<JSONCliente> list = autocompleteClienteRFC.getClientesList("", "GT");
			list = ListUtils.union(list, autocompleteClienteRFC.getClientesList("", "SV"));
			list = ListUtils.union(list, autocompleteClienteRFC.getClientesList("", "HN"));
			list = ListUtils.union(list, autocompleteClienteRFC.getClientesList("", "NI"));
			list = ListUtils.union(list, autocompleteClienteRFC.getClientesList("", "CR"));
			
			list = ListUtils.union(list, autocompleteClienteRFC.getClientesList("", "PE"));
			list = ListUtils.union(list, autocompleteClienteRFC.getClientesList("", "AR"));
			list = ListUtils.union(list, autocompleteClienteRFC.getClientesList("", "MX"));
			list = ListUtils.union(list, autocompleteClienteRFC.getClientesList("", "BR"));
			return new JSONArray(list).toString();
		}else if(pais.equals("CA")){
			List<JSONCliente> list = autocompleteClienteRFC.getClientesList("", "GT");
			list = ListUtils.union(list, autocompleteClienteRFC.getClientesList("", "SV"));
			list = ListUtils.union(list, autocompleteClienteRFC.getClientesList("", "HN"));
			list = ListUtils.union(list, autocompleteClienteRFC.getClientesList("", "NI"));
			list = ListUtils.union(list, autocompleteClienteRFC.getClientesList("", "CR"));			
//			System.out.println(new JSONArray(list).toString());
			return new JSONArray(list).toString();
		}else{
			return new JSONArray(autocompleteClienteRFC.getClientesList("", pais)).toString();
		}
	}
	
	public static String readJSONClienteList(String pais) throws Exception{
		String filePath = "";
		String fileName = "clientes_"+pais+".json";		
		filePath = pathJSON + fileName;		
		return new String(Files.readAllBytes( Paths.get( filePath ) ));
	}
	
	public static void writeJSONClienteList(String fileContent, String pais) throws Exception{
		String filePath = "";
		String fileName = "clientes_"+pais+".json";
		filePath = pathJSON + fileName;
		
		//Si no existe la ruta entonces la crea
		if(!new File(pathJSON).exists()){ new File(pathJSON).mkdir(); }
		
		FileOutputStream os = new FileOutputStream(filePath);
		os.write( fileContent.getBytes() );
		
	}
	
	public static boolean isTimeToReloadJSONfiles(){
		File file = new File(pathJSON + "clientes_CA.json");
		boolean response = false;
		if(!file.exists()){
			response =  true;
		}else{						
			long diff = Calendar.getInstance().getTimeInMillis() - file.lastModified();
			int days = (int)(diff / (1000 * 60 * 60 * 24))  ;
			if(days > 7){
				response = true;
			}			 			
		}
		
		return response;
	}
	
	public static String getPaisFromRoles(Set<RolUsuario> roles){
		String pais = "MX";
		for(RolUsuario rol : roles){
			if(rol.getRol().contains("_AR")){ pais = "AR"; }
			if(rol.getRol().contains("_PE")){ pais = "PE"; }
			if(rol.getRol().contains("_CA")){ pais = "CA"; }
			if(rol.getRol().contains("_BR")){ pais = "BR"; }
		}		
		return pais;
	}
	
	public static String getPaisFromPais(String pais){
		if((pais != null) && (pais.equals("GT") || pais.equals("SV") || pais.equals("HN") || pais.equals("NI") || pais.equals("CR"))){
			pais = "CA";
		}
		return pais;
	}
	
	public static Set<Entry<String, String>> getRolesPais(Authentication authentication, Map<String,String> rolesToShow, Map<String, String> rolesCat){
		
		if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN_MEMBER_AR"))){
			for (Map.Entry<String,String> rol : rolesCat.entrySet()) {				
				if(rol.getKey().contains("_AR")){
					rolesToShow.put(rol.getKey(), rol.getValue());
				}
			}
			return rolesToShow.entrySet();
		}
		if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN_MEMBER_PE"))){
			for (Map.Entry<String,String> rol : rolesCat.entrySet()) {				
				if(rol.getKey().contains("_PE")){
					rolesToShow.put(rol.getKey(), rol.getValue());
				}
			}
			return rolesToShow.entrySet();
		}
		if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN_MEMBER_CA"))){
			for (Map.Entry<String,String> rol : rolesCat.entrySet()) {				
				if(rol.getKey().contains("_CA")){
					rolesToShow.put(rol.getKey(), rol.getValue());
				}
			}
			return rolesToShow.entrySet();
		}
		
		if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN_MEMBER_BR"))){
			for (Map.Entry<String,String> rol : rolesCat.entrySet()) {				
				if(rol.getKey().contains("_BR")){
					rolesToShow.put(rol.getKey(), rol.getValue());
				}
			}
			return rolesToShow.entrySet();
		}		
		
		return rolesCat.entrySet();		
	}
	
	/***********************faces-config.xml <managed-bean-scope>application</managed-bean-scope>*************************
	 ******************Las variables definidas en faces-config.xml ahora de definen en la clase helper********************/
	
	/**
	 * tipoBusquedaExMap
	 * @param lblMain ResourceBundle con el idioma correcto
	 * @return Map<Integer,String>  definido en faces-config.xml
	 */
	public static Map<Integer,String> getTipoBusquedaExMap(ResourceBundle lblMain){
		//tipoBusquedaExMap    Catalogo Tipo de Busqueda de Existencias
		Map<Integer,String> map = new LinkedHashMap<>();
		 map.put(1, lblMain.getString("localidad"));
		 map.put(2, lblMain.getString("planta"));
		return map;
	}
	
	/**
	 * estatusGlobalMap
	 * @param lblMain
	 * @return Map<String,String>
	 */
	public static Map<String,String> getEstatusGlobalMap(ResourceBundle lblMain){
		//estatusGlobalMap   Estatus Globales de los pedidos
		Map<String,String> map = new LinkedHashMap<>();
		 map.put("T", lblMain.getString("todos"));
		 map.put("A", lblMain.getString("globalA"));
		 map.put("C", lblMain.getString("globalC"));
		return map;
	}
	
	/**
	 * rangoFechaMap
	 * @param lblMain
	 * @return Map<Strig,String>
	 */
	public static Map<String,String> getRangoFechaMap(ResourceBundle lblMain){
		//rangoFechaMap   Catalogo para rantos de fechas
		Map<String,String> map = new LinkedHashMap<>();
		 map.put("H", lblMain.getString("hoy"));
		 map.put("S", lblMain.getString("sieteDias"));
		 map.put("T", lblMain.getString("treintaDias"));
		 map.put("N", lblMain.getString("noventaDias"));
		 map.put("F", lblMain.getString("fecha"));
		 map.put("P", lblMain.getString("periodo"));
		return map;
	}
	
	/**
	 * usrEstatusMap
	 * @param lblMain
	 * @return Map<String,String>
	 */
	public static Map<String,String> getUsrEstatusMap(ResourceBundle lblMain){
		//usrEstatusMap   Estatus de los usuarios
		Map<String,String> map = new LinkedHashMap<>();
		 map.put("A", lblMain.getString("activo"));
		 map.put("F", lblMain.getString("bloqIntentoFallido"));
		 map.put("B", lblMain.getString("bloqAdministrador"));		 
		 map.put("E", lblMain.getString("bloqBaja"));
		 map.put("N", lblMain.getString("bloqInactivo"));
		 map.put("C", lblMain.getString("bloqPwdCaduco"));
		 map.put("I", lblMain.getString("pwdInicial"));
		return map;
	}
	
	/**
	 * usrRolesMap
	 * @param lblMain
	 * @return Map<String,String>
	 */
	public static Map<String,String> getUsrRolesMap(ResourceBundle lblMain){
		//usrRolesMap  Roles disponibles para asignar directamente a usuarios
		Map<String,String> map = new LinkedHashMap<>();
		//Roles administrativos extra
		 map.put("ROLE_AUTOCOMPLETE_ALL", lblMain.getString("rolAutocompleteAll"));
		//MX
		 map.put("ROLE_CLIENT_MEMBER", lblMain.getString("rolCliente"));
		 map.put("ROLE_ADMIN_MEMBER", lblMain.getString("rolAdministrador"));
		 map.put("ROLE_VENTAS_MEMBER", lblMain.getString("rolVentas"));
		 map.put("ROLE_EJECUTIVO_MEMBER", lblMain.getString("rolEjecutivo"));
		//AR
		 map.put("ROLE_ADMIN_MEMBER_AR", lblMain.getString("rolAdministradorAR"));
		 map.put("ROLE_VENTAS_MEMBER_AR", lblMain.getString("rolVentasAR"));
		 map.put("ROLE_EJECUTIVO_MEMBER_AR", lblMain.getString("rolEjecutivoAR"));
		//PE 
		 map.put("ROLE_ADMIN_MEMBER_PE", lblMain.getString("rolAdministradorPE"));
		 map.put("ROLE_VENTAS_MEMBER_PE", lblMain.getString("rolVentasPE"));
		 map.put("ROLE_EJECUTIVO_MEMBER_PE", lblMain.getString("rolEjecutivoPE"));
		//CA 
		 map.put("ROLE_ADMIN_MEMBER_CA", lblMain.getString("rolAdministradorCA"));
		 map.put("ROLE_VENTAS_MEMBER_CA", lblMain.getString("rolVentasCA"));
		 map.put("ROLE_EJECUTIVO_MEMBER_CA", lblMain.getString("rolEjecutivoCA"));
		//BR 
		 map.put("ROLE_ADMIN_MEMBER_BR", lblMain.getString("rolAdministradorBR"));
		 map.put("ROLE_VENTAS_MEMBER_BR", lblMain.getString("rolVentasBR"));
		 map.put("ROLE_EJECUTIVO_MEMBER_BR", lblMain.getString("rolEjecutivoBR"));
		return map;
	}	
	
	
	
	/*********************************************************************************************************************/
	
}
