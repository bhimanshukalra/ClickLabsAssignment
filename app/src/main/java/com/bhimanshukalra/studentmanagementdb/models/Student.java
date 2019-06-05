package com.bhimanshukalra.studentmanagementdb.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The type Student.
 */
public class Student implements Parcelable {
    private String studentName;
    private String className;
    private int rollNumber;

    /**
     * The constant CREATOR.
     */
    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    /**
     * Instantiates a new Student.
     */
    public Student() {
    }

    /**
     * Instantiates a new Student.
     *
     * @param studentName the student name
     * @param className   the class name
     * @param rollNumber  the roll number
     */
    public Student(String studentName, String className, int rollNumber) {
        this.studentName = studentName;
        this.className = className;
        this.rollNumber = rollNumber;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(studentName);
        dest.writeString(className);
        dest.writeInt(rollNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Instantiates a new Student.
     *
     * @param in the in
     */
    protected Student(Parcel in) {
        studentName = in.readString();
        className = in.readString();
        rollNumber = in.readInt();
    }

    /**
     * Gets student name.
     *
     * @return the student name
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Sets student name.
     *
     * @param studentName the student name
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
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
     * Sets class name.
     *
     * @param className the class name
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Gets roll number.
     *
     * @return the roll number
     */
    public int getRollNumber() {
        return rollNumber;
    }

    /**
     * Sets roll number.
     *
     * @param rollNumber the roll number
     */
    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }
}
