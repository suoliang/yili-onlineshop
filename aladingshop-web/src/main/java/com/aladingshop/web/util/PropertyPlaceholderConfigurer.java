package com.aladingshop.web.util;

import java.util.Properties;
import javax.servlet.ServletContext;
import org.springframework.web.context.ServletContextAware;

/**
 * 
 * @description properties工具类
 * @author 孙涛
 * @date 2015年7月24日上午10:31:35
 */
public class PropertyPlaceholderConfigurer extends
		org.springframework.beans.factory.config.PropertyPlaceholderConfigurer implements ServletContextAware {
	private static final String DEFAULT_KEY_CTXPATH = "_ctxPath";
	private static final String DEFAULT_JSPVARPREFIX = "jspvar.";

	private String keyCtxpath = DEFAULT_KEY_CTXPATH;
	private String jspvarPrefix = DEFAULT_JSPVARPREFIX;
	private String firstStr = DEFAULT_PLACEHOLDER_PREFIX;
	private String lastStr = DEFAULT_PLACEHOLDER_SUFFIX;

	private ServletContext servletContext;
	private Properties props;

	@Override
	protected String resolvePlaceholder(String placeholder, Properties props) {
		this.props = props;
		initApplicationScopeVar();
		return super.resolvePlaceholder(placeholder, props);
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	private void initApplicationScopeVar() {
		if (null != servletContext && null != props) {
			String ctxPath = servletContext.getContextPath();
			servletContext.setAttribute(keyCtxpath, ctxPath);
			props.put(keyCtxpath, ctxPath);
			for (Object keyo : props.keySet()) {
				String key = String.valueOf(keyo);
				if (key.startsWith(jspvarPrefix)) {
					String value = props.getProperty(key);
					value = convertValue(value);
					servletContext.setAttribute(key.substring(jspvarPrefix.length()), value);
				}
			}
		}
	}

	private String convertValue(String value) {
		int firstIndex = value.indexOf(firstStr);
		if (firstIndex != -1) {
			int lastIndex = value.indexOf(lastStr);
			String key1 = value.substring(firstIndex + 2, lastIndex);
			String value1 = props.getProperty(key1);

			value = value.replace(firstStr + key1 + lastStr, value1);

			return convertValue(value);
		} else {
			return value;
		}

	}

	public void setKeyCtxpath(String keyCtxpath) {
		this.keyCtxpath = keyCtxpath;
	}

	public void setJspvarPrefix(String jspvarPrefix) {
		this.jspvarPrefix = jspvarPrefix;
	}

	public void setFirstStr(String firstStr) {
		this.firstStr = firstStr;
	}

	public void setLastStr(String lastStr) {
		this.lastStr = lastStr;
	}

}
