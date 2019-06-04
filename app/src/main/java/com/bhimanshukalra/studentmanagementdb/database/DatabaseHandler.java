package com.bhimanshukalra.studentmanagementdb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bhimanshukalra.studentmanagementdb.models.Student;

import java.util.ArrayList;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.EMPTY_STRING;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.TABLE_STUDENT;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "studentManager";
    public static final String KEY_ROLL_NUMBER = "roll_number";
    public static final String KEY_NAME = "name";
    public static final String KEY_CLASS = "class";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createContactsTable = "CREATE TABLE " + TABLE_STUDENT + "(" +
                KEY_ROLL_NUMBER + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, " +
                KEY_CLASS + " TEXT)";
        /**
         * TODO: PRIMARY KEY,
         */
//        log(createContactsTable);
        db.execSQL(createContactsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);
    }

    public String addStudent(Student student) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_NAME, student.getStudentName());
            contentValues.put(KEY_CLASS, student.getClassName());
            contentValues.put(KEY_ROLL_NUMBER, student.getRollNumber());
            db.insertOrThrow(TABLE_STUDENT, null, contentValues);
            db.close();
        } catch (Exception exception) {
            return exception.toString();
        }
        return EMPTY_STRING;
    }

    public Student getStudent(int rollNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENT, new String[]{KEY_ROLL_NUMBER, KEY_NAME,
                        KEY_CLASS}, KEY_ROLL_NUMBER + "=?", new String[]{String.valueOf(rollNumber)},
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Student student = new Student(cursor.getString(0),
                    cursor.getString(1), Integer.parseInt(cursor.getString(2)));
            cursor.close();
            return student;
        }
        return null;
    }

    public void getAllStudents(ArrayList<Student> studentList) {
        String selectQuery = "SELECT * FROM " + TABLE_STUDENT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setRollNumber(Integer.parseInt(cursor.getString(0)));
                student.setStudentName(cursor.getString(1));
                student.setClassName(cursor.getString(2));
                studentList.add(student);
//                log("dbHelper1: " + student.getStudentName() + " " + student.getClassName() + " " + student.getRollNumber());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    public long updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, student.getStudentName());
        contentValues.put(KEY_CLASS, student.getClassName());
        long result = db.update(TABLE_STUDENT, contentValues, KEY_ROLL_NUMBER + "=?",
                new String[]{String.valueOf(student.getRollNumber())});
        db.close();
        return result;
    }

    public long deleteStudent(int rollNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_STUDENT, KEY_ROLL_NUMBER + "=?",
                new String[]{String.valueOf(rollNumber)});
        db.close();
        return result;
    }

    public int getStudentCount() {
        String countQuery = "SELECT * FROM " + TABLE_STUDENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }


}
