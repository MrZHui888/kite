package com.file;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WriteLine {


    public static void main(String[] args) throws Exception {

        String path = "/Users/gzh/IdeaProjects/kite/java/src/main/java/com/file/test1.txt";


        List<String> lines = Lists.newArrayList();

        String ling = "/Users/gzh/Downloads/car/rushPipe/1";
        File fileFolder = new File(ling);
        File[] files = fileFolder.listFiles();


        lines = Arrays.asList(files)
                .stream()
                .skip(0).limit(200)
                .map(file -> {
                    return file.getName() + "#" + file.getAbsolutePath() + "#" + "8";
                }).collect(Collectors.toList());
//        for (File file : files) {
//            lines.add(file.getName() + "#" + file.getAbsolutePath() + "#" + "0");
//        }


        String ling1 = "/Users/gzh/Downloads/car/未命名文件夹/48/4";
        File fileFolder1 = new File(ling1);
        File[] files1 = fileFolder1.listFiles();

        List<String> list = Arrays.asList(files1)
                .stream()
                .skip(0).limit(200)
                .map(file -> {
                    return file.getName() + "#" + file.getAbsolutePath() + "#" + "4";
                }).collect(Collectors.toList());

        lines.addAll(list);

        lines = Lists.reverse(lines);

        writeFile(lines, path);

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
