package com.example.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todoc.data.entities.ProjectEntity;

import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM projects WHERE projectName = :projectName")
    public ProjectEntity getProjectByName(String projectName);

    @Query("SELECT * FROM projects")
    LiveData<List<ProjectEntity>> getAllProjects();
}