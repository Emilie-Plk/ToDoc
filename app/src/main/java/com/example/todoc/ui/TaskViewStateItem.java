package com.example.todoc.ui;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.util.Objects;

public class TaskViewStateItem {

    private final long taskId;
    @NonNull
    private final String taskDescription;

    @NonNull
    private final String projectName;

    @ColorInt
    private final int projectColor;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    private final Timestamp timestamp;

    public TaskViewStateItem(long taskId, @NonNull String taskDescription, @NonNull String projectName, int projectColor, Timestamp timestamp) {
        this.taskId = taskId;
        this.taskDescription = taskDescription;
        this.projectName = projectName;
        this.projectColor = projectColor;
        this.timestamp = timestamp;
    }

    public long getTaskId() {
        return taskId;
    }

    @NonNull
    public String getTaskDescription() {
        return taskDescription;
    }

    @NonNull
    public String getProjectName() {
        return projectName;
    }

    public int getProjectColor() {
        return projectColor;
    }

    @Override
    public String toString() {
        return "TaskViewStateItem{" +
                "taskId=" + taskId +
                ", taskDescription='" + taskDescription + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectColor=" + projectColor +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskViewStateItem that = (TaskViewStateItem) o;
        return taskId == that.taskId && projectColor == that.projectColor && taskDescription.equals(that.taskDescription) && projectName.equals(that.projectName) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, taskDescription, projectName, projectColor, timestamp);
    }
}
