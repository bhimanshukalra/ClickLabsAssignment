package com.bhimanshukalra.studentmanagementdb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bhimanshukalra.studentmanagementdb.models.Student;

import java.util.ArrayList;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DB_SQ_LITE_UNIQUE_CONTRAINT_ERROR;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DB_UNIQUE_CONTRAINT_ERROR_MSG;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.EMPTY_STRING;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.TABLE_STUDENT;

/**
 * The type Database handler.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "studentManager";
    private static final String KEY_ROLL_NUMBER = "roll_number";
    private static final String KEY_NAME = "name";
    private static final String KEY_CLASS = "class";

    /**
     * Instantiates a new Database handler.
     *
     * @param context the context
     */
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createContactsTable = "CREATE TABLE " + TABLE_STUDENT + "(" +
                KEY_ROLL_NUMBER + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, " +
                KEY_CLASS + " TEXT)";
        db.execSQL(createContactsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);
    }

    /**
     * Add new student to db.
     *
     * @param student the student to be added.
     * @return if an error occurred, then error else empty string.
     */
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
            String response = exception.toString();
            if (response.equals(DB_SQ_LITE_UNIQUE_CONTRAINT_ERROR)) {
                response = DB_UNIQUE_CONTRAINT_ERROR_MSG;
            }
            return response;
        }
        return EMPTY_STRING;
    }


    /**
     * Get list of all students.
     *
     * @return the ArrayList of all students.
     */
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> studentList = new ArrayList<>();
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
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return studentList;
    }

    /**
     * Update student details.
     *
     * @param student the student whose details have to be updated.
     * @return if an error occurred then -1 is returned.
     */
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

    /**
     * Delete student details from db.
     *
     * @param rollNumber the roll number of student whose details have to be deleted.
     * @return if an error occurred then -1 is returned.
     */
    public long deleteStudent(int rollNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_STUDENT, KEY_ROLL_NUMBER + "=?",
                new String[]{String.valueOf(rollNumber)});
        db.close();
        return result;
    }

}
