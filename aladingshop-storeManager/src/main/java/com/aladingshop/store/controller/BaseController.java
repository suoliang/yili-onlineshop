/**
 * Copyright &copy; 2014-2020 <a href="https://www.bailiangroup.com/osp">Bailian Group OSP</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.aladingshop.store.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 控制器支持类
 * 
 * @author IBM Consultant Team
 * @version 2013-3-23
 */
public abstract class BaseController {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 添加Model消息
	 * 
	 * @param messages
	 *            消息
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages) {
			sb.append(message).append(messages.length > 1 ? "<br/>" : "");
		}
		model.addAttribute("message", sb.toString());
	}

	/**
	 * 添加Flash消息
	 * 
	 * @param messages
	 *            消息
	 */
	protected void addMessage(RedirectAttributes redirectAttributes,
			String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages) {
			sb.append(message).append(messages.length > 1 ? "<br/>" : "");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}

	/**
	 * 字节流输出
	 *
	 */
	protected void Writer(String content) {
		try {
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(content);
			logger.info(content);
		} catch (IOException e) {
			logger.error("对象写出异常", e);
		}
	}
}
