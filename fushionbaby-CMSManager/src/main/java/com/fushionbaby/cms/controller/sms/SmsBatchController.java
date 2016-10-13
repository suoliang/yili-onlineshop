package com.fushionbaby.cms.controller.sms;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;

/***
 * 批量发送短信
 * @author King 索亮
 *
 */
@Controller
@RequestMapping("/smsBatch")
public class SmsBatchController {
	
	/**短信表*/
	@Autowired
	private SmsService<Sms> smsService;
	
	@RequestMapping("sendBatchSms")
	public String gotoSendBatchSms(){
		return "sms/sendBatchSms";
	}
	
	@SuppressWarnings("resource")
	@RequestMapping("importSmsExcel")
	public Object importSmsExcel(
			@RequestParam(value="sms_excel",required=false)MultipartFile member_excel,
			String content){
		try {
			if (member_excel != null && member_excel.getSize() > 0 ) {
				// 进行Excel解析
				// 1、 工作薄对象
				HSSFWorkbook hssfWorkbook = new HSSFWorkbook(member_excel.getInputStream());
				// 解析工作薄
				hssfWorkbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK); // 避免空指针异常
				// 2、 获得Sheet
				HSSFSheet sheet = hssfWorkbook.getSheetAt(0); // 获得第一个sheet
				// 3、解析Sheet中每一行
				for (Row row : sheet) {
					// 进行解析 ， 每一行数据，对应 会员手机信息
					if (row.getRowNum() == 0) {// 第一行（表头，无需解析）
						continue;
					}
					//从第二行开始解析
					String telephone = row.getCell(0).toString(); // 获得第一个单元格信息
					telephone = telephone.trim();
					if (telephone.equals("")) {
						// id 无值，跳过
						continue;
					}
					smsService.sendSmsUserDefined(telephone, content, SourceConstant.CMS_CODE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Jsonp.success();
	}
}
