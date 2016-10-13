package com.fushionbaby.cms.controller.alabao.excel;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.fushionbaby.cms.util.ExportExcelUtils;
import com.fushionbaby.common.enums.AlabaoAccountLevelEnum;
import com.fushionbaby.common.enums.AlabaoAccountStatusEnum;
import com.fushionbaby.common.util.date.DateFormat;

public class AlabaoAccountExcelReport {
	/**
	 * 导出Excel
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	public void getReport(String fileName,List<AlabaoAccount> alabaoAccountList,HttpServletResponse response) throws IOException{
		ExportExcelUtils.setHeader(response, fileName);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("如意宝账号导出结果");
		
		sheet.setColumnWidth(((short)0), (short)(8*256));
		sheet.setColumnWidth(((short)1), (short)(25*256));
		sheet.setColumnWidth(((short)2), (short)(15*256));
		sheet.setColumnWidth(((short)3), (short)(15*256));
		sheet.setColumnWidth(((short)4), (short)(25*256));
		sheet.setColumnWidth(((short)5), (short)(10*256));
		sheet.setColumnWidth(((short)6), (short)(10*256));
		sheet.setColumnWidth(((short)7), (short)(15*256));
		sheet.setColumnWidth(((short)8), (short)(20*256));
		sheet.setColumnWidth(((short)9), (short)(25*256));
		sheet.setColumnWidth(((short)10), (short)(20*256));
		sheet.setColumnWidth(((short)11), (short)(20*256));

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
		for(AlabaoAccount alabaoAccount:alabaoAccountList){
			int cellType = HSSFCell.CELL_TYPE_STRING;
			seqno++;
			HSSFRow row = sheet.createRow(rowNum++);
			ExportExcelUtils.createCell(row,0, centerStyle,cellType,seqno);//序号
			ExportExcelUtils.createCell(row,1, centerStyle,cellType,alabaoAccount.getAccount());//账号
			ExportExcelUtils.createCell(row,2, centerStyle,cellType,alabaoAccount.getMemberName());//会员名称
			ExportExcelUtils.createCell(row,3, centerStyle,cellType,alabaoAccount.getTrueName());//真实姓名
			ExportExcelUtils.createCell(row,4, centerStyle,cellType,alabaoAccount.getIdentityCardNo());//身份证号
			ExportExcelUtils.createCell(row,5, centerStyle,cellType,alabaoAccount.getLockedBalance());//锁定余额
			ExportExcelUtils.createCell(row,6, centerStyle,cellType,alabaoAccount.getBalance());//阿拉宝余额
			ExportExcelUtils.createCell(row,7, centerStyle,cellType,alabaoAccount.getYesterdayIncome());//昨日收益
			ExportExcelUtils.createCell(row,8, centerStyle,cellType,alabaoAccount.getTotalIncome());//总收益
			String createTime = ObjectUtils.equals(null, alabaoAccount.getCreateTime()) ? StringUtils.EMPTY :  DateFormat.dateToString(alabaoAccount.getCreateTime());
			ExportExcelUtils.createCell(row,9, centerStyle,cellType,createTime);//创建时间
			
			ExportExcelUtils.createCell(row,10, centerStyle,cellType,AlabaoAccountStatusEnum.parseCode(alabaoAccount.getStatus()));//状态
			
			ExportExcelUtils.createCell(row,11, centerStyle,cellType,AlabaoAccountLevelEnum.parseCode(alabaoAccount.getLevel()));//账号等级
			
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
		ExportExcelUtils.createCell(headRow,1, boldStyle,cellType,"账号");
		ExportExcelUtils.createCell(headRow,2, boldStyle,cellType,"会员名称");
		ExportExcelUtils.createCell(headRow,3, boldStyle,cellType,"真实姓名");
		ExportExcelUtils.createCell(headRow,4, boldStyle,cellType,"身份证号");
		ExportExcelUtils.createCell(headRow,5, boldStyle,cellType,"锁定余额");
		ExportExcelUtils.createCell(headRow,6, boldStyle,cellType,"阿拉宝余额");
		ExportExcelUtils.createCell(headRow,7, boldStyle,cellType,"昨日收益");
		ExportExcelUtils.createCell(headRow,8, boldStyle,cellType,"总收益");
		ExportExcelUtils.createCell(headRow,9, boldStyle,cellType,"创建时间");
		ExportExcelUtils.createCell(headRow,10, boldStyle,cellType,"状态");
		ExportExcelUtils.createCell(headRow,11, boldStyle,cellType,"账号等级");
	}
}
