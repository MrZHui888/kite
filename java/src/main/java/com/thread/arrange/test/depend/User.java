package com.thread.arrange.test.depend;

/**
 * 一个包装类
 * @author gzh  on 2019-12-26
 * @version 1.0
 */
public class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
