package com.rotoplas.ws;

import com.alliax.portalclientes.util.Helper;
import java.math.BigDecimal;

public class UtilTest {
	
	public static void main(String[] args) {
		System.out.println( Helper.getSignoPesos("PE") );
				
		System.out.println( Helper.getMontoFormateado(new BigDecimal("-1232454562345243124") , "PE") );
		
	}
	
}
