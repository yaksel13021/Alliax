package com.rotoplas.pdf;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormatoImporte {

	public static void main(String[] args) {
				
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		 dfs.setDecimalSeparator(',');
		 dfs.setGroupingSeparator('.');
		DecimalFormat df = new DecimalFormat( "R$ " + "#,##0.00", dfs);
		
		System.out.println( df.format(1000000.00) );
		
		String lang = null;
		
		System.out.println(lang == null);
		System.out.println(lang.isEmpty());
		System.out.println(!lang.equals("pt"));
		System.out.println(!lang.equals("en"));
		System.out.println(!lang.equals("en"));		
		
        switch(lang){
        	case "pt":
        	case "en":
        	case "es":
        		break;
        	default:
        		lang="es";
        }
        
        System.out.println( lang );
	}
		
}
