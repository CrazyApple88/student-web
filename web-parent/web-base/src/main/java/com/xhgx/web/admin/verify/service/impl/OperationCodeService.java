package com.xhgx.web.admin.verify.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.xhgx.web.admin.verify.service.AbstractVerifyCodeService;

/**
 * @ClassName OperationCodeService
 * @Description  算术验证码
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 17:54
 * @vresion 1.0
 */
public class OperationCodeService extends AbstractVerifyCodeService {

	/**
	 * 默认图片的宽度
	 */
	private final static int DEF_WIDTH = 100;

	/**
	 * 默认图片的高度
	 */
	private final static int DEF_HEIGHT = 20;

	/**
	 * 图片的宽度
	 */
	private int width;

	/**
	 * 图片的高度
	 */
	private int height;

	/**
	 * Constructor for OperationCodeService
	 */
	public OperationCodeService() {
		super();
		this.width = DEF_WIDTH;
		this.height = DEF_HEIGHT;
	}

	@Override
	public Object createImage() {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 生成随机类
		Random random = new Random();
		// 设定背景色
		g.setColor(getRandColor(240, 250));
		g.fillRect(0, 0, width, height);
		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(180, 230));
		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		// 验证码，由2个一位数的加减法构成，例如：3 + 4 = ？
		int num1 = (int) (Math.random() * 10);
		int num2 = (int) (Math.random() * 10);
		int funNo = random.nextInt(3);
		// 产生[0,2]之间的随机整数
		String funMethod = "";
		int result = 999;
		switch (funNo) {
		case 0:
			funMethod = "+";
			result = num1 + num2;
			break;
		case 1:
			funMethod = "-";
			result = num1 - num2;
			break;
		case 2:
			funMethod = "×";
			result = num1 * num2;
			break;
			default:
			break;	
		}
		String calc = num1 + " " + funMethod + " " + num2 + " = ?";
		g.setColor(new Color(20 + random.nextInt(110),
				20 + random.nextInt(110), 20 + random.nextInt(110)));
		g.drawString(calc, 5, 16);
		// 图象生效
		g.dispose();
		return result;
	}

	@Override
	public boolean valdateCode(Object o) {
		return String.valueOf(o).trim()
				.equals(String.valueOf(this.getSrcCode()));
	}

	/**
	 * @param begin
	 *            范围的开始数据
	 * @param end
	 *            范围的结束数据
	 * @return getRandColor
	 * @Title: getRandColor
	 * @date 2014-3-6
	 * @Description: 给定范围获得随机颜色
	 */
	Color getRandColor(int begin, int end) {
		Random random = new Random();

		if (begin > 255){
			begin = 255;
		}

		if (end > 255){
			end = 255;
		}
			
		int r = begin + random.nextInt(end - begin);
		int g = begin + random.nextInt(end - begin);
		int b = begin + random.nextInt(end - begin);

		return new Color(r, g, b);

	}

}
