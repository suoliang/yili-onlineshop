package com.aladingshop.wap.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @description FTL视图显示工具
 * @author 孟少博
 * @date 2015年7月24日下午4:49:23
 */
public class FreemarkerViewUtil extends FreeMarkerView {

	@Override
	protected void doRender(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Expose model to JSP tags (as request attributes).
		exposeModelAsRequestAttributes(model, request);
		// Expose all standard FreeMarker hash models.
		SimpleHash fmModel = buildTemplateModel(model, request, response);

		if (logger.isDebugEnabled()) {
			logger.debug("Rendering FreeMarker template [" + getUrl()
					+ "] in FreeMarkerView '" + getBeanName() + "'");
		}
		// Grab the locale-specific version of the template.
		Locale locale = RequestContextUtils.getLocale(request);

		/** 静态化 **/
		String savePath = (String) request.getAttribute("savePath");
		/** 首页保存路径 */
		String detailPath = (String) request.getAttribute("detailPath");
		/** 明细保存路径 */
		if (StringUtils.isNotBlank(savePath)) {
			createHTML(getTemplate(locale), fmModel, savePath, response);
			response.sendRedirect(request.getContextPath()
					+ "/html"
					+ savePath.substring(savePath.indexOf("html") + 4).replace(
							File.separator, "/"));

		} else if (StringUtils.isNotBlank(detailPath)) {
			createHTML(getTemplate(locale), fmModel, detailPath, response);
			response.sendRedirect(request.getContextPath()
					+ "/detail/"
					+ detailPath.substring(detailPath.indexOf("detail") + 7,
							detailPath.length()));

		} else {
			processTemplate(getTemplate(locale), fmModel, response);
		}
	}

	public static void main(String[] args) {
		String ss = "sadffsaddf/detail/a123sdsfsdfsdfsdfs.html";
		System.out.println(ss.substring(ss.indexOf("detail") + 7, ss.length()));
	}

	public void createHTML(Template template, SimpleHash model,
			String filePath, HttpServletResponse response) throws IOException,
			TemplateException, ServletException {
		File htmlFile = new File(filePath);
		if (!htmlFile.getParentFile().exists()) {

			htmlFile.getParentFile().mkdirs();
		}
		if (!htmlFile.exists()) {
			htmlFile.createNewFile();
		}
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(htmlFile), "UTF-8"));
			template.process(model, out);
			out.flush();
		} catch (IOException e) {

		} finally {
			if (out != null)
				out.close();
		}
	}
}
