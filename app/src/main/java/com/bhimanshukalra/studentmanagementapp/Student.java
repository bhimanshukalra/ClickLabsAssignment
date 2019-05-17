package com.bhimanshukalra.studentmanagementapp;

import java.io.Serializable;

/**
 * The type Student.
 */
public class Student implements Serializable {
    private String name;
    private String className;
    private Integer rollNum;

    /**
     * Instantiates a new Student.
     *
     * @param name      the name
     * @param className the class name
     * @param rollNum   the roll num
     */
    public Student(String name, String className, Integer rollNum) {
        this.name = name;
        this.className = className;
        this.rollNum = rollNum;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets class name.
     *
     * @return the class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Gets roll num.
     *
     * @return the roll num
     */
    public Integer getRollNum() {
        return rollNum;
    }
}
