package com.fushionbaby.cms.controller.order.excel;

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

public class MemberExcelReport {
	/**
	 * 导出Excel
	 * @throws IOException 
	 */
	public void getReport(String fileName,List<Member> memberList,HttpServletResponse response) throws IOException{
		ExportExcelUtils.setHeader(response, fileName);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("会员查询导出结果");
		
		sheet.setColumnWidth(((short)0), (short)(8*256));
		sheet.setColumnWidth(((short)1), (short)(25*256));
		sheet.setColumnWidth(((short)2), (short)(15*256));
		sheet.setColumnWidth(((short)3), (short)(15*256));
		sheet.setColumnWidth(((short)4), (short)(25*256));
		sheet.setColumnWidth(((short)5), (short)(10*256));
		sheet.setColumnWidth(((short)6), (short)(10*256));
		sheet.setColumnWidth(((short)7), (short)(15*256));
		sheet.setColumnWidth(((short)8), (short)(25*256));
		sheet.setColumnWidth(((short)9), (short)(50*256));

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
		for(Member member:memberList){
			int cellType = HSSFCell.CELL_TYPE_STRING;
			seqno++;
			HSSFRow row = sheet.createRow(rowNum++);
			ExportExcelUtils.createCell(row,0, centerStyle,cellType,seqno);//序号
			ExportExcelUtils.createCell(row,1, centerStyle,cellType,member.getLoginName());//用户名
			String openId=member.getOpenId();
			if(openId==null){
				openId="非第三方用户";
			}
			ExportExcelUtils.createCell(row,2, centerStyle,cellType,openId);//第三方用户标识（openid）
			
			ExportExcelUtils.createCell(row,3, centerStyle,cellType,member.getTelephone());//手机号
			ExportExcelUtils.createCell(row,4, centerStyle,cellType,member.getEmail());//邮箱
			ExportExcelUtils.createCell(row,5, centerStyle,cellType,member.getEpoints());//会员积分
			String sourceCode=member.getSourceCode();
			String sourceName="APP端";
			if(SourceConstant.ANDROID_CODE.equals(sourceCode)){
				sourceName="ANDROID客户端";
			}else if(SourceConstant.CMS_CODE.equals(sourceCode)){
				sourceName="CMS管理系统";
			}else if(SourceConstant.ERP_CODE.equals(sourceCode)){
				sourceName="ERP管理系统";
			}else if(SourceConstant.IOS_CODE.equals(sourceCode)){
				sourceName="IOS客户端";
			}else if(SourceConstant.OPERATE_CODE.equals(sourceCode)){
				sourceName="运营后台";
			}else if(SourceConstant.TIMING_TASK_CODE.equals(sourceCode)){
				sourceName="定时任务";
			}else if(SourceConstant.WEB_CODE.equals(sourceCode)){
				sourceName="商城WEB端";
			}
			ExportExcelUtils.createCell(row,6, centerStyle,cellType,sourceName);//注册来源
			
			String channelCode=member.getChannelCode();
			String channel="默认";
			if(ChannelConstant.QQ_CHANNEL.equals(channelCode)){
				channel="QQ";
			}else if(ChannelConstant.WX_CHANNEL.equals(channelCode)){
				channel="微信";
			}else if(ChannelConstant.SINA_CHANNEL.equals(channelCode)){
				channel="新浪微博";
			}
			ExportExcelUtils.createCell(row,7, centerStyle,cellType,channel);//注册渠道
			String createTimeStr = DateFormat.dateToString(member.getCreateTime());
			ExportExcelUtils.createCell(row,8, centerStyle,cellType,createTimeStr);//注册时间
			String disable="启用";
			if("y".equals(member.getDisable())){
				disable="禁用";
			}
			ExportExcelUtils.createCell(row,9, centerStyle,cellType,disable);//会员状态
			
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
		ExportExcelUtils.createCell(headRow,1, boldStyle,cellType,"用户名");
		ExportExcelUtils.createCell(headRow,2, boldStyle,cellType,"第三方用户标识(openid)");
		ExportExcelUtils.createCell(headRow,3, boldStyle,cellType,"手机号");
		ExportExcelUtils.createCell(headRow,4, boldStyle,cellType,"邮箱");
		ExportExcelUtils.createCell(headRow,5, boldStyle,cellType,"会员积分");
		ExportExcelUtils.createCell(headRow,6, boldStyle,cellType,"注册来源");
		ExportExcelUtils.createCell(headRow,7, boldStyle,cellType,"注册渠道");
		ExportExcelUtils.createCell(headRow,8, boldStyle,cellType,"注册时间");
		ExportExcelUtils.createCell(headRow,9, boldStyle,cellType,"会员状态");
	}
}
