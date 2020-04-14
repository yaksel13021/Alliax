package com.alliax.portalclientes.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alliax.portalclientes.model.Item;

@Configuration
public class DetallePedidoConfig {
	
	@Bean
	public List<Item> partidas(){
		List<Item> resultado = new ArrayList<Item>();
		
		Item item = new Item();
		
		item.setDocumentoComercial("9876543210");
		item.setPosicion(1);
		item.setFecha(new Date());
		item.setBloqueoEntrega("BL");
		item.setNoMaterial("310092");
		item.setDescripcion("MULTICONECTOR C/VALV-TUERCA UNION INTEG");
		item.setFechaReparto(new Date());
		item.setCantidad(new BigDecimal(0));
		item.setCantidadConfirmada(new BigDecimal(0));
		item.setUnidadMedida("PZA");
		item.setMonto(new BigDecimal("2778.60"));
		item.setMoneda("MXN");
		item.setPrecioNeto(new BigDecimal("55.57"));
		item.setCantidadBase(new BigDecimal("100"));
		item.setUnidadMedidaCondicion("PZA");
		item.setNoEntrega("2000085751");
		item.setPosicionEntrega(2);
		item.setFechaEntrega(new Date());
		item.setCantidadEntregada(new BigDecimal(500));
		item.setCantidadRefUmb(new BigDecimal(101));
		item.setUnidadEntrega("Camión");
		item.setUnidadMedidaISO("Tarima");
		item.setMotivoRechazo("Sistema Contable");
		item.setPedidoCliente("1234567891");
		item.setEstatus("P");
		item.setEstatusDes("Pendiente");
		item.setDocumentoComercial("Estatus");
		item.setEstatusGeneralDes("En Proceso");
		item.setEstatusGeneral("Proc");
		
		resultado.add(item);
		
		item = new Item();
		
		item.setDocumentoComercial("9876543210");
		item.setPosicion(2);
		item.setFecha(new Date());
		item.setBloqueoEntrega("BL");
		item.setNoMaterial("310088");
		item.setDescripcion("VALVULA DE PIE (PICHANCHA)");
		item.setFechaReparto(new Date());
		item.setCantidad(new BigDecimal(10));
		item.setCantidadConfirmada(new BigDecimal(10));
		item.setUnidadMedida("PZA");
		item.setMonto(new BigDecimal("23933.54"));
		item.setMoneda("MXN");
		item.setPrecioNeto(new BigDecimal("2393.35"));
		item.setCantidadBase(new BigDecimal("100"));
		item.setUnidadMedidaCondicion("Pieza");
		item.setNoEntrega("2000085751");
		item.setPosicionEntrega(2);
		item.setFechaEntrega(new Date());
		item.setCantidadEntregada(new BigDecimal(500));
		item.setCantidadRefUmb(new BigDecimal(101));
		item.setUnidadEntrega("Camión");
		item.setUnidadMedidaISO("Tarima");
		item.setMotivoRechazo("Sistema Contable");
		item.setPedidoCliente("1234567891");
		item.setEstatus("C");
		item.setEstatusDes("Concluido");
		item.setDocumentoComercial("Estatus");
		item.setEstatusGeneralDes("Concluido");
		item.setEstatusGeneral("Conc");
		
		resultado.add(item);		
		
		item = new Item();
		
		item.setDocumentoComercial("9876543210");
		item.setPosicion(3);
		item.setFecha(new Date());
		item.setBloqueoEntrega("BL");
		item.setNoMaterial("510003");
		item.setDescripcion("Esta es una descripción breve de la posición 3.");
		item.setFechaReparto(new Date());
		item.setCantidad(new BigDecimal(1000));
		item.setCantidadConfirmada(new BigDecimal(500));
		item.setUnidadMedida("Pieza");
		item.setMonto(new BigDecimal("200000"));
		item.setMoneda("MXP");
		item.setPrecioNeto(new BigDecimal("1000"));
		item.setCantidadBase(new BigDecimal("100"));
		item.setUnidadMedidaCondicion("Pieza");
		item.setNoEntrega("2000085751");
		item.setPosicionEntrega(2);
		item.setFechaEntrega(new Date());
		item.setCantidadEntregada(new BigDecimal(500));
		item.setCantidadRefUmb(new BigDecimal(101));
		item.setUnidadEntrega("Camión");
		item.setUnidadMedidaISO("Tarima");
		item.setMotivoRechazo("Sistema Contable");
		item.setPedidoCliente("1234567891");
		item.setEstatus("R");
		item.setEstatusDes("Rechazado");
		item.setDocumentoComercial("Estatus");
		item.setEstatusGeneralDes("Facturado Parcial");
		item.setEstatusGeneral("Pinv");
		
		resultado.add(item);			
		
		
		return resultado;		
	}

}
