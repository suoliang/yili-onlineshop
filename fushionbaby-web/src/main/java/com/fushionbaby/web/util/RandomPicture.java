package com.fushionbaby.web.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.RandomNumUtil;

/***
 * 输出一张随机的图片验证码
 * @author King 索亮
 *
 */
public class RandomPicture extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**图片长度和高度*/
	private static final int WIDTH = 64;
	private static final int HEIGHT = 37;
	/**字体高度*/
	private int fontHeight = HEIGHT/2;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		BufferedImage image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();

		// 1.设置背景色
		setBackGround(g);

		// 2.设置边框
		setBorder(g);

		// 3.画干扰线
		drawRandomLine(g);

		// 4.写随机数
		drawRandomNum((Graphics2D) g,request);

		// 5.图形写给浏览器
		response.setContentType("image/jpeg");

		// 发头控制浏览器不要缓存
		// 控制所有浏览器不要缓存
		response.setDateHeader("expries", -1);
		response.setHeader("Cache-Contorl", "no-cache");
		response.setHeader("Pragma", "no-cache");
		ImageIO.write(image, "jpg", response.getOutputStream());

	}

	// 设置背景色
	private void setBackGround(Graphics g) {
		g.setColor(Color.WHITE);
		// 填充指定的矩形
		g.fillRect(0, 0, WIDTH, HEIGHT);

	}

	// 设置边框
	private void setBorder(Graphics g) {
		g.setColor(Color.WHITE);
		// 绘制指定矩形的边框。
		g.drawRect(0, 0, WIDTH - 2, HEIGHT - 2);
	}

	//给定范围获得随机颜色
	public Color getRandColor(int fc,int bc){
	     Random random = new Random();
	     if(fc>255) fc=255;
	     if(bc>255) bc=255;
	     int r=fc+random.nextInt(bc-fc);
	     int g=fc+random.nextInt(bc-fc);
	     int b=fc+random.nextInt(bc-fc);
	     return new Color(r,g,b);
	}
	// 画干扰线
	private void drawRandomLine(Graphics g) {
		g.setColor(getRandColor(200,250));//干扰线颜色随机
		//生成160条干扰线
		for (int i = 0; i < 160; i++) {
			// 定义起点坐标
			int x1 = new Random().nextInt(WIDTH);
			int y1 = new Random().nextInt(HEIGHT);
			// 定义终点坐标
			int x2 = new Random().nextInt(WIDTH);
			int y2 = new Random().nextInt(HEIGHT);
			g.drawLine(x1, y1, x2, y2);
		}
	}

	private void drawRandomNum(Graphics2D g,HttpServletRequest request) {
		// 创建字体，字体的大小根据图片的高度来定
		Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
		// 设置字体。
		g.setFont(font);
		
		/**获取纯数字的验证码*/
		String registerRandomNum = RandomNumUtil.getRandom(RandomNumUtil.NUM, RandomNumUtil.REGISTER_USER_LENGTH);
		//拿到cookie，判断用户是否进行注册操作
		String register_verification = CookieUtil.getCookieValue(request, CookieConstant.REGISTER_COOKIE);
		
		//保存生成的验证码到SessionCache
		SessionCache.put(register_verification,registerRandomNum);
		
		// 创建一个随机数生成器类
		Random random = new Random();
		int red = 0, green = 0, blue = 0;
		int x=1;
		for (int i = 0; i < 4; i++) {
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			int degree= random.nextInt()%20;//返回-30-30之间的数
			String ch=String.valueOf(registerRandomNum.charAt(i));
			g.rotate(degree*Math.PI/180, x, 25);//设置旋转的弧度值
			// 用随机产生的颜色将验证码绘制到图像中。
			g.setColor(new Color(red, green, blue));
			g.drawString(ch, x, 25);
			g.rotate(-degree*Math.PI/180, x, 25);//旋转回来
			x+=15;//间距
		}
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

