package com.fushionbaby.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * @author 张明亮 文件操作工具类
 */
public class FileTools {
	/** 文件名称 */
	/*
	 * public static final String FILE_NAME = "_test.html";
	 *//** 文件路径 */
	/*
	 * public static final String FILE_URL = "e:/file/test/";
	 *//** 存放文本编辑器中图片的路径 */
	/*
	 * public static final String PICTURE_FOLDER = "images";
	 */
	/**
	 * 写入文件
	 * 
	 * @param path
	 *            路径
	 * @param fileName
	 *            文件名
	 * @param fileContent
	 *            文件内容
	 * @throws IOException
	 */
	public static void createFile(String path, String fileName,
			String fileContent) throws IOException {
		String filePath = path + fileName;
		File myFile = new File(path);
		if (!myFile.exists()) {// 目录不存在
			myFile.mkdirs();
		}

		// 读取字符流
		BufferedReader bufferReader = new BufferedReader(new StringReader(
				fileContent));

		BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(
				new File(filePath)));

		// 缓冲字符串区域
		char[] buffer = new char[2048];
		int len = 0;
		while ((len = bufferReader.read(buffer)) != -1) {
			bufferWriter.write(buffer, 0, len);
		}

		// 写完了先关掉写的
		if (bufferWriter != null) {
			bufferWriter.close();
		}

		// 最后关掉读取的
		if (bufferReader != null) {
			bufferReader.close();
		}
	}

	/**
	 * @author mengshaobo 获得文件中的内容
	 * 
	 * @param path
	 * @return 内容
	 * @throws IOException
	 */
	public static String getContent(String path) throws IOException {

		Reader fReader = null;
		fReader = new BufferedReader(new FileReader(path)); // 创建文件字符输入流
		char[] data = new char[512];
		int rs = 0;
		StringBuffer strBuf = new StringBuffer();
		while ((rs = fReader.read(data)) > 0) { // 在循环中读取数据
			strBuf.append(data, 0, rs);
		}
		if (fReader != null)
			fReader.close();

		return strBuf.toString();

	}
	
	public static void main(String[] args) throws IOException {

		// String s =
		// "<div class=\"cwd vname thide\">        {if x.moveFrom=='wap'}          <a class=\"noul pnt\" target=\"_blank\" href=\"http://blog.163.com/services/wapblog.html?frompersonalbloghome\"><span title=\"来自网易手机博客\" class=\"iblock wapIcon\">&nbsp;</span></a>        {elseif x.moveFrom=='iphone'}          <a class=\"noul pnt\" target=\"_blank\"><span title=\"来自iPhone客户端\" class=\"iblock iphoneIcon\">&nbsp;</span></a>        {elseif x.moveFrom=='android'}          <a class=\"noul pnt\" target=\"_blank\"><span title=\"来自Android客户端\" class=\"iblock androidIcon\">&nbsp;</span></a>        {elseif x.moveFrom=='mobile'}          <a class=\"noul pnt\" target=\"_blank\" href=\"http://blog.163.com/services/emsblog.html?frompersonalbloghome\"><span title=\"来自网易短信写博\" class=\"iblock wapIcon\">&nbsp;</span></a>        {/if}        <a class=\"fc03 m2a\"  target=\"_blank\" hidefocus=\"true\" href=\"http://blog.163.com/${x.visitorName}/\">          ${fn(x.visitorNickname,8)|escape}        </a>      </div>    </div>    {/if}    {/list}  </textarea>  <textarea name=\"jst\" id=\"m-3-jst-2\">    {if !!a}    <a target=\"_blank\" href=\"http://blog.163.com/${a.userName}/\"><img class=\"bdwa bdc0 pnt\" onerror=\"this.src=location.f60\" src=\"${fn1(a.userName)}\"/></a>    <a target=\"_blank\" class=\"fc03 m2a\" href=\"http://blog.163.com/${a.userName}/\">${fn(a.nickname,8)|escape}</a>    <div class=\"intro fc05\">${a.selfIntro|escape}{if great260}${suplement}{/if}</div>	    <div class=\"acts ztag\"></div>	  <div class=\"mbga phide xtag\">        <div class=\"mbgai\">&nbsp;</div>        <a class=\"fc03 xtag m2a\" href=\"#\" target=\"_blank\"></a>      </div>    {/if}  </textarea>  <#--最新日志，群博日志-->  <textarea name=\"jst\" id=\"m-3-jst-3\">    {list a as x}    {if !!x}    <li class=\"thide\"><a target=\"_blank\" class=\"fc03 m2a\" href=\"${furl()}${x.permalink}/?latestBlog\">${fn(x.title,26)|escape}</a></li>    {/if}    {/list}  </textarea>  <#--推荐日志-->  <textarea name=\"jst\" id=\"m-3-jst-4\">    <p class=\"fc06\">推荐过这篇日志的人：</p>    <div>      {list a as x}      {if !!x}      <div class=\"iblock nbw-fce nbw-f40\">        <a class=\"fc03 noul\" target=\"_blank\" hidefocus=\"true\" href=\"http://blog.163.com/${x.recommenderName}/\">        <img alt=\"${x.recommenderNickname|escape}\" onerror=\"this.src=location.f40\" class=\"cwd bdwa bdc0\" src=\"${fn1(x.recommenderName)}\"/>        </a>        <div class=\"cwd thide\">          <a class=\"fc03 m2a\" target=\"_blank\" hidefocus=\"true\" href=\"http://blog.163.com/${x.recommenderName}/\">            ${fn(x.recommenderNickname,6)|escape}          </a>        </div>      </div>      {/if}      {/list}    </div>    {if !!b&&b.length>0}    <p  class=\"fc06\">他们还推荐了：</p>    <ul>    {list b as y}      {if !!y}        <li class=\"rrb\"><span class=\"iblock\">&#183;</span><a class=\"fc03 m2a\" target=\"_blank\" href=\"http://blog.163.com/${y.recommendBlogPermalink}/?from=blog/static/1117069812009418111152372\">${y.recommendBlogTitle|escape}</a></li>      {/if}    {/list}    </ul>    {/if}  </textarea>  <#--引用记录-->";
		// FileTools.createFile("d:/", "测试文件.html", s);
		String path = "e:/file/test/a1d54f02-8577-42f1-8d4c-ee7aca39df28_测试文件.html";
		System.out.print(FileTools.getContent(path));

		// String s =
		// "<div class=\"cwd vname thide\">        {if x.moveFrom=='wap'}          <a class=\"noul pnt\" target=\"_blank\" href=\"http://blog.163.com/services/wapblog.html?frompersonalbloghome\"><span title=\"来自网易手机博客\" class=\"iblock wapIcon\">&nbsp;</span></a>        {elseif x.moveFrom=='iphone'}          <a class=\"noul pnt\" target=\"_blank\"><span title=\"来自iPhone客户端\" class=\"iblock iphoneIcon\">&nbsp;</span></a>        {elseif x.moveFrom=='android'}          <a class=\"noul pnt\" target=\"_blank\"><span title=\"来自Android客户端\" class=\"iblock androidIcon\">&nbsp;</span></a>        {elseif x.moveFrom=='mobile'}          <a class=\"noul pnt\" target=\"_blank\" href=\"http://blog.163.com/services/emsblog.html?frompersonalbloghome\"><span title=\"来自网易短信写博\" class=\"iblock wapIcon\">&nbsp;</span></a>        {/if}        <a class=\"fc03 m2a\"  target=\"_blank\" hidefocus=\"true\" href=\"http://blog.163.com/${x.visitorName}/\">          ${fn(x.visitorNickname,8)|escape}        </a>      </div>    </div>    {/if}    {/list}  </textarea>  <textarea name=\"jst\" id=\"m-3-jst-2\">    {if !!a}    <a target=\"_blank\" href=\"http://blog.163.com/${a.userName}/\"><img class=\"bdwa bdc0 pnt\" onerror=\"this.src=location.f60\" src=\"${fn1(a.userName)}\"/></a>    <a target=\"_blank\" class=\"fc03 m2a\" href=\"http://blog.163.com/${a.userName}/\">${fn(a.nickname,8)|escape}</a>    <div class=\"intro fc05\">${a.selfIntro|escape}{if great260}${suplement}{/if}</div>	    <div class=\"acts ztag\"></div>	  <div class=\"mbga phide xtag\">        <div class=\"mbgai\">&nbsp;</div>        <a class=\"fc03 xtag m2a\" href=\"#\" target=\"_blank\"></a>      </div>    {/if}  </textarea>  <#--最新日志，群博日志-->  <textarea name=\"jst\" id=\"m-3-jst-3\">    {list a as x}    {if !!x}    <li class=\"thide\"><a target=\"_blank\" class=\"fc03 m2a\" href=\"${furl()}${x.permalink}/?latestBlog\">${fn(x.title,26)|escape}</a></li>    {/if}    {/list}  </textarea>  <#--推荐日志-->  <textarea name=\"jst\" id=\"m-3-jst-4\">    <p class=\"fc06\">推荐过这篇日志的人：</p>    <div>      {list a as x}      {if !!x}      <div class=\"iblock nbw-fce nbw-f40\">        <a class=\"fc03 noul\" target=\"_blank\" hidefocus=\"true\" href=\"http://blog.163.com/${x.recommenderName}/\">        <img alt=\"${x.recommenderNickname|escape}\" onerror=\"this.src=location.f40\" class=\"cwd bdwa bdc0\" src=\"${fn1(x.recommenderName)}\"/>        </a>        <div class=\"cwd thide\">          <a class=\"fc03 m2a\" target=\"_blank\" hidefocus=\"true\" href=\"http://blog.163.com/${x.recommenderName}/\">            ${fn(x.recommenderNickname,6)|escape}          </a>        </div>      </div>      {/if}      {/list}    </div>    {if !!b&&b.length>0}    <p  class=\"fc06\">他们还推荐了：</p>    <ul>    {list b as y}      {if !!y}        <li class=\"rrb\"><span class=\"iblock\">&#183;</span><a class=\"fc03 m2a\" target=\"_blank\" href=\"http://blog.163.com/${y.recommendBlogPermalink}/?from=blog/static/1117069812009418111152372\">${y.recommendBlogTitle|escape}</a></li>      {/if}    {/list}    </ul>    {/if}  </textarea>  <#--引用记录-->";

		// System.out.println("c" + File.pathSeparator + File.separator);
		// FileTools.createFile("c:" + File.separator, "测试文件.html", s);
	}
}
