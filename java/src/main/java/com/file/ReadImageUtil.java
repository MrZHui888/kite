package com.file;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

public class ReadImageUtil {

    public static void main(String[] args) throws Exception{

        String imagePath = "/Users/gzh/Downloads/样本/image";


        File file = new File(imagePath);
        String[] fileName = file.list();


        List<String> dazibao = Lists.newArrayList();

        List<String> nora = Lists.newArrayList();



        Arrays.asList(fileName)
                .forEach(x -> {

                    if (x.contains("0-")) {
                        nora.add(imagePath+"/"+x+"#0");

                    } else if (x.contains("1-")) {
                        dazibao.add(imagePath+"/"+x+"#1");
                    }
                });


        List<String> result =Lists.newArrayList();

        result.addAll(dazibao.subList(0,100));

        result.addAll(nora.subList(0,100));

        String txtx ="/Users/gzh/IdeaProjects/kite/java/src/main/java/com/file/train.txt";

        writeFile(result,txtx);

        System.out.println("");



    }

    private static void writeFile(List<String> list, String path) throws Exception {
        File text = new File(path);

        FileOutputStream fileOutputStream = null;
        if (!text.exists()) {
            boolean hasFile = text.createNewFile();
            fileOutputStream = new FileOutputStream(text);
        } else {
            fileOutputStream = new FileOutputStream(text, true);
        }


        OutputStreamWriter osw = new OutputStreamWriter(fileOutputStream);


        for (int i = 0; i < list.size(); i++) {
            osw.write(list.get(i) + "\r");
        }

        osw.close();

    }
}
