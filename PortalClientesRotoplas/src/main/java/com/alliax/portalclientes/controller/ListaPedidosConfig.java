/**
 * Configuration bean para precargar listado de pedidos
 */
package com.alliax.portalclientes.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alliax.portalclientes.model.OrdenVenta;

@Configuration
public class ListaPedidosConfig {

	@Bean
	public List<OrdenVenta> pedidos() {

		List<OrdenVenta> lista = new ArrayList<OrdenVenta>();
		
		OrdenVenta ov = new OrdenVenta();
		
		ov.setDocumentoComercial("90071871");
		ov.setPedidoCliente("10023828");
		ov.setFechaCreacion(new Date());
		ov.setHoraCreacion(new Date().getTime());
		ov.setMonto(new BigDecimal("29691.00"));
		ov.setMoneda("MXP");
		ov.setDestinatario("Con. Suc. Periferico / Tablaje Catastral 13951 / 97210 Merida Sin Distrito/colonia");
		ov.setEstatusGlobal("P");
		ov.setEstatusGlobalDes("Being processed");
		ov.setEstatusCredito("B");
		ov.setEstatusCreditoDes("Aprobado");
		ov.setEstatusEntrega("X");
		ov.setEstatusEntregaDes("Verificar SAC");
		ov.setEstatusGeneral("Vsac".toUpperCase());
		ov.setEstatusGeneralDes("Verificar SAC");
		lista.add(ov);
		
		ov = new OrdenVenta();
		
		ov.setDocumentoComercial("90071872");
		ov.setPedidoCliente("10023829");
		ov.setFechaCreacion(new Date());
		ov.setHoraCreacion(new Date().getTime());
		ov.setMonto(new BigDecimal("29691.00"));
		ov.setMoneda("MXP");
		ov.setDestinatario("Con. Suc. Periferico / Tablaje Catastral 13951 / 97210 Merida Sin Distrito/colonia");
		ov.setEstatusGlobal("P");
		ov.setEstatusGlobalDes("Being processed");
		ov.setEstatusCredito("B");
		ov.setEstatusCreditoDes("Aprobado");
		ov.setEstatusEntrega("X");
		ov.setEstatusEntregaDes("Retenido");
		ov.setEstatusGeneral("Held".toUpperCase());
		ov.setEstatusGeneralDes("Retenido");
		lista.add(ov);		

		ov = new OrdenVenta();
		
		ov.setDocumentoComercial("90071873");
		ov.setPedidoCliente("10023830");
		ov.setFechaCreacion(new Date());
		ov.setHoraCreacion(new Date().getTime());
		ov.setMonto(new BigDecimal("29691.00"));
		ov.setMoneda("MXP");
		ov.setDestinatario("Con. Suc. Periferico / Tablaje Catastral 13951 / 97210 Merida Sin Distrito/colonia");
		ov.setEstatusGlobal("P");
		ov.setEstatusGlobalDes("Being processed");
		ov.setEstatusCredito("B");
		ov.setEstatusCreditoDes("Aprobado");
		ov.setEstatusEntrega("X");
		ov.setEstatusEntregaDes("Cancelado");
		ov.setEstatusGeneral("Canc".toUpperCase());
		ov.setEstatusGeneralDes("Cancelado");
		lista.add(ov);	
		
		ov = new OrdenVenta();
		
		ov.setDocumentoComercial("90071874");
		ov.setPedidoCliente("10023831");
		ov.setFechaCreacion(new Date());
		ov.setHoraCreacion(new Date().getTime());
		ov.setMonto(new BigDecimal("29691.00"));
		ov.setMoneda("MXP");
		ov.setDestinatario("Con. Suc. Periferico / Tablaje Catastral 13951 / 97210 Merida Sin Distrito/colonia");
		ov.setEstatusGlobal("P");
		ov.setEstatusGlobalDes("Being processed");
		ov.setEstatusCredito("B");
		ov.setEstatusCreditoDes("Aprobado");
		ov.setEstatusEntrega("X");
		ov.setEstatusEntregaDes("Liberado");
		ov.setEstatusGeneral("Rele".toUpperCase());
		ov.setEstatusGeneralDes("Liberado");
		lista.add(ov);		
		
		
		ov = new OrdenVenta();
		
		ov.setDocumentoComercial("90071875");
		ov.setPedidoCliente("10023832");
		ov.setFechaCreacion(new Date());
		ov.setHoraCreacion(new Date().getTime());
		ov.setMonto(new BigDecimal("29691.00"));
		ov.setMoneda("MXP");
		ov.setDestinatario("Con. Suc. Periferico / Tablaje Catastral 13951 / 97210 Merida Sin Distrito/colonia");
		ov.setEstatusGlobal("P");
		ov.setEstatusGlobalDes("Being processed");
		ov.setEstatusCredito("B");
		ov.setEstatusCreditoDes("Aprobado");
		ov.setEstatusEntrega("X");
		ov.setEstatusEntregaDes("Proceso");
		ov.setEstatusGeneral("Proc".toUpperCase());
		ov.setEstatusGeneralDes("Proceso");
		lista.add(ov);				
		
		
		ov = new OrdenVenta();
		
		ov.setDocumentoComercial("90071876");
		ov.setPedidoCliente("10023833");
		ov.setFechaCreacion(new Date());
		ov.setHoraCreacion(new Date().getTime());
		ov.setMonto(new BigDecimal("29691.00"));
		ov.setMoneda("MXP");
		ov.setDestinatario("Con. Suc. Periferico / Tablaje Catastral 13951 / 97210 Merida Sin Distrito/colonia");
		ov.setEstatusGlobal("P");
		ov.setEstatusGlobalDes("Being processed");
		ov.setEstatusCredito("B");
		ov.setEstatusCreditoDes("Aprobado");
		ov.setEstatusEntrega("X");
		ov.setEstatusEntregaDes("Partially Delivered");
		ov.setEstatusGeneral("Pinv".toUpperCase());
		ov.setEstatusGeneralDes("Facturado Parcialmente");
		lista.add(ov);			
		
		
		ov = new OrdenVenta();
		
		ov.setDocumentoComercial("90071877");
		ov.setPedidoCliente("10023834");
		ov.setFechaCreacion(new Date());
		ov.setHoraCreacion(new Date().getTime());
		ov.setMonto(new BigDecimal("29691.00"));
		ov.setMoneda("MXP");
		ov.setDestinatario("Con. Suc. Periferico / Tablaje Catastral 13951 / 97210 Merida Sin Distrito/colonia");
		ov.setEstatusGlobal("P");
		ov.setEstatusGlobalDes("Being processed");
		ov.setEstatusCredito("B");
		ov.setEstatusCreditoDes("Aprobado");
		ov.setEstatusEntrega("X");
		ov.setEstatusEntregaDes("Concluido");
		ov.setEstatusGeneral("Conc".toUpperCase());
		ov.setEstatusGeneralDes("Concluido");
		lista.add(ov);		
		
		
		ov = new OrdenVenta();
		
		ov.setDocumentoComercial("90071878");
		ov.setPedidoCliente("10023835");
		ov.setFechaCreacion(new Date());
		ov.setHoraCreacion(new Date().getTime());
		ov.setMonto(new BigDecimal("29691.00"));
		ov.setMoneda("MXP");
		ov.setDestinatario("Con. Suc. Periferico / Tablaje Catastral 13951 / 97210 Merida Sin Distrito/colonia");
		ov.setEstatusGlobal("P");
		ov.setEstatusGlobalDes("Being processed");
		ov.setEstatusCredito("B");
		ov.setEstatusCreditoDes("Aprobado");
		ov.setEstatusEntrega("X");
		ov.setEstatusEntregaDes("Completado");
		ov.setEstatusGeneral("Comp".toUpperCase());
		ov.setEstatusGeneralDes("Completado");
		lista.add(ov);			
		
		/*Pend=Pendiente
		Vsac=Verificar SAC
		Held=Retenido
		Canc=Cancelado
		Rele=Liberado
		Proc=En Proceso
		Pinv=Facturado Parcial
		Conc=Concluido
		Comp=Completo*/
		

		return lista;
	}
}
