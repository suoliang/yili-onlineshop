package com.fushionbaby.facade.image;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import util.ImageConstantFacade;

import com.fushionbaby.sku.model.SkuImage;
import com.fushionbaby.sku.model.SkuImageStandardConfig;
import com.fushionbaby.sku.service.SkuImageService;
import com.fushionbaby.sku.service.SkuImageStandardConfigService;

@Service
public class ImageProcessImp implements ImageProcess {

	@Autowired
	private SkuImageService<SkuImage> skuImageService;
	@Autowired
	private SkuImageStandardConfigService<SkuImageStandardConfig> skuImageStandardService;


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.image.ImageProcess#getThumImageList(java.lang.
	 * String, java.lang.String)
	 */
	public List<String> getThumImageList(String skuCode, String imageStandard) {
		SkuImageStandardConfig skuImageStandard = skuImageStandardService
				.findByCode(imageStandard);
		String imageSize = skuImageStandard.getSize();
		List<SkuImage> skuImages = skuImageService.findBySkuCode(skuCode);
		List<String> thumImgs = new ArrayList<String>();
		if (CollectionUtils.isEmpty(skuImages)) {
			return thumImgs;
		}
		for (int i = 0; i < skuImages.size(); i++) {
			SkuImage image = skuImages.get(i);
			String imgUrl = image.getImgUrl();
			if(StringUtils.isBlank(imgUrl)){
				continue;
			}
			
			
			thumImgs.add(this.thumImagePath(imageSize, imgUrl));
			
			
			
		}

		return thumImgs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.image.ImageProcess#getThumImagePath(java.lang.
	 * String, java.lang.String)
	 */
	public String getThumImagePath(String skuCode, String imageStandard) {
		List<String> imageList = this.getThumImageList(skuCode, imageStandard);
		if (CollectionUtils.isEmpty(imageList)) {
			return StringUtils.EMPTY;
		}
		return imageList.get(0);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.image.ImageProcess#getOrigImageList(java.lang.
	 * String, java.lang.String)
	 */
	public List<String> getOrigImageList(String skuCode) {
		List<SkuImage> skuImages = skuImageService.findBySkuCode(skuCode);
		List<String> origImgs = new ArrayList<String>();
		if (CollectionUtils.isEmpty(skuImages)) {
			return origImgs;
		}
		for (int i = 0; i < skuImages.size(); i++) {
			SkuImage image = skuImages.get(i);
			origImgs.add( this.imagePath(image.getImgUrl()));
		}

		return origImgs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.image.ImageProcess#getOrigImagePath(java.lang.
	 * String, java.lang.String)
	 */
	public String getOrigImagePath(String skuCode) {
		List<String> imageList = this.getOrigImageList(skuCode);
		if (CollectionUtils.isEmpty(imageList)) {
			return StringUtils.EMPTY;
		}
		return imageList.get(0);
	}

	

	private String imagePath(String picPath) {
		
		//String picPath ="ad/201507/maojienwoeu_jpg_430x430q90.jpg";
		
		String picPathName = picPath.substring((picPath.lastIndexOf("/")+1));
		String picPrefix = picPath.substring(0,(picPath.lastIndexOf("/"))+1);
		
		String imageUrl =picPrefix+picPathName;
		imageUrl = StringUtils.replace(imageUrl, "//", "/");
		return this.getImagePath(ImageConstantFacade.IMAGE_SERVER_ROOT_PATH + imageUrl);
	}
	
	public String  thumImagePath(String imageSize,String imgUrl) {
		String prefix = imgUrl.substring(0, imgUrl.lastIndexOf("."));
		String pfPath = prefix.substring(0, prefix.lastIndexOf("/")+1);
		String pfName = prefix.substring(prefix.lastIndexOf("/")+1);
		String suffix = imgUrl.substring(imgUrl.lastIndexOf("."));
		
		String imageUrl = pfPath + pfName + imageSize + suffix;
		imageUrl = StringUtils.replace(imageUrl, "//", "/");
		return this.getImagePath(ImageConstantFacade.IMAGE_SERVER_ROOT_PATH +  imageUrl);
	}
	
	public String getImagePath(String imageUrl){
		String http = "http://";
		
		String uu = StringUtils.substring(imageUrl,http.length());
		
		String url =  StringUtils.replace(uu, "//", "/");
		
		return http+url;
	}
	
	
	public static void main(String[] args) {
//		System.out.println(new ImageProcessImp().imagePath("/ad/201507/maojienwoeu_jpg_430x430q90.jpg"));
//		System.out.println(new ImageProcessImp().thumImagePath("_175x175", "/ad/201507/maojienwoeu_jpg_430x430q90.jpg"));
		
		System.out.println(new ImageProcessImp().getImagePath("http://filepic.aladingshop.com/img/userfiles/images//ad/201507/maojienwoeu_jpg_430x430q90.jpg"));
	}
}
