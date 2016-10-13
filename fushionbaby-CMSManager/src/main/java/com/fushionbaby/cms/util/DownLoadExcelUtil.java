package com.fushionbaby.cms.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DownLoadExcelUtil {
	public static void downLoadExcel(String fileName,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			String newFileName = request.getSession().getServletContext().getRealPath("")+"/template/"+fileName;
	
			InputStream inStream = new FileInputStream(newFileName);// 文件的存放路径
			// 设置输出的格式
			response.reset();
			response.setContentType("application/x-download");
			response.setCharacterEncoding("UTF-8");
			response.setHeader( "Content-Disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) ); 
			// 循环取出流中的数据
			byte[] b = new byte[100];
			int len;
	
			while ((len = inStream.read(b)) > 0)
			response.getOutputStream().write(b, 0, len);
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}
