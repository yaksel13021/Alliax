USE [PortalClientes]
GO

/****** Object:  Table [dbo].[Pedido]    Script Date: 5/8/2020 5:38:51 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Pedido](
	[idPedido] [int] NOT NULL,
	[destino] [varchar](20) NULL,
	[nroCliente] [varchar](15) NULL,
	[nroPedido] [varchar](15) NULL,
	[destinatario] [varchar](10) NULL,
	[codigoPostal] [varchar](10) NULL,
	[organizacionVenta] [varchar](10) NULL,
	[sociedad] [varchar](10) NULL,
	[destinatarioMercancia] [varchar](10) NULL,
	[clasePedido] [varchar](10) NULL,
	[tipoMaterial] [varchar](10) NULL,
	[estatus] [varchar](10) NULL,
	[metodoPago] [varchar](20) NULL,
	[usoCFDI] [varchar](10) NULL,
	[comprobanteBancario] [varchar](10) NULL,
	[datosEntrega] [varchar](100) NULL,
	[nombreContacto] [varchar](50) NULL,
	[apellidoContacto] [varchar](50) NULL,
	[telefonoContacto] [varchar](30) NULL,
	[telefonoFijoContacto] [varchar](30) NULL,
	[horarioRecepcion] [varchar](20) NULL,
	[referenciaUbicacion] [varchar](400) NULL,
	[productoAlmacenar] [varchar](400) NULL,
	[capacidadesTransporte] [varchar](400) NULL,
	[equipoEspecial] [varchar](400) NULL,
	[noCotizacion] [varchar](20) NULL,
	[estatusCotizacion] [varchar](10) NULL,
	[correoElectronico] [varchar](50) NULL,
 CONSTRAINT [PK_Pedido] PRIMARY KEY CLUSTERED 
(
	[idPedido] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

