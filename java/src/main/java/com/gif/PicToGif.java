package com.gif;

/**
 * Copyright © 2021 All rights reserved
 *
 * @Package com.leeannm.util
 * @Function PicToGif.java
 * @author  LIAN
 * @date    2021年2月4日 下午6:36:05
 * @Description 2.调用方法，实现图片合成的代码逻辑，有参考
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class PicToGif {

    public static void main(String[] args) {//非程序入口，测试用

        File file=new File("F://1");//以下5行是在检测资源所在的文件夹
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }
        String str1 = "F://1//1.jpg"; //以下5行是在配置输入的imgList，类似的实现即可
        String str2 = "F://1//2.png";
        String[] pic = new String[2];
        pic[0] = str2;
        pic[1] = str1;
        String newPic = "F://1//output.gif"; //输出路径
        int playTime = 500;				//时间间隔
        jpgToGif(pic,newPic,playTime);	//执行静态函数(imgList,输出路径，时间间隔)
        System.out.println("结束！");
    }

    /**
     * @param pic String[] 多个jpg文件名 包含路径
     * @param newPic String 生成的gif文件名 包含路径
     * @param playTime int 播放的延迟时间
     * @Description 把多张图片合成一张（将新图片插入到gif图末尾中）
     */
    public synchronized static void jpgToGif(String[] pic, String newPic, int playTime) {
        try {
            AnimatedGifEncoder e = new AnimatedGifEncoder();
            e.setRepeat(0);
            e.start(newPic);
            BufferedImage[] src = new BufferedImage[pic.length];
            for (int i = 0; i < src.length; i++) {
                e.setDelay(playTime); //设置播放的延迟时间
                src[i] = ImageIO.read(new File(pic[i])); // 读入需要播放的jpg文件
                e.addFrame(src[i]);  //添加到帧中
            }
            e.finish();
        } catch (Exception e) {
            System.out.println( "jpgToGif Failed:");
            e.printStackTrace();
        }
    }

    /**
     * @Description 将上一函数，扩展为可自定义图片数量
     */
    public synchronized static void jpgToGif(String[] pic, int imgNum, String newPic, int playTime) {
        try {
            AnimatedGifEncoder e = new AnimatedGifEncoder();
            e.setRepeat(0);
            e.start(newPic+"\\output.gif");
            BufferedImage[] src = new BufferedImage[imgNum];
            for (int i = 0; i < imgNum; i++) {
                e.setDelay(playTime); //设置播放的延迟时间
                src[i] = ImageIO.read(new File(pic[i])); // 读入需要播放的jpg文件
                e.addFrame(src[i]);  //添加到帧中
            }
            e.finish();
        } catch (Exception e) {
            System.out.println( "jpgToGif Failed:");
            e.printStackTrace();
        }
    }

}
