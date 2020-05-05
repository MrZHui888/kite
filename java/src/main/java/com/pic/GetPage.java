package com.pic;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetPage {

    private final static int BUFFER_SIZE = 2048;

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        StringBuilder result = new StringBuilder();
        BufferedReader bf = null;
        try {
            // 打开和URL之间的连接
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            System.out.println("正在打开页面");
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
            // 建立实际的连接
           /* connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            bf = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = bf.readLine()) != null) {
                result.append(line);
            }
            System.out.println("页面解析完成");
            return result.toString();
        } catch (MalformedURLException e) {
            return "URL协议出错" + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "未知IO错误";
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将要提取的页面URL存到jpgUrl中
     *
     * @param page 要提取的页面
     * @return
     */
    public static void remixUrl(String page) {
        Pattern pattern = Pattern.compile("thumbURL\":\"https://ss(?s:(.*?))jpg");
        Matcher matcher = pattern.matcher(page);
        while (matcher.find()) {
            String group = matcher.group();
            ImageSpider.jpgUrls.push(group.substring(11));
        }

    }
    /**
     *
     * @param jpgUrl 获取的图片的URL
     * @return 获取到的图片
     */
    public static ArrayList<Byte> getJpg(String jpgUrl) {
        BufferedInputStream bf = null;
        try {
            URL url = new URL(jpgUrl);
            URLConnection urlConnection = url.openConnection();
            bf = new BufferedInputStream(urlConnection.getInputStream());
            ArrayList<Byte> result = new ArrayList<Byte>(2048);
            int r = 0;
            while ((r=bf.read()) != -1) {
                result.add((byte)r);
            }
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if (bf !=null){
                try {
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
