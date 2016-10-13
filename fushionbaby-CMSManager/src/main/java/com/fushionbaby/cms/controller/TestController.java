/**
 * 
 */
package com.fushionbaby.cms.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.sku.cms.model.SkuImage;
import com.fushionbaby.cms.config.FilePathUtils;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.common.util.jsonp.Jsonp;

/**
 * @author mengshaobo
 * 
 * 
 */
@Controller
@RequestMapping("/cms")
public class TestController  extends BaseController{

	@ResponseBody
	@RequestMapping("test")
	public Object test() {
		return Jsonp.success();
	}

	@RequestMapping(value="jbox",method= RequestMethod.GET)
	public String jbox(){
		
//		String str = "[{'name':'kevin','age':25},{'name':'cissy','age':24}]";
//		Gson gson = new Gson();
//		List<Object> objList =  gson.fromJson(str, new TypeToken<List<Object>>(){}.getType());
		return "views/jbox/NewFile";
	}
	
	@RequestMapping(value="save")
	public String save(String image,String graphicDetails,String filePath,
			RedirectAttributes redirectAttributes,HttpServletRequest request){
		addMessage(redirectAttributes, "成功");
		List<SkuImage> skuImages = new ArrayList<SkuImage>();
		try {
			 List<String> imageList = new filed().readFiled(filePath);
			 for (String imgAllPath : imageList) {
			
				SkuImage skuImage = new SkuImage();
				String[] imagePath = imgAllPath.split("\\\\");
				int lastIndex = imagePath.length-1;
				for (int i = lastIndex; i >=0; i--) {
					if(i == lastIndex ){
						skuImage.setImgUrl(imagePath[i]);
					}else if(i == (lastIndex - 1) ){
						skuImage.setImageTypeCode(imagePath[i]);
					}else if(i== (lastIndex - 2)){
						skuImage.setSkuCode(imagePath[i]);
					}
				}
				skuImages.add(skuImage);
				
			 }
			String path= FilePathUtils.uri2url(image, request);
			System.out.println(path);
			System.out.println(graphicDetails);
			for (int i = 0 ; i < skuImages.size();i++) {
				Thread.sleep(100);
				SkuImage skuImage = skuImages.get(i);
//				System.out.println("商品编号:"+skuImage.getSkuCode() + ";图片类型编号:"+skuImage.getImageTypeCode()+";图片路径："+skuImage.getImgUrl());
				String newpath = "D:/product/sku/" + skuImage.getSkuCode() + "/" + skuImage.getImageTypeCode() +"/" ;
//				FileUtils.copyFileCover(imageList.get(i),newpath,true);
//				new FileSender().sendFile(imageList.get(i), 8821);
	//			new FileIncepter().receiveFile(newpath, "192.168.1.132", 8821);
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:"+Global.getAdminPath()+"/cms/jbox" ;
	}
	
	
	
	
	/**
	 * 成员内部类
	 * @author 孟少博
	 *
	 */
	class filed{
		 List<String> imgList = new ArrayList<String>();
		 public List<String> readFiled(String filePath) throws Exception{
			 
			
			 File file = new File(filePath);
			 File[] array = file.listFiles();
			 for(int i=0;i<array.length;i++)  
			 {   
				 if(array[i].isFile()){
					 imgList.add(array[i].toString());
				 }else if(array[i].isDirectory()){
					 readFiled(array[i].getPath());
				 }
				 
			 }
			 return  imgList;
		 }
	}
	@RequestMapping(value="detail/{id}",method=RequestMethod.GET)
	public String detail(@PathVariable String id){
		
		System.out.println(  "detail"+id);
		
		return "views/jbox/NewFile";
	}
	
	@RequestMapping(value="testboot",method=RequestMethod.GET)
	public String testboot(){
		
		return "models/product/productList";
	}
	
	
}
