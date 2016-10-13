package com.fushionbaby.cms.controller.article;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fushionbaby.cms.util.ImageConstantCms;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.FileTools;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.sysactivity.model.SysActivityWill;
import com.fushionbaby.sysactivity.service.SysActivityWillService;

/**
 * 
 * @author xupeijun
 * 
 */
@Controller
@RequestMapping("/fushionbaby")
public class FushionBabyArticleController {
	/** 风尚宝贝志 */
	@Autowired
	private SysActivityWillService<SysActivityWill> willService;

	private static final Log logg = LogFactory.getLog(FushionBabyArticleController.class);

	/***
	 * 风尚宝贝的列表
	 * 
	 * @param author
	 * @param title
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("fushionBabyList")
	public String findAll(

			BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			page = this.willService.getListPageOne(page);
			// 分页结果
			List<SysActivityWill> fushionBabyList = (List<SysActivityWill>) page
					.getResult();
			model.addAttribute("fushionBabyList", fushionBabyList);
			model.addAttribute("fushionbaby_path", ImageConstantCms.SYS_ACTIVITY_FUSHIONBABY_SERVER_PATH + "/");//风尚宝贝图片的根路径
			model.addAttribute("page", page);

			return "article/fushionbaby/fushionbaby_list";
		} catch (Exception e) {
			logg.error("风尚宝贝查询失败", e);
			return "";
		}
	}

	/***
	 * 跳转到添加的页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "find")
	public String goAdd() {
		return "article/fushionbaby/fushionbaby_add";
	}

	/***
	 * 跳转到修改的页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("edit")
	public String goEdit(@RequestParam("id") Long id, Model model) {
		try {
			SysActivityWill will = this.willService.findById(id);
			model.addAttribute("will", will);
		} catch (Exception e) {
			logg.error("加载风尚宝贝失败", e);
		}
		return "article/fushionbaby/fushionbaby_edit";
	}

	/**
	 * 获取图文详情内容
	 * 
	 * @param 获得图文的地址
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getArticleContent.do", produces = "text/html;charset=UTF-8")
	public String getArticleContent(@RequestParam("fileurl") String fileurl) {
		String content = "";
		if (fileurl != null && fileurl != "") {
			try {
				content = FileTools.getContent(fileurl);
			} catch (IOException e) {
				logg.error("读取图文内容失败");
			}
		}
		return content;
	}

	/**
	 * 风尚宝贝进行图片的上传（web和app）
	 * 
	 * @param file
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "loadPhoto", method = RequestMethod.POST)
	public void loadPhoto(
			@RequestParam("photo") MultipartFile file,
	        @RequestParam(value = "type", defaultValue = "") String type,
			PrintWriter out,ModelMap model) throws Exception {
		if (!file.isEmpty()) {
			InputStream input = null;
			try {
				input = file.getInputStream();
				String photoDir = "";
				photoDir = getPhotoDirByType(type, photoDir);
				File rootPath = new File(photoDir);
				if (!rootPath.exists()) {
					rootPath.mkdirs();
				}
				String fileName = getPictureName(file, type);
				File uploadFile = new File(rootPath, fileName);
				FileUtils.copyInputStreamToFile(input, uploadFile);
				out.print(fileName);
				model.addAttribute("fushionbaby_path",ImageConstantCms.SYS_ACTIVITY_FUSHIONBABY_SERVER_PATH + "");
			} catch (IOException e) {
				logg.error("商品上传图片失败", e);
			} finally {
				if (input != null) {
					input.close();
				}

			}

		}

	}

	private String getPhotoDirByType(String type, String photoDir) {
		if ("web".equals(type)) {
			photoDir = ImageConstantCms.SYS_ACTIVITY_FUSHIONBABY_PATH + File.separator;
		}
		if ("app".equals(type)) {
			photoDir = ImageConstantCms.SYS_ACTIVITY_FUSHIONBABY_PATH + File.separator;
		}
		return photoDir;
	}

	private String getPictureName(MultipartFile file, String type) {
		String name=file.getOriginalFilename();
		       name=name.substring(name.lastIndexOf("."));
		String fileName = type + "_" + RandomNumUtil.getNumber(5) + name;
		return fileName;
	}

	/***
	 * 添加风尚宝贝
	 * 
	 * @param model
	 * @param sysActivityWill
	 * @return
	 */
	@RequestMapping("addFushionBaby")
	public String addFushionBaby(Model model, SysActivityWill sysActivityWill) {
		try {
			this.willService.add(sysActivityWill);
		} catch (Exception e) {
			logg.error("风尚宝贝添加失败", e);
		}
		return this.findAll(new BasePagination(), model);
	}

	/***
	 * 删除风尚宝贝
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteFushionBaby")
	@ResponseBody
	public JsonResponseModel deleteFushionBaby(@RequestParam("id") Long id) {
		JsonResponseModel jsonModel = new JsonResponseModel();
		
		try {
			SysActivityWill will=this.willService.findById(id);
			deletePictureBeforeDeleteById(will);
			this.willService.deleteById(id);
			jsonModel.Success("删除成功");
		} catch (Exception e) {
			logg.error("风尚宝贝删除失败", e);
			jsonModel.Fail("删除失败");
		}
		return jsonModel;
	}

	private void deletePictureBeforeDeleteById(SysActivityWill will) {
		if(StringUtils.isNotBlank(will.getWebBannerUrl())){
			FileUtils.deleteQuietly(new File(
					ImageConstantCms.SYS_ACTIVITY_FUSHIONBABY_PATH,
					will.getWebBannerUrl()));
		}
		if (StringUtils.isNotBlank(will.getAppBannerUrl())) {
			FileUtils.deleteQuietly(new File(
					ImageConstantCms.SYS_ACTIVITY_FUSHIONBABY_PATH,
					will.getAppBannerUrl()));
		}
	}

	/***
	 * 修改风尚宝贝
	 * 
	 * @param id
	 * @param activityWill
	 * @param model
	 * @return
	 */
	@RequestMapping("updateFushionBaby")
	public String updateFushionBaby(SysActivityWill activityWill, Model model) {
		try {
			this.deletePicture(activityWill.getId(), activityWill);
			this.willService.update(activityWill);
		} catch (Exception e) {
			logg.error("风尚宝贝修改出错", e);
					}
		return this.findAll(new BasePagination(), model);
	}

	/***
	 * 修改的时候删除已经存在的图片
	 * 
	 * @param id
	 * @param activityWill
	 */
	public void deletePicture(Long id, SysActivityWill activityWill) {

		try {
			SysActivityWill sys = this.willService.findById(id);
			deleteAppBanner(activityWill, sys);
			deleteWebBanner(activityWill, sys);
		} catch (Exception e) {
			logg.error("修改风尚宝贝时删除原来的图片失败", e);
		}
	}

	private void deleteWebBanner(SysActivityWill activityWill,
			SysActivityWill sys) {
		if (StringUtils.isNotBlank(activityWill.getWebBannerUrl())) {
			/** 删除已存在的web banner 图片 */
			if (isCanDeleteWebBanner(activityWill, sys)) {
				FileUtils.deleteQuietly(new File(ImageConstantCms.SYS_ACTIVITY_FUSHIONBABY_PATH,sys.getWebBannerUrl()));
			}
		}
	}

	private void deleteAppBanner(SysActivityWill activityWill,
			SysActivityWill sys) {
		if (StringUtils.isNotBlank(activityWill.getAppBannerUrl())) {
			/** 删除已存在的app banner 图片 */
			if (isCanDeleteAppBanner(activityWill, sys)) {
				FileUtils.deleteQuietly(new File(ImageConstantCms.SYS_ACTIVITY_FUSHIONBABY_PATH,sys.getAppBannerUrl()));
			}
		}
	}

	private boolean isCanDeleteWebBanner(SysActivityWill activityWill,
			SysActivityWill sys) {
		return StringUtils.isNotBlank(sys.getWebBannerUrl())&& !activityWill.getWebBannerUrl().equals(sys.getWebBannerUrl());
	}

	private boolean isCanDeleteAppBanner(SysActivityWill activityWill,SysActivityWill sys) {
		return StringUtils.isNotBlank(sys.getAppBannerUrl())&& !activityWill.getAppBannerUrl().equals(sys.getAppBannerUrl());
	}
}
