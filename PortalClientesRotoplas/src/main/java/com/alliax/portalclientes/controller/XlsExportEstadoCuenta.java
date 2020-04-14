/**
 * Clase para generear exporte de XLS de pedidos
 * @author saul.ibarra
 * @fecha 10-May-2018
 */

package com.alliax.portalclientes.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.alliax.portalclientes.general.formato.Fecha;
import com.alliax.portalclientes.model.EstadoCuenta;
import com.alliax.portalclientes.model.EstadoCuentaDet;
import com.alliax.portalclientes.util.Helper;


@Configuration
@Service("exportEstadoCuenta")
public class XlsExportEstadoCuenta extends XlsExport {

	private static final Logger logger = Logger.getLogger(XlsExportEstadoCuenta.class);	
	
	/**
	 * Genera el reporte en Excel de lo pagado
	 * @param pagado
	 * @param transaccion
	 * @param pagina
	 * @param lang
	 * @return
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public Workbook estadoCuentaToXls(EstadoCuenta reporte, ResourceBundle lblMain, String lang) throws ClassNotFoundException, Exception{
        try{
        	logger.info("estadoCuentaToXls");
            this.langu = lang;
            
            this.setEncabezado(reporte);
            
            List<Object> listaprueba = new ArrayList<Object>(Arrays.asList(reporte.getDetalle().toArray()));            
            this.setListado(listaprueba);
            logger.info("Cantidad de registros : " + listaprueba);
            //Generando Excel
            this.generandoExcel("ESTADOCUENTA", lblMain);

            //Regresa el nombre del archivo
            return this.wb;

        }catch(Exception as ){
            logger.info("Error al preparar archivo \n " + as.toString());
            throw new Exception("Error al preparar archivo \n " + as.toString());
        }
    }    	
	

	@Override
	protected void llenaEncabezado(Sheet ws1, String prefix, ResourceBundle lblMain) throws ClassNotFoundException, Exception {

        //Estilo del encabezado
        Font titleFont = this.wb.createFont();
        titleFont.setFontHeightInPoints((short)9);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        CellStyle style = this.wb.createCellStyle();
         style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
         style.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
         style.setFont(titleFont);
        CellStyle styleRight = this.wb.createCellStyle();
         styleRight.setFillForegroundColor(IndexedColors.BLUE.getIndex());
         styleRight.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
         styleRight.setFont(titleFont);
         styleRight.setAlignment(CellStyle.ALIGN_RIGHT);
        
//        CellStyle moneda = this.wb.createCellStyle();
        
        DataFormat df = this.wb.createDataFormat();
//        moneda.setDataFormat(df.getFormat("$#,##0.00_);-$#,##0.00"));
        EstadoCuenta objeto = (EstadoCuenta) this.getEncabezado();
        
        Row row = null;
        Row rowMail = null;
        Cell cell = null;
        int r = 3;
        
        row = ws1.createRow(r-1);
        
        cell = row.createCell(2);
        cell.setCellValue(objeto.getNombre());
        cell.setCellStyle(style);  
        cell = row.createCell(3);
        cell.setCellValue(objeto.getNombre2());
        cell.setCellStyle(style);  
        cell = row.createCell(4);
        cell.setCellValue(objeto.getNombre3());
        cell.setCellStyle(style);  
        cell = row.createCell(5);
        cell.setCellValue(objeto.getNombre4());
        cell.setCellStyle(style); 

        ws1.addMergedRegion(new CellRangeAddress(
                2, //first row (0-based)
                2, //last row  (0-based)
                2, //first column (0-based)
                5  //last column  (0-based)
        ));
        
        row = ws1.createRow(r+1);
        
        cell = row.createCell(1);
        cell.setCellValue( lblMain.getString("cliente"));
        cell.setCellStyle(style);    
        
        cell = row.createCell(2);
        cell.setCellValue(objeto.getNumCliente());
        
        cell = row.createCell(4);
        cell.setCellValue(lblMain.getString("limiteCredito"));
        cell.setCellStyle(style);  
        
        boolean isImporteToRight = objeto.getPais().equals("AR") || objeto.getPais().equals("GT") || objeto.getPais().equals("SV") || objeto.getPais().equals("HN") || objeto.getPais().equals("NI") || objeto.getPais().equals("CR") ? true : false;
        cell = row.createCell(5);
        cell.setCellValue( Helper.getMontoFormateado( objeto.getLimiteCredito(), objeto.getPais()) );
        if(isImporteToRight){cell.getCellStyle().setAlignment( CellStyle.ALIGN_RIGHT );}
        
//        cell.setCellStyle(moneda);

        row = ws1.createRow(r+2);
        
        cell = row.createCell(1);
        
        //RFC
        cell.setCellValue( Helper.getRFCLabel( objeto.getPais() ) + " :" );
        	
        cell.setCellStyle(style);    
        
        cell = row.createCell(2);
        cell.setCellValue(objeto.getRfc());

        cell = row.createCell(4);
        cell.setCellValue(lblMain.getString("vencido"));
        cell.setCellStyle(style);
        if(isImporteToRight){cell.getCellStyle().setAlignment( CellStyle.ALIGN_RIGHT );}
        
        cell = row.createCell(5);
        cell.setCellValue( Helper.getMontoFormateado( objeto.getSaldoVencido(), objeto.getPais()) );        
//        cell.setCellStyle(moneda);

        row = ws1.createRow(r+3);
        
        cell = row.createCell(1);
        cell.setCellValue(lblMain.getString("telefono"));
        cell.setCellStyle(style);    
        
        cell = row.createCell(2);
        cell.setCellValue(objeto.getTel());

        cell = row.createCell(4);
        cell.setCellValue(lblMain.getString("vencer"));
        cell.setCellStyle(style);
        if(isImporteToRight){cell.getCellStyle().setAlignment( CellStyle.ALIGN_RIGHT );}
        
        cell = row.createCell(5);
        cell.setCellValue(Helper.getMontoFormateado( objeto.getSaldoVencer(), objeto.getPais()));
//        cell.setCellStyle(moneda);  
        
        row = ws1.createRow(r+4);
        
        cell = row.createCell(4);
        cell.setCellValue(lblMain.getString("saldoTotal"));
        cell.setCellStyle(style);
        if(isImporteToRight){cell.getCellStyle().setAlignment( CellStyle.ALIGN_RIGHT );}
        
        cell = row.createCell(5);
        cell.setCellValue(Helper.getMontoFormateado( objeto.getTotal(), objeto.getPais()));
//        cell.setCellStyle(moneda);  
        
        row = ws1.createRow(r+5);
        
        cell = row.createCell(4);
        cell.setCellValue(lblMain.getString("disponible"));
        cell.setCellStyle(style);
        if(isImporteToRight){cell.getCellStyle().setAlignment( CellStyle.ALIGN_RIGHT );}
        
        cell = row.createCell(5);
        cell.setCellValue(Helper.getMontoFormateado( objeto.getCreditoDisponible(), objeto.getPais()));
//        cell.setCellStyle(moneda);  
        
        row = ws1.createRow(r+6);
        
        cell = row.createCell(4);
        cell.setCellValue(lblMain.getString("fechaCorte"));
        cell.setCellStyle(style);    
        
        cell = row.createCell(5);
        cell.setCellValue(Fecha.getFechaFormateada(objeto.getFechaCorte(), 7));
        cell.setCellStyle(styleRight);
        row = ws1.createRow(r+7);
        
        cell = row.createCell(4);
        cell.setCellValue(lblMain.getString("fechaConsulta"));
        
        cell = row.createCell(5);
        cell.setCellValue(Fecha.getFechaFormateada(Fecha.getDateActual(), 7));
        cell.setCellStyle(styleRight);   

        row = ws1.createRow(r+8);
        
        cell = row.createCell(4);
        cell.setCellValue(lblMain.getString("horaConsulta"));
        
        cell = row.createCell(5);
        cell.setCellValue(Fecha.getFechaFormateada(Fecha.getDateActual(), 3));
        cell.setCellStyle(styleRight);   

        rowMail = ws1.getRow(r+4);
        cell = rowMail.createCell(1);
        cell.setCellValue(lblMain.getString("correos"));
        cell.setCellStyle(style);    

        String[] mailList = objeto.geteMail().split(";");
        
        int contMail = 0;
        for(String mail:mailList){
            
            cell = rowMail.createCell(2);
            cell.setCellValue(mail);
            contMail ++;
            rowMail = ws1.getRow(r+4+contMail);
        }
        
        if(!objeto.getPais().equals("BR")){
            cell = rowMail.createCell(1);
            cell.setCellValue(lblMain.getString("monedaEdoCta"));
            cell.setCellStyle(style);    
            
            cell = rowMail.createCell(2);
            cell.setCellValue(objeto.getMoneda());
        }

        
	}
	
	
	@Override
	protected void ejecutaListado(Sheet ws1, ResourceBundle lblMain) throws ClassNotFoundException, Exception {
        
        //Graba Encabezados de las columnas 
        this.grabaColHeaders(ws1, 10,"ESTADOCUENTA", lblMain);
                
        //Fila del excel en la que inicia poniendo valores
        int renglon=15;
        boolean mainRow = false;
        //int renglonMain = 0;

        Row row = null;
        Cell cell = null;        
        
        Font subTitleFont = this.wb.createFont();
        subTitleFont.setFontHeightInPoints((short)9);
        subTitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        CellStyle styleSub = this.wb.createCellStyle();
        styleSub.setFont(subTitleFont);
        
//        CellStyle moneda = this.wb.createCellStyle();
        
//        DataFormat df = this.wb.createDataFormat();
//        moneda.setDataFormat(df.getFormat("$#,##0.00_);-$#,##0.00"));        
        
        EstadoCuentaDet objeto = null;
        EstadoCuenta edoCta = (EstadoCuenta) this.getEncabezado();
        
        int col = 0; 
        
        //Filas
        for (Object reporteObject : this.getListado()) {
        	 try {
        		 objeto = (EstadoCuentaDet)reporteObject;
                      
        		logger.info("renglon: " + renglon);
                	                	                          
            	row = ws1.createRow(renglon);            	
            	col = 0;
            	if(edoCta.getPais() != null && edoCta.getPais().equals("AR")){
            		//Tipo de Documento
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getTipoDocumento());  
                    //Factura Fiscal
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNotaEntrega()); 
                    //No. Remito
                	cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNoFactFiscal());
                    
                	//Fecha Factura
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getFechaFactura()); 
                	//Vencimiento
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getFechaVenc());                                          
                	//Días Mora
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getDiasMora());                                      
                	//Importe
                    cell = row.createCell(col++);
                    cell.setCellValue(Helper.getMontoFormateado( objeto.getImporte(), edoCta.getPais()));
                    cell.getCellStyle().setAlignment( CellStyle.ALIGN_RIGHT );
//                    cell.setCellStyle(moneda);                                      
                	//Estatus
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getEstatus()); 
            	}else if(edoCta.getPais() != null && edoCta.getPais().equals("PE")){
            		//Tipo de Documento
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getTipoDocumento());
                    //No. Factura
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNotaEntrega()); 
            		//No. Letra
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNoPedido());
                    //Orden de Compra
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getOrdenCompra());
                    //Numero unico
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNoFactFiscal()); 
                    
                	//Fecha Factura
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getFechaFactura()); 
                	//Vencimiento
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getFechaVenc());                                          
                	//Días Mora
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getDiasMora());                                      
                	//Importe
                    cell = row.createCell(col++);
                    cell.setCellValue(Helper.getMontoFormateado( objeto.getImporte(), edoCta.getPais()));  
//                    cell.setCellStyle(moneda);                                      
                	//Estatus
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getEstatus());
            	}else if(edoCta.getPais() != null && edoCta.getPais().equals("GT")){
            		//Tipo de Documento
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getTipoDocumento());                    
                    //Orden de Compra
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getOrdenCompra());
                    //No. Pedido
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNoPedido());
                    //Nota de entrega
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getEntrega());                    
                    //Número de Autorización SAT
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNoFactFiscal());                    
                    //Factura Fiscal
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNoFactura());
                    
                	//Fecha Factura
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getFechaFactura());                                      
                	//Vencimiento
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getFechaVenc());                                          
                	//Días Mora
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getDiasMora());                                      
                	//Importe
                    cell = row.createCell(col++);
                    cell.setCellValue(Helper.getMontoFormateado( objeto.getImporte(), edoCta.getPais()));
                    cell.getCellStyle().setAlignment( CellStyle.ALIGN_RIGHT );
//                    cell.setCellStyle(moneda);                                      
                	//Estatus
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getEstatus());             		
            	}else if(edoCta.getPais() != null && (edoCta.getPais().equals("SV") || edoCta.getPais().equals("HN") || edoCta.getPais().equals("NI"))){
            		//Tipo de Documento
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getTipoDocumento());                    
                    //Orden de Compra
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getOrdenCompra());
                    //No. Pedido
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNoPedido());                  
                    //Factura Fiscal
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNotaEntrega());
                    
                	//Fecha Factura
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getFechaFactura());                                      
                	//Vencimiento
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getFechaVenc());                                          
                	//Días Mora
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getDiasMora());                                      
                	//Importe
                    cell = row.createCell(col++);
                    cell.setCellValue(Helper.getMontoFormateado( objeto.getImporte(), edoCta.getPais()));
                    cell.getCellStyle().setAlignment( CellStyle.ALIGN_RIGHT );
//                    cell.setCellStyle(moneda);                                      
                	//Estatus
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getEstatus());             		
            	}else if(edoCta.getPais() != null && edoCta.getPais().equals("CR")){
            		//Tipo de Documento
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getTipoDocumento());                    
                    //Orden de Compra
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getOrdenCompra());
                    //No. Pedido
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNoPedido());                  
                    //Factura Fiscal
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNoFactFiscal());
                    
                	//Fecha Factura
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getFechaFactura());                                      
                	//Vencimiento
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getFechaVenc());                                          
                	//Días Mora
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getDiasMora());                                      
                	//Importe
                    cell = row.createCell(col++);
                    cell.setCellValue(Helper.getMontoFormateado( objeto.getImporte(), edoCta.getPais()));
                    cell.getCellStyle().setAlignment( CellStyle.ALIGN_RIGHT );
//                    cell.setCellStyle(moneda);                                      
                	//Estatus
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getEstatus());             		
            	}else if(edoCta.getPais() != null && edoCta.getPais().equals("BR")){
            		//Tipo de Documento
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getTipoDocumento());                    
                    //Orden de Compra
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getOrdenCompra());
                    //No. Pedido
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNoPedido());                  
                    //Factura Fiscal
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNotaEntrega());
                    
                	//Fecha Factura
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getFechaFactura());                                      
                	//Vencimiento
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getFechaVenc());                                          
                	//Días Mora
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getDiasMora());                                      
                	//Importe
                    cell = row.createCell(col++);
                    cell.setCellValue(Helper.getMontoFormateado( objeto.getImporte(), edoCta.getPais()));
                    cell.getCellStyle().setAlignment( CellStyle.ALIGN_RIGHT );
//                    cell.setCellStyle(moneda);                                      
                	//Estatus
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getEstatus());             		
            	}else{
            		//Tipo de Documento
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getTipoDocumento());
                    //No. Pedido
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNoPedido());
                    //Orden de Compra
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNotaEntrega());
                    //Factura SAP
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNoFactura());
                    //Factura Fiscal
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getNoFactFiscal());
                    //Factura Relacionada
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getFacturaRelacionada());
                    //UUID Relacionado
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getUUIDRelacionado());                    
                    
                	//Fecha Factura
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getFechaFactura());                                      
                	//Vencimiento
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getFechaVenc());                                          
                	//Días Mora
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getDiasMora());                                      
                	//Importe
                    cell = row.createCell(col++);
                    cell.setCellValue(Helper.getMontoFormateado( objeto.getImporte(), edoCta.getPais()));  
//                    cell.setCellStyle(moneda);                                      
                	//Estatus
                    cell = row.createCell(col++);
                    cell.setCellValue(objeto.getEstatus()); 
            	}
            	                                           
        	 } catch(Exception e){
        		logger.error("Error al introducir informacion col " + " " + e.getLocalizedMessage(),e);
            } finally{
            	renglon++;
            }           
        }//end for pedidos
              
        
        //Ajustar las columnas al contenido
        for(int i = 0; i <= col; i++){
        	ws1.autoSizeColumn(i);
        }		
	}
}
