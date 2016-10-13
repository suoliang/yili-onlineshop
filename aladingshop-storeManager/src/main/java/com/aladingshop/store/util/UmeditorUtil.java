package com.aladingshop.store.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fushionbaby.common.util.FileOperate;

/**
 * @author 张明亮
 */
public class UmeditorUtil {
	/**
	 * 移动富文本框提交的图片到服务器指定的位置,并且替换富文本框上传img标签的src路径为服务器指定位置
	 * 
	 * @param images
	 *            生成html同级图片目录
	 * @param htmlContent
	 *            富文本框文件html内容
	 * @param request
	 * @return
	 */
	public static String replaceHtmlImgSrc(String images, String htmlContent,
			HttpServletRequest request) {
		if (StringUtils.isBlank(htmlContent)) {
			return "";
		}

		Document doc = Jsoup.parse(htmlContent);

		Element html = doc.select("html").first();

		html.attr("lang", "en");
		Element head = doc.head();
		Element meta = head.appendElement("meta");
		meta.attr("charset", "utf-8");

		Elements imgs = doc.select("img");
		int imgLen = imgs == null ? 0 : imgs.size();
		for (int i = 0; i < imgLen; i++) {
			Element img = imgs.get(i);
			if (img == null) {
				continue;
			}
			String srcOld = img.attr("src");
			if (StringUtils.isBlank(srcOld) || (!srcOld.startsWith(request.getContextPath()))) {
				continue;
			}
			int lastIndex = srcOld.lastIndexOf("/");
			if (lastIndex < 0) {
				continue;
			}
			String fileNameTmp = srcOld.substring(lastIndex);
			if (StringUtils.isBlank(fileNameTmp)) {
				continue;
			}
			// String srcNewDir = FileTools.FILE_URL + images;
			String srcNewDir = ImageConstantCms.SKU_HTML_PATH + File.separator + images;
			File myFile = new File(srcNewDir);
			if (!myFile.exists()) {// 目录不存在
				myFile.mkdirs();
			}

			String path = request.getSession().getServletContext().getRealPath("/");

			// 正式运行环境,不需要getParent()
			if (!"deploy".equalsIgnoreCase(CMSEnvironmentConstant.ENVIRONMENT)) {
				path = new File(path).getParent();
			}
			String srcNew = srcNewDir + fileNameTmp;
			FileOperate.moveFile(path + srcOld, srcNew);

			img.attr("src", images + fileNameTmp);
			img.attr("_src", images + fileNameTmp);
		}
		return doc.html();
	}

	/**
	 * 根据http url 返回响应的文本内容
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String getUrlContent(String url) throws IOException {
		if (StringUtils.isBlank(url)) {
			return "";
		}
		Connection con = Jsoup.connect(url);
		if (con == null) {
			return "";
		}
		Document doc = con.get();
		if (doc == null) {
			return "";
		}
		return doc.html();
	}

	public static void main(String[] args) throws IOException {
		String d = getUrlContent("http://192.168.8.221/img/sku_html/b61003d9-e38f-4b8c-a159-0cb496ca4498.html");
		System.out.println(d);
	}
}
