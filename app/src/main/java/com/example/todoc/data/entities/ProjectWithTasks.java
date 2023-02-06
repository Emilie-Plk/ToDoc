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
}
