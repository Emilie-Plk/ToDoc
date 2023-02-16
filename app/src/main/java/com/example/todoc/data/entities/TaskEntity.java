package com.example.todoc.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.util.Objects;

@Entity(tableName = "tasks",
        foreignKeys = @ForeignKey(
                entity = ProjectEntity.class,
                parentColumns = "id",
                childColumns = "projectId"))
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    private final long id;

    @NonNull
    @ColumnInfo(name = "taskDescription")
    private final String taskDescription;

    @NonNull
    @ColumnInfo(name = "taskTimeStamp")
    private final Timestamp taskTimeStamp;

    @ColumnInfo(index = true)
    private final long projectId;

    public TaskEntity(
            long id,
            long projectId,
            @NonNull String taskDescription,
            @NonNull Timestamp taskTimeStamp
    ) {
        this.id = id;
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

    @NonNull
    public Timestamp getTaskTimeStamp() {
        return taskTimeStamp;
    }

    public long getProjectId() {
        return projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity task = (TaskEntity) o;
        return id == task.id && projectId == task.projectId && taskDescription.equals(task.taskDescription) && taskTimeStamp.equals(task.taskTimeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskDescription, taskTimeStamp, projectId);
    }

    @NonNull
    @Override
    public String toString() {
        return "TaskEntity{" +
                "id=" + id +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskTimeStamp=" + taskTimeStamp +
                ", projectId=" + projectId +
                '}';
    }
}
