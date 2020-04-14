/**
 * Clase para Generar archivos PDF
 *
 * @author saul.ibarra
 * @Fecha 21-Julio-2013
 *
 */

package com.alliax.portalclientes.general.pdf;
//import com.alliax.portalcrp.controller.ConstructEmail;
import com.alliax.portalclientes.general.pdf.HeaderFooter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Font.FontFamily;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.awt.Color;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;
import javax.activation.URLDataSource;

import org.apache.log4j.Logger;


import com.itextpdf.text.DocumentException;


public class Pdf {
	//Instancias
	private final static Logger logger = Logger.getLogger(Pdf.class);
			
	
	//variables
    private Document document;
    private Paragraph parrafo1;	
    private String fileName;
    private int fontType = 0;
    
    private String filePath;

    private String docsPath;

    private String imgPath;

    private ByteArrayOutputStream pdfOutput;
    
    //Metodo Set 
    private void setPath(String path){
        this.filePath   =  path + "archivos/temp/";
        this.docsPath   =  path + "";
        this.imgPath    =  path + "images/";
    }
    
    public ByteArrayOutputStream getPdfOutput(){
    	return this.pdfOutput;
    }
    
    
    /**
     * Constructor
     * @throws DocumentException 
     */
    public Pdf() throws DocumentException{
        //Establece rutas
        //this.setPath(path);    
    	logger.info("Pdf");
    	this.pdfOutput = new ByteArrayOutputStream();

        this.document = new Document(PageSize.A4, 10, 10, 10, 30);
        
        PdfWriter writer = PdfWriter.getInstance(this.document,this.pdfOutput);
        
        //limites de la pagina
        Rectangle art = new Rectangle(50, 50, 545, 792);
        writer.setBoxSize("art", art);
        
        //Eventos para Footer y Header
        writer.setPageEvent(new HeaderFooter());

        this.document.open();    	            	    	        
    }

    /**
     * Constructor
     * @param filename
     * @param path
     * @throws FileNotFoundException
     * @throws Exception
     */
    /*public Pdf(String filename, String path) throws FileNotFoundException,Exception{
        //Establece rutas
        this.setPath(path);
        
        this.fileName = filename + ".pdf";

        this.document = new Document(PageSize.A4, 40, 10, 40, 10);
        PdfWriter writer = PdfWriter.getInstance(this.document, 
        			new FileOutputStream(this.docsPath + this.fileName));
        this.document.open();
    }*/

    /**
     * Establece la fuente que se utilizara en el documento.
     */
    public void setFontType(int fontType){
        this.fontType = fontType;
    }
    
    
    /**
     * Establece la fuente del documento, por default va la Helvetica
     */
    private FontFamily getFontType(int fontType ){
        
        switch(fontType){
            case 1: return FontFamily.HELVETICA;
            case 2: return FontFamily.TIMES_ROMAN;
            case 3: return FontFamily.COURIER;            
        }
        
        return FontFamily.HELVETICA;
    }


    /**
     * Crea un parrafo
     * @param text
     * @param fontSize
     * @param Style Font.constant
     * @param align Element.constant
     * @param spaceAfter
     * @param indentLeft
     * @param color
     * @return
     * @throws FileNotFoundException
     * @throws Exception
     */
    public Paragraph creaParrafo(String text, int fontSize, int Style, int align, int spaceAfter, int indentLeft, BaseColor color ) throws FileNotFoundException,Exception {        

        Paragraph parrafo = new Paragraph(text,
        			new Font(this.getFontType(this.fontType), fontSize, Style,color));

        parrafo.setAlignment(align);
        parrafo.setIndentationLeft(indentLeft);
        
        parrafo.setSpacingAfter(spaceAfter);
        parrafo.setSpacingBefore(spaceAfter);

        return parrafo;
    }
    
   
    /**
     * Escribe un parrafo en el PDF     
     * @param align Element.constant
     * @param spaceAfter
     * @return
     * @throws FileNotFoundException
     * @throws Exception
     */
    public Paragraph creaParrafo(int align, int spaceAfter) throws FileNotFoundException,Exception {
    	return this.creaParrafo(align, spaceAfter, spaceAfter);                
    }
    
    /**
     * Escribe un parrafo en el PDF     
     * @param align Element.constant
     * @param spaceBefore
     * @param spaceAfter
     * @return
     * @throws FileNotFoundException
     * @throws Exception
     */
    public Paragraph creaParrafo(int align, int spaceBefore, int spaceAfter) throws FileNotFoundException,Exception {

    	Paragraph parrafo = new Paragraph();

    	parrafo.setAlignment(align);

    	parrafo.setSpacingAfter(spaceBefore);
    	parrafo.setSpacingBefore(spaceAfter);
        
        return parrafo;
    }    

    /*
     * Agrega un pedaso de texto al parrafo creado con this.creaParrafo
     * @texto Texto a agregar
     * @fontSize tamaño de letra
     * @Style Estilo del parrafo... 0.NORMAL 1.BOLD 2.ITALIC 3.BOLDITALIC 4.UNDERLINE 5. BOLDUNDERLINE
     */
    /*public void construyeParrafo(String texto,int fontSize, int Style){
        if(this.parrafo1 != null) {
            Chunk name = new Chunk(texto, new Font(this.getFontType(this.fontType), fontSize, Style, new BaseColor(0,0,0)));
            //name.setUnderline(0.2f, -2f);
            this.parrafo1.add(name);
        }
    }*/

    /*
     * Finaliza parrafo creado con this.creaParrafo
     */
    /*public void terminaParrafo() throws DocumentException{
        this.document.add(this.parrafo1);

        this.parrafo1 = null;
    }*/

    
    /**
     * Escribe una Frase en el PDF, Es como un span...
     * @param frase
     * @param fuente
     * @return
     * @throws FileNotFoundException
     * @throws Exception
     */
    public Phrase creaFrase(String frase, Font fuente)throws FileNotFoundException,Exception {
        Phrase phrase = new Phrase();
        Chunk name = new Chunk(frase, fuente);
        //name.setUnderline(0.2f, -2f);
        phrase.add(name);
        
                
        return phrase;
    }

    
    /*
     * Escribe una seccion en el PDF
     * @text Texto del parrafo
     * @fontSize Tamaño de letra
     * @Style Estilo del parrafo... 0.NORMAL 1.BOLD 2.ITALIC 3.BOLDITALIC 4.UNDERLINE 5. BOLDUNDERLINE
     * @align Alineacion del parrafo 0.ALIGN_LEFT 1.ALIGN_CENTER 2.ALIGN_RIGHT 3.ALIGN_JUSTIFIED 4.ALIGN_JUSTIFIED_ALL
     */
    /*public void escribeSeccion(String text, int fontSize, int Style, int align) throws FileNotFoundException,Exception {

        //this.document.add(new Paragraph(text));

        Paragraph parrafo = new Paragraph(text, new Font(this.getFontType(this.fontType), fontSize, Style, new BaseColor(0, 0, 0)));
        parrafo.setAlignment(align);

        Chapter chapter1 = new Chapter(parrafo, 1);

        chapter1.setNumberDepth(0);

        Paragraph title11 = new Paragraph("This is Section 1 in Chapter 1",
                                new Font(this.getFontType(this.fontType), 18, Font.BOLDITALIC, new BaseColor(0, 0, 255)));

        Section section1 = chapter1.addSection(title11);

        document.add(chapter1);

        //this.document.add(parrafo);

        /*Paragraph title1 = new Paragraph("Chapter 1", new Font(FontFamily.HELVETICA, fontSize, Style, new BaseColor(0,0,0)));

        Chapter chapter1 = new Chapter(title1, 1);

        chapter1.setNumberDepth(0);

        Paragraph title11 = new Paragraph("This is Section 1 in Chapter 1",
                                new Font(FontFamily.HELVETICA, 18, Font.BOLDITALIC, new BaseColor(0, 0, 255)));

        Section section1 = chapter1.addSection(title11);

        Paragraph someSectionText = new Paragraph("This text comes as part of section 1 of chapter 1.");

        section1.add(someSectionText);

        someSectionText = new Paragraph("Following is a 3 X 2 table.");

        section1.add(someSectionText);

        document.add(chapter1);*/
    //}


    /**
     * 
     * @param fontSize
     * @return
     */
    public Font creaFuente(int fontSize){
    	return new Font(this.getFontType(this.fontType), fontSize, 0, new BaseColor(0,0,0));
    }

    
    /**
     *  Agrega una imagen al PDF
     * @param imagen
     * @param align Element.constant
     * @return
     * @throws BadElementException
     * @throws DocumentException
     * @throws MalformedURLException
     * @throws IOException
     */
    public Image insertaImagen(String imagen, int align) throws BadElementException, DocumentException,MalformedURLException, IOException{
        //Add an image
    	try {
    		logger.info("insertando imagen " +  imagen);
			URL urlLogo = HeaderFooter.class.getResource(imagen);//TODO
	
	        Image img = Image.getInstance(urlLogo);
	        
	        img.setAlignment(align);
	        img.scalePercent(65);
	        //img.scaleToFit(img.getWidth(), img.getHeight());
	        //img.scaleToFit(150, 114);
	        //img.scaleToFit(120, 95);
	        img.setBorder(Rectangle.NO_BORDER);
	        
	        return img;
	        
    	} catch (Exception e){
    		logger.error("No se encontró logo " + e.getMessage());
    		//Empresas sin imagen..
    		return null;
    	}                
    }
    
    /*
     * Agrega una imagen al PDF
     * @string Nombre de la imagne que esta en la ruta this.imgpath
     * @int Alineacion de la imagen  0.ALIGN_LEFT 1.ALIGN_CENTER 2.ALIGN_RIGHT 3.ALIGN_JUSTIFIED 4.ALIGN_JUSTIFIED_ALL
     * @x Alineacion en eje x
     * @y Alineacion en eje y
     */
    /*public void insertaImagen(String imagen, int align, int x, int y) throws BadElementException, DocumentException,MalformedURLException, IOException{
        //Add an image
        Image img = Image.getInstance(this.imgPath +imagen);
        img.setAlignment(align);        
         img.scaleToFit(img.getWidth(), img.getHeight());
        img.setAbsolutePosition(x, y);
         img.setBorder(3);         
        this.document.add(img);
    }*/    

    
    /**
     * Crea una tabla
     * @param columns
     * @param width
     * @param halign Element.constant
     * @return
     * @throws DocumentException
     */
    public PdfPTable creaTabla(int columns, float width, int halign) throws DocumentException{
    	PdfPTable table = new PdfPTable(columns);
    	table.setWidthPercentage(width);
    	table.setHorizontalAlignment(halign);    	
    	
    	return table;
    }
    
    /**
     * Crea una celda
     * @param frase
     * @return
     */
    public PdfPCell creaCelda(Phrase frase){
    	PdfPCell cell = new PdfPCell(frase);
    	return cell;
    }
    
    
    /**
     * Genera un codigo QR de acuerdo a la cadena de texto
     * @param cadenaQR
     * @return
     * @throws Exception
     */
    public Image creaCodigoQR(String cadenaQR) throws Exception{
    	String metodo = "creaCodigoQR ";
    	try{
	    	BarcodeQRCode qrcode = new BarcodeQRCode(
		    		"https://verificacfdi.facturaelectronica.sat.gob.mx/default.aspx" +
		    			cadenaQR, 1, 1, null);
    	
	    	Image qrcodeImage = qrcode.getImage();
	    	
	    	//qrcodeImage.setAbsolutePosition(10,200);
	    	qrcodeImage.scaleAbsolute(98, 98);
	    	//qrcodeImage.scaleToFit(80, 80);
	    	qrcodeImage.setAlignment(Rectangle.ALIGN_CENTER);	    	
	
	    	return qrcodeImage;
    	}catch (Exception e){
    		logger.error(metodo + e.getLocalizedMessage());
    		throw new Exception("Imposible generar codigo QR " + e.toString());
    	}
    }
    
    public Document getDocument(){
    	return this.document;
    }
    
    
    /*
     * Ejercicios de prueba
     */
    public void test(String filename, String path) throws FileNotFoundException,Exception {
        //Establece rutas
        /*this.setPath(path);        
        this.fileName = filename + ".pdf";
        
        this.document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter writer = PdfWriter.getInstance(this.document, new FileOutputStream(this.docsPath + this.fileName));
        this.document.open();*/
        
        /*PdfContentByte canvas = writer.getDirectContentUnder();
        
        //1
        canvas.setRGBColorFill(0xFF, 0x45, 0x00);
        canvas.rectangle(10, 10, 60, 60);
        canvas.fill();
        
        //2
        canvas.saveState();
        canvas.setLineWidth(3);
        canvas.setRGBColorFill(0x8B, 0x00, 0x00);
        canvas.rectangle(40, 20, 60, 60);
        canvas.fillStroke();
        
         
        //3
        //canvas.saveState();                
        canvas.concatCTM(10, 0, 0.1f, 1, 0, 0);
        canvas.setRGBColorStroke(0xFF, 0x45, 0x00);
        canvas.setRGBColorFill(0xFF, 0xD7, 0x00);
        canvas.rectangle(70, 30, 60, 60);
        canvas.fillStroke();
        canvas.restoreState();
             
        //4
        canvas.restoreState();
        canvas.rectangle(100, 40, 60, 60);
        canvas.stroke();
        
        //5
        canvas.restoreState();
        canvas.rectangle(130, 50, 60, 60);
        canvas.fillStroke();
        
        Font helvetica = new Font(FontFamily.TIMES_ROMAN, 90);
        BaseFont bf_helv = helvetica.getCalculatedBaseFont(false);
        canvas.beginText();
        canvas.setFontAndSize(bf_helv, 90);
        
        //int alignment, String text, float x, float y, float rotation
        canvas.showTextAligned(Element.ALIGN_CENTER, "FORMATO ", 315, 540, 30);
        canvas.showTextAligned(Element.ALIGN_CENTER, "GRATUITO", 335, 440, 30);
        canvas.endText();   */        
        
    }
    
    

    /**
     * Graba PDF
     */
    public void grabaPdf(){
        this.document.close();

        this.document = null;
        this.fileName = null;
    }



    /*
     * Main para Unir varios PDF
     *
     */
    public void mergePdf(String[] files,String pdfMerge){
        try {
            List<InputStream> pdfs = new ArrayList<InputStream>();

            //Elimina el archivo en caso que exista
            File arch = new File(this.filePath + pdfMerge);
            arch.delete();
            arch = null;

            int i = 0;
            System.out.println("Uniendo PDF " + pdfMerge);
            while(i < files.length) {
                if(files[i] != null) {
                    System.out.println(files[i]);
                    pdfs.add(new FileInputStream(this.docsPath + files[i]));
                }
                i++;
            }
                                                            
            OutputStream output = new FileOutputStream(this.filePath + pdfMerge);
            this.concatPDFs(pdfs, output, false);
        } catch (Exception e) {
            logger.error(e.toString(),e);
        }
    }

    /*
     * Une Varios PDF en uno solo
     */
    private void concatPDFs(List streamOfPDFFiles, OutputStream outputStream, boolean paginate) {
       
        Document document = new Document();
        try {
            List<InputStream> pdfs = streamOfPDFFiles;
            List<PdfReader> readers = new ArrayList<PdfReader>();
            int totalPages = 0;
            Iterator<InputStream> iteratorPDFs = pdfs.iterator();

            // Create Readers for the pdfs.
            while (iteratorPDFs.hasNext()) {
                InputStream pdf = iteratorPDFs.next();
                PdfReader pdfReader = new PdfReader(pdf);
                readers.add(pdfReader);
                totalPages += pdfReader.getNumberOfPages();
            }
            // Create a writer for the outputstream
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            document.open();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
                    BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
            // data

            PdfImportedPage page;
            int currentPageNumber = 0;
            int pageOfCurrentReaderPDF = 0;
            Iterator<PdfReader> iteratorPDFReader = readers.iterator();

            // Loop through the PDF files and add to the output.
            while (iteratorPDFReader.hasNext()) {
                PdfReader pdfReader = iteratorPDFReader.next();

                // Create a new page in the target for each source page.
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                    document.newPage();
                    pageOfCurrentReaderPDF++;
                    currentPageNumber++;
                    page = writer.getImportedPage(pdfReader,
                            pageOfCurrentReaderPDF);
                    cb.addTemplate(page, 0, 0);

                    // Code for pagination.
                    if (paginate) {
                        cb.beginText();
                        cb.setFontAndSize(bf, 9);
                        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, ""
                                + currentPageNumber + " of " + totalPages, 520,
                                5, 0);
                        cb.endText();
                    }
                }
                pageOfCurrentReaderPDF = 0;
            }
            outputStream.flush();
            document.close();
            outputStream.close();
        } catch (Exception e) {
        	logger.error(e.toString(),e);
        } finally {
            if (document.isOpen())
                document.close();
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException ioe) {
            	logger.error(ioe.toString(),ioe);
            }
        }
    }

}
