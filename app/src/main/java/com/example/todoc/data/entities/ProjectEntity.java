package com.example.todoc.data.entities;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "projects", indices = {@Index(value = "projectName", unique = true)})
public class ProjectEntity {
    @PrimaryKey(autoGenerate = true)
    private final long id;

    @ColumnInfo(name = "projectName")
    private final String projectName;

    @ColumnInfo(name = "projectColor")
    @ColorInt
    private final int projectColor;


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

    @ColorInt
    public int getProjectColor() {
        return projectColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectEntity project = (ProjectEntity) o;
        return id == project.id && projectColor == project.projectColor && Objects.equals(projectName, project.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectName, projectColor);
    }

    @NonNull
    @Override
    public String toString() {
        return "ProjectEntity{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", projectColor=" + projectColor +
                '}';
    }
}


