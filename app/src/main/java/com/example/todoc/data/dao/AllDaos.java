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
    @Insert
    void insertProject(ProjectEntity projectEntity);

    @Insert
    void insertTask(TaskEntity taskEntity);

    @Query("DELETE FROM tasks WHERE taskId = :taskId")
    void deleteTask(long taskId);


    @Query("SELECT * FROM tasks")
    List<TaskEntity> getAllTasks();

    @Query("SELECT projectId FROM projects WHERE projectName = :projectName")
    long getProjectId(String projectName);

    @Query("SELECT taskId FROM tasks WHERE taskName = :taskName")
    long getTaskId(String taskName);

    @Transaction
    @Query("SELECT * FROM projects INNER JOIN tasks ON projects.projectId = tasks.projectId WHERE projects.projectId = :projectId")
    LiveData<List<ProjectWithTaskEntity>> getAllTasksbyProjectId(long projectId);

    @Query("SELECT * FROM tasks ORDER BY timeStamp DESC")
    List<TaskEntity> getTasksByTimeDesc();

    @Query("SELECT * FROM tasks ORDER BY timeStamp ASC")
    List<TaskEntity> getTasksByTimeAsc();

    @Query("SELECT * FROM tasks ORDER BY taskName COLLATE NOCASE ASC")
    List<TaskEntity> getAllTasksSortedByAtoZ();

    @Query("SELECT * FROM tasks ORDER BY taskName COLLATE NOCASE DESC")
    List<TaskEntity> getAllTasksSortedByZtoA();
}
