package com.rotoplas.ws;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alliax.portalclientes.controller.AutocompleteClientesRFC;
import com.alliax.portalclientes.repository.EstadoRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"context:spring/root-context.xml"})
public class ClienteRFCTest {
	
		@Autowired
		AutocompleteClientesRFC autocompleteClienteRFC;
		@Autowired
		private EstadoRepository estadoRepository;
		
	
		@Test
		public void getClientes(){
			System.out.println("El test corrió " + autocompleteClienteRFC + " " + estadoRepository);
		}
}
