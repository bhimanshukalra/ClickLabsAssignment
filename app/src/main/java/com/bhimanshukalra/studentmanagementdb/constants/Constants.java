package com.bhimanshukalra.studentmanagementdb.constants;

public class Constants {
    public static final int SPLASH_TIME_DELAY = 1000;
    public static final int LIST_MIN_SIZE = 0;
    public static final int ASYNC_TASK = 1;
    public static final int LIST_TAB = 0;
    public static final int FORM_TAB = 1;
    public static final int SERVICE = 2;
    public static final int INTENT_SERVICE = 3;
    public static final String BG_HANDLER_PREF_KEY = "backgroundTaskHandler";
    public static final String PREF_KEY = "MyPref";

    public static final String EMPTY_STRING = "";
    public static final String UPDATE_FORM_BTN_TEXT = "Update";
    public static final String ADD_FORM_BTN_TEXT = "Add";
    public static final String CREATE_OPERATION = "create";
    public static final String READ_ALL_OPERATION = "readAll";
    public static final String UPDATE_OPERATION = "update";
    public static final String DELETE_OPERATION = "delete";
    public static final String TAB_ONE_TITLE = "List";
    public static final String STUDENT_LIST = "studentList";
    public static final String SERVICE_MSG = "com.bhimanshukalra.studentmanagementdb.SERVICE_MSG";
    public static final String SERVICE_STUDENT = "com.bhimanshukalra.studentmanagementdb.SERVICE_STUDENT";
    public static final String INTENT_SERVICE_MSG = "com.bhimanshukalra.studentmanagementdb.INTENT_SERVICE_MSG";
    public static final String INTENT_SERVICE_STUDENT = "com.bhimanshukalra.studentmanagementdb.INTENT_SERVICE_STUDENT";
    public static final String BROADCAST_UDATE_UI = "com.bhimanshukalra.studentmanagementdb.UPDATE_UI";
    public static final String TAB_TWO_EDIT_TITLE = "Edit Student";
    public static final String TAB_TWO_ADD_TITLE = "Add Student";
    public static final String DB_SQ_LITE_UNIQUE_CONTRAINT_ERROR = "android.database.sqlite.SQLiteConstraintException: " +
            "UNIQUE constraint failed: student.roll_number (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)";
    public static final String DB_ERROR_MSG = "Db Error.";
    public static final String OPERATION = "operation";
    public static final String DB_UNIQUE_CONTRAINT_ERROR_MSG = "Please enter unique roll number.";
    public static final String FORM_EMPTY_EDIT_TEXT_ERROR = "Please fill all edittexts.";
    public static final String HOME_APP_BAR_TITLE = "Home";
    public static final String VIEW_ACTIVITY_APP_BAR_TITLE = "View student";
    public static final String ALERT_DIALOG_TITLE = "Alert";
    public static final String ALERT_DIALOG_MSG = "What action would you like to perform";
    public static final String ALERT_DIALOG_NEUTRAL_BTN_TEXT = "View";
    public static final String VIEW_INTENT_KEY = "rollNum";
    public static final String ALERT_DIALOG_POSITIVE_BTN_TEXT = "Update";
    public static final String ALERT_DIALOG_NEGATIVE_BTN_TEXT = "Delete";
    public static final String ACCESS_MODE = "accessMode";
    public static final String BUNDLE_STUDENT_KEY = "student";
    public static final String INTENT_STUDENT_KEY = "student";
    public static final String ACCESS_MODE_VIEW = "view";
    public static final String TABLE_STUDENT = "student";
    public static boolean UPDATION_IN_LIST = false;
    public static int BACKGROUND_TASK_HANDLER;
}
