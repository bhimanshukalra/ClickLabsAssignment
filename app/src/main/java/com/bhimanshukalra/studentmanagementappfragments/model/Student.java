package com.bhimanshukalra.studentmanagementappfragments.model;

import java.io.Serializable;

/**
 * The type Student.
 */
public class Student implements Serializable {
    private String name;
    private String className;
    private Integer rollNumber;

    /**
     * Instantiates a new Student.
     *
     * @param name       the name
     * @param className  the class name
     * @param rollNumber the roll number
     */
    public Student(String name, String className, Integer rollNumber) {
        this.name = name;
        this.className = className;
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public Integer getRollNumber() {
        return rollNumber;
    }
}
