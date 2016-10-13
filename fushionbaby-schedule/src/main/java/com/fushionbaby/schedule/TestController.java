package com.fushionbaby.schedule;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;

/***
 * 测试定时任务有没有跑起来
 * @author King 索亮
 *
 */
@Controller
@RequestMapping("/")
public class TestController {
	@ResponseBody
	@RequestMapping("/test")
	public Object test(HttpServletRequest request)  {
		try {
			return Jsonp_data.success("定时任务正常运行");
		} catch(DataAccessException e) {
			e.printStackTrace();
			return Jsonp.error("定时任务到异常块了");
		}		
	}
}
