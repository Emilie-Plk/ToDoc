package com.example.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.ProjectWithTaskEntity;
import com.example.todoc.data.entities.TaskEntity;

import java.util.List;

@Dao
public interface AllDaos {



    @Query("DELETE FROM tasks WHERE id = :taskId")
    void deleteTask(long taskId);


    @Query("SELECT * FROM tasks")
    List<TaskEntity> getAllTasks();

    @Query("SELECT * FROM projects")
    List<ProjectEntity> getAllProjects();




    @Query("SELECT * FROM projects WHERE projectName = :name")
    ProjectEntity getProjectByName(String name);

    @Query("SELECT id FROM tasks WHERE taskName = :taskName")
    long getTaskId(String taskName);

  /*  @Transaction
    @Query("SELECT * FROM projects INNER JOIN tasks ON projects.projectId = tasks.projectId WHERE projects.projectId = :projectId")
    LiveData<List<ProjectWithTaskEntity>> getAllTasksbyProjectId(long projectId);

    @Transaction
    @Query("SELECT * FROM projects INNER JOIN tasks ON projects.projectId = tasks.projectId")
    LiveData<List<ProjectWithTaskEntity>> getAllTasksbyProject();*/


}
