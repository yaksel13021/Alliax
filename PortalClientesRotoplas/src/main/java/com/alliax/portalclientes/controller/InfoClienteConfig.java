package com.alliax.portalclientes.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alliax.portalclientes.model.ClienteInfo;

@Configuration
public class InfoClienteConfig {

	@Bean
	public ClienteInfo informacionCliente(){
		ClienteInfo info = new ClienteInfo();
		
		info.setNoCliente("123456");
		info.setNombre("Alliax S.A. de C.V.");
		info.setEmail("saul.ibarra@alliax.com");
		info.setCalle("Nogalar Sur");
		info.setCiudad("San Nicolas de los Garza");
		info.setCodigoPostal("50001");
		info.setFax("8748-3121");
		info.setLang("ES");
		info.setPais("MX");
		info.setTelefono("8748-3121");
		info.setRegion("NL");
		
		return info;
	}
}
