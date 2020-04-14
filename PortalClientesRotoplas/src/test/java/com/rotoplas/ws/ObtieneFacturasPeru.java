package com.rotoplas.ws;

import org.datacontract.schemas._2004._07.tt_eol_level_be.RPTADOCTRIBBE;

public class ObtieneFacturasPeru {

	
	public static void main(String[] args) {
		
		
		  
//		String usuario = "demo@dalka.eol.pe"; //QAS
//		String password = "abc123abc"; //QAS 
		String usuario = "oper@dalka.eol.pe";  // PRD
		String password = "2f3c2df7";  //PRD
		String ruc = "20389748669";
//		String tipoComprobante = st.nextToken();  
//		String serie = st.nextToken();
//		String correlativo = st.nextToken();
		String tipoComprobante = "01";  
		String serie = "FA01";
		String correlativo = "00102666";
					
		org.tempuri.IwsOnlineToCPE serviceFacturas = new org.tempuri.WsOnlineToCPE().getBasicHttpBindingIwsOnlineToCPE();		 
		RPTADOCTRIBBE response = serviceFacturas.callExtractCPE(usuario, 
													            password, 
													            ruc, 
													            tipoComprobante, 
													            serie,	 
													            correlativo, 
													            true, 
													            true, 
													            false);		
		
		byte[] test = response.getDOCTRIBPDF().getValue();
		
		System.out.println( test.length );
		System.out.println( response.getDOCTRIBXML().getValue().length() > 0 ? "Encontrada" : "No Encontrada"  );
		
		System.out.println( response.getDOCTRIBXML().getValue() );
		System.out.println( response.getCODRPTA().getValue() );
		System.out.println( response.getDESCRIPCION().getValue() );
		
		
	}
	
}
