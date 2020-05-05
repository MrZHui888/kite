package com.file;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Download {

    public static void main(String[] args) throws Exception {

//        String tag = "1209680791069331456";// 叉车

//        String tag="1140449781135818752";//其他

        // String tag = "1140449781106458624";// 房车

        //1140449781123235840  未完成
        // String tag = "1140449781123235840";// 特殊车辆


        // 1140449781093875712  救护车
//        String tag="1140449781093875712";

//        1140449781081292800 货车
//        String tag="1140449781081292800";


        // 1140449781068709888  新能源车
//        String tag="1140449781068709888";

        //1140449781056126976 SUV

//        String tag="1140449781056126976";

        //1140449781043544064  跑车
//        String tag="1140449781043544064";


        //1140449781030961152 普通轿车
//        String tag="1140449781030961152";

        // 1140449781018378240 豪华轿车
//        String tag="1140449781018378240";

        //1140449781005795328 Mpv 商务车
//        String tag = "1140449781005795328";

        //1140449780997406720  面包车
//        String tag = "1140449780997406720";

        //1140449780984823808  中巴
        String tag="xx";


        String path = String.format("/Users/gzh/IdeaProjects/kite/java/src/main/java/com/file/data/file/%s.txt", tag);
        String downPath = "/Users/gzh/Downloads/car/" + tag;

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),
                "UTF-8"));

        String lineTxt = null;
        while ((lineTxt = br.readLine()) != null) {

            String[] urlTag = lineTxt.split("#");


            String uri = getCommonCdnPicUrl(urlTag[0]);

            String name = uri.split("_")[1].split("\\.")[0];

            down(uri, downPath, name);

            System.out.println(uri);
        }
        br.close();


    }


    private static void down(String url, String path, String picName) {
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        BufferedInputStream bis = null;
        FileOutputStream out = null;
        try {
            File file0 = new File(path);
            if (!file0.isDirectory() && !file0.exists()) {
                file0.mkdirs();
            }

            String directorPath = file0.getAbsolutePath();
            out = new FileOutputStream(directorPath + "/" + picName + ".jpg");
            // 建立链接
            URL httpUrl = new URL(url);
            conn = (HttpURLConnection) httpUrl.openConnection();
            //以Post方式提交表单，默认get方式
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream = conn.getInputStream();
            bis = new BufferedInputStream(inputStream);
            byte b[] = new byte[1024];
            int len = 0;
            while ((len = bis.read(b)) != -1) {
                out.write(b, 0, len);
            }
            System.out.println("下载完成...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static String getCommonCdnPicUrl(String url) {
        if (url.startsWith("http://")) {
            int index = url.indexOf('/', "http://".length());
            if (index > 0) {
                return "http://pic" + ((int) (Math.random() * 8) + 1) + ".58cdn.com.cn" + url.substring(index);
            } else {
                return url;
            }
        } else if (url.startsWith("https://")) {
            int index = url.indexOf('/', "https://".length());
            if (index > 0) {
                return "https://pic" + ((int) (Math.random() * 8) + 1) + ".58cdn.com.cn" + url.substring(index);
            } else {
                return url;
            }
        } else if (url.startsWith("/")) {
            return "http://pic" + ((int) (Math.random() * 8) + 1) + ".58cdn.com.cn" + url;
        } else {
            return "http://pic" + ((int) (Math.random() * 8) + 1) + ".58cdn.com.cn/" + url;
        }
    }
}
