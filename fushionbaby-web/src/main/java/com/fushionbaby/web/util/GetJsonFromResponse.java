package com.fushionbaby.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import com.qq.connect.utils.json.JSONException;
import com.qq.connect.utils.json.JSONObject;

/***
 * 
 * @author xupeijun 用于得到http请求之后得到的json数据接收
 */
public class GetJsonFromResponse {
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			out.print(param);

			out.flush();

			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlName = url + "?" + param;
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 建立实际的连接
			conn.connect();
			// 获取所有响应头字段
			// Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/***
	 * post 方法不行的（针对得到微博用户的信息）
	 * 
	 * get方法可以得到数据
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String ss = GetJsonFromResponse
				.sendGet(
						"https://api.weibo.com/2/users/show.json",
						"access_token=2.00tSjVoFKdkLSB1ba7bbbf8fC_dYIB&uid=5327043279&client_id=1184907740");
		System.out.println(ss);
		try {
			JSONObject jo3 = new JSONObject(ss);
			String nickName = jo3.getString("name");
			System.out.println("nickName" + nickName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String ss2 = GetJsonFromResponse
				.sendPost(
						"https://api.weibo.com/2/users/show.json",
						"access_token=2.00tSjVoFKdkLSB1ba7bbbf8fC_dYIB&uid=5327043279&client_id=1184907740");
		System.out.println(ss2);
		try {
			JSONObject jo3 = new JSONObject(ss2);
			String nickName2 = jo3.getString("name");
			System.out.println("nickName2" + nickName2);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
