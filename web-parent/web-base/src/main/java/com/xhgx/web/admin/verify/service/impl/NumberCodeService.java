package com.xhgx.web.admin.verify.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.xhgx.web.admin.verify.service.AbstractVerifyCodeService;

/**
 * @ClassName NumberCodeService
 * @Description 简单的数字验证码
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 17:34
 * @vresion 1.0
 */
public class NumberCodeService extends AbstractVerifyCodeService {

	/**
	 * 默认图片的宽度
	 */
	private final static int DEF_WIDTH = 60;

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
	 * Constructor for NumberCodeService
	 */
	public NumberCodeService() {
		this.width = DEF_WIDTH;
		this.height = DEF_HEIGHT;
	}

	/**
	 * 创建一个4位的验证码
	 * 
	 * @return Object
	 */
	@Override
	public Object createImage() {
		// 创建图片
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();
		String code = "";

		// 设置背景颜色
		g.setColor(this.getRandColor(200, 255));
		g.fillRect(0, 0, width, height);
		g.drawRect(0, 0, width - 1, height - 1);
		// 设置字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		// 创建随即对象
		Random random = new Random();
		g.setColor(this.getRandColor(160, 200));

		// 画一个验证码
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);

			g.drawLine(x, y, x + xl, y + yl);
		}

		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			code += rand;

			// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));

			g.drawString(rand, 13 * i + 6, 16);
		}

		g.dispose();
		return code;
	}

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

	/**
	 * 验证输入的验证码是否正确
	 * 
	 * @param o
	 *            需要验证的对象
	 * @return boolean
	 */
	@Override
	public boolean valdateCode(Object o) {
		return String.valueOf(o).trim()
				.equals(String.valueOf(this.getSrcCode()));
	}
}
