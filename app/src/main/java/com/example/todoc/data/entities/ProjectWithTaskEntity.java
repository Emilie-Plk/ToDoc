package com.example.todoc.data.entities;

import androidx.room.Embedded;
import androidx.room.Query;
import androidx.room.Relation;
import androidx.room.Transaction;

import java.util.List;

public class ProjectWithTaskEntity {
    @Embedded public ProjectEntity projectEntity;
    @Relation(
            parentColumn = "projectId",
            entityColumn = "projectId"
    )
    public List<TaskEntity> taskEntities;

}
