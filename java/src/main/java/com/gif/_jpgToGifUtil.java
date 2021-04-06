package com.gif;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class _jpgToGifUtil {
    public static void main(String[] args) {


        String imagePath = "/Users/gzh/Desktop/图片/";


        for (int i = 0; i < 5; i++) {

            long start =System.currentTimeMillis();
            File filePath = new File(imagePath);
            List<String> images = Arrays.stream(filePath.list()).map(x -> {
                return imagePath + x;
            }).collect(Collectors.toList());
            String newwPic = imagePath + String.valueOf(i) + ".gif";
            int playTime = 500;
            jpgToGif(images, newwPic, playTime);

            System.out.println(System.currentTimeMillis()-start);
        }


    }


    private void gen(String imagePath, String newPicPath) {
        File filePath = new File(imagePath);
        List<String> images = Arrays.stream(filePath.list()).map(x -> {
            return imagePath + x;
        }).collect(Collectors.toList());

        int playTime = 500;
        jpgToGif(images, newPicPath, playTime);
    }

    /**
     * 把多张jpg图片合成一张
     *
     * @param pic      String[] 多个jpg文件名 包含路径
     * @param newPic   String 生成的gif文件名 包含路径
     * @param playTime int 播放的延迟时间
     */
    private synchronized static void jpgToGif(List<String> pic, String newPic, int playTime) {
        try {
            AnimatedGifEncoder e = new AnimatedGifEncoder();
            e.setRepeat(0);
            e.start(newPic);
            BufferedImage src[] = new BufferedImage[pic.size()];
            for (int i = 0; i < src.length; i++) {
                e.setDelay(playTime); //设置播放的延迟时间
                src[i] = ImageIO.read(new File(pic.get(i))); // 读入需要播放的jpg文件
                e.addFrame(src[i]);  //添加到帧中
            }
            e.finish();
        } catch (Exception e) {
            System.out.println("jpgToGif Failed:");
            e.printStackTrace();
        }
    }


}
