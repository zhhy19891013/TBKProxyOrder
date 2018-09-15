package com.yj.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VrCodeUtil {
	private static Logger logger = LoggerFactory.getLogger(VrCodeUtil.class);
	public static final String KEY_SING_UP="sign";
	public static final String KEY_SING_Login="login";
	
	public static final String KEY_ADMIN_UPLOAD="admin_upload";
	public static final String KEY_ADMIN_LOGIN="admin_login";
	
	public static final String KEY_AUDIT_LOGIN="audit_login";
	public static final String  KEY_BUSINESS_LOGIN="business_login";
	
	public static final String  KEY_RECALL="recall";
	
	
	
	
	/**
	 * 检验验证码
	 * @param request
	 * @param vcoder
	 * @return
	 */
	public static  boolean validateVrCode(HttpServletRequest request, String vcoder,
			String name) {
		try {
			String key = CountUtil.getIpAddress(request) + name;
			return null != request.getSession().getAttribute(key)
					&& request.getSession().getAttribute(key).toString()
							.equalsIgnoreCase(vcoder);
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 创建验证码
	 * @param response
	 * @param request
	 */
	public static void createVrCode(HttpServletResponse response,
			HttpServletRequest request,String key) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		// 在内存中创建图象
		int width = 100, height = 39;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 生成随机类
		Random random = new Random();
		// 设定背景色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		// 画边框
		// g.drawRect(0,0,width-1,height-1);
		g.draw3DRect(0, 0, width - 1, height - 1, true);
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		// 取随机产生的认证码(6位数字)
		String sRand = "";
		String s = "012345678901234567890123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ012345678901234567890123456789abcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < 4; i++) {
			char rand = s.charAt(random.nextInt(s.length()));
			sRand += rand;
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(String.valueOf(rand), 13 * i + 20, 20);
		}
		g.drawOval(20, 12, 60, 11);
		// 将认证码存入SESSION
		try {
			request.getSession().setAttribute(
					CountUtil.getIpAddress(request) + key, sRand);
			// 图象生效
			g.dispose();
			ServletOutputStream output;
			output = response.getOutputStream();
			// 输出图象到页面
			ImageIO.write(image, "JPEG", output);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 生成随机颜色
	 */
	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}
