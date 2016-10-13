package com.fushionbaby.wap.controller.activities;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/***
 * 外来合作的一些活动
 * 
 * 过后台跳转到页面
 * 
 * @author xupeijun
 *
 */
@Controller
@RequestMapping("/activity")
public class CooperationActivityController {
	/***
	 * 跳转到滴滴活动页面
	 * @return
	 */
	@RequestMapping("go_didi")
	public String goToDiDiActivity(){
		return "activities/didi";
	}
	/***
	 * 跳转到跨境兜拿下的分块下
	 * 
	 * @return
	 */
	@RequestMapping("goCross")
	public String goCross() {

		return "activities/cross";
	}

	/***
	 * 跳转到宝宝摄影的分块下
	 * 
	 * @return
	 */
	@RequestMapping("goTakePhoto")
	public String goTakePhoto() {

		return "activities/photography";
	}

	/***
	 * 跳转到兜爱早教的分块下
	 * 
	 * @return
	 */
	@RequestMapping("goInfantEducation")
	public String goInfantEducation() {

		return "activities/teach";
	}
	
}
