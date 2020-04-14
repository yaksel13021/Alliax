package com.rotoplas.general;

import java.util.StringTokenizer;

public class AltaMasivaClienteVendedor {
	
	
	public static void main(String[] args) {
		String s = "1001400	dgalindo";
		StringTokenizer st = new StringTokenizer(s, "\t");
		
		System.out.println(st.nextToken());
		
		char c = '	';
		String a = Integer.toHexString(c); // gives you---> a = "61"
		System.out.println(a);
	}
	
}
