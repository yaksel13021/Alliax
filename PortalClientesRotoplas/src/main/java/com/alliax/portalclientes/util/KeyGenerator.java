package com.alliax.portalclientes.util;
/**
 * 
 * Clase de utelerias para generar una llave unica que se utiliza como messageKey de la transaccion
 *
 */
public class KeyGenerator {
	private static final long LIMIT = 10000000000L;
	private static long last = 0;

	public static long getKey() {
	  // 10 digits.
	  long id = System.currentTimeMillis() % LIMIT;
	  if ( id <= last ) {
	    id = (last + 1) % LIMIT;
	  }
	  return last = id;
	}
}
