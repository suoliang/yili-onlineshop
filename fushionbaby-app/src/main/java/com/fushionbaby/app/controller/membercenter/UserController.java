package com.fushionbaby.app.controller.membercenter;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import org.apache.axis.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;

import com.fushionbaby.app.util.ImageConstantApp;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;

/**
 * 用户信息业务相关模块
 * @author guolongchao
 */
@SuppressWarnings("restriction")
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private MemberService<Member> memberService;
	
	/***
	 * 获取用户个人信息
	 * @param member_id 用户id
	 * @return User对象
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping("/userinfo")
	public Object userInfo(@RequestParam(value="sid") String sid) {
		UserDto userDto= (UserDto)SessionCache.get(sid);
		if(userDto == null) {
			return Jsonp.noLoginError("请先登录");
		}
		long member_id = userDto.getMemberId();
		
		Member member = memberService.findById(member_id);
		if(member!=null) {
			UserDto user = new UserDto();
			user.setMemberId(member_id);
			user.setLoginName(member.getLoginName());
			BigDecimal epoints = (BigDecimal) ((member.getEpoints()==null)?0:member.getEpoints());
			user.setEpoints(epoints);
			user.setImgPath(ImageConstantApp.MEMBER_IMAGE_SERVER_PATH+"/"+member.getImgPath());
			return Jsonp_data.success(user);
		}
		return Jsonp.error("用户不存在！");
	}


	
	/***
	 * 修改修改头像
	 * @param member_id 用户id
	 * @param head_img 用户头像
	 * @throws UnsupportedEncodingException
	 * response_code 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("/changePhoto")
	public Object changePhoto(@RequestParam(value="sid") String sid,
			@RequestParam(value = "head_img", required = true) String base64Data) {
		UserDto userDto= (UserDto)SessionCache.get(sid);
		if(userDto == null) {
			return Jsonp.noLoginError("请先登录");
		}
		if(StringUtils.isEmpty(base64Data)) {
			return Jsonp.paramError("头像不能为空");
		}
		
		long member_id = userDto.getMemberId();
		
        String destFileName = System.currentTimeMillis()+".jpg";
		String path = ImageConstantApp.MEMBER_IMAGE_PATH;//文件上传的路径
		String head_img = path + "/" + destFileName;
		
		try {
			uploadBase64(base64Data,new File(head_img));

			String url = ImageConstantApp.MEMBER_IMAGE_SERVER_PATH +"/"+destFileName;
					
			Member member = new Member();
			member.setId(member_id);
			member.setImgPath(destFileName);
			memberService.update(member);
			
			return Jsonp_data.success(url);
		} catch(Exception e) {
			e.printStackTrace();
			return Jsonp.error("修改用户头像失败！");
		}		
	}
	
	/**
	 * 接受并保存以base64格式上传的文件
	 * @param fieldName
	 */
	public void uploadBase64(String base64Data,File outFile) throws Exception {
		OutputStream output = null;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			output = new FileOutputStream(outFile);
			byte[] b = decoder.decodeBuffer(base64Data);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) b[i] += 256;
			}
			output.write(b);
			output.flush();
		}  catch(Exception e) {
			e.printStackTrace();
		} finally {
		   if(output!=null) output.close();
		}
	}
}
