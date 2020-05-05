package com.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReadFileUtils {


    public static void main(String[] args) throws Exception {
        File fileFolder = new File("/Users/gzh/Downloads/image/");

        int tmp = 0;

        // 1 是大字报
        List<File> daZiBaos = new ArrayList<>();

        // 0不是大字报
        List<File> bushidazibao = new ArrayList<>();

        // 1 test是大字报
        List<File> testDazibao = new ArrayList<>();

        // 0test 不是大字报
        List<File> testBushidazibao = new ArrayList<>();


        String path = "/Users/gzh/IdeaProjects/kite/java/src/main/java/com/file/";


        int indes = 0;
        if (fileFolder.exists()) {
            File[] files = fileFolder.listFiles();

            for (File file : files) {
                indes = indes + 1;
                if (!file.isDirectory()) {
                    String fileName = file.getName();

                    String[] filNames = fileName.split("-");
                    if (filNames[0].equals("0")) {
                        bushidazibao.add(file);
                    } else {
                        daZiBaos.add(file);
                    }


                    // 批次创建
                    if (daZiBaos.size() + bushidazibao.size() >= 16000 || indes == files.length) {


                        String batchFilePath = String.format(path + "%s", tmp);
                        // 检查批次文件夹
                        File bathFile = new File(batchFilePath);
                        if (!bathFile.exists()) {//如果文件夹不存在
                            bathFile.mkdir();//创建文件夹
                        }


                        // 检查批次文件夹
                        File trainMk = new File(batchFilePath + "/train");
                        if (!trainMk.exists()) {//如果文件夹不存在
                            trainMk.mkdir();//创建文件夹
                        }


                        // 检查批次文件夹
                        File testMk = new File(batchFilePath + "/test");
                        if (!testMk.exists()) {//如果文件夹不存在
                            testMk.mkdir();//创建文件夹
                        }


                        File dazibaofile = null;
                        // 创建大字报
                        if (daZiBaos.size() > 0) {

                            int testTrainSize = daZiBaos.size() / 14;

                            List<File> testTrainFiles = daZiBaos.stream()
                                    .skip(0).limit(testTrainSize)
                                    .collect(Collectors.toList());

                            List<File> trainFiles = daZiBaos.stream()
                                    .skip(testTrainSize).limit(daZiBaos.size())
                                    .collect(Collectors.toList());
                            if (testTrainFiles.size() > 0) {
                                String dazibaoPath = testMk.getAbsolutePath() + "/1";
                                dazibaofile = new File(dazibaoPath);

                                if (!dazibaofile.exists()) {
                                    dazibaofile.mkdir();
                                }

                                for (File file1 : testTrainFiles) {
                                    copyFile(file1, dazibaoPath + "/" + file1.getName());
                                }

                            }

                            if (trainFiles.size() > 0) {

                                String dazibaoPath = trainMk.getAbsolutePath() + "/1";
                                dazibaofile = new File(dazibaoPath);

                                if (!dazibaofile.exists()) {
                                    dazibaofile.mkdir();
                                }

                                for (File file1 : trainFiles) {
                                    copyFile(file1, dazibaoPath + "/" + file1.getName());
                                }
                            }


                        }

                        File bushidazibaoFile = null;
                        if (bushidazibao.size() > 0) {


                            int testTrainSize = bushidazibao.size() / 14;

                            List<File> testTrainFiles = bushidazibao.stream()
                                    .skip(0).limit(testTrainSize)
                                    .collect(Collectors.toList());

                            List<File> train = bushidazibao.stream()
                                    .skip(testTrainSize).limit(daZiBaos.size())
                                    .collect(Collectors.toList());

                            if (testTrainFiles.size() > 0) {
                                String dazibaoPath = testMk.getAbsolutePath() + "/0";

                                bushidazibaoFile = new File(dazibaoPath);
                                if (!bushidazibaoFile.exists()) {
                                    bushidazibaoFile.mkdir();
                                }
                                for (File file1 : testTrainFiles) {
                                    copyFile(file1, dazibaoPath + "/" + file1.getName());
                                }

                            }


                            if (train.size() > 0) {
                                String dazibaoPath = trainMk.getAbsolutePath() + "/0";

                                bushidazibaoFile = new File(dazibaoPath);
                                if (!bushidazibaoFile.exists()) {
                                    bushidazibaoFile.mkdir();
                                }
                                for (File file1 : train) {
                                    copyFile(file1, dazibaoPath + "/" + file1.getName());
                                }

                            }

                        }

                        daZiBaos.clear();
                        bushidazibao.clear();

                        tmp = tmp + 1;

                    }
                }


            }


        }

    }

    public static void copyFile(File oldFile, String newPath) throws IOException {
        copy(oldFile, newPath);

    }

    private static void copy(File srcFile, String target) {
        //创建源文件，和目标文件
        File targetFile = new File(target);
        //创建输入输出流
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(targetFile);
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param path 文件夹路径
     * @return java.util.List<java.io.File>
     * @description 使用递归的方法调用
     * @author https://blog.csdn.net/chen_2890
     * @date 2019/6/14 17:35
     * @version V1.0
     */
    public static List<File> traverseFolder2(String path) {
        List<File> fileList = new ArrayList<>();
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
                return null;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                        fileList.add(file2);
                        System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        return fileList;
    }


    //    public static void main(String[] args) {
//        recursiveTraversalFolder("/Users/gzh/Downloads/trunk/信安/1");
//    }
    public static void recursiveTraversalFolder(String path) {
        File folder = new File(path);
        if (folder.exists()) {
            File[] fileArr = folder.listFiles();
            if (null == fileArr || fileArr.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                File newDir = null;//文件所在文件夹路径+新文件名
                String newName = "";//新文件名
                String fileName = null;//旧文件名
                File parentPath = new File("");//文件所在父级路径

                String newString = "";
                for (File file : fileArr) {
                    if (file.isDirectory()) {//是文件夹，继续递归，如果需要重命名文件夹，这里可以做处理
                        System.out.println("文件夹:" + file.getAbsolutePath() + "，继续递归！");
                        recursiveTraversalFolder(file.getAbsolutePath());

                    } else {//是文件，判断是否需要重命名
                        fileName = file.getName();
                        parentPath = file.getParentFile();
//normal (5892).jpg
                        newString = fileName.replaceAll(" \\(", "-").replace(")", "");

                        newDir = new File(parentPath + "/" + newString);//文件所在文件夹路径+新文件名
                        file.renameTo(newDir);//重命名
                        System.out.println("修改后：" + newDir);

                    }
                }
            }
        } else {
            System.out.println("文件不存在!");

        }

    }
}
