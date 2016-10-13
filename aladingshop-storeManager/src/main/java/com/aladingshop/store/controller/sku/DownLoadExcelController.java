package com.aladingshop.store.controller.sku;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aladingshop.store.controller.BaseController;
import com.aladingshop.store.util.DownLoadExcelUtil;
import com.fushionbaby.common.util.BasePagination;


@Controller
@RequestMapping("/excel")
public class DownLoadExcelController extends BaseController {

	
	private static final Log LOGGER = LogFactory.getLog(DownLoadExcelController.class);
	@RequestMapping(value="downLoadExcel")
	public void downLoadExcel(BasePagination page, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		try {
			String fileName=request.getParameter("fileName");
			byte bb[] = fileName.getBytes("ISO-8859-1");
			fileName = new String(bb,"UTF-8");
			DownLoadExcelUtil.downLoadExcel(fileName,request, response);
		} catch (Exception e) {
			LOGGER.error("下载Excel加载失败", e);
		}
		
	}

}
