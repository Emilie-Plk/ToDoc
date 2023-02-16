package com.example.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.todoc.data.entities.ProjectWithTasks;
import com.example.todoc.data.entities.TaskEntity;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insertTask(TaskEntity taskEntity);

    @Query("DELETE FROM tasks WHERE id = :taskId")
    void deleteTask(long taskId);

    @Transaction
    @Query("SELECT * FROM projects")
    LiveData<List<ProjectWithTasks>> getProjectWithTasks();
}
