package com.example.todoc.data.entities;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "projects")
public class ProjectEntity {
    @PrimaryKey
    private final long id;

    @ColumnInfo(name = "projectName")
    private final String projectName;

    @ColumnInfo(name = "projectColor")
    @ColorInt
    private final Integer projectColor;

    public ProjectEntity(long id, String projectName, @ColorInt Integer projectColor) {
        this.id = id;
        this.projectName = projectName;
        this.projectColor = projectColor;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getProjectName() {
        return projectName;
    }

    @ColorInt
    public Integer getProjectColor() {
        return projectColor;
    }

    }


