package com.fushionbaby.cms.controller.activity;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.fushionbaby.sysactivity.model.SysActivityActivities;
import com.fushionbaby.sysactivity.service.SysActivityActivitiesService;

/**
 * 
 * @author xupeijun
 * 
 */
@Controller
@RequestMapping("/activity")
public class SysActivityController {
	@Autowired
	private SysActivityActivitiesService<SysActivityActivities> sysActivityActivitiesService;
	private static final Log logger = LogFactory.getLog(SysActivityController.class);
	/***
	 * 添加活动
	 * 
	 * @param myEditorContent
	 * @param sysActivities
	 * @param model
	 * @return
	 */
	@RequestMapping("addActivity")
	public String add(@RequestParam(value = "myEditorContent", defaultValue = "") String myEditorContent,
			SysActivityActivities sysActivityActivities, ModelMap model) {
		String url = ImageConstantCms.ACTIVITY_HTML_PATH + File.separator;
		String fileName = RandomNumUtil.getNumber(10) + ".html";
		try {
			FileTools.createFile(url, fileName, myEditorContent);
			sysActivityActivities.setIntroduceUrl(fileName);
			sysActivityActivitiesService.add(sysActivityActivities,myEditorContent);
		} catch (Exception e) {
			logger.error("户外活动添加失败", e);
		}
		return this.findAll("", "", null, model);
	}

	/**
	 * 活动模块进行图片的上传（地点和介绍图片）
	 * 
	 * @param file
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "loadPhoto", method = RequestMethod.POST)
	public void loadPhoto(@RequestParam("photo") MultipartFile file,
	/** 通过type判断属于哪部分的图片 */
	@RequestParam(value = "type", defaultValue = "") String type,
			PrintWriter out) throws Exception {
		if (!file.isEmpty()) {
			InputStream input = null;
			/** 得到选择文件的输入流 */
			try {input = file.getInputStream();
				/** 以该商品的code作为文件名 */
				String photoDir = "";
				/** 根据不同的图片放入到不同的文件夹中 */
				if ("ppu".equals(type)) {
					photoDir = ImageConstantCms.SYS_ACTIVITY_PLACE_PICTURE_PATH+ File.separator;
				}
				else{
					photoDir = ImageConstantCms.SYS_ACTIVITY_INTRO_PATH+ File.separator;
				}
				/** 存放文件的路径 */
				File rootPath = new File(photoDir);
				if (!rootPath.exists()) {
					rootPath.mkdirs();
				}
				String fileName=renameFile(file,type);
				/** 根据路径和名字创建文件 */
				File uploadFile = new File(rootPath, fileName);
				/** 将input 文件copy */
				FileUtils.copyInputStreamToFile(input, uploadFile);
				out.print(fileName);
			} catch (IOException e) {
				logger.error("商品上传图片失败", e);
			} finally {
				if (input != null) {
					input.close();
				}
			}
		}
	}
	/***
	 * 给上传的文件重新命名
	 * @param file
	 * @param type 
	 * @return
	 */
	private String renameFile(MultipartFile file, String type) {
		String fileName=file.getOriginalFilename();
		fileName=type+RandomNumUtil.getNumber(5)+fileName.substring(fileName.lastIndexOf("."));
		return fileName;
	}

	/***
	 * 通过id删除活动 还要删除 相关的图片
	 * 
	 * @param id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("deleteById")
	public JsonResponseModel deleteById(@RequestParam("id") Long id) {
		JsonResponseModel jsonMode = new JsonResponseModel();
		try {
			SysActivityActivities sys = this.sysActivityActivitiesService.findById(id);
			deletePictureWithDeleteById(sys);
			this.sysActivityActivitiesService.deleteById(id);
			jsonMode.Success("活动删除成功");
		} catch (Exception e) {
			logger.error("活动删除失败", e);
			jsonMode.Fail("活动删除失败");
		}
		return jsonMode;
	}
/***
 * 20150203 重构之后
 * 通过id删除活动的时候还要删除相关的图片和HTML文件
 * @param sys
 */
	private void deletePictureWithDeleteById(SysActivityActivities sys){
		/** 删除地点图片 */
		deletePictureWithDeleteById2(sys.getPlacePictureUrl(),ImageConstantCms.SYS_ACTIVITY_PLACE_PICTURE_PATH);
		/** 删除web banner图片 */
		deletePictureWithDeleteById2(sys.getWebBannerUrl(),ImageConstantCms.SYS_ACTIVITY_INTRO_PATH);
		/** 删除app banner图片 */
		deletePictureWithDeleteById2(sys.getAppBannerUrl(),ImageConstantCms.SYS_ACTIVITY_INTRO_PATH);
		/** 删除web introduce图片 */
		deletePictureWithDeleteById2(sys.getWebIntroduceUrl(),ImageConstantCms.SYS_ACTIVITY_INTRO_PATH);
		/** 删除app introduce图片 */
		deletePictureWithDeleteById2(sys.getAppIntroduceUrl(),ImageConstantCms.SYS_ACTIVITY_INTRO_PATH);
		/** 还要删除HTML 文件 */
		deletePictureWithDeleteById2(sys.getIntroduceUrl(),ImageConstantCms.ACTIVITY_HTML_PATH);
	}
	
	private void deletePictureWithDeleteById2(String fileName, String path){
		if (StringUtils.isNotBlank(fileName)) {
			/** 删除地点图片 */
			this.deletePicture(path,fileName);
		}
		
	}
	/***
	 * 活动分页列表
	 * 
	 * @param name
	 * @param place
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("findAll")
	public String findAll(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "place", defaultValue = "") String place,
			BasePagination page, ModelMap model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			// 分页参数封装
			Map<String, String> params = new HashMap<String, String>();
			params.put("name", name.trim());
			params.put("place", place.trim());
			page.setParams(params);
			// 分页对象
			page = this.sysActivityActivitiesService.getListPage(page);
			// 分页结果
			List<SysActivityActivities> sysActivityActivitiesList = (List<SysActivityActivities>) page
					.getResult();
			model.addAttribute("sysActivitiesList", sysActivityActivitiesList);
			model.addAttribute("page", page);
			model.addAttribute("name", name);
			model.addAttribute("place", place);
			return "activity/activity_list";
		} catch (Exception e) {
			logger.error("活动列表加载失败", e);
			return "";
		}
	}

	/***
	 * 活动的修改
	 * 如果上传的文件不是空的，则说明进行了图片的修改，需要先删除以前的图片
	 * @param myEditorContent
	 * @param sysActivities
	 * @param model
	 * @return
	 */
	@RequestMapping("updateActivity")
	public String update(@RequestParam("myEditorContent") String myEditorContent,
			SysActivityActivities sysActivityActivities, ModelMap model) {
		try {
			this.deletePictureWhenUpdate(sysActivityActivities.getId(),sysActivityActivities);
			String fileName = sysActivityActivities.getIntroduceUrl();
		      fileName=	editHtmlFileWhenUpdate(fileName,myEditorContent);
			sysActivityActivities.setIntroduceUrl(fileName);
			this.sysActivityActivitiesService.updateActivity(myEditorContent,sysActivityActivities);
		} catch (Exception e) {
			logger.error("活动修改失败", e);
		}
		return this.findAll("", "", null, model);
	}

	/***
	 * 在修改的时候进行HTML文件的名的修改
	 * @param fileName
	 * @param myEditorContent
	 * @return
	 */
	private String editHtmlFileWhenUpdate(String fileName,String myEditorContent){
		if (StringUtils.isNotBlank(fileName)) {
			FileUtils.deleteQuietly(new File(ImageConstantCms.ACTIVITY_HTML_PATH,fileName));
			try {
				FileTools.createFile(ImageConstantCms.ACTIVITY_HTML_PATH+ File.separator, fileName, myEditorContent);
			} catch (IOException e) {
				logger.error("创建HTML文件失败", e);
			}
		} else {
			fileName=RandomNumUtil.getNumber(10)+".html";
			try {
				FileTools.createFile(ImageConstantCms.ACTIVITY_HTML_PATH+ File.separator, fileName, myEditorContent);
			} catch (IOException e) {
				logger.error("创建HTML文件失败", e);
			}
		}
		return fileName;
	}
	
	
	/**
	 * 跳转到修改的页面
	 * 
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "edit")
	public String edit(@RequestParam("id") String id, Map<String, Object> map) {
		try {
			SysActivityActivities sysActivityActivities = this.sysActivityActivitiesService
					.findById(Long.valueOf(id));
			map.put("sysActivities", sysActivityActivities);
			return "activity/activity_edit";
		} catch (Exception e) {
			logger.error("跳转到修改页面", e);
			return "";
		}
	}

	/**
	 * 修改活动是否开启状态
	 * 
	 * @param id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("change")
	public JsonResponseModel change(@RequestParam(value = "id") Long id) {
		JsonResponseModel jsonMode = new JsonResponseModel();
		try {
			this.sysActivityActivitiesService.changeIsOpen(id);
			jsonMode.Success("修改活动状态成功");
		} catch (Exception e) {
			jsonMode.Fail("修改活动状态失败");
			logger.error("修改活动状态失败", e);
		}
		return jsonMode;
	}

	/**
	 * 跳转到活动添加的页面
	 * 
	 * @return
	 */
	@RequestMapping("find")
	public String find() {
		return "activity/activity_add";
	}

	/**
	 * 获得数据库中活动介绍的内容传到页面的富文本编辑框里面
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getActivityIntroduce", produces = "text/html;charset=UTF-8")
	public String getActivityIntroduce(@RequestParam("id") Long id) {
		String introduce = "";
		try {
			SysActivityActivities sysActivityActivities = this.sysActivityActivitiesService
					.findById(id);
			introduce = sysActivityActivities.getIntroduce();
		} catch (Exception e) {
			logger.error("活动介绍信息获取失败", e);
		}
		return introduce;
	}

	/***
	 * 通过id和传入的修改对象 判断该原对象（id获得）的某些属性下是否有图片，如果有，并且修改对象（传入的对象）该属性也有值，则把其删除，否则不删除
	 * 专门为修改使用
	 * 
	 * @param id
	 */
	public void deletePictureWhenUpdate(Long id, SysActivityActivities sys) {
		try {
			/**old ,sys为新的*/
			SysActivityActivities activity = this.sysActivityActivitiesService
					.findById(id);
			/** 如果修改的对象 属性里面有值，则新的和旧的做比较，相同则说明没有更新，或则就要删除原来的 */
			/**app 端的banner处理*/
			deleteOldFileWhenUpdate(activity.getAppBannerUrl(),sys.getAppBannerUrl(),ImageConstantCms.SYS_ACTIVITY_INTRO_PATH);
			/** 删除已存在的web banner图片 */
			deleteOldFileWhenUpdate(activity.getWebBannerUrl(),sys.getWebBannerUrl(),ImageConstantCms.SYS_ACTIVITY_INTRO_PATH);
			/** 删除已存在的app introduce 图片 */
			deleteOldFileWhenUpdate(activity.getAppIntroduceUrl(),sys.getAppIntroduceUrl(),ImageConstantCms.SYS_ACTIVITY_INTRO_PATH);
			/** 删除已存在的web introduce图片 */
			deleteOldFileWhenUpdate(activity.getWebIntroduceUrl(),sys.getWebIntroduceUrl(),ImageConstantCms.SYS_ACTIVITY_INTRO_PATH);
			/** 删除已经存在的 地点图片 */
			deleteOldFileWhenUpdate(activity.getPlacePictureUrl(),sys.getPlacePictureUrl(),ImageConstantCms.SYS_ACTIVITY_PLACE_PICTURE_PATH);
		} catch (Exception e) {
			logger.error("活动修改时删除图片失败", e);
		}
	}

	/***
	 * 是否进行了新的修改操作，如果原来的数据存在，并且进行了修改（新的数据也存在），就删除旧的数据
	 *20150203 重构之后
	 * @param old
	 * @param now
	 * @param path
	 * @return
	 */
	private void deleteOldFileWhenUpdate(String old,String now,String path){
		try {
			/** 旧的存在*/
			if(StringUtils.isNotBlank(old)){
				/** 新的也存在，并且旧的不等于新的,删除旧的*/
				if(StringUtils.isNotBlank(now)&&!old.equals(now))
				FileUtils.deleteQuietly(new File(ImageConstantCms.SYS_ACTIVITY_INTRO_PATH, old));
			}
		} catch (Exception e) {
			logger.error("活动进行修改时，关于文件修改出错",e);
		}
	
	}
	
	/***
	 * 删除图片
	 * 
	 * @param dir
	 * @param fileName
	 */
	@RequestMapping("deleteImage")
	public void deletePicture(String dir, String fileName) {

		try {
			if (StringUtils.isNotBlank(fileName)) {
				FileUtils.deleteQuietly(new File(dir, fileName));
			}
		} catch (Exception e) {
			logger.error("活动图片删除失败", e);
		}

	}

	
	
}
