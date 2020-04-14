package com.rotoplas.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alliax.portalclientes.controller.EstadoCuentaPdf;
import com.alliax.portalclientes.model.EstadoCuenta;
import com.alliax.portalclientes.model.SociedadClienteCorreoCargaMasiva;
import com.alliax.portalclientes.util.EnviaCorreoPDFUtil;

public class DeserializePDFObject {

	
	public static void main(String[] args) {
		System.out.println("main");
		  EstadoCuenta edoCta;
	      try {
	          FileInputStream fileIn = new FileInputStream("C:\\temp\\EstadoCuenta.ser");
	          ObjectInputStream in = new ObjectInputStream(fileIn);
	          edoCta = (EstadoCuenta) in.readObject();
	          in.close();
	          fileIn.close();
	       } catch (IOException i) {
	          i.printStackTrace();
	          return;
	       } catch (ClassNotFoundException c) {
	          System.out.println("Employee class not found");
	          c.printStackTrace();
	          return;
	       }
	      	      
	      System.out.println( edoCta.getNumCliente() );
	      //ApplicationContext springContext = ContextLoader.getCurrentWebApplicationContext();
	      //WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();	      
	       SociedadClienteCorreoCargaMasiva sccm = new SociedadClienteCorreoCargaMasiva();
	       sccm.setChecked(true);
	       sccm.setCorreo("daniel.galindo@alliax.com");
	       sccm.setSociedad("SS02");
	      
	       String fechaCorte = new SimpleDateFormat("yyyyMMdd").format(new Date());
	       //EstadoCuentaPdf comppdf = springContext.getBean("estadoCuentaPdf",EstadoCuentaPdf.class);
	       EstadoCuentaPdf comppdf = new EstadoCuentaPdf();
	       
			String baseName = "com.alliax.portalclientes.locale.Labels";
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			
	       ResourceBundle rb = ResourceBundle.getBundle(baseName, new Locale("pt"), loader);
	       try{
	    	   
	    	   ByteArrayOutputStream baos = comppdf.GenerarPdf(edoCta, rb).getPdfOutput();
	    	   FileUtils.writeByteArrayToFile(new File("C:\\temp\\EstadoCuenta_"+edoCta.getNumCliente()+".pdf"), baos.toByteArray());
	       }catch(Exception e){
	    	   e.printStackTrace();
	       }
	       
	      
	}
	
}
