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
    public long timeStamp;

    @ColumnInfo(index = true)
    public long projectId;

    public TaskEntity(
            @NonNull String taskName,
            long timeStamp,
            long id
    ) {
        this.taskName = taskName;
        this.timeStamp = timeStamp;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getTaskName() {
        return taskName;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public long getProjectId() {
        return projectId;
    }

    public static class TaskAZComparator implements Comparator<TaskEntity> {
        @Override
        public int compare(TaskEntity left, TaskEntity right) {
            return left.taskName.compareTo(right.taskName);
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class TaskZAComparator implements Comparator<TaskEntity> {
        @Override
        public int compare(TaskEntity left, TaskEntity right) {
            return right.taskName.compareTo(left.taskName);
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements Comparator<TaskEntity> {
        @Override
        public int compare(TaskEntity left, TaskEntity right) {
            return (int) (right.timeStamp - left.timeStamp);
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements Comparator<TaskEntity> {
        @Override
        public int compare(TaskEntity left, TaskEntity right) {
            return (int) (left.timeStamp - right.timeStamp);
        }
    }
}
