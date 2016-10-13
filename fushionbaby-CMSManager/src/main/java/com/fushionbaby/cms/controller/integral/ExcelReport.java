package com.fushionbaby.cms.controller.integral;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.fushionbaby.cms.util.ExportExcelUtils;
import com.fushionbaby.common.constants.ChannelConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.order.model.OrderEpoint;

public class ExcelReport {
	/**
	 * 导出Excel
	 * @throws IOException 
	 */
	public void getReport(String fileName,List<OrderEpoint> orderEpointList,HttpServletResponse response) throws IOException{
		ExportExcelUtils.setHeader(response, fileName);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("积分查询导出结果");
		
		sheet.setColumnWidth(((short)0), (short)(8*256));
		sheet.setColumnWidth(((short)1), (short)(25*256));
		sheet.setColumnWidth(((short)2), (short)(15*256));
		sheet.setColumnWidth(((short)3), (short)(15*256));
		sheet.setColumnWidth(((short)4), (short)(25*256));
		sheet.setColumnWidth(((short)5), (short)(10*256));
		sheet.setColumnWidth(((short)6), (short)(10*256));
		sheet.setColumnWidth(((short)7), (short)(15*256));
		sheet.setColumnWidth(((short)8), (short)(25*256));

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
		for(OrderEpoint orderEpoint:orderEpointList){
			int cellType = HSSFCell.CELL_TYPE_STRING;
			seqno++;
			HSSFRow row = sheet.createRow(rowNum++);
			ExportExcelUtils.createCell(row,0, centerStyle,cellType,seqno);//序号
			ExportExcelUtils.createCell(row,1, centerStyle,cellType,orderEpoint.getOrderCode());//订单编号
			ExportExcelUtils.createCell(row,2, centerStyle,cellType,orderEpoint.getSkuCode());//商品编码
			ExportExcelUtils.createCell(row,3, centerStyle,cellType,orderEpoint.getSkuName());//商品名称
			ExportExcelUtils.createCell(row,4, centerStyle,cellType,orderEpoint.getQuantity());//商品数量
			ExportExcelUtils.createCell(row,5, centerStyle,cellType,orderEpoint.getOrderStatus());//商品状态
			ExportExcelUtils.createCell(row,6, centerStyle,cellType,orderEpoint.getSourceCode());//订单来源
			ExportExcelUtils.createCell(row,7, centerStyle,cellType,orderEpoint.getTotalEpointUsed());//使用积分
			String createTimeStr = DateFormat.dateToString(orderEpoint.getCreateTime());
			ExportExcelUtils.createCell(row,8, centerStyle,cellType,createTimeStr);//创建时间
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
		//
		String names[] ={"序号","订单编号","商品编码","商品名称","商品数量","商品状态","订单来源","使用积分","创建时间"};
		for(int i=0;i<names.length;i++){
			ExportExcelUtils.createCell(headRow,i, boldStyle,cellType,names[i]);
		}		
	}
}
