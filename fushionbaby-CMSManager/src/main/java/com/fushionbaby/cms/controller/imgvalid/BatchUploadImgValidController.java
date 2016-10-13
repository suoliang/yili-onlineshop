package com.fushionbaby.cms.controller.imgvalid;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cms.opdic.BatchUpLoadValid;
import com.fushionbaby.cms.util.bean.BatchUploadImageResult;
import com.fushionbaby.cms.util.bean.UploadResult;
import com.fushionbaby.cms.util.constant.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 批量上传图片检测controller
 * 
 * @author suntao
 *
 */
@Controller
@RequestMapping("/batchValid")
public class BatchUploadImgValidController {
	@ResponseBody
	@RequestMapping(value = "validImg", method = RequestMethod.POST)
	public Object batchImgValidRequest(@RequestParam("rootPath") String rootPath) {
		// 验证地址是否正确
		File file = new File(rootPath);
		if (!file.exists()) {
			return new BatchUploadImageResult(Constant.PATH_EXIST);
		}

		List<String> sucResults = new ArrayList<String>();
		List<UploadResult> failResults = new ArrayList<UploadResult>();
		BatchUploadImageResult batchUploadImageResult = new BatchUploadImageResult();
		batchUploadImageResult = BatchUpLoadValid.getValidResult(rootPath,
				sucResults, failResults);

		return batchUploadImageResult;
	}

	/***
	 * 批量上传图片预检测
	 * 
	 * @param skuBrandDto
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("initValidImages")
	public String initValidImagesRequest() {
		return "models/valid/img-valid";
	}

	/**
	 * 批量上传图片检测结果展示
	 * 
	 * @param jsonStr
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("showUploadImageValidResult")
	public String initBatchImagesPage(@RequestParam("jsonStr") String jsonStr,
			ModelMap modelMap) {
		try {
			String convertStr = URLDecoder.decode(jsonStr, "utf-8");
			Gson gson = new Gson();
			BatchUploadImageResult result = gson.fromJson(convertStr,
					new TypeToken<BatchUploadImageResult>() {
					}.getType());
			modelMap.addAttribute("result", result);
		} catch (Exception e) {
			return "models/error/error";
		}

		return "models/valid/img-result";
	}
}
