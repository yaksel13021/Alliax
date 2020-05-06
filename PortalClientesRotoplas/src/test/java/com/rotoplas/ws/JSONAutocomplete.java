package com.rotoplas.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ListUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.alliax.portalclientes.util.Helper;
import com.alliax.portalclientes.util.JSONCliente;

public class JSONAutocomplete {
	
	public static void main(String[] args) {
		System.out.println( toJSON() );
	}
	
	public static String toJSON(){
		List<JSONCliente> list = new ArrayList<>();
		 list.add( new JSONCliente("98123","Home Depot Mexico S.A. de C.V","MX") );
		 list.add(new JSONCliente("32409","Ferretería del Estado de México","MX"));
	     list.add(new JSONCliente("98723","Tienda Importante a nivel Nacional","MX"));
	     List<JSONCliente> list2 = new ArrayList<>();
	     list2.add( new JSONCliente("98123","Home2 Depot Mexico S.A. de C.V","MX") );
	     List<JSONCliente> list3 = new ArrayList<>();
	     list3.add( new JSONCliente("98123","Home3 Depot Mexico S.A. de C.V","MX") );
	     
	     list = ListUtils.union(list, list2);
	     list = ListUtils.union(list, list3);
	     
	     JSONArray json = new JSONArray( list );
	     
	    
	    try {
//			System.out.println( Helper.readJSONClienteList("PE") );
//			Helper.writeJSONClienteList("algo que escribir pais CA", "CA");
//			System.out.println( Helper.isTimeToReloadJSONfiles() );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return json.toString();
	}
	
}
