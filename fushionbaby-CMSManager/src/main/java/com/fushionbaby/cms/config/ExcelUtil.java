package com.fushionbaby.cms.config;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * TODO  excel 批量导入数据  例子，需要需改
 * @author 孟少博
 *
 */
public class ExcelUtil {
	/**
	 * 支持97-2003的excel 导入 
	 * @param filePath
	 * @return
	 */
	 public static boolean readExcel(String filePath){
		 
		try {
			InputStream is = new FileInputStream(filePath);
			@SuppressWarnings("resource")
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			HSSFSheet sheet = workbook.getSheetAt(0);/** 获取sheet1中的表格*/
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				HSSFRow row = sheet.getRow(i);
				String productCode = row.getCell(0).getStringCellValue();
				String productName = row.getCell(1).getStringCellValue();
				String skuCode = row.getCell(2).getStringCellValue();
				String skuName = row.getCell(3).getStringCellValue();
				System.out.println("产品编号："+productCode +";产品名称:"+productName + ";商品编号"+skuCode+";商品名称"+skuName);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
		 
	 }
	 /**
	  * 支持2007 excel 的导入
	  * @param filePath
	  * @return
	  */
	 public static boolean readExcels(String filePath){
		 
		try {
			InputStream is = new FileInputStream(filePath);
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheetAt(0);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				XSSFRow row = sheet.getRow(i);
				String productCode = row.getCell(0).getStringCellValue();
				String productName = row.getCell(1).getStringCellValue();
				String skuCode = row.getCell(2).getStringCellValue();
				String skuName = row.getCell(3).getStringCellValue();
				System.out.println("产品编号："+productCode +";产品名称:"+productName + ";商品编号"+skuCode+";商品名称"+skuName);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
		 
	 }
	 public static void main(String[] args) {
		 readExcel("E://sku.xls");
		 readExcels("E://product.xlsx");
	}
	
}
