package com.example.todoc.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "tasks",
        foreignKeys = @ForeignKey(
                entity = ProjectEntity.class,
                parentColumns = "id",
                childColumns = "projectId"))
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    @ColumnInfo(name = "taskDescription")
    private final String taskDescription;

    @ColumnInfo(name = "taskTimeStamp")
    private final Timestamp taskTimeStamp;

    @ColumnInfo(index = true)
    private final long projectId;

    public TaskEntity(
            long projectId,
            @NonNull String taskDescription,
            Timestamp taskTimeStamp
    ) {
        this.projectId = projectId;
        this.taskDescription = taskDescription;
        this.taskTimeStamp = taskTimeStamp;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getTaskDescription() {
        return taskDescription;
    }

    public Timestamp getTaskTimeStamp() {
        return taskTimeStamp;
    }

    public long getProjectId() {
        return projectId;
    }

}
