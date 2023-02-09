package com.example.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.todoc.data.entities.TaskEntity;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insertTask(TaskEntity taskEntity);

    @Query("SELECT * FROM tasks")
    LiveData<List<TaskEntity>> getTasks();

    @Query("SELECT * FROM tasks")
    List<TaskEntity> getTasksSync();

    @Query("DELETE FROM tasks WHERE id = :taskId")
    void deleteTask(long taskId);

}
