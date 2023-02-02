package com.example.todoc.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.todoc.data.dao.AllDaos;
import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.ProjectWithTaskEntity;
import com.example.todoc.data.entities.TaskEntity;
import com.example.todoc.data.entities.TimeStampConverter;


@Database(entities = {TaskEntity.class, ProjectEntity.class, ProjectWithTaskEntity.class}, version = 1, exportSchema = false)
@TypeConverters({TimeStampConverter.class})
public abstract class TaskManagementDatabase extends RoomDatabase {

    public abstract AllDaos allDaos();

    public static volatile TaskManagementDatabase INSTANCE;

    public static TaskManagementDatabase getDatabase(@NonNull final Context context) {  // SINGLETON (getDatabase() returns it)
        if (INSTANCE == null) {
            synchronized (TaskManagementDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TaskManagementDatabase.class,
                                    "task_management_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
