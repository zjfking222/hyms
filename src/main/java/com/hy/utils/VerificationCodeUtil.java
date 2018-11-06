package com.hy.utils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/2 8:08
 * @Description:验证码相关操作工具类
 */
public class VerificationCodeUtil {

    private static Random random = new Random();

    /**
     * @Author 钱敏杰
     * @Description 生成随机验证码字符串
     * @Date 2018/11/5 8:56
     * @Param [length：验证码长度, exChars：不能出现的字符]
     * @return java.lang.String
     **/
    public static String getRandomCode(int length, String exChars){
        StringBuffer buf = new StringBuffer();
        int i = 0;
        while(i<length){
            int t= random.nextInt(123);
            //数字+(大小写)字母
            if((t>=97||(t>=65&&t<=90)||(t>=48&&t<=57))&&(exChars==null||exChars.indexOf((char)t)<0)){
                buf.append((char)t);
                i++;
            }
        }
        return buf.toString();
    }

    /**
     * @Author 钱敏杰
     * @Description 根据验证码字符串生成相应大小的验证码图片
     * @Date 2018/11/5 10:01
     * @Param [width, height, code]
     * @return java.awt.image.BufferedImage
     **/
    public static BufferedImage getCodeImage(int width, int height, String code){
        //创建图片对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置边框色
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, width, height);
        // 设置背景色
        Color c = getRandColor(200, 250);
        g2.setColor(c);
        g2.fillRect(0, 2, width, height-4);
        //绘制10条干扰线
        drawLine(g2, 10, width, height);
        // 添加噪点
        addHotPixel(image, width, height);
        // 使图片扭曲
        //shearX(g2, width, height, c);
        //shearY(g2, width, height, c);
        //添加验证码字符串到图片中
        int fontSize = height-4;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        int verifySize = code.length();
        //字符字体用黑色需要能清晰显示
        g2.setColor(Color.black);
        for(int i = 0; i < verifySize; i++){
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * random.nextDouble() * (random.nextBoolean() ? 1 : -1), (width / verifySize) * i + fontSize/2, height/2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((width-10) / verifySize) * i + 5, height/2 + fontSize/2 - 10);
        }

        g2.dispose();
        return image;
    }

    /**
     * @Author 钱敏杰
     * @Description 添加噪点
     * @Date 2018/11/5 16:08
     * @Param [image, width, height]
     * @return void
     **/
    private static void addHotPixel(BufferedImage image, int width, int height){
        //噪声率
        float yawpRate = 0.05f;
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 给图片绘制干扰线
     * @Date 2018/11/5 15:56
     * @Param [g2, num, width]
     * @return void
     **/
    private static void drawLine(Graphics2D g2, int num, int width, int height){
        if(num >0){
            int x=random.nextInt(4),y=0;
            int x1=width-random.nextInt(4),y1=0;
            for (int i = 0; i < num; i++) {
                g2.setColor(getRandColor(160, 200));// 设置线条的颜色
                y=random.nextInt(height-random.nextInt(4));
                y1=random.nextInt(height-random.nextInt(4));
                //绘制
                g2.drawLine(x,y,x1,y1);
            }
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 生成颜色对象
     * @Date 2018/11/5 9:35
     * @Param [fc, bc]
     * @return java.awt.Color
     **/
    private static Color getRandColor(int fc, int bc) {
        if (fc > 255){
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * @Author 钱敏杰
     * @Description 生成随机颜色数值
     * @Date 2018/11/5 9:52
     * @Param []
     * @return int
     **/
    private static int getRandomIntColor() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    /**
     * @Author 钱敏杰
     * @Description 扭曲X轴
     * @Date 2018/11/5 9:44
     * @Param [g, w1, h1, color]
     * @return void
     **/
    private static void shearX(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(2);
        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);
        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 扭曲Y轴
     * @Date 2018/11/5 9:42
     * @Param [g, w1, h1, color]
     * @return void
     **/
    private static void shearY(Graphics g, int w1, int h1, Color color) {
        // 10-50;
        int period = random.nextInt(40) + 10;
        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }
    }
}
