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

  /*  @Query("SELECT * FROM projects")
    LiveData<List<ProjectWithTasks>> getProjectWithTasks();*/

    @Query("SELECT projectName FROM projects")
    LiveData<List<String>> getAllProjectNames();

}
