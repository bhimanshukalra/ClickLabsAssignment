package com.bhimanshukalra.studentmanagementapp;

import java.io.Serializable;

public class Student implements Serializable {
    private String name;
    private String className;
    private Integer rollNum;

    public Student(String name, String className, Integer rollNum) {
        this.name = name;
        this.className = className;
        this.rollNum = rollNum;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public Integer getRollNum() {
        return rollNum;
    }
}
