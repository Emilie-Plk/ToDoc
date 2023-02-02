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
    public long taskId;

    @NonNull
    @ColumnInfo(name = "taskName")
    public String taskName;

    @ColumnInfo(name = "timeStamp")
    public Timestamp timeStamp;

    @ColumnInfo(index = true)
    public long projectId;

    public TaskEntity(
            @NonNull String taskName,
            @NonNull Timestamp timeStamp,
            long projectId
    ) {
        this.taskName = taskName;
        this.timeStamp = timeStamp;
        this.projectId = projectId;
    }
}
