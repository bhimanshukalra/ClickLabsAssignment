package com.bhimanshukalra.studentmanagementdb.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    private String studentName;
    private String className;
    private int rollNumber;

    public Student() {
    }

    public Student(String studentName, String className, int rollNumber) {
        this.studentName = studentName;
        this.className = className;
        this.rollNumber = rollNumber;
    }

    protected Student(Parcel in) {
        studentName = in.readString();
        className = in.readString();
        rollNumber = in.readInt();
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

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getClassName() {
        return className;
    }

    public int getRollNumber() {
        return rollNumber;
    }
}
