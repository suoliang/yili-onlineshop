package com.fushionbaby.pay.controller.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 简述：
 * 工具类用来转换InputStream为String
 *  包括输入流和文件流
 * @author admin
 *
 */
public class StreamToString {

	/**
	 * 将inputStream中得到的输入转换为了一串String：
	 * @param inputStream
	 * @return
	 */
	public static String ConvertToString(InputStream inputStream){  
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);  
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
        StringBuilder result = new StringBuilder();  
        String line = null;  
        try {  
            while(!(line = bufferedReader.readLine()).equals("")){  
                result.append(new String(line.getBytes(),"UTF-8"));  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try{  
                inputStreamReader.close();  
                inputStream.close();  
                bufferedReader.close();  
            }catch(IOException e){  
                e.printStackTrace();  
            }  
        }  
        return result.toString();  
    }  
  
    /**
     * 从文件流中得到输入，并转换为String
     * @param inputStream
     * @return
     */
    public static String ConvertToString(FileInputStream inputStream){  
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);  
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
        StringBuilder result = new StringBuilder();  
        String line = null;  
        try {  
            while((line = bufferedReader.readLine()) != null){  
                result.append(new String(line.getBytes(),"UTF-8"));  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try{  
                inputStreamReader.close();  
                inputStream.close();  
                bufferedReader.close();  
            }catch(IOException e){  
                e.printStackTrace();  
            }  
        }  
        return result.toString();  
    }  
}
