package com.rotoplas.general;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class InternetAddresTest {

	public static void main(String[] args) {
		String correos = "david.loredo@alliax.com;daniel.galindo@alliax.com";
		try {
			InternetAddress[] addresses = InternetAddress.parse(correos.replace(";",","));
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
