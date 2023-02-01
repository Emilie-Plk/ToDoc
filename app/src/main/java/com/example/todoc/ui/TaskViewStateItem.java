package com.example.todoc.ui;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import java.util.Objects;

public class TaskViewStateItem {

    private final long taskId;
    @NonNull
    private final String taskName;

    @NonNull
    private final String projectName;

    @ColorInt
    private final int projectColor;

    public TaskViewStateItem(long taskId, @NonNull String taskName, @NonNull String projectName, int projectColor) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.projectName = projectName;
        this.projectColor = projectColor;
    }

    public long getTaskId() {
        return taskId;
    }

    @NonNull
    public String getTaskName() {
        return taskName;
    }

    @NonNull
    public String getProjectName() {
        return projectName;
    }

    public int getProjectColor() {
        return projectColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskViewStateItem that = (TaskViewStateItem) o;
        return taskId == that.taskId && projectColor == that.projectColor && taskName.equals(that.taskName) && projectName.equals(that.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, taskName, projectName, projectColor);
    }
}
