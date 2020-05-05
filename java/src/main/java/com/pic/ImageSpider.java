package com.pic;


import org.apache.commons.lang3.RandomUtils;

import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;

//  图片爬取
public class ImageSpider {
    public static Stack<String> jpgUrls = new Stack<>();
    public static CountDownLatch countDownLatch;

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.println("输入要查询的关键字：");
        String keyWord = scan.nextLine();
        String url = "https://image.baidu.com/search/index";

        String path ="/Users/gzh/Downloads/car/spider/";


        for (int i = 1; i < 100; i++) {
            String parm = "tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fr=&sf=1&fmq=1526269427171_R&pv=&ic=0&nc=1&z=&se=%s&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=";
            String urlParam = String.format(parm, i);

            Thread.sleep(RandomUtils.nextLong(20, 1000));

            String s = GetPage.sendGet(url, urlParam + keyWord);
            //提取URL
            GetPage.remixUrl(s);
        }




        int threadCount = jpgUrls.size();//因为stack的长度会在pop 的时候减少，所以将完整长度保存
        System.out.println("获取到" + threadCount + "张图片，正在保存到本地");
        countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new SavePage().start();
        }
        try {
            countDownLatch.await();
            System.out.println("运行完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
