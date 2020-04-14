/**
 * Clase para exportar Objetos a Excel
 * @author saul.ibarra
 * @fecha 27Diciembre17
 */
package com.alliax.portalclientes.controller;

import java.io.InputStream;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.alliax.portalclientes.model.EstadoCuenta;


public abstract class XlsExport {
	
    //Instancias
    private static final Logger logger = Logger.getLogger(XlsExport.class);	

    
	@Autowired
	private Environment lblHeader;
        
    private String TemplateFilePath = "/META-INF/downloadXls/";
    protected String templateFileName;
    protected String langu;
    protected Workbook wb = null;
    
    private List<Object> listado;
    private Object encabezado;
    
    public List<Object> getListado() {
		return listado;
	}
    
	public void setListado(List<Object> listado) {
		this.listado = listado;
	}
    
    public Object getEncabezado() {
		return encabezado;
	}

	public void setEncabezado(Object encabezado) {
		this.encabezado = encabezado;
	}

	public Environment getLblHeader() {
		return lblHeader;
	}

	public void setLblHeader(Environment lblHeader) {
		this.lblHeader = lblHeader;
	}

	/**
     * Prepara el XLS para escritura
     * @throws Exception
     */
    protected void generandoExcel(String prefix, ResourceBundle lblMain) throws Exception{
        InputStream inp = null;
    	try{
            //Crea InputStream
            this.templateFileName = "edoCtaDownload.xlsx";
            inp = this.getClass().getClassLoader().getResourceAsStream("META-INF/downloadXls/"+this.templateFileName); 
            this.wb = WorkbookFactory.create(inp);

            //Abrir Worksheet
            Sheet ws1 = this.wb.getSheetAt(0);

            //********** Encabezado
            logger.info("	Encabezado....");
            this.llenaEncabezado(ws1,prefix,lblMain);
            
            //********** Encabezado
            logger.info("	Encabezado....");
            this.GrabaEncabezado(ws1,prefix,lblMain);

            //********** Listado de Datos
            logger.info("Listado....");
            this.ejecutaListado(ws1, lblMain);

            logger.info("Done....");

        }catch(Exception as ){
            logger.error("Error al generar Excel \n " + as.getLocalizedMessage(),as);
            throw new Exception("Error al generar Excel \n " + as.getLocalizedMessage());
        }finally{
        	try{               
        		inp.close();                                     
            }catch(Exception e){
                logger.debug("Error al cerrar inp :  " + e.getLocalizedMessage(),e);
            }
        }
    }    
    
    
    /**
     * Este metodo se sobreescribiria en cada export...
     * @param ws1
     * @throws ClassNotFoundException
     * @throws Exception
     */
    protected abstract void ejecutaListado(Sheet ws1, ResourceBundle lblMain) throws ClassNotFoundException, Exception;
 
    
    /**
     * Este metodo se sobreescribiria en cada export...
     * @param ws1
     * @throws ClassNotFoundException
     * @throws Exception
     */
    protected abstract void llenaEncabezado(Sheet ws1, String prefix, ResourceBundle lblMain) throws ClassNotFoundException, Exception;
    
    
    /**
     * Graba los titulos de las columnas
     * @param ws1
     * @param totalCols
     * @throws ClassNotFoundException
     * @throws Exception
     */
    protected void grabaColHeaders(Sheet ws1, int totalCols, String prefix, ResourceBundle lblMain) throws ClassNotFoundException, Exception {
        
    	EstadoCuenta objeto = (EstadoCuenta) this.getEncabezado();
    	
        //Estilo del encabezado
        Font titleFont = this.wb.createFont();
        titleFont.setFontHeightInPoints((short)9);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        CellStyle style = this.wb.createCellStyle();

        style.setBorderBottom(CellStyle.BORDER_THICK);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THICK);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THICK);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THICK);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
        style.setFont(titleFont);
        
        int s = 1;
        
        Row row = null;
        Cell cell = null;
        
        //Inicia la fila en el renglon 4
        row = ws1.createRow(14);
        
//        while(s <= totalCols){
//            try {
//                //Creando Celdas en la fila 
//                cell = row.createCell(s-1);
//                cell.setCellValue(this.getLblHeader().getProperty(prefix+s));
//
//                cell.setCellStyle(style);    
//            } catch (Exception e) {
//                logger.error("Error grabaColHeaders " + s + " " + e.getLocalizedMessage(),e);
//            }
//            s++;
//        }
        
        //Titulos en columnas
        int col = 0;
        
        if(objeto.getPais() != null && objeto.getPais().equals("AR")){
            //Tipo Documento
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("tipoDocumento"));
            cell.setCellStyle(style);
            //Fact. Fiscal
          	cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("factFiscal"));
            cell.setCellStyle(style);
            //No. Remito
        	cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("remito"));
            cell.setCellStyle(style);            
            //Fecha Factura
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("fechaFactura"));
            cell.setCellStyle(style);            
            //Vencimiento
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("vencimiento"));
            cell.setCellStyle(style);            
            //Días Mora
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("diasMora"));
            cell.setCellStyle(style);            
            //Importe
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("importe"));
            cell.setCellStyle(style);
            cell.getCellStyle().setAlignment( CellStyle.ALIGN_RIGHT );
            //No. pedido
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("estatus"));
            cell.setCellStyle(style);
            
        }else if(objeto.getPais() != null && objeto.getPais().equals("PE")){
            //Tipo Documento
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("tipoDocumento"));
            cell.setCellStyle(style);
            //No. Factura
        	cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("numeroFactura"));
            cell.setCellStyle(style);
            //No. Letra
        	cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("numeroLetra"));
            cell.setCellStyle(style);
            //Orden de Compra
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("pedido"));
            cell.setCellStyle(style);   
            //Número único
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("numeroUnico"));
            cell.setCellStyle(style);            
            //Fecha Factura
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("fechaFactura"));
            cell.setCellStyle(style);            
            //Vencimiento
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("vencimiento"));
            cell.setCellStyle(style);            
            //Días Mora
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("diasMora"));
            cell.setCellStyle(style);            
            //Importe
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("importe"));
            cell.setCellStyle(style);            
            //Estatus
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("estatus"));
            cell.setCellStyle(style);
        }else if(objeto.getPais() != null && objeto.getPais().equals("GT")){
            //Tipo Documento
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("tipoDocumento"));
            cell.setCellStyle(style);
            //Orden de Compra
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("pedido"));
            cell.setCellStyle(style);
            //No. pedido
          	cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("noPedido"));
            cell.setCellStyle(style);            
            //Nota de entrega
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("notaEntrega"));
            cell.setCellStyle(style);
            //Número de Autorización SAT
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("numeroAutorizacionSAT"));
            cell.setCellStyle(style);           
            //No. Fact Fiscal
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("factSAP"));
            cell.setCellStyle(style);            

            //Fecha Factura
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("fechaFactura"));
            cell.setCellStyle(style);            
            //Vencimiento
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("vencimiento"));
            cell.setCellStyle(style);            
            //Días Mora
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("diasMora"));
            cell.setCellStyle(style);            
            //Importe
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("importe"));
            cell.setCellStyle(style);
            cell.getCellStyle().setAlignment( CellStyle.ALIGN_RIGHT );
            //Estatus
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("estatus"));
            cell.setCellStyle(style);        	
        }else if(objeto.getPais() != null && (objeto.getPais().equals("SV") || objeto.getPais().equals("HN") || objeto.getPais().equals("NI")) ){
            //Tipo Documento
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("tipoDocumento"));
            cell.setCellStyle(style);
            //Orden de Compra
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("pedido"));
            cell.setCellStyle(style);
            //No. pedido
          	cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("noPedido"));
            cell.setCellStyle(style);            
            //No. Fact Fiscal
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("factFiscal"));
            cell.setCellStyle(style);          

            //Fecha Factura
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("fechaFactura"));
            cell.setCellStyle(style);            
            //Vencimiento
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("vencimiento"));
            cell.setCellStyle(style);            
            //Días Mora
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("diasMora"));
            cell.setCellStyle(style);            
            //Importe
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("importe"));
            cell.setCellStyle(style);   
            cell.getCellStyle().setAlignment( CellStyle.ALIGN_RIGHT );
            //Estatus
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("estatus"));
            cell.setCellStyle(style);           	
        }else if(objeto.getPais() != null && objeto.getPais().equals("CR")){
            //Tipo Documento
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("tipoDocumento"));
            cell.setCellStyle(style);
            //Orden de Compra
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("pedido"));
            cell.setCellStyle(style);
            //No. pedido
          	cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("noPedido"));
            cell.setCellStyle(style);            
            //No. Fact Fiscal
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("factFiscal"));
            cell.setCellStyle(style);           

            //Fecha Factura
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("fechaFactura"));
            cell.setCellStyle(style);            
            //Vencimiento
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("vencimiento"));
            cell.setCellStyle(style);            
            //Días Mora
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("diasMora"));
            cell.setCellStyle(style);            
            //Importe
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("importe"));
            cell.setCellStyle(style);
            cell.getCellStyle().setAlignment( CellStyle.ALIGN_RIGHT );
            //No. pedido
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("estatus"));
            cell.setCellStyle(style);          	
        }else if(objeto.getPais() != null && objeto.getPais().equals("BR")){
            //Tipo Documento
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("tipoDocumento"));
            cell.setCellStyle(style);
            //Orden de Compra
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("pedido"));
            cell.setCellStyle(style);
            //No. pedido
          	cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("noPedido"));
            cell.setCellStyle(style);            
            //No. Fact Fiscal
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("factFiscalBR"));
            cell.setCellStyle(style);           

            //Fecha Factura
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("fechaFactura"));
            cell.setCellStyle(style);            
            //Vencimiento
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("vencimiento"));
            cell.setCellStyle(style);            
            //Días Mora
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("diasMora"));
            cell.setCellStyle(style);            
            //Importe
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("importe"));
            cell.setCellStyle(style);
            cell.getCellStyle().setAlignment( CellStyle.ALIGN_RIGHT );
            //No. pedido
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("estatus"));
            cell.setCellStyle(style);          	
        }else{        
            //Tipo Documento
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("tipoDocumento"));
            cell.setCellStyle(style);
            //No. pedido
        	cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("noPedido"));
            cell.setCellStyle(style);
            //Orden de Compra
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("pedido"));
            cell.setCellStyle(style);
            //Fact SAP
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("factSAP"));
            cell.setCellStyle(style);
            //Fact. Fiscal
          	cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("factFiscal"));
            cell.setCellStyle(style);
            //Factura Relacionada
          	cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("facturaRelacionada"));
            cell.setCellStyle(style);
            //UUID Relacionado
          	cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("UUIDRelacionado"));
            cell.setCellStyle(style);            
            //Fecha Factura
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("fechaFactura"));
            cell.setCellStyle(style);            
            //Vencimiento
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("vencimiento"));
            cell.setCellStyle(style);            
            //Días Mora
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("diasMora"));
            cell.setCellStyle(style);            
            //Importe
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("importe"));
            cell.setCellStyle(style);            
            //No. pedido
            cell = row.createCell(col++);
            cell.setCellValue(lblMain.getString("estatus"));
            cell.setCellStyle(style);
        }        
        
    }
    
    
    /**
     * Graba el titulo del excel
     * @param ws1
     * @throws ClassNotFoundException
     * @throws Exception
     */
    protected void GrabaEncabezado(Sheet ws1, String prefix, ResourceBundle lblMain) throws ClassNotFoundException, Exception{               
        try{               	        	        	
            CreationHelper createHelper = this.wb.getCreationHelper();

            Cell cell = null;
            Row row = null;

            // Style the cell with borders all around.
            Font titleFont = this.wb.createFont();
            titleFont.setFontHeightInPoints((short)14);
            titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

            CellStyle style = this.wb.createCellStyle();

            style.setFont(titleFont);

            row = ws1.createRow(0);

            cell = row.createCell(0);
            cell.setCellValue(createHelper.createRichTextString(lblMain.getString("estadoCuenta")));
            cell.setCellStyle(style);
            
            ws1.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    0, //last row  (0-based)
                    0, //first column (0-based)
                    4  //last column  (0-based)
            ));

        }catch( Exception as ){
            logger.error("Error en GrabaEncabezado " + as.getLocalizedMessage(),as);
        }
    }
        
}
