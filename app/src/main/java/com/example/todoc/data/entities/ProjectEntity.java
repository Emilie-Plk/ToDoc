package com.example.todoc.data.entities;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "projects")
public class ProjectEntity {
    @PrimaryKey(autoGenerate = true)
    public long projectId;

    @NonNull
    @ColumnInfo(name = "projectName")
    public String projectName;

    @ColumnInfo(name = "projectColor")
    @ColorInt
    public int projectColor;

    public ProjectEntity(long projectId, @NonNull String projectName, @ColorInt int projectColor) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectColor = projectColor;
    }
}

