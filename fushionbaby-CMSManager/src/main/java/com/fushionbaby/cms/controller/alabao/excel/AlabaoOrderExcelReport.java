package com.fushionbaby.cms.controller.alabao.excel;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.aladingshop.alabao.dto.AlabaoOrderCMSDto;
import com.fushionbaby.cms.util.ExportExcelUtils;

public class AlabaoOrderExcelReport {
	/**
	 * 导出Excel
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	public void getReport(String fileName,List<AlabaoOrderCMSDto> orderList,HttpServletResponse response) throws IOException{
		ExportExcelUtils.setHeader(response, fileName);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("阿拉宝订单查询导出结果");
		
		sheet.setColumnWidth(((short)0), (short)(8*256));
		sheet.setColumnWidth(((short)1), (short)(25*256));
		sheet.setColumnWidth(((short)2), (short)(15*256));
		sheet.setColumnWidth(((short)3), (short)(15*256));
		sheet.setColumnWidth(((short)4), (short)(20*256));
		sheet.setColumnWidth(((short)5), (short)(20*256));
		sheet.setColumnWidth(((short)6), (short)(20*256));
		sheet.setColumnWidth(((short)7), (short)(25*256));
		sheet.setColumnWidth(((short)8), (short)(25*256));
		sheet.setColumnWidth(((short)9), (short)(25*256));
		

		HSSFCellStyle centerStyle = ExportExcelUtils.createTextStyle(wb);
		centerStyle.setBorderTop(HSSFCellStyle.SOLID_FOREGROUND);
		centerStyle.setBorderLeft(HSSFCellStyle.SOLID_FOREGROUND);
		centerStyle.setBorderBottom(HSSFCellStyle.SOLID_FOREGROUND);
		centerStyle.setBorderRight(HSSFCellStyle.SOLID_FOREGROUND);
		centerStyle.setTopBorderColor(HSSFColor.BLACK.index);
		centerStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		centerStyle.setRightBorderColor(HSSFColor.BLACK.index);
		centerStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		centerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFCellStyle leftStyle = ExportExcelUtils.createTextStyle(wb);
		leftStyle.setBorderTop(HSSFCellStyle.SOLID_FOREGROUND);
		leftStyle.setBorderLeft(HSSFCellStyle.SOLID_FOREGROUND);
		leftStyle.setBorderBottom(HSSFCellStyle.SOLID_FOREGROUND);
		leftStyle.setBorderRight(HSSFCellStyle.SOLID_FOREGROUND);
		leftStyle.setTopBorderColor(HSSFColor.BLACK.index);
		leftStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		leftStyle.setRightBorderColor(HSSFColor.BLACK.index);
		leftStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		leftStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		leftStyle.setWrapText(true);
		this.createHead(wb,sheet);
		
		//填充数据
		int rowNum = 1;
		int seqno=0;
		for(AlabaoOrderCMSDto alabaoOrderCMSDto:orderList){
			int cellType = HSSFCell.CELL_TYPE_STRING;
			seqno++;
			HSSFRow row = sheet.createRow(rowNum++);
			ExportExcelUtils.createCell(row,0, centerStyle,cellType,seqno);//序号
			ExportExcelUtils.createCell(row,1, centerStyle,cellType,alabaoOrderCMSDto.getAccount());//如意消费卡号
			ExportExcelUtils.createCell(row,2, centerStyle,cellType,alabaoOrderCMSDto.getAlabaoStatus());//转入财务状态
			ExportExcelUtils.createCell(row,3, centerStyle,cellType,alabaoOrderCMSDto.getMemberName());//会员姓名
			ExportExcelUtils.createCell(row,4, centerStyle,cellType,alabaoOrderCMSDto.getOrderCode());//转入订单号
			ExportExcelUtils.createCell(row,5, centerStyle,cellType,alabaoOrderCMSDto.getPaymentType());//支付方式
			ExportExcelUtils.createCell(row,6, centerStyle,cellType,alabaoOrderCMSDto.getTotalActual());//交易金额
			ExportExcelUtils.createCell(row,7, centerStyle,cellType,alabaoOrderCMSDto.getCreateTimeTo());//交易时间
			ExportExcelUtils.createCell(row,8, centerStyle,cellType,alabaoOrderCMSDto.getOrderNumber());//平台交易号
			ExportExcelUtils.createCell(row,9, centerStyle,cellType,alabaoOrderCMSDto.getTradeNo());//支付流水号
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
		boldStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);//前景颜色
		boldStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//填充方式，前色填充
		boldStyle.setBorderTop(HSSFCellStyle.SOLID_FOREGROUND);
		boldStyle.setBorderLeft(HSSFCellStyle.SOLID_FOREGROUND);
		boldStyle.setBorderBottom(HSSFCellStyle.SOLID_FOREGROUND);
		boldStyle.setBorderRight(HSSFCellStyle.SOLID_FOREGROUND);
		boldStyle.setTopBorderColor(HSSFColor.BLACK.index);
		boldStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		boldStyle.setRightBorderColor(HSSFColor.BLACK.index);
		boldStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		boldStyle.setWrapText(true);
		
		HSSFRow headRow = sheet.createRow(0);
		int cellType = HSSFCell.CELL_TYPE_STRING;
		ExportExcelUtils.createCell(headRow,0, boldStyle,cellType,"序号");
		ExportExcelUtils.createCell(headRow,1, boldStyle,cellType,"如意消费卡账号");
		ExportExcelUtils.createCell(headRow,2, boldStyle,cellType,"转入财务状态");
		ExportExcelUtils.createCell(headRow,3, boldStyle,cellType,"会员姓名");
		ExportExcelUtils.createCell(headRow,4, boldStyle,cellType,"转入订单号");
		ExportExcelUtils.createCell(headRow,5, boldStyle,cellType,"转入（支付）方式");
		ExportExcelUtils.createCell(headRow,6, boldStyle,cellType,"交易金额");
		ExportExcelUtils.createCell(headRow,7, boldStyle,cellType,"交易时间");
		ExportExcelUtils.createCell(headRow,8, boldStyle,cellType,"平台交易号");
		ExportExcelUtils.createCell(headRow,9, boldStyle,cellType,"支付流水号");
	}
}
