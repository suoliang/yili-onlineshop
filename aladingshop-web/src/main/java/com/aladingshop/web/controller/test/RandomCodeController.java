package com.aladingshop.web.controller.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fushionbaby.common.util.date.DateFormat;
/***
 * 图片验证码---页面注册
 * @description 类描述...
 * @author 徐培峻
 * @date 2016年1月28日下午5:40:19
 */
@Controller
@RequestMapping("code")
public class RandomCodeController {
	
	
	
	private static final Log Logger=LogFactory.getLog(RandomCodeController.class);
	
	  private int width = 165;//定义图片的width
	  private int height = 38;//定义图片的height
	  private int codeCount = 4;//定义图片上显示验证码的个数
	  /**字符距离*/
	  private int xx = 30;
	  /**字符高度*/
	  private int fontHeight = 36;
	  /***/
	  private int codeY = 30;
	  char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
	      'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
	      'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	  
	  @RequestMapping("/code")
	  public void getCode(HttpServletRequest req, HttpServletResponse resp)throws IOException {
	    // 定义图像buffer
	    BufferedImage buffImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        //Graphics2D gd = buffImg.createGraphics();
	    //Graphics2D gd = (Graphics2D) buffImg.getGraphics();
	    Graphics gd = buffImg.getGraphics();
	    // 创建一个随机数生成器类
	    Random random = new Random();
	    // 将图像填充为白色
	    gd.setColor(Color.WHITE);
	    gd.fillRect(0, 0, width, height);

	    // 创建字体，字体的大小应该根据图片的高度来定。
	    Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
	    // 设置字体。
	    gd.setFont(font);

	    // 画边框。
	    gd.setColor(Color.BLACK);
	    gd.drawRect(0, 0, width - 1, height - 1);

	    // 随机产生60条干扰线，使图象中的认证码不易被其它程序探测到。
	    gd.setColor(Color.BLACK);
	    for (int i = 0; i < 100; i++) {
	      int x = random.nextInt(width);
	      int y = random.nextInt(height);
	      int xl = random.nextInt(12);
	      int yl = random.nextInt(12);
	      gd.drawLine(x, y, x + xl, y + yl);
	    }

	    // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
	    StringBuffer randomCode = new StringBuffer();
	    int red = 0, green = 0, blue = 0;

	    // 随机产生codeCount数字的验证码。
	    for (int i = 0; i < codeCount; i++) {
	      // 得到随机产生的验证码数字。
	      String code = String.valueOf(codeSequence[random.nextInt(36)]);
	      // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
	      red = random.nextInt(255);
	      green = random.nextInt(255);
	      blue = random.nextInt(255);

	      // 用随机产生的颜色将验证码绘制到图像中。
	      gd.setColor(new Color(red, green, blue));
	      gd.drawString(code, (i + 1) * xx, codeY);

	      // 将产生的四个随机数组合在一起。
	      randomCode.append(code);
	    }
	    // 将四位数字的验证码保存到Session中。
	    HttpSession session = req.getSession();
	    Logger.info("现在时间为："+DateFormat.dateToString(new Date())+"图片验证码为："+randomCode.toString());
	    session.setAttribute("code", randomCode.toString());
	    // 禁止图像缓存。
	    resp.setHeader("Pragma", "no-cache");
	    resp.setHeader("Cache-Control", "no-cache");
	    resp.setDateHeader("Expires", 0);

	    resp.setContentType("image/jpeg");
	    // 将图像输出到Servlet输出流中。
	    ServletOutputStream sos = resp.getOutputStream();
	    ImageIO.write(buffImg, "jpeg", sos);
	    sos.close();
	  }
	  
	  
	  
	  @RequestMapping("codeCheck")
	  public void codeCheck(@RequestParam("number") String number,HttpSession session,HttpServletResponse response){
      try {
		  boolean  result=true;
		  response.reset();
		  response.setContentType("text/html;charset=UTF-8");
		  String code=(String) session.getAttribute("code");
		  if(StringUtils.isBlank(number)||ObjectUtils.notEqual(number.toUpperCase(), code))
			  result=false;
		   Logger.info("当前时间为："+DateFormat.dateToString(new Date())+".验证图片验证码结果为："+result);
		   response.getWriter().print(result);
		} catch (IOException e) {
			Logger.error("当前时间为："+DateFormat.dateToString(new Date())+".验证图片验证码结果为："+false, e);
			e.printStackTrace();
		}
	  }
	  public static void main(String[] args) {
		String aa="ASD";
		String bb="asd";
		System.out.println(ObjectUtils.notEqual(aa, bb.toUpperCase()));
	}
	  
}
