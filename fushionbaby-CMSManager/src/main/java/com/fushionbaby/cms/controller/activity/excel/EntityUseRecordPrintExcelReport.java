package com.fushionbaby.cms.controller.activity.excel;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.fushionbaby.act.activity.dto.EntityCardUseRecordDto;
import com.fushionbaby.cms.util.ExportExcelUtils;

/***
 * 打印小票的导出
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年12月15日下午2:29:22
 */
public class EntityUseRecordPrintExcelReport {
	
	private static final Log LOGGER=LogFactory.getLog(EntityUseRecordPrintExcelReport.class);
	/**
	 * 导出Excel
	 * @throws IOException 
	 */
	public void getReport(String fileName,EntityCardUseRecordDto record,HttpServletResponse response) throws IOException{
		ExportExcelUtils.setHeader(response, fileName);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("打印实体卡交易小票导出excel");
		
		sheet.setColumnWidth(((short)0), (short)(8*256));

		HSSFCellStyle centerStyle = ExportExcelUtils.createTextStyle(wb);
//		centerStyle.setBorderTop(HSSFCellStyle.SOLID_FOREGROUND);
//		centerStyle.setBorderLeft(HSSFCellStyle.SOLID_FOREGROUND);
//		centerStyle.setBorderBottom(HSSFCellStyle.SOLID_FOREGROUND);
//		centerStyle.setBorderRight(HSSFCellStyle.SOLID_FOREGROUND);
//		centerStyle.setTopBorderColor(HSSFColor.BLACK.index);
//		centerStyle.setLeftBorderColor(HSSFColor.BLACK.index);
//		centerStyle.setRightBorderColor(HSSFColor.BLACK.index);
//		centerStyle.setBottomBorderColor(HSSFColor.BLACK.index);
//		centerStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//		
//		HSSFCellStyle leftStyle = ExportExcelUtils.createTextStyle(wb);
//		leftStyle.setBorderTop(HSSFCellStyle.SOLID_FOREGROUND);
//		leftStyle.setBorderLeft(HSSFCellStyle.SOLID_FOREGROUND);
//		leftStyle.setBorderBottom(HSSFCellStyle.SOLID_FOREGROUND);
//		leftStyle.setBorderRight(HSSFCellStyle.SOLID_FOREGROUND);
//		leftStyle.setTopBorderColor(HSSFColor.BLACK.index);
//		leftStyle.setLeftBorderColor(HSSFColor.BLACK.index);
//		leftStyle.setRightBorderColor(HSSFColor.BLACK.index);
//		leftStyle.setBottomBorderColor(HSSFColor.BLACK.index);
//		leftStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//		leftStyle.setWrapText(true);
		this.createHead(wb,sheet);
		
		//填充数据
		int rowNum = 1;
		int seqno=0;
		try {
		  Field[] fields = record.getClass().getDeclaredFields(); 
		  for(Field f : fields){
		   int cellType = HSSFCell.CELL_TYPE_STRING;
				seqno++;
				HSSFRow row = sheet.createRow(rowNum++);
		   String name=f.getName();
		   String name2 = name.substring(0,1).toUpperCase()+name.substring(1); //将属性的首字符大写，方便构造get，set方法
              Method m = record.getClass().getMethod("get"+name2);
              String value = (String) m.invoke(record);    //调用getter方法获取属性值
              LOGGER.info("导出打印小票的信息，类属性为"+f.getName()+"属性值为:"+value);
              ExportExcelUtils.createCell(row,0, centerStyle,cellType,value);
		  }
		
		} catch (Exception e) {
			LOGGER.error("导出打印小票异常", e);
		}
		//导出
		ExportExcelUtils.release(response, wb);
	}
	
	/**
	 * 创建标题列
	 * @param sheet
	 * @param headStyle
	 * @param resources
	 */
	private void createHead(HSSFWorkbook wb,HSSFSheet sheet){
		HSSFCellStyle boldStyle = ExportExcelUtils.createBoldStyle(wb);
//		boldStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);//前景颜色
//		boldStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//填充方式，前色填充
//		boldStyle.setBorderTop(HSSFCellStyle.SOLID_FOREGROUND);
//		boldStyle.setBorderLeft(HSSFCellStyle.SOLID_FOREGROUND);
//		boldStyle.setBorderBottom(HSSFCellStyle.SOLID_FOREGROUND);
//		boldStyle.setBorderRight(HSSFCellStyle.SOLID_FOREGROUND);
//		boldStyle.setTopBorderColor(HSSFColor.BLACK.index);
//		boldStyle.setLeftBorderColor(HSSFColor.BLACK.index);
//		boldStyle.setRightBorderColor(HSSFColor.BLACK.index);
//		boldStyle.setBottomBorderColor(HSSFColor.BLACK.index);
//		boldStyle.setWrapText(true);
		
		HSSFRow headRow = sheet.createRow(0);
		int cellType = HSSFCell.CELL_TYPE_STRING;
	    ExportExcelUtils.createCell(headRow,0, boldStyle,cellType,"实体卡消费小票");
//		ExportExcelUtils.createCell(headRow,1, boldStyle,cellType,"如意宝账号");
//		ExportExcelUtils.createCell(headRow,2, boldStyle,cellType,"阿拉丁卡号");
//		ExportExcelUtils.createCell(headRow,3, boldStyle,cellType,"退卡面额");
//		ExportExcelUtils.createCell(headRow,4, boldStyle,cellType,"银行卡卡号");
//		ExportExcelUtils.createCell(headRow,5, boldStyle,cellType,"银行卡持卡人");
//		ExportExcelUtils.createCell(headRow,6, boldStyle,cellType,"银行名称");
//		ExportExcelUtils.createCell(headRow,7, boldStyle,cellType,"退卡申请时间");
//		ExportExcelUtils.createCell(headRow,8, boldStyle,cellType,"处理状态");
	}
}
