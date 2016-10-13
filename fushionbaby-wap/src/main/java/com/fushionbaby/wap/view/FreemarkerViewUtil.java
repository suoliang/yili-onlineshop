package com.fushionbaby.wap.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;





import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerViewUtil extends FreeMarkerView {
	 
	@Override
	protected void doRender(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        //Expose model to JSP tags (as request attributes).
        exposeModelAsRequestAttributes(model, request);
        //Expose all standard FreeMarker hash models.
        SimpleHash fmModel = buildTemplateModel(model, request, response);

        if (logger.isDebugEnabled()) {
            logger.debug("Rendering FreeMarker template [" + getUrl() + "] in FreeMarkerView '" + getBeanName() + "'");
        }
        //Grab the locale-specific version of the template.
        Locale locale = RequestContextUtils.getLocale(request);       
       
	    if(null != request.getAttribute("savePath")) {
	    	 String savePath = (String) request.getAttribute("savePath");
	    	 if(savePath.length() > 0) {
	    		 createHTML(getTemplate(locale), fmModel, savePath, response);
	    		 response.sendRedirect(request.getContextPath()+"/html"+savePath.substring(savePath.indexOf("html")+4).replace(File.separator, "/"));
	    	 }
	    }else {
	       processTemplate(getTemplate(locale), fmModel, response);
	    }
	 }


	public void createHTML(Template template, SimpleHash model,String filePath,
	            HttpServletResponse response) throws IOException, TemplateException, ServletException {    
        File htmlFile = new File(filePath);
        if(!htmlFile.getParentFile().exists()){
            htmlFile.getParentFile().mkdirs();
        }
        if(!htmlFile.exists()){
            htmlFile.createNewFile();
        }
        Writer out = null;
        try{
        	out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile),"UTF-8"));
            template.process(model, out);
            out.flush();
        } catch(IOException e){
        	
        } finally {
        	if(out!=null) out.close();
        }
	}
}
