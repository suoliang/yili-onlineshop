/**
 * 
 */
package com.fushionbaby.cms.util;

import java.io.File;
import java.io.FileInputStream;

import com.ibm.icu.text.DecimalFormat;

/**
 * @description 类描述...
 * @author 孙涛
 * @date 2015年9月1日下午2:52:05
 */
public class ImageUtils {
	public static Double getFileSizes(File f) throws Exception {// 取得文件大小[/KB]
		long size = 0;
		if (!f.exists()) {
			return Double.valueOf(size);
		}

		FileInputStream fis = null;
		fis = new FileInputStream(f);
		size = fis.available();

		DecimalFormat df = new DecimalFormat("#.00");
		fis.close();

		return Double.valueOf(df.format((double) size / 1024));
	}
}
