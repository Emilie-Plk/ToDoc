package com.example.todoc.data.entities;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;


import java.util.List;

@Entity(tableName = "projectWithTasks")
public class ProjectWithTaskEntity {
    @Embedded public ProjectEntity projectEntity;
    @Relation(
            parentColumn = "projectId",
            entityColumn = "projectId", // TODO: project name too?
            entity = TaskEntity.class
    )
    public List<TaskEntity> taskEntities;

    public ProjectWithTaskEntity(ProjectEntity projectEntity, List<TaskEntity> taskEntities) {
        this.projectEntity = projectEntity;
        this.taskEntities = taskEntities;
    }

}
