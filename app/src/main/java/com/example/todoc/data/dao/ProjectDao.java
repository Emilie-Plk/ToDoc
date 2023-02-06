package com.example.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.ProjectWithTasks;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    void insertProject(ProjectEntity projectEntity);

    @Query("SELECT * FROM projects")
    LiveData<List<ProjectEntity>> getProjects();

    @Query("SELECT * FROM projects")
   List<ProjectEntity> getProjectsSync();

    @Query("SELECT * FROM projects WHERE id = :id")
    ProjectEntity getProjectId(long id);

    @Query("SELECT projectName FROM projects")
    LiveData<List<String>> getAllProjectNames();

    @Query("SELECT * FROM projects")
    LiveData<List<ProjectWithTasks>> getProjectWithTasks();

    @Query("DELETE FROM projects")
    void deleteAll();
}
