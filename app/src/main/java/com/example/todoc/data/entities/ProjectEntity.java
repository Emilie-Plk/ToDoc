package com.example.todoc.data.entities;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "projects")
public class ProjectEntity {
    @PrimaryKey
    public long id;

    @ColumnInfo(name = "projectName")
    public String projectName;

    @ColumnInfo(name = "projectColor")
    @ColorInt
    public int projectColor;

    public ProjectEntity(long id, String projectName, @ColorInt int projectColor) {
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

    public int getProjectColor() {
        return projectColor;
    }

    @NonNull
    public static ProjectEntity[] getProjects() {
        return new ProjectEntity[]{
                new ProjectEntity(1L, "Projet Tartampion", 0xFFEADAD1),
                new ProjectEntity(2L, "Projet Lucidia", 0xFFB4CDBA),
                new ProjectEntity(3L, "Projet Circus", 0xFFA3CED2),
        };
    }

}

