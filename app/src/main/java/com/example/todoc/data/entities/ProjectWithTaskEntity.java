package com.example.todoc.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;


import java.util.List;
import java.util.Objects;

public class ProjectWithTaskEntity {
    @NonNull
    @Embedded public ProjectEntity projectEntity;
    @Relation(
            parentColumn = "projectId",
            entityColumn = "projectId"
    )
    public List<TaskEntity> taskEntities;

    public ProjectWithTaskEntity(@NonNull ProjectEntity projectEntity, @NonNull List<TaskEntity> taskEntities) {
        this.projectEntity = projectEntity;
        this.taskEntities = taskEntities;
    }

    public ProjectEntity getProjectEntity() {
        return projectEntity;
    }

    public List<TaskEntity> getTaskEntities() {
        return taskEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectWithTaskEntity that = (ProjectWithTaskEntity) o;
        return Objects.equals(projectEntity, that.projectEntity) && Objects.equals(taskEntities, that.taskEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectEntity, taskEntities);
    }

    @Override
    public String toString() {
        return "ProjectWithTaskEntity{" +
                "projectEntity=" + projectEntity +
                ", taskEntities=" + taskEntities +
                '}';
    }
}
