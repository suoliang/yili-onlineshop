package com.fushionbaby.cms.controller.article;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.util.ImageConstantCms;
import com.fushionbaby.common.constants.SessionKeyConstant;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.FileTools;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.sysactivity.model.SysActivityFamily;
import com.fushionbaby.sysactivity.model.SysActivityFamilyComment;
import com.fushionbaby.sysactivity.service.SysActivityFamilyCommentService;
import com.fushionbaby.sysactivity.service.SysActivityFamilyService;

/***
 * 亲子课程
 * 
 * @author xupeijun
 * 
 */
@Controller
@RequestMapping("/parent_child")
public class ParentChildCourseController {
	@Autowired
	private SysActivityFamilyService<SysActivityFamily> articleService;

	@Autowired
	private SysActivityFamilyCommentService<SysActivityFamilyComment> commentService;

	private static final Log logger = LogFactory
			.getLog(ParentChildCourseController.class);

	/***
	 * 添加亲子课程方法
	 * 
	 * @param sysArticle
	 * @param myEditorContent
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addParentChildCourse")
	public String add(SysActivityFamily sysArticle,
			@RequestParam("myEditorContent") String myEditorContent,
			ModelMap model, HttpServletRequest request) {
		String url = ImageConstantCms.ARTICLE_HTML_PATH + File.separator;
		String fileName = UUID.randomUUID() + ".html";
		try {
			FileTools.createFile(url, fileName, myEditorContent);
			sysArticle.setFamilyUrl(fileName);
			// 文章的内容先存文件的路径和名称
			// sysArticle.setContent(url + fileName);
			sysArticle.setContent(myEditorContent);
			this.articleService.add(sysArticle);
		} catch (Exception e) {
			logger.error("添加亲子课程失败", e);
		}
		return this.findAll("", "", null, model);
	}

	/***
	 * 亲子课程的删除
	 * 
	 * @param id
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteById")
	public JsonResponseModel deleteById(@RequestParam("id") Long id,
			HttpServletResponse response) {
		JsonResponseModel jsonMode = new JsonResponseModel();
		try {

			SysActivityFamily family = this.articleService.findById(id);
			/** 删除的时候要删除对应的图片 */
			if (StringUtils.isNotBlank(family.getAppBannerUrl())) {
				/** 删除 app的banner的图片 */
				this.deletePicture(ImageConstantCms.SYS_ACTIVITY_FAMILY_PATH,
						family.getAppBannerUrl());
			}
			if (StringUtils.isNotBlank(family.getWebBannerUrl())) {
				/** 删除 web 的banner 的图片 */
				this.deletePicture(ImageConstantCms.SYS_ACTIVITY_FAMILY_PATH,
						family.getWebBannerUrl());
			}
			if (StringUtils.isNotBlank(family.getFamilyUrl())) {
				this.deletePicture(ImageConstantCms.ARTICLE_HTML_PATH,
						family.getFamilyUrl());
			}

			/** 还要把评论表的东西删除的 */
			this.commentService.deleteByArticleId(id);

			this.articleService.deleteById(id);
			jsonMode.Success("亲子课程删除成功");
		} catch (Exception e) {
			jsonMode.Fail("亲子课程删除失败");
			logger.error("亲子课程删除失败", e);
		}
		return jsonMode;
	}

	/***
	 * 亲子课程的修改
	 * 
	 * @param myEditorContent
	 * @param sysArticle
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateParentChildCourse")
	public String edit(@RequestParam("myEditorContent") String myEditorContent,
			SysActivityFamily sysArticle, ModelMap model) {
		String url = ImageConstantCms.ARTICLE_HTML_PATH + File.separator;
		String fileName = UUID.randomUUID().toString() + ".html";
		if (StringUtils.isNotBlank(sysArticle.getFamilyUrl())) {
			fileName = sysArticle.getFamilyUrl();
			FileUtils.deleteQuietly(new File(ImageConstantCms.ARTICLE_HTML_PATH,
					fileName));
		}
		try {
			FileTools.createFile(url, fileName, myEditorContent);
			sysArticle.setFamilyUrl(fileName);
			this.deletePicture(sysArticle.getId(), sysArticle);
			this.articleService.update(sysArticle);
		} catch (Exception e) {
			logger.error("亲子课程文章修改失败");
		}
		return this.findAll("", "", null, model);
	}
	/***
	 * 通过id和传入的修改对象 判断该原对象（id获得）的某些属性下是否有图片，如果有，并且修改对象（传入的对象）该属性也有值，则把其删除，否则不删除
	 * 
	 * @param id
	 * @param sysArticle
	 */
	private void deletePicture(Long id, SysActivityFamily sysArticle) {
		try {
			SysActivityFamily family = this.articleService.findById(id);

			/** 如果修改的对象 属性里面有值，则新的和旧的做比较，相同则说明没有更新，或则就要删除原来的 */
			if (StringUtils.isNotBlank(sysArticle.getAppBannerUrl())) {
				/** 删除已存在的app banner 图片 */
				if (StringUtils.isNotBlank(family.getAppBannerUrl())
						&& !sysArticle.getAppBannerUrl().equals(
								family.getAppBannerUrl())) {
					FileUtils.deleteQuietly(new File(
							ImageConstantCms.SYS_ACTIVITY_FAMILY_PATH,
							family.getAppBannerUrl()));
				}
			}
			if (StringUtils.isNotBlank(sysArticle.getWebBannerUrl())) {
				/** 删除已存在的web banner 图片 */
				if (StringUtils.isNotBlank(family.getWebBannerUrl())
						&& !sysArticle.getWebBannerUrl().equals(
								family.getWebBannerUrl())) {
					FileUtils.deleteQuietly(new File(
							ImageConstantCms.SYS_ACTIVITY_FAMILY_PATH,
							family.getWebBannerUrl()));
				}
			}

		} catch (Exception e) {
			logger.error("修改时删除亲子课程的图片失败", e);
		}
	}

	/***
	 * 查询出所有的亲子课程的文章列表
	 * 
	 * @param author
	 * @param title
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "parentChildCourseList")
	public String findAll(
			@RequestParam(value = "author", defaultValue = "") String author,
			@RequestParam(value = "title", defaultValue = "") String title,
			BasePagination page, ModelMap model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			// 分页参数封装
			Map<String, String> params = new HashMap<String, String>();
			params.put("author", author.trim());
			params.put("title", title.trim());
			page.setParams(params);
			// 分页对象
			page = this.articleService.getListPage(page);
			// 分页结果
			List<SysActivityFamily> parentChildCourseList = (List<SysActivityFamily>) page
					.getResult();
			model.addAttribute("parentChildCourseList", parentChildCourseList);
			model.addAttribute("page", page);
			model.addAttribute("author", author);
			model.addAttribute("title", title);
			return "article/parent_child/parent_child_list";
		} catch (Exception e) {
			logger.error("亲子课程查询列表失败", e);
			return "";
		}
	}

	/***
	 * 跳转到添加的页面
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("find")
	public String findAdd(HttpSession session, Model model) {
		AuthUser user = (AuthUser) session
				.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		model.addAttribute("user", user);
		return "article/parent_child/parent_child_add";
	}

	/***
	 * 跳转到修改的页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("edit")
	public String findEdit(@RequestParam("id") String id, Model model) {
		SysActivityFamily parentChildCourse = this.articleService
				.findById(Long.valueOf(id));
		model.addAttribute("parentChildCourse", parentChildCourse);
		return "article/parent_child/parent_child_edit";
	}

	/**
	 * 得到文本编辑器的内容
	 * 
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "getParentChildCourseContent.do", produces = "text/html;charset=UTF-8")
	public String getParentChildCourseContent(
			@RequestParam("fileurl") String fileurl) {
		String content = "";
		if (fileurl != null && fileurl != "") {
			try {
				content = FileTools.getContent(ImageConstantCms.ARTICLE_HTML_PATH
						+ File.separator + fileurl);
			} catch (Exception e) {
				logger.error("亲子课程加载图文信息出错");
			}
		}
		return content;
	}

	/**
	 * 亲子课程的图片上传
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
			try {
				input = file.getInputStream();
				/** 以该商品的code作为文件名 */
				String photoDir = "";
				/** 根据不同的图片放入到不同的文件夹中 */
				if ("web".equals(type)) {
					photoDir = ImageConstantCms.SYS_ACTIVITY_FAMILY_PATH
							+ File.separator;
				}
				if ("app".equals(type)) {
					photoDir = ImageConstantCms.SYS_ACTIVITY_FAMILY_PATH
							+ File.separator;
				}

				/** 存放文件的路径 */
				File rootPath = new File(photoDir);
				if (!rootPath.exists()) {
					rootPath.mkdirs();
				}
				/** 活动图片的文件名 */
				String fileName = type + "_" + RandomNumUtil.getNumber(5)
						+ file.getOriginalFilename();
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
	 * 通过路径和文件（图片）的名称删除文件
	 * 
	 * @param dir
	 * @param fileName
	 */
	@RequestMapping("deletePicture")
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
