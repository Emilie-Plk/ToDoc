package com.example.todoc.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.util.Comparator;

@Entity(tableName = "tasks",
        foreignKeys = @ForeignKey(
                entity = ProjectEntity.class,
                parentColumns = "id",
                childColumns = "projectId"))
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    @ColumnInfo(name = "taskName")
    public String taskName;

    @ColumnInfo(name = "timeStamp")
    public Timestamp timeStamp;

    @ColumnInfo(index = true)
    public long projectId;

    public TaskEntity(
            long projectId,
            @NonNull String taskName,
            Timestamp timeStamp
    ) {
        this.projectId = projectId;
        this.taskName = taskName;
        this.timeStamp = timeStamp;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getTaskName() {
        return taskName;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public long getProjectId() {
        return projectId;
    }

}
