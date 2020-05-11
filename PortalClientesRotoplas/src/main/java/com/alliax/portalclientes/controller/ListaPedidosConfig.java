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
		
		List<OrdenVenta> resultados = new ArrayList<OrdenVenta>();
		OrdenVenta orden = null;
		
		for (int i = 0; i < 10; i++) {
			orden = new OrdenVenta();
			orden.setDocumentoComercial("90071871");
			orden.setPedidoCliente("90071871");
			orden.setFechaCreacion(new Date());
			orden.setHoraCreacion(new Date().getTime());
			orden.setMonto(new BigDecimal("29691.00"));
			orden.setMoneda("90071871");
			orden.setDestinatario("90071871");
			orden.setEstatusGlobal("90071871");
			orden.setEstatusGlobalDes("90071871");
			orden.setEstatusCredito("90071871");
			orden.setEstatusCreditoDes("90071871");
			orden.setEstatusEntrega("90071871");
			orden.setEstatusEntregaDes("90071871");
			orden.setEstatusGeneral("90071871");
			orden.setEstatusGeneralDes("90071871");
			orden.setRazonBloqueo("90071871");
			
			resultados.add(orden);
		}
		return resultados;

			}
}
