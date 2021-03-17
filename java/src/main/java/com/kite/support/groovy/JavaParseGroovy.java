package com.kite.support.groovy;

import org.apache.commons.io.IOUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JavaParseGroovy {

    static String groovyFile = "/com/kite/support/groovy/HelloWord/HelloWord.groovy";


    public static void main(String[] args) throws ScriptException, FileNotFoundException, IOException {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("groovy");
        //先测试下，行不行

        User user = new User();
        user.setAge(1);
        user.setType(1);

        String temp = "1 == %s&& 10> %s  ";
        String rule1 = String.format(temp, user.getType(), user.getAge());

        if ((Boolean) engine.eval(rule1)) {
            System.out.println("日了");
        }


        String template = "1== " + user.getAge();

        System.out.println(engine.eval(template));


        System.out.println("groovy解析结果:" + engine.eval("println 'HelloWord' \n 'HelloWordReturn'"));
        //读取源Groovy源程序
        String fileFullPath = "/Users/gzh/IdeaProjects/kite/java/src/main/java/com/kite/support/groovy/HelloWord/HelloWord.groovy";
        String scriptContent = IOUtils.toString(new FileInputStream(fileFullPath));
        System.out.println("----------groovy-exec----------");
        engine.eval(scriptContent);
    }

    public static String rootDir() {
        String classDir = JavaParseGroovy.class.getClassLoader().getResource("").getPath();
        int idx = classDir.lastIndexOf("/", classDir.length() - 2);
        return classDir.substring(0, idx);
    }


}


class User {
    private int age;

    private int type;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
