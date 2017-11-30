/**
 * 
 */
package com.boco.util;

import java.io.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.Iterator;

/**
 * @author jack
 *
 */
public class ExeclToPdf {

	
	/**
	 * 把EXECL的输入流转化为pdf的输入流
	 * 解决中文不显示问题
	 * 兼容2003和2007
	 * 这里因为项目需要，如果想生成pdf文件请把第128行代码删掉
	 * 	// 设置字体，因为itext中并没有中文字体，
	 //所以我们在Windows上找了个中文字体,加入到项目中
	 //这里我们需要在项目的resource下加入中文字体库
	 //我这里加入的是Windows下的SIMKAI.TTF字体库
	 * @param in
	 * @param sroucePath
	 * @param Destpath
	 * @return
	 * @throws DocumentException
	 * @throws FileNotFoundException
	 */
	public static InputStream Execl2Pdf(InputStream in,String sroucePath,String Destpath)
			throws DocumentException, FileNotFoundException {

		File file = new File(Destpath);

		try {
			Workbook workbook =null;
			
			 if(sroucePath.endsWith("pdf")){  
	                //2003  
	                workbook = new HSSFWorkbook(in);  
	            }else if(sroucePath.endsWith("xlsx")){  
	                //2007  
	                workbook = new XSSFWorkbook(in);  
	            }  

			// 1.新建document对象
			// 第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
			Document iText_xls_2_pdf = new Document();
			OutputStream out = new FileOutputStream(Destpath);
			
			// 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
			PdfWriter.getInstance(iText_xls_2_pdf, out);
			
			// 设置字体，因为itext中并没有中文字体，
			//所以我们在Windows上找了个中文字体,加入到项目中
			//这里我们需要在项目的resource下加入中文字体库
			//我这里加入的是Windows下的SIMKAI.TTF字体库
			BaseFont bfChinese = BaseFont.createFont("/SIMKAI.TTF",
					BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			Font font = new Font(bfChinese, 10, Font.NORMAL);
			// 3.打开文档
			iText_xls_2_pdf.open();

			// 得到execl表总表数
			int sheetSize = workbook.getNumberOfSheets();
			PdfPCell table_cell;
			
			for (int i = 0; i < sheetSize; i++) {
				Sheet my_worksheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = my_worksheet.iterator();
				
				while (rowIterator.hasNext()) {
					
					Row row = rowIterator.next();
					//每行的列数
					int num=row.getPhysicalNumberOfCells();
					//如果当前的列数为0就跳过当次循环
					if (num==0) {
						continue;
					}
					PdfPTable my_table = new PdfPTable(num);
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							table_cell = new PdfPCell(new Paragraph(
									cell.getStringCellValue(), font));
							my_table.addCell(table_cell);
							break;
						}
					}
					iText_xls_2_pdf.add(my_table);
				
				}

			

			}
			iText_xls_2_pdf.close();
			in.close();
			System.out.println("file created pdf");
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("file not created pdf");
		} catch (IOException es) {
			es.printStackTrace();
		}

		InputStream inPdf = new FileInputStream(Destpath);
		if (file.exists() && file.isFile()) { file.delete(); }
		
		return inPdf;

	}

	public static void main(String[] args) throws FileNotFoundException,
			DocumentException {
		InputStream in = new FileInputStream(
				"C:\\Users\\jack\\Desktop\\123.xlsx");

	ExeclToPdf.Execl2Pdf(in,"C:\\Users\\jack\\Desktop\\123.xlsx", 
			"C:\\Users\\jack\\Desktop\\666.pdf");
	}
}
