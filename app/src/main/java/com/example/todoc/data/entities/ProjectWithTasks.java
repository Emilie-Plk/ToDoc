package com.example.todoc.data.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;
import java.util.Objects;

public class ProjectWithTasks {

    @Embedded
    public ProjectEntity project;

    @Relation(parentColumn = "id", entityColumn = "projectId", entity = TaskEntity.class)
    public List<TaskEntity> tasks;

    public ProjectWithTasks(ProjectEntity project, List<TaskEntity> tasks) {
        this.project = project;
        this.tasks = tasks;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectWithTasks that = (ProjectWithTasks) o;
        return Objects.equals(project, that.project) && Objects.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, tasks);
    }

    @Override
    public String toString() {
        return "ProjectWithTasks{" +
                "project=" + project +
                ", tasks=" + tasks +
                '}';
    }
}
