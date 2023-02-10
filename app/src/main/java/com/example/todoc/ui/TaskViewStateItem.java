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
    @NonNull
    private final Timestamp taskTimeStamp;

    public TaskViewStateItem(long taskId, @NonNull String taskDescription, @NonNull String projectName, int projectColor, @NonNull Timestamp taskTimeStamp) {
        this.taskId = taskId;
        this.taskDescription = taskDescription;
        this.projectName = projectName;
        this.projectColor = projectColor;
        this.taskTimeStamp = taskTimeStamp;
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

    @NonNull
    public Timestamp getTaskTimeStamp() {
        return taskTimeStamp;
    }

    @NonNull
    @Override
    public String toString() {
        return "TaskViewStateItem{" +
                "taskId=" + taskId +
                ", taskDescription='" + taskDescription + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectColor=" + projectColor +
                ", taskTimeStamp=" + taskTimeStamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskViewStateItem that = (TaskViewStateItem) o;
        return taskId == that.taskId && projectColor == that.projectColor && taskDescription.equals(that.taskDescription) && projectName.equals(that.projectName) && Objects.equals(taskTimeStamp, that.taskTimeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, taskDescription, projectName, projectColor, taskTimeStamp);
    }
}
