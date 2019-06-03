package com.bhimanshukalra.studentmanagementdb.backgroundTasks;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.bhimanshukalra.studentmanagementdb.database.DatabaseHandler;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.CREATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DELETE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SERVICE_MSG;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SERVICE_STUDENT;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATE_OPERATION;

public class ServiceBroadcastReceiver extends BroadcastReceiver {
    private DatabaseHandler mDb;
    private String mOperation;
    private Student mStudent;

    @Override
    public void onReceive(Context context, Intent intent) {
        mDb = new DatabaseHandler(context);
        mOperation = intent.getStringExtra(SERVICE_MSG);
        mStudent = intent.getParcelableExtra(SERVICE_STUDENT);
        Toast.makeText(context, "Service", Toast.LENGTH_SHORT).show();
//        new MyService().onCreate();
        context.startService(new Intent(context, MyService.class));
    }
    public class MyService extends Service{
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            switch (mOperation){
                case CREATE_OPERATION:
                    mDb.addStudent(mStudent);
                    break;
                case UPDATE_OPERATION:
                    mDb.updateStudent(mStudent);
                    break;
                case DELETE_OPERATION:
                    mDb.deleteStudent(mStudent.getRollNumber());
                    break;
            }
        }

    }
//    TODO: unregisterReceiver
}
