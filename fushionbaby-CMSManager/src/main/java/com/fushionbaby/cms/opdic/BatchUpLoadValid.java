package com.fushionbaby.cms.opdic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuImage;
import com.aladingshop.sku.cms.service.SkuImageService;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.fushionbaby.cache.service.GlobalConfig;
import com.fushionbaby.cms.util.BatchUploadFiles;
import com.fushionbaby.cms.util.ImageUtils;
import com.fushionbaby.cms.util.bean.BatchUploadImageResult;
import com.fushionbaby.cms.util.bean.BatchUploadValidResult;
import com.fushionbaby.cms.util.bean.FileInfo;
import com.fushionbaby.cms.util.bean.UploadResult;
import com.fushionbaby.cms.util.constant.Constant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.config.model.SysmgrDictConfig;
import com.fushionbaby.config.service.SysmgrDictConfigService;

@SuppressWarnings("unchecked")
public class BatchUpLoadValid {
	private static final Log LOGGER = LogFactory.getLog(BatchUpLoadValid.class);
	private final static SkuInfoService skuService = SpringContextHolder.getBean(SkuInfoService.class);
	private final static GlobalConfig globalConfig = SpringContextHolder.getBean(GlobalConfig.class);
	private final static SkuImageService<SkuImage> skuImageService = SpringContextHolder.getBean(SkuImageService.class);
	
	private final static SysmgrDictConfigService<SysmgrDictConfig>  sysmgrDictConfigService= SpringContextHolder.getBean(SysmgrDictConfigService.class);

	private static final String UPIMAGE_SIZE = "500"; // 上传限制大小为500
	private static final String types = "jpgjpegpnggif"; // 图片校验格式
	private static String regEx = "[\u4e00-\u9fa5]";//图片文件名 中文检验
	private static Pattern pat = Pattern.compile(regEx);
	private static BatchUploadValidResult validBatchUpImg(List<String> sucResults, FileInfo info,List<UploadResult> failResults) {
		File dir = new File(info.getRootPath());
		File[] fs = dir.listFiles();
		/**加入文件大小可控制   begin*/
		Double image_size=Double.valueOf(UPIMAGE_SIZE);
	    List<SysmgrDictConfig> dictConfigList = sysmgrDictConfigService.findByLabelValueType("","",Constant.SKU_IMAGE_SIZE);
	    if(dictConfigList!=null&&dictConfigList.size()>0)
	    {
	    	image_size=Double.valueOf(StringUtils.isBlank(dictConfigList.get(0).getValue())?UPIMAGE_SIZE:dictConfigList.get(0).getValue());
	    }
	    /**加入文件大小可控制   end*/
	    
		try {
			for (int i = 0; i < fs.length; i++) {
				String fi = fs[i].getName();
				String name = fi.substring(
						fi.indexOf(".") == -1 ? 0 : fi.indexOf(".") + 1,
						fi.length());
			
				if (fs[i].isFile()) {
					List<String> errorMsg = new ArrayList<String>();
					/**加入文件名有没有中文校验*/
					Matcher matcher = pat.matcher(fi);
					if(matcher.find()){
						errorMsg.add(Constant.CHINESE_STR);
					}
					
					if (info.getTypes().indexOf(name.toLowerCase()) < 0) {
						errorMsg.add(Constant.UPLOAD_FORMAT_ERROR);
					} else if (ImageUtils.getFileSizes(fs[i]) > image_size) {
						errorMsg.add(String.format(Constant.UPLOAD_SIZE_ERROR,
								image_size + "KB"));
					}

					/** 校验数据层 */
					String curpath = fs[i].getAbsolutePath().toLowerCase();
					Integer maxCount = Integer
							.valueOf(globalConfig
									.findByCode(GlobalConfigConstant.WEB_UPDETAIL_IMG_COUNT));
					Sku currentSku = skuService.queryByBarCode(BatchUploadFiles
							.getFileBeforDic(curpath),null);
					if (currentSku == null) {
						errorMsg.add(Constant.UNCODE_NOT_EXIST);
					} else {
						List<SkuImage> skuImages = skuImageService
								.findBySkuCode(currentSku.getUniqueCode());
						int historyLen = skuImages == null ? 0 : skuImages
								.size();
						if (historyLen >= maxCount) {
							errorMsg.add(Constant.UPLIAD_COUNT_LIMIT);
						}
					}

					if (errorMsg.size() != 0) {
						failResults.add(new UploadResult(
								Constant.UPLOAD_FAILURE, errorMsg, fs[i]
										.getAbsolutePath(), fi));
					} else {
						sucResults.add(fs[i].getAbsolutePath().toLowerCase());
					}
				}

				if (fs[i].isDirectory()) {
					info.setRootPath(fs[i].getAbsolutePath());
					validBatchUpImg(sucResults, info, failResults);
				}
			}
		} catch (Exception e) {
			LOGGER.error("上传文件内部异常：" + e);
		}

		return new BatchUploadValidResult(sucResults, failResults);
	}

	public static BatchUploadImageResult getValidResult(String rootPath,
			List<String> sucResults, List<UploadResult> failResults) {
		BatchUploadValidResult result = validBatchUpImg(sucResults,
				new FileInfo(rootPath, null, types), failResults);
		/** 封装校验结果 */
		BatchUploadImageResult upResult = new BatchUploadImageResult();
		Integer suc_count = 0;
		Integer fai_count = 0;
		if (result.getSucResults() != null) {
			suc_count = result.getSucResults().size();
		}

		if (result.getFaiResults() != null) {
			fai_count = result.getFaiResults().size();
		}

		upResult.setCount(suc_count + fai_count);
		upResult.setSuccessCount(suc_count);
		upResult.setFailureCount(fai_count);
		upResult.setFailureResults(result.getFaiResults());

		return upResult;
	}
	
	

}
