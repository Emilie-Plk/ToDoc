package com.example.todoc.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "tasks")
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    public long taskId;

    @NonNull
    @ColumnInfo(name = "taskName")
    public String taskName;

    @ColumnInfo(name = "projectId")
    public long projectId;

    @NonNull
    @ColumnInfo(name = "projectName")
    public String projectName;
    @NonNull
    @ColumnInfo(name = "timeStamp")
    public Timestamp timeStamp;

    public TaskEntity(
            @NonNull String taskName,
            long projectId,
            @NonNull String projectName,
            @NonNull Timestamp timeStamp
    ) {
        this.taskName = taskName;
        this.projectId = projectId;
        this.projectName = projectName;
        this.timeStamp = timeStamp;
    }
}
