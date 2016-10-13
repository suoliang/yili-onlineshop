
package com.aladingshop.store.config;

import javax.servlet.ServletConfig;

import com.ckfinder.connector.configuration.Configuration;
import com.ckfinder.connector.utils.AccessControlUtil;

/**
 * CKFinder配置
 * @author 孟少博
 * @version 2015-06-08
 */
public class CKFinderConfig extends Configuration {

	public CKFinderConfig(ServletConfig servletConfig) {
        super(servletConfig);  
    }
	
	@Override
    protected Configuration createConfigurationInstance() {
		AccessControlUtil.getInstance(this).loadACLConfig();
		try {
			//this.baseURL = ServletContextFactory.getServletContext().getContextPath()+"/userfiles/";/** 本地上传图片*/
			this.baseURL = Global.getBaseUrl();
			
			this.baseDir = Global.getBaseDir();/**如果使用本地的请将此行注释*/
			
			System.out.println(this.baseURL);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new CKFinderConfig(this.servletConf);
	}
	
}
