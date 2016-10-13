package com.aladingshop.wap.controller.version;

import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.dto.SysVersionDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrGlobalConfig;
import com.fushionbaby.config.model.SysmgrVersionConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.config.service.SysmgrVersionConfigService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;

/**
 * @description 检查版本更新
 * @author 索亮
 * @date 2015年7月27日下午6:50:28
 */
@Controller
@RequestMapping("/checkUpdate")
public class SysVersionContorller {
	/**注入日志*/
	private static final Log LOGGER = LogFactory.getLog(SysVersionContorller.class);

	@Autowired
	private SysmgrVersionConfigService<SysmgrVersionConfig> sysVersionService;
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private SysmgrGlobalConfigService globalConfigService;

	/**
	 * @param sourceCode
	 * @param version
	 * @param macAddr
	 * @return
	 */
	@RequestMapping("/updateApp")
	@ResponseBody
	public Object checkUpdate(String sourceCode, String version,@RequestParam(value="sid",defaultValue="")String sid,
			@RequestParam(value="macAddr",defaultValue="")String macAddr){
		LOGGER.info("请求参数:"+sourceCode+",version:"+version+",sid:"+sid);
		// 将版本信息封装后返回。
		SysVersionDto sysVersionDto = new SysVersionDto();
		try {
			SysmgrVersionConfig sysVersion = new SysmgrVersionConfig();
			// 检查更新来源是否为空。
			if (StringUtils.isEmpty(sourceCode)) {
				return Jsonp.paramError("来源为空");
			}
			// 检查版本号是否为空。
			if (StringUtils.isEmpty(version)) {
				return Jsonp.paramError("版本号为空");
			}
			int latestVersionNum = sysVersionService.getLatestVersionNum(sourceCode);
			if (StringUtils.equals(version, String.valueOf(latestVersionNum))) {
				// 用户当前是最新版本
				return Jsonp.currentVersionState("最新版本!");
			}
			SysmgrGlobalConfig globalConfig = globalConfigService.findByCode(GlobalConfigConstant.IS_ALL_UPDATE);
			if (CommonConstant.NO.equalsIgnoreCase(globalConfig.getValue())) {
				UserDto userDto= (UserDto)SessionCache.get(sid);
				if(userDto == null) {
					return Jsonp.currentVersionState("暂最新版本!");
				} 
					
				/** 根据用户类型更新 */
				Member member = memberService.findById(userDto.getMemberId());
				if (ObjectUtils.equals(member, null)) {
					return Jsonp.currentVersionState("暂最新版本!");
				}
				if (!"3".equalsIgnoreCase(member.getMemberType())) {
					return Jsonp.currentVersionState("暂最新版本!");
				}
				
			}
			
			sysVersion.setVersionNum(Integer.valueOf(version));
			sysVersion.setSourceCode(sourceCode);
			List<SysmgrVersionConfig> checkIsNeedUpdateOrNotList = sysVersionService.checkIsNeedUpdateOrNot(sysVersion);
			if (CollectionUtils.isEmpty(checkIsNeedUpdateOrNotList)) {
				sysVersionDto.setIsForce(2);
			} else {
				sysVersionDto.setIsForce(1);
			}
			//获取最新的版本信息
			sysVersion.setVersionNum(latestVersionNum);
			sysVersion.setSourceCode(sourceCode);
			SysmgrVersionConfig lastestSysmgrVersion = sysVersionService.getLatestVersionByLatestVersionNumAndSourceCode(sysVersion);
			sysVersionDto.setName(lastestSysmgrVersion.getName());
			String date = DateFormat.dateToString(lastestSysmgrVersion.getCreateTime());
			sysVersionDto.setUpdateTime(StringUtils.isNotBlank(date)?date:StringUtils.EMPTY);
			sysVersionDto.setVersion(lastestSysmgrVersion.getVersionNum());
			sysVersionDto.setContent(lastestSysmgrVersion.getFeature());
			sysVersionDto.setUpdateUrl(lastestSysmgrVersion.getUrl());
			BeanNullConverUtil.nullConver(sysVersionDto);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("SysVersionContorller检查最新版本异常APP端" + e);
			return Jsonp.error("检查新版本出错");
		}
		// 返回最新版本内容
		return Jsonp_data.success(sysVersionDto);
	}
	
	/**
	 * @param sourceCode
	 * @param version
	 * @param macAddr
	 * @return
	 */
	@RequestMapping("/updateIOS")
	@ResponseBody
	public Object checkUpdateIOS(String sourceCode, String version,
			@RequestParam(value="macAddr",defaultValue="")String macAddr){
		LOGGER.info("IOS请求参数:"+sourceCode+",version:"+version);
		// 检查更新来源是否为空。
		if (StringUtils.isEmpty(sourceCode)) {
			return Jsonp.paramError("来源为空");
		}
		// 检查版本号是否为空。
		if (StringUtils.isEmpty(version)) {
			return Jsonp.paramError("版本号为空");
		}
		
		String value = globalConfigService.findByCode(GlobalConfigConstant.IOS_AUDIT_UPDATE).getValue();
		/** 审核期间不需要更新 */
		if (CommonConstant.NO.equalsIgnoreCase(value)) {
			return Jsonp_data.success(CommonConstant.NO);
		}
		int latestVersionNum = sysVersionService.getLatestVersionNum(sourceCode);
		if (StringUtils.equals(version, String.valueOf(latestVersionNum))) {
			// 用户当前是最新版本，返回n不需要更新
			return Jsonp_data.success(CommonConstant.NO);
		}
		return Jsonp_data.success(CommonConstant.YES);
	}
	
}
