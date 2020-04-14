/**
 * Clase para representar el comprobante en PDF CFDI 33
 * @author saul.ibarra
 * @Fecha 23-Junio-2017
 * 
 */

package com.alliax.portalclientes.controller;


import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.alliax.portalclientes.general.formato.Fecha;
import com.alliax.portalclientes.general.pdf.Pdf;
import com.alliax.portalclientes.model.EstadoCuenta;
import com.alliax.portalclientes.model.EstadoCuentaDet;
import com.alliax.portalclientes.util.Helper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

@Service("estadoCuentaPdf")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE,proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EstadoCuentaPdf {
	
	//Instancias
	private EstadoCuenta edoCta;
	
	@Autowired
	private Environment formato;
	
	private final static Logger logger = Logger.getLogger(EstadoCuentaPdf.class);
	
	//Desarrollo
	private String logoPath = "";
	private String logo_brPath = "";
									 
	
	public Pdf GenerarPdf(EstadoCuenta edoCta, ResourceBundle lblMain) throws Exception{
		String metodo = "GenerarPdf ";
		logger.info(metodo);
		this.edoCta = edoCta;
		try{
		    //Titulo del Documento
			Pdf pdf = new Pdf();
			
//			int fontSize = 12;
			
			Font fuente10Header = pdf.creaFuente(9);
			fuente10Header.setStyle(Font.BOLD);
			
//			Font fuente9 = pdf.creaFuente(9);
			
			Font fuente9bold = pdf.creaFuente(9);
			fuente9bold.setStyle(Font.BOLD);
			
			Font fuente7bold = pdf.creaFuente(7);
			fuente7bold.setStyle(Font.BOLD);		
			
			Font fuente8 = pdf.creaFuente(8);
			Font fuente8Bold = pdf.creaFuente(8);
			fuente8Bold.setStyle(Font.BOLD);
						
			Font fuente7 = pdf.creaFuente(7);
			Font fuente7Red = pdf.creaFuente(7);
			fuente7Red.setColor(230,46,45);
			Font fuente7BoldRed = pdf.creaFuente(7);
			fuente7BoldRed.setStyle(Font.BOLD);
			fuente7BoldRed.setColor(230,46,45);
			
			Font fuente6 = pdf.creaFuente(6);
			
			Font fuente5 = pdf.creaFuente(5);
			Font fuente5bold = pdf.creaFuente(5);
			fuente5bold.setStyle(Font.BOLD);
					
			Font fuente13bold = pdf.creaFuente(13);
			fuente13bold.setStyle(Font.BOLD);
			
			Font fuente24 = pdf.creaFuente(24);						
			
							 							

		    //Container
		    PdfPTable tblLogoYTitulo = pdf.creaTabla(2,100,Element.ALIGN_CENTER);
		    tblLogoYTitulo.setWidths(new int [] {3,7});
		    PdfPCell celda = new PdfPCell();
		    
		    //Logo		    		    		    		    		    
		    if(edoCta.getPais().equals("BR")){
		    	this.logo_brPath = formato.getProperty("logo_br");
		    	Image logo_br = pdf.insertaImagen(this.logo_brPath, Element.ALIGN_MIDDLE);
		    	celda.addElement(logo_br);
		    }else{
		    	this.logoPath = formato.getProperty("logo");
		    	Image logo = pdf.insertaImagen(this.logoPath, Element.ALIGN_MIDDLE);
		    	celda.addElement(logo);
		    }
		    celda.setBorder(Rectangle.NO_BORDER);
		    celda.setVerticalAlignment(Element.ALIGN_TOP);
		    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tblLogoYTitulo.addCell(celda);
		    

			//Titulo (Estado de cuenta)
			 celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("estadoCuenta"), fuente24));
			 celda.setBorder(Rectangle.NO_BORDER);
			 celda.setPaddingRight(20);
			 celda.setHorizontalAlignment(Element.ALIGN_RIGHT);		     
		    tblLogoYTitulo.addCell(celda);
	    
		    //Agrega de Header a documento
		    pdf.getDocument().add(tblLogoYTitulo);
		    
		    //nombreCliente
		   
		    PdfPTable tblNombreCliente = pdf.creaTabla(1,100,Element.ALIGN_LEFT);
		   		celda = pdf.creaCelda(pdf.creaFrase(edoCta.getNombre() + " " +
		   				                            edoCta.getNombre2() + " " +
		   				                            edoCta.getNombre3() + " " +
		   				                            edoCta.getNombre4() + " "
		   				                            ,fuente7));
		   		celda.setBorder(Rectangle.NO_BORDER);
		   		celda.setPaddingTop(10);
		   		celda.setPaddingLeft(15);
		   		tblNombreCliente.addCell(celda);
		   	pdf.getDocument().add(tblNombreCliente);
		   	
		   	//Container
		   	PdfPTable tblReceptor = pdf.creaTabla(2, 100, Element.ALIGN_LEFT);

	   	
		   	//Celda cierre tabla
		   	celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("cliente"),fuente7bold));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	tblReceptor.addCell(celda);		   	
	
		   	celda = pdf.creaCelda(pdf.creaFrase(edoCta.getNumCliente(),fuente7));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	tblReceptor.addCell(celda);		   	

		   	//RFC etiqueta según el pais
		   	celda = pdf.creaCelda(pdf.creaFrase( Helper.getRFCLabel( edoCta.getPais() ) + " :",fuente7bold));
		   	
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		   	tblReceptor.addCell(celda);

		   	celda = pdf.creaCelda(pdf.creaFrase(edoCta.getRfc(),fuente7));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		   	tblReceptor.addCell(celda);
		   	
		   	celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("telefono"),fuente7bold));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		   	tblReceptor.addCell(celda);

		   	celda = pdf.creaCelda(pdf.creaFrase(edoCta.getTel(),fuente7));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		   	tblReceptor.addCell(celda);

		   	celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("correos"),fuente7bold));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		   	tblReceptor.addCell(celda);

		   	celda = pdf.creaCelda(pdf.creaFrase(edoCta.geteMail(),fuente7));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		   	tblReceptor.addCell(celda);
		   	
		   	if(!edoCta.getPais().equals("BR")){
			   	celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("monedaEdoCta"),fuente7bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblReceptor.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase(edoCta.getMoneda(),fuente7));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblReceptor.addCell(celda);
		   	}		  

		   	//Informacion del comprobante
		    PdfPTable tblInfoComp =  pdf.creaTabla(2,100, Element.ALIGN_LEFT);	    
		   	celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("limiteCredito"),fuente7bold));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
		   	tblInfoComp.addCell(celda);

		   	celda = pdf.creaCelda(pdf.creaFrase( Helper.getMontoFormateado(edoCta.getLimiteCredito(), edoCta.getPais()),fuente7));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		   	tblInfoComp.addCell(celda);
		   	
		   	celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("vencido"),fuente7bold));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		   	tblInfoComp.addCell(celda);

		   	celda = pdf.creaCelda(pdf.creaFrase( Helper.getMontoFormateado( edoCta.getSaldoVencido(), edoCta.getPais())  ,fuente7));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		   	tblInfoComp.addCell(celda);
		   	
		   	celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("vencer") ,fuente7bold));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		   	tblInfoComp.addCell(celda);

		   	celda = pdf.creaCelda(pdf.creaFrase( Helper.getMontoFormateado( edoCta.getSaldoVencer(), edoCta.getPais()),fuente7));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		   	tblInfoComp.addCell(celda);

		   	celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("saldoTotal"),fuente7bold));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		   	tblInfoComp.addCell(celda);
		   	
		   	celda = pdf.creaCelda(pdf.creaFrase( Helper.getMontoFormateado( edoCta.getTotal(), edoCta.getPais()),fuente7));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		   	tblInfoComp.addCell(celda);

		   	celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("disponible"),fuente7bold));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		   	tblInfoComp.addCell(celda);

		   	celda = pdf.creaCelda(pdf.creaFrase( Helper.getMontoFormateado( edoCta.getCreditoDisponible(), edoCta.getPais()),fuente7));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		   	tblInfoComp.addCell(celda);
		   	
		   	celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("fechaCorte"),fuente7bold));
		   	celda.setBorder(Rectangle.TOP);
		   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		   	tblInfoComp.addCell(celda);
		   	
		   	celda = pdf.creaCelda(pdf.creaFrase( Fecha.getFechaFormateada(edoCta.getFechaCorte(), 7 ), fuente7));
		   	celda.setBorder(Rectangle.TOP);
		   	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		   	tblInfoComp.addCell(celda);
		   	
		   	celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("fechaConsulta"),fuente7));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		   	tblInfoComp.addCell(celda);
		   	
		   	celda = pdf.creaCelda(pdf.creaFrase( Fecha.getFechaFormateada(Fecha.getDateActual(), 7 ), fuente7bold));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		   	tblInfoComp.addCell(celda);
		   	
		   	celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("horaConsulta"),fuente7Red));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		   	tblInfoComp.addCell(celda);
		   	
		   	celda = pdf.creaCelda(pdf.creaFrase( Fecha.getFechaFormateada(Fecha.getDateActual(), 3 ), fuente7BoldRed));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		   	tblInfoComp.addCell(celda);

		   	
		   	//Gráfica
		    PdfPTable tblGrafica =  pdf.creaTabla(1,100, Element.ALIGN_CENTER);	    
//		   	celda = pdf.creaCelda(pdf.creaFrase("Saldo ",fuente7));
		   	celda = pdf.creaCelda(pdf.creaFrase("",fuente7));
		   	celda.setBorder(Rectangle.NO_BORDER);
		   	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		   	tblGrafica.addCell(celda);

		   	

//		   	
		   	PdfPTable tblReceptorComprobante = pdf.creaTabla(3, 100, Element.ALIGN_LEFT);
		   	tblReceptorComprobante.setSpacingBefore(10);
//		   	//Tabla Receptor
		   	celda = new PdfPCell();
		   	celda.addElement(tblReceptor);
		   	celda.setBorder(Rectangle.NO_BORDER);		   	
		   	tblReceptorComprobante.addCell(celda);
//		   	//Tabla Comprobante
		   	celda = new PdfPCell();
		   	celda.addElement(tblInfoComp);
		   	celda.setBorder(Rectangle.NO_BORDER);		   	
		   	tblReceptorComprobante.addCell(celda);
//		   	//Tabla Grafica
		   	celda = new PdfPCell();
		   	celda.addElement(tblGrafica);
		   	celda.setBorder(Rectangle.NO_BORDER);		   	
		   	tblReceptorComprobante.addCell(celda);
//
//		    //Agrega tabla a documento
//		    pdf.getDocument().add(tblFirst);	 
//		   	
//		    //Agrega tabla a documento
		    pdf.getDocument().add(tblReceptorComprobante);
		    
		    if( edoCta.getPais().equals("GT") ){
			   	//Datos Bancarios
			   	PdfPTable tblDatosBancarios1 =  pdf.creaTabla(2,100, Element.ALIGN_LEFT);
			   	celda = pdf.creaCelda(pdf.creaFrase("Banco:	",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "G&T CONTINENTAL",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Tipo de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "Monetaria",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Número de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "056-0711000-0",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Nombre de la cuenta:	",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "TINACOS Y TANQUES DE CENTROAMERICA, S.A.",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	PdfPTable tblDatosBancarios2 =  pdf.creaTabla(2,100, Element.ALIGN_LEFT);
			   	celda = pdf.creaCelda(pdf.creaFrase("Banco:	",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "BANRURAL",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Tipo de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "Monetaria",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Número de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "3-137-06717-5",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Nombre de la cuenta:	",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "TINACOS Y TANQUES DE CENTROAMERICA, S.A.",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);
			   	
			   	PdfPTable tblContainerDatosBancarios = pdf.creaTabla(2, 50, Element.ALIGN_LEFT);
			   	tblReceptorComprobante.setSpacingBefore(10);
			   	 celda = new PdfPCell();
			   	 celda.addElement(tblDatosBancarios1);
			   	 celda.setBorder(Rectangle.RIGHT);	   	
			   	tblContainerDatosBancarios.addCell(celda);
			   	 celda = new PdfPCell();
			   	 celda.addElement(tblDatosBancarios2);
			   	celda.setBorder(Rectangle.NO_BORDER);	   	
			   	tblContainerDatosBancarios.addCell(celda);
			   	
			   	pdf.getDocument().add(tblContainerDatosBancarios);
			   	
		    }if( edoCta.getPais().equals("SV") ){
			   	//Datos Bancarios
			   	PdfPTable tblDatosBancarios1 =  pdf.creaTabla(2,100, Element.ALIGN_LEFT);
			   	celda = pdf.creaCelda(pdf.creaFrase("Banco:	",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "BANCO DE AMERICA CENTRAL, S.A.",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Tipo de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "Corriente",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Número de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "200083269",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Nombre de la cuenta:	",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "TINACOS Y TANQUES DE CENTROAMERICA, S.A. DE C.V.",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	PdfPTable tblDatosBancarios2 =  pdf.creaTabla(2,100, Element.ALIGN_LEFT);
			   	celda = pdf.creaCelda(pdf.creaFrase("",fuente5bold));		   	
			   	tblDatosBancarios2.addCell(celda);			   	
			   	
			   	PdfPTable tblContainerDatosBancarios = pdf.creaTabla(1, 50, Element.ALIGN_LEFT);
			   	tblReceptorComprobante.setSpacingBefore(10);
			   	 celda = new PdfPCell();
			   	 celda.addElement(tblDatosBancarios1);
			   	 celda.setBorder(Rectangle.NO_BORDER);	   	
			   	tblContainerDatosBancarios.addCell(celda);
			   	 celda = new PdfPCell();
			   	 celda.addElement(tblDatosBancarios2);
			   	celda.setBorder(Rectangle.NO_BORDER);	   	
			   	tblContainerDatosBancarios.addCell(celda);
			   	
			   	pdf.getDocument().add(tblContainerDatosBancarios);
		    }if( edoCta.getPais().equals("HN") ){
			   	//Datos Bancarios
			   	PdfPTable tblDatosBancarios1 =  pdf.creaTabla(2,100, Element.ALIGN_LEFT);
			   	celda = pdf.creaCelda(pdf.creaFrase("Banco:	",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "BANCO DE OCCIDENTE, S.A.",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Tipo de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "Corriente Lempiras",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Número de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "11-424-000091-5",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Nombre de la cuenta:	",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "TINACOS Y TANQUES DE HONDURAS, S.A.",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	PdfPTable tblDatosBancarios2 =  pdf.creaTabla(2,100, Element.ALIGN_LEFT);
			   	celda = pdf.creaCelda(pdf.creaFrase("Banco:	",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "BANCO DE OCCIDENTE, S.A.",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Tipo de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "Corriente Dólares",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Número de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "12-424-000004-1",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Nombre de la cuenta:	",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( " TINACOS Y TANQUES DE HONDURAS, S.A.",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);
			   	
			   	PdfPTable tblContainerDatosBancarios = pdf.creaTabla(2, 50, Element.ALIGN_LEFT);
			   	tblReceptorComprobante.setSpacingBefore(10);
			   	 celda = new PdfPCell();
			   	 celda.addElement(tblDatosBancarios1);
			   	 celda.setBorder(Rectangle.RIGHT);	   	
			   	tblContainerDatosBancarios.addCell(celda);
			   	 celda = new PdfPCell();
			   	 celda.addElement(tblDatosBancarios2);
			   	celda.setBorder(Rectangle.NO_BORDER);	   	
			   	tblContainerDatosBancarios.addCell(celda);
			   	
			   	pdf.getDocument().add(tblContainerDatosBancarios);
		    }if( edoCta.getPais().equals("NI") ){
			   	//Datos Bancarios
			   	PdfPTable tblDatosBancarios1 =  pdf.creaTabla(2,100, Element.ALIGN_LEFT);
			   	celda = pdf.creaCelda(pdf.creaFrase("Banco:	",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "BANCO DE LA PRODUCCION, S.A.",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Tipo de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "Corriente Córdobas",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Número de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "10010002781066",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Nombre de la cuenta:	",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "TINACOS Y TANQUES DE NICARAGUA, S.A. (TITANSA)",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	PdfPTable tblDatosBancarios2 =  pdf.creaTabla(2,100, Element.ALIGN_LEFT);
			   	celda = pdf.creaCelda(pdf.creaFrase("Banco :	",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "BANCO DE LA PRODUCCION, S.A.",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Tipo de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "Corriente Dólares",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Número de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "10010012781006",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Nombre de la cuenta:	",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "TINACOS Y TANQUES DE NICARAGUA, S.A. (TITANSA)",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);
			   	
			   	PdfPTable tblContainerDatosBancarios = pdf.creaTabla(2, 50, Element.ALIGN_LEFT);
			   	tblReceptorComprobante.setSpacingBefore(10);
			   	 celda = new PdfPCell();
			   	 celda.addElement(tblDatosBancarios1);
			   	 celda.setBorder(Rectangle.RIGHT);	   	
			   	tblContainerDatosBancarios.addCell(celda);
			   	 celda = new PdfPCell();
			   	 celda.addElement(tblDatosBancarios2);
			   	celda.setBorder(Rectangle.NO_BORDER);	   	
			   	tblContainerDatosBancarios.addCell(celda);
			   	
			   	pdf.getDocument().add(tblContainerDatosBancarios);
		    }if( edoCta.getPais().equals("CR") ){
			   	//Datos Bancarios
			   	PdfPTable tblDatosBancarios1 =  pdf.creaTabla(2,100, Element.ALIGN_LEFT);
			   	celda = pdf.creaCelda(pdf.creaFrase("Banco:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "BANCO NACIONAL DE COSTA RICA",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Tipo de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "Corriente Colones",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Número de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "100-01-000-218792-5",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Cuenta Cliente (sistema SINPE):",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "15100010012187921",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);			   	
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Nombre de la cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "TANQUES PLASTICOS S.A.",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);

			   	celda = pdf.creaCelda(pdf.creaFrase("IBAN:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios1.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "CR67 0151 0001 0012 1879 21",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios1.addCell(celda);			   	
			   	
			   	PdfPTable tblDatosBancarios2 =  pdf.creaTabla(2,100, Element.ALIGN_LEFT);
			   	celda = pdf.creaCelda(pdf.creaFrase("Banco:	",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "BANCO NACIONAL DE COSTA RICA",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Tipo de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "Corriente Dólares",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Número de cuenta:",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "100-02-146-600150-8",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Cuenta Cliente (sistema SINPE):",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "15114610026001509",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);			   	
			   	
			   	celda = pdf.creaCelda(pdf.creaFrase("Nombre de la cuenta:	",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "TANQUES PLASTICOS S.A.",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);

			   	celda = pdf.creaCelda(pdf.creaFrase("IBAN:	",fuente5bold));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);		   	
			   	tblDatosBancarios2.addCell(celda);
			   	celda = pdf.creaCelda(pdf.creaFrase( "CR15 0151 1461 0026 0015 09",fuente5));
			   	celda.setBorder(Rectangle.NO_BORDER);
			   	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			   	tblDatosBancarios2.addCell(celda);			   	
			   	
			   	PdfPTable tblContainerDatosBancarios = pdf.creaTabla(2, 55, Element.ALIGN_LEFT);
			   	tblReceptorComprobante.setSpacingBefore(10);
			   	 celda = new PdfPCell();
			   	 celda.addElement(tblDatosBancarios1);
			   	 celda.setBorder(Rectangle.RIGHT);	   	
			   	tblContainerDatosBancarios.addCell(celda);
			   	 celda = new PdfPCell();
			   	 celda.addElement(tblDatosBancarios2);
			   	celda.setBorder(Rectangle.NO_BORDER);	   	
			   	tblContainerDatosBancarios.addCell(celda);
			   	
			   	pdf.getDocument().add(tblContainerDatosBancarios);
		    }


/***
 * 
 * INICIO TABLA CONCEPTOS
 * 	    
 */


		    int padding = 3;		    		    		    	   
		    int borderLeft, borderRight;
			borderLeft = Rectangle.LEFT;
			borderRight = Rectangle.LEFT | Rectangle.RIGHT;
		

			
           /***************************************Encabezado del PDF dependiendo el país*************************************************/
		    //Tabla de Registros
			int columnas = 12;		    
		    if( null != edoCta.getPais() && edoCta.getPais().equals("AR") ){
		    	columnas = 8;
		    }else if( null != edoCta.getPais() && edoCta.getPais().equals("PE") ){
		    	columnas = 10;
		    }else if( null != edoCta.getPais() && edoCta.getPais().equals("GT") ){
		    	columnas = 11;
		    }else if( null != edoCta.getPais() && (edoCta.getPais().equals("SV") || edoCta.getPais().equals("HN") || edoCta.getPais().equals("NI") ) ){
		    	columnas = 9;
		    }else if( null != edoCta.getPais() && edoCta.getPais().equals("CR") ){
		    	columnas = 9;
		    }else if( null != edoCta.getPais() && edoCta.getPais().equals("BR") ){
		    	columnas = 9;
		    }
		    		    
			
		    PdfPTable det = pdf.creaTabla(columnas, 100, Element.ALIGN_CENTER);	    		    
		    if( null != edoCta.getPais() && edoCta.getPais().equals("AR") ){
		    	//8 Columnas
//		    	det.setWidths(new float [] {(float)1,(float)2.7,(float)2.7,(float).8,(float).8,(float).4,(float).9,(float).7});
		    	det.setWidths(new float [] {(float)1.8,(float)1.8,(float)1.9,(float)1,(float)1,(float)1,(float)1.2,(float)1.2});
		    }else if( null != edoCta.getPais() && edoCta.getPais().equals("PE") ){
		    	//10 Columnas
		    	det.setWidths(new float [] {(float).9,(float)1.4,(float)1.2, (float).9, (float)2,(float).7,(float).7,(float).4,(float).9,(float).7});
		    }else if( null != edoCta.getPais() && edoCta.getPais().equals("GT") ){
		    	//11 Columnas
		    	det.setWidths(new float [] {(float).9,(float).9,(float).9, (float).9, (float)2.7,(float).9,(float).7,(float)1,(float).5,(float).7, (float).7});
		    }else if( null != edoCta.getPais() && (edoCta.getPais().equals("SV") || edoCta.getPais().equals("HN") || edoCta.getPais().equals("NI") ) ){
		    	//9 columnas
		    	det.setWidths(new float [] {(float).9,(float).9,(float)1.2, (float).9, (float)1.1,(float).8,(float).7,(float)1.1,(float).9});
		    }else if( null != edoCta.getPais() && edoCta.getPais().equals("CR") ){
		    	//9 Columnas
		    	det.setWidths(new float [] {(float).8,(float)1,(float)1, (float)1.5, (float).6,(float).7,(float).6,(float).7,(float).7});		    
		    }else if( null != edoCta.getPais() && edoCta.getPais().equals("BR") ){
		    	//9 Columnas
		    	det.setWidths(new float [] {(float).8,(float)1,(float)1, (float)1.5, (float).6,(float).7,(float).6,(float).7,(float).7});		    
		    }else{		  		    	
		    	//12 Columnas
		    	det.setWidths(new float [] {(float).9,(float).9,(float)1.2, (float).9, (float)2.7, (float).9, (float).9,(float).7,(float).7,(float).4,(float).9,(float).7});
		    }
		    
		    
		    det.setSpacingBefore(10);
//		    int padding = 3;
		    
		    if( null != edoCta.getPais() && edoCta.getPais().equals("AR") ){
			    //Tipo documento
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("tipoDocumento"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //No. Factura Fiscal
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("factFiscal"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //Remito
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("remito"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    
			    //Fecha factura
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("fechaFactura"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);			    		    
			    //Vencimiento
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("vencimiento"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);	  
			    //Dias mora
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("diasMora"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);	
			    //Importe
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("importe"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);			    
			    //Estatus
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("estatus"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    
		    }else if( null != edoCta.getPais() && edoCta.getPais().equals("PE") ){
			    //Tipo documento
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("tipoDocumento"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //No. Factura
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("numeroFactura"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //No. Letra
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("numeroLetra"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //Orden de Compra
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("pedido"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //Numero unico
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("numeroUnico"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);			    
			    
			    //Fecha factura
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("fechaFactura"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);			    		    
			    //Vencimiento
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("vencimiento"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);	  
			    //Dias mora
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("diasMora"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);	
			    //Importe
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("importe"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);			    
			    //Estatus
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("estatus"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
		    }else if( null != edoCta.getPais() && edoCta.getPais().equals("GT") ){
			    //Tipo documento
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("tipoDocumento"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //Orden de compra
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("pedido"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);			    
			    //No.pedido
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("noPedido"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //Nota de entrega
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("notaEntrega"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);	
			    //Número de Autorización SAT
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("numeroAutorizacionSAT"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);			   
			    //No. Factura Fiscal
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("factSAP"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    
			    //Fecha factura
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("fechaFactura"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);			    		    
			    //Vencimiento
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("vencimiento"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);	  
			    //Dias mora
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("diasMora"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);	
			    //Importe
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("importe"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);			    
			    //Estatus
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("estatus"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);		    	

		    }else if( null != edoCta.getPais() && edoCta.getPais().equals("SV") || null != edoCta.getPais() && edoCta.getPais().equals("HN") || null != edoCta.getPais() && edoCta.getPais().equals("NI") ){
			    //Tipo documento
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("tipoDocumento"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //Orden de compra
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("pedido"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //No. pedido
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("noPedido"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //No. Factura fiscal
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("factFiscal"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);				    
			    
			    //Fecha factura
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("fechaFactura"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);			    		    
			    //Vencimiento
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("vencimiento"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);	  
			    //Dias mora
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("diasMora"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);	
			    //Importe
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("importe"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);			    
			    //Estatus
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("estatus"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);		  		    	
		    }else if( null != edoCta.getPais() && edoCta.getPais().equals("CR") ){
			    //Tipo documento
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("tipoDocumento"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //Orden de compra
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("pedido"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //No. Factura SAP
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("noPedido"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);	    
			    //No. Factura Fiscal
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("factFiscal"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    
			    //Fecha factura
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("fechaFactura"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);			    		    
			    //Vencimiento
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("vencimiento"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);	  
			    //Dias mora
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("diasMora"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);	
			    //Importe
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("importe"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);			    
			    //Estatus
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("estatus"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);		    	
		    }else if( null != edoCta.getPais() && edoCta.getPais().equals("BR") ){
			    //Tipo documento
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("tipoDocumento"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //Orden de compra
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("pedido"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //No. Pedido
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("noPedido"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);	    
			    //Nota de Fiscal
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("factFiscalBR"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    
			    //Fecha factura
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("fechaFactura"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);			    		    
			    //Vencimiento
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("vencimiento"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);	  
			    //Dias mora
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("diasMora"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);	
			    //Importe
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("importe"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);			    
			    //Estatus
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("estatus"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);		    	
		    }else{
		    	//El layout por default es el de MX
			    //Tipo documento
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("tipoDocumento"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //No. pedido
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("noPedido"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //Orden de compra
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("pedido"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //No. Factura SAP
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("factSAP"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //No. Factura Fiscal
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("factFiscal"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //Factura Relacionada
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("facturaRelacionada"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    //UUID Relacionado
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("UUIDRelacionado"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    
			    //Fecha factura
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("fechaFactura"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);			    		    
			    //Vencimiento
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("vencimiento"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);	  
			    //Dias mora
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("diasMora"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);	
			    //Importe
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("importe"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);			    
			    //Estatus
			    celda = pdf.creaCelda(pdf.creaFrase(lblMain.getString("estatus"), fuente7bold));
			    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    celda.setPaddingTop(padding);
			    celda.setPaddingBottom(padding);
			    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    det.addCell(celda);
			    			    
		    }
		    			    
		    
		    //Footer
		    for(int f = 1; f<=columnas;f++){
		    	celda = pdf.creaCelda(pdf.creaFrase("", fuente7));
		    	if(f<8){
		    		celda.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
		    	} else{
		    		celda.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
		    	}
			    det.addCell(celda);
		    }
	    
		    det.setHeaderRows(2);
		    det.setFooterRows(1);		    
		    	    
			borderLeft = Rectangle.LEFT;
			borderRight = Rectangle.LEFT | Rectangle.RIGHT;
		
			/**********************************************Detalle del PDF*****************************************************************/
		    for(int s = 0; s < edoCta.getDetalle().size(); s++){
		    			    	
		    	EstadoCuentaDet detalle = edoCta.getDetalle().get(s);
		    	if( null != edoCta.getPais() && edoCta.getPais().equals("AR") ){
		    		
		    		//Tipo documento
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getTipoDocumento(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//Factura Fiscal			    	
			    	celda =  pdf.creaCelda(pdf.creaFrase(detalle.getNotaEntrega(), fuente6));		    		
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//No Remito
			    	celda =  pdf.creaCelda(pdf.creaFrase(detalle.getNoFactFiscal(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	
			    	
			    	//Fecha factura
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getFechaFactura(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Vencimiento
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getFechaVenc(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);
			    	//Días mora
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getDiasMora(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Importe
			    	celda = pdf.creaCelda(pdf.creaFrase(Helper.getMontoFormateado( detalle.getImporte(), edoCta.getPais()), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Estatus
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getEstatus(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);
		    		
		    	}else if( null != edoCta.getPais() && edoCta.getPais().equals("PE") ){
		    		//Tipo documento
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getTipoDocumento(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//No. Factura
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getNotaEntrega(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//No. Letra
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getNoPedido(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//Orden de compra
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getOrdenCompra(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//Numero unico
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getNoFactFiscal(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
		    		
		    		
			    	//Fecha factura
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getFechaFactura(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Vencimiento
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getFechaVenc(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);
			    	//Días mora
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getDiasMora(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Importe
			    	celda = pdf.creaCelda(pdf.creaFrase(Helper.getMontoFormateado( detalle.getImporte(), edoCta.getPais()), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Estatus
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getEstatus(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);
		    	}else if( null != edoCta.getPais() && edoCta.getPais().equals("GT") ){
		    		//Tipo documento
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getTipoDocumento(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//Orden de Compra
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getOrdenCompra(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//No. pedido
			    	celda =  pdf.creaCelda(pdf.creaFrase(detalle.getNoPedido(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//Nota de entrega
			    	celda =  pdf.creaCelda(pdf.creaFrase(detalle.getEntrega(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);			    	
			    	//Numero de Autorizacion SAT			    			    	
			    	celda =  pdf.creaCelda(pdf.creaFrase(detalle.getNoFactFiscal(), fuente6));		    				    	
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//Factura Fiscal			    			    	
			    	celda =  pdf.creaCelda(pdf.creaFrase(detalle.getNoFactura(), fuente6));		    				    	
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);			    	
			    				    	
			    	//Fecha factura
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getFechaFactura(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Vencimiento
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getFechaVenc(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);
			    	//Días mora
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getDiasMora(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Importe
			    	celda = pdf.creaCelda(pdf.creaFrase(Helper.getMontoFormateado( detalle.getImporte(), edoCta.getPais()), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Estatus
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getEstatus(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    		
		    	}else if( null != edoCta.getPais() && edoCta.getPais().equals("SV") || null != edoCta.getPais() && edoCta.getPais().equals("HN") || null != edoCta.getPais() && edoCta.getPais().equals("NI") ){
		    		//Tipo documento
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getTipoDocumento(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//Orden de Compra
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getOrdenCompra(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//No. pedido
			    	celda =  pdf.creaCelda(pdf.creaFrase(detalle.getNoPedido(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);			    	
			    	//Factura Fiscal			    			    	
			    	celda =  pdf.creaCelda(pdf.creaFrase(detalle.getNotaEntrega(), fuente6));		    				    	
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);		    	
			    				    	
			    	//Fecha factura
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getFechaFactura(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Vencimiento
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getFechaVenc(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);
			    	//Días mora
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getDiasMora(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Importe
			    	celda = pdf.creaCelda(pdf.creaFrase(Helper.getMontoFormateado( detalle.getImporte(), edoCta.getPais()), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Estatus
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getEstatus(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);				    		
		    	}else if( null != edoCta.getPais() && edoCta.getPais().equals("CR") ){
		    		//Tipo documento
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getTipoDocumento(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//Orden de Compra
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getOrdenCompra(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//No. pedido
			    	celda =  pdf.creaCelda(pdf.creaFrase(detalle.getNoPedido(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);		    	
			    	//Factura Fiscal			    			    	
			    	celda =  pdf.creaCelda(pdf.creaFrase(detalle.getNoFactFiscal(), fuente6));		    				    	
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);			    	
			    				    	
			    	//Fecha factura
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getFechaFactura(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Vencimiento
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getFechaVenc(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);
			    	//Días mora
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getDiasMora(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Importe
			    	celda = pdf.creaCelda(pdf.creaFrase(Helper.getMontoFormateado( detalle.getImporte(), edoCta.getPais()), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Estatus
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getEstatus(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);				    		
		    	}else if( null != edoCta.getPais() && edoCta.getPais().equals("BR") ){
		    		//Tipo documento
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getTipoDocumento(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//Orden de Compra
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getOrdenCompra(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//No. pedido
			    	celda =  pdf.creaCelda(pdf.creaFrase(detalle.getNoPedido(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);		    	
			    	//Factura Fiscal			    			    	
			    	celda =  pdf.creaCelda(pdf.creaFrase(detalle.getNotaEntrega(), fuente6));		    				    	
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);			    	
			    				    	
			    	//Fecha factura
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getFechaFactura(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Vencimiento
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getFechaVenc(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);
			    	//Días mora
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getDiasMora(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Importe
			    	celda = pdf.creaCelda(pdf.creaFrase(Helper.getMontoFormateado( detalle.getImporte(), edoCta.getPais()), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Estatus
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getEstatus(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);				    		
		    	}else{	    		
		    		//Tipo documento
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getTipoDocumento(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//No. Pedido
			    	celda =  pdf.creaCelda(pdf.creaFrase(detalle.getNoPedido(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//Orden de Compra
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getNotaEntrega(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//Factura SAP
			    	celda =  pdf.creaCelda(pdf.creaFrase(detalle.getNoFactura(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//Factura Fiscal			    			    	
			    	celda =  pdf.creaCelda(pdf.creaFrase(detalle.getNoFactFiscal(), fuente6));		    				    	
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//Factura Relacionada			    			    	
			    	celda =  pdf.creaCelda(pdf.creaFrase(detalle.getFacturaRelacionada(), fuente6));		    				    	
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);
			    	//UUID Relacionado			    			    	
			    	celda =  pdf.creaCelda(pdf.creaFrase(detalle.getUUIDRelacionado(), fuente6));		    				    	
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setBorder(borderLeft);
			    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	det.addCell(celda);			    	
			    				    	
			    	//Fecha factura
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getFechaFactura(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Vencimiento
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getFechaVenc(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);
			    	//Días mora
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getDiasMora(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Importe
			    	celda = pdf.creaCelda(pdf.creaFrase(Helper.getMontoFormateado( detalle.getImporte(), edoCta.getPais()), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);		    	
			    	//Estatus
			    	celda = pdf.creaCelda(pdf.creaFrase(detalle.getEstatus(), fuente6));
				    celda.setPaddingTop(padding);
				    celda.setPaddingBottom(padding);
			    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	celda.setBorder(borderRight);
			    	det.addCell(celda);
		    	}
		    }
		    		
		    	
	    	    
		    //Agrega tabla a documento 
		    pdf.getDocument().add(det);	    
/***********************************************************************/
		    		    			       		   
		    padding = 10;  
		    pdf.grabaPdf();
		    
		    return pdf;  
		} catch(Exception e){
			logger.error(metodo + e.toString());
			throw new Exception(e.getLocalizedMessage());
		}
	}
	
	
	/**
	 * Regresa el array de bytes del pdf
	 * @return
	 */
	public byte[] getPdfOutput(Pdf pdf){
		return pdf.getPdfOutput().toByteArray();
	}	
}
