package com.fushionbaby.cms.controller.card.excel;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.aladingshop.alabao.model.AlabaoTurnOut;
import com.aladingshop.card.model.MemberCardBack;
import com.fushionbaby.cms.util.ExportExcelUtils;
import com.fushionbaby.common.util.date.DateFormat;

public class MemberCardBackExcelReport {
	/**
	 * 导出Excel
	 * @throws IOException 
	 */
	public void getReport(String fileName,List<MemberCardBack> memberCardBackList,HttpServletResponse response) throws IOException{
		ExportExcelUtils.setHeader(response, fileName);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("益多宝退卡记录导出列表Excel导出结果");
		
		sheet.setColumnWidth(((short)0), (short)(8*256));
		sheet.setColumnWidth(((short)1), (short)(25*256));
		sheet.setColumnWidth(((short)2), (short)(25*256));
		sheet.setColumnWidth(((short)3), (short)(25*256));
		sheet.setColumnWidth(((short)4), (short)(25*256));
		sheet.setColumnWidth(((short)5), (short)(25*256));
		sheet.setColumnWidth(((short)6), (short)(25*256));
		sheet.setColumnWidth(((short)7), (short)(25*256));
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
		for(MemberCardBack memberCardBack:memberCardBackList){
			int cellType = HSSFCell.CELL_TYPE_STRING;
			seqno++;
			HSSFRow row = sheet.createRow(rowNum++);
			ExportExcelUtils.createCell(row,0, centerStyle,cellType,seqno);
			ExportExcelUtils.createCell(row,1, centerStyle,cellType,memberCardBack.getAcount());
			ExportExcelUtils.createCell(row,2, centerStyle,cellType,memberCardBack.getCardNo());
			ExportExcelUtils.createCell(row,3, centerStyle,cellType,memberCardBack.getMoney());
			ExportExcelUtils.createCell(row,4, centerStyle,cellType,memberCardBack.getBankCardNo());
			ExportExcelUtils.createCell(row,5, centerStyle,cellType,memberCardBack.getBankCardHolder());
			ExportExcelUtils.createCell(row,6, centerStyle,cellType,memberCardBack.getBankName()+" "+memberCardBack.getBankBranchName());
			ExportExcelUtils.createCell(row,7, centerStyle,cellType,DateFormat.dateToString(memberCardBack.getCreateTime()));
			String backStatus="";
	    	if("1".equals(memberCardBack.getBackStatus())){
	    		backStatus="待审核";
	    	}else if("2".equals(memberCardBack.getBackStatus())){
	    		backStatus="审核通过";
	    	}else if("3".equals(memberCardBack.getBackStatus())){
	    		backStatus="审核不通过";
	    	}else if("4".equals(memberCardBack.getBackStatus())){
	    		backStatus="交易完成";
	    	}else if("5".equals(memberCardBack.getBackStatus())){
	    		backStatus="已转入如意宝";
	    	}
			ExportExcelUtils.createCell(row,8, centerStyle,cellType,backStatus);
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
		ExportExcelUtils.createCell(headRow,1, boldStyle,cellType,"如意宝账号");
		ExportExcelUtils.createCell(headRow,2, boldStyle,cellType,"阿拉丁卡号");
		ExportExcelUtils.createCell(headRow,3, boldStyle,cellType,"退卡面额");
		ExportExcelUtils.createCell(headRow,4, boldStyle,cellType,"银行卡卡号");
		ExportExcelUtils.createCell(headRow,5, boldStyle,cellType,"银行卡持卡人");
		ExportExcelUtils.createCell(headRow,6, boldStyle,cellType,"银行名称");
		ExportExcelUtils.createCell(headRow,7, boldStyle,cellType,"退卡申请时间");
		ExportExcelUtils.createCell(headRow,8, boldStyle,cellType,"处理状态");
	}
}
