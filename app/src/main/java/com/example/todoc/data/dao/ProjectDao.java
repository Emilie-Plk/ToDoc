package com.example.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.todoc.data.entities.ProjectEntity;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    void insertProject(ProjectEntity projectEntity);

    @Query("SELECT * FROM projects")
    LiveData<List<ProjectEntity>> getProjects();

    @Query("SELECT projectId FROM projects WHERE projectId = :id")
    LiveData<ProjectEntity> getProjectId(long id);
}
