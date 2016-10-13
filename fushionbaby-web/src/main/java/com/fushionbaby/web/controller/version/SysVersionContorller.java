package com.fushionbaby.web.controller.version;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.dto.SysVersionDto;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrVersionConfig;
import com.fushionbaby.config.service.SysmgrVersionConfigService;

/**
 * 
 * @author cyla
 *         <p>
 *         检查版本更新
 *         </p>
 * @version 1.0.0
 */
@Controller
@RequestMapping("/checkupdate")
public class SysVersionContorller {
	/**注入日志*/
	private static final Log logger = LogFactory.getLog(SysVersionContorller.class);

	@Autowired
	private SysmgrVersionConfigService<SysmgrVersionConfig> sysVersionService;

	/**
	 * <p>
	 * 检查版本更新.
	 * </p>
	 * 
	 * @param source_code
	 *            版本类型
	 * @param version
	 *            版本号
	 * @param mac_addr
	 * 			  手机mac地址
	 * @return 检查结果
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/updateApp")
	@ResponseBody
	public Object checkUpdate(String source_code, String version,String mac_addr)
			throws UnsupportedEncodingException {
		// 将版本信息封装后返回。
		SysVersionDto sysVersionDto = new SysVersionDto();
		try {
			SysmgrVersionConfig sysVersion = new SysmgrVersionConfig();
			// 检查更新来源是否为空。
			if (StringUtils.isEmpty(source_code)) {
				return Jsonp.paramError("更新来源为空");
			}
			// 检查版本号是否为空。
			if (StringUtils.isEmpty(version)) {
				return Jsonp.paramError("版本号为空");
			}
			int latestVersionNum = sysVersionService.getLatestVersionNum();
			if (StringUtils.equals(version, String.valueOf(latestVersionNum))) {
				// 用户当前是最新版本
				return Jsonp.currentVersionState("最新版本!");
			}
			//检查用户是否需要更新
			sysVersion.setVersionNum(Integer.valueOf(version));
			List<SysmgrVersionConfig> checkIsNeedUpdateOrNotList = sysVersionService.checkIsNeedUpdateOrNot(sysVersion);
			if (CollectionUtils.isEmpty(checkIsNeedUpdateOrNotList)) {
				sysVersionDto.setIsNeedUpdate(CommonConstant.NO);
			} else {
				sysVersionDto.setIsNeedUpdate(CommonConstant.YES);
			}
			//获取最新的版本信息
			sysVersion.setVersionNum(latestVersionNum);
			sysVersion.setSourceCode(source_code);
			SysmgrVersionConfig lastestSysmgrVersion = sysVersionService.getLatestVersionByLatestVersionNumAndSourceCode(sysVersion);
			sysVersionDto.setName(lastestSysmgrVersion.getName());
			sysVersionDto.setVersion(lastestSysmgrVersion.getVersionNum());
			sysVersionDto.setContent(lastestSysmgrVersion.getFeature());
			sysVersionDto.setIsForce(lastestSysmgrVersion.getIsFource());
			sysVersionDto.setUpdateUrl(lastestSysmgrVersion.getUrl());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("SysVersionContorller检查最新版本异常APP端" + e);
		}
		// 返回最新版本内容
		return Jsonp_data.success(sysVersionDto);
	}
}
