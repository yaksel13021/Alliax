package com.alliax.portalclientes.general.xml;

/**
 * Clase para apilcar trasnformaciones con XSLT
 *@author saul.ibarra
 *@fecha: 30-Julio-2013
 * 
 */

import java.io.*;
import java.net.URL;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import org.apache.log4j.Logger;

public class XSLTransformer {
	
	//Variables       
    private final String templatePath = "META-INF/TemplatesXSLT/";
    
    private final static Logger logger = Logger.getLogger(XSLTransformer.class);
    
    /**
     * Constructor
     */
    public XSLTransformer() {
    	    	
    }
   
    
    /**
     * Aplica un template de XSLT a un XML
     * @param template
     * @return
     * @throws TransformerException
     * @throws Exception
     */
   public String doTransformer(String template, String xml) throws TransformerException,Exception {
	   String metodo = "doTransformer ";
	   InputStream is = null;
       try{		   	
		   logger.debug("metodo " + metodo);
		   	
		   logger.trace("xml " + xml);
		   logger.debug("Temp " + this.templatePath + template);
		   
		   URL url = this.getClass().getClassLoader().getResource(this.templatePath + template);
		   logger.debug(url.toExternalForm());
		   		   
		   StringReader readerXML = new StringReader(xml);
	       
		   is = XSLTransformer.class.getResourceAsStream(this.templatePath + template);		  
	        	        	
	       Source xmlSource = new StreamSource(readerXML);	       
	       Source xsltSource = new StreamSource(is,url.toExternalForm());
	
	       Writer w = new StringWriter();
	       StreamResult strm = new StreamResult(w);
	        
	       //Instancia TransformerFactory
	       TransformerFactory transFact = TransformerFactory.newInstance();
	       Transformer trans = transFact.newTransformer(xsltSource);
	       trans.setParameter(OutputKeys.MEDIA_TYPE, "text");
	       trans.setParameter(OutputKeys.ENCODING, "UTF-8");
	       trans.setOutputProperty(OutputKeys.INDENT, "no");
	       trans.transform(xmlSource, strm);
	
	       String strcadena = w.toString();
	        	        
	       logger.debug("Result " + strcadena);
	        
	       return strcadena;
       } catch (Exception e){
    	   logger.fatal(metodo + e.getLocalizedMessage(),e);
    	   throw new Exception("Error al transformar xml " + template + " . Error: " + e.getLocalizedMessage() );
       } finally {
    	   try {
    		   is.close();
    	   } catch(Exception e){}
       }
   }
}
