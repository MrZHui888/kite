package com.file;

import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.Objects;

public class Txt {

    public static void main(String[] args) {
        File fileFolder = new File("/Users/gzh/IdeaProjects/kite/java/src/main/java/com/file/test1.txt");

        Map<String, String> map = txt2String(fileFolder);

        String x1="/Users/gzh/Downloads/样本/dazibao/1";

        String x0="/Users/gzh/Downloads/样本/dazibao/0";


        map.entrySet().forEach(entry->{
            String value =entry.getValue();
            String path=entry.getKey();

            if (Objects.equals(value,"0")){

                File file = new File(path);
                try {
                    ReadFileUtils.copyFile(file,x0+"/"+file.getName());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                File file = new File(path);
                try {
                    ReadFileUtils.copyFile(file,x1+"/"+file.getName());

                }catch (Exception e){
                    e.printStackTrace();
                }



            }


        });


    }

    public static Map<String, String> txt2String(File file) {

        Map<String, String> map = Maps.newHashMap();
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行

                String[] xx = s.split(" ");

                String label = xx[10];
                String path = xx[8];
                map.put(path, label);

                System.out.println(s);

            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
