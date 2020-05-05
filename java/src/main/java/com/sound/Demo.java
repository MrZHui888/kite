package com.sound;

import java.net.URLEncoder;

public class Demo {


    public static void main(String[] args) throws Exception{
      String u=  URLEncoder.encode("大巴车", "gb2312");

      System.out.println(u);
    }
}
