USE [PortalClientes]
GO

/****** Object:  Table [dbo].[Material]    Script Date: 5/6/2020 12:34:17 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Material](
	[tipoMaterial] [nchar](10) NOT NULL,
	[sku] [nchar](20) NOT NULL,
	[descripcion] [nchar](300) NULL,
	[unidadMedida] [nchar](10) NULL,
	[urlFoto] [nchar](500) NULL,
 CONSTRAINT [PK_Material] PRIMARY KEY CLUSTERED 
(
	[sku] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'10(Almacenamiento), 11(Industria)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Material', @level2type=N'COLUMN',@level2name=N'tipoMaterial'
GO

