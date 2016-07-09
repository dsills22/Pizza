package com.projects.pizza;

/**
 * Created by devin on 7/8/16.
 */
public class Util {
    public Util() {}

    private static Integer maxIdUsed = 0;

    public static synchronized Integer newId() {
        return ++maxIdUsed;
    }

    public static synchronized void setMaxId(Integer maxId) {
        maxIdUsed = maxId;
        newId();
    }
}
