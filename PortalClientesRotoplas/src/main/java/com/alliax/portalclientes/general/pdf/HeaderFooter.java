package com.alliax.portalclientes.general.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooter extends PdfPageEventHelper {
	//variables
	
	int pagenumber = 0;	
	
	Font fuente7 = new Font(FontFamily.HELVETICA, 7, 0, new BaseColor(0,0,0));
	
	
	public void onStartPage(PdfWriter writer, Document document) { 
		pagenumber++;
	}	
	
	public void onEndPage(PdfWriter writer, Document document) {
		Rectangle rect = writer.getBoxSize("art");
		  /*switch(writer.getPageNumber() % 2) {
		    case 0:
			      	ColumnText.showTextAligned(writer.getDirectContent(),
		      			Element.ALIGN_RIGHT, new Phrase("Test 1",fuente7),
		      				rect.getRight(), rect.getTop(),0);
			      	break;
		    case 1:
	    			ColumnText.showTextAligned(writer.getDirectContent(),
    					Element.ALIGN_LEFT, new Phrase("Test 2",fuente7),
    					rect.getLeft(), rect.getTop(),0);
	    			break; 
		}
				
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, 
				new Phrase("Este documento es una representación impresa de un CFDI", fuente7), 
				(rect.getLeft() + rect.getRight()) / 2,20, 0);	*/	
		  
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, 
				new Phrase(String.format("Página %d", pagenumber),fuente7),
				(rect.getLeft() + rect.getRight()) / 2,20, 0);
	}
				
}
