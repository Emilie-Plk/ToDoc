package com.example.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.ProjectWithTaskEntity;
import com.example.todoc.data.entities.TaskEntity;

import java.util.List;

@Dao
public interface TaskDao {





    @Query("SELECT * FROM tasks WHERE projectId = :projectId")
    @Transaction
    LiveData<List<TaskEntity>> getTasksByProjectId(long projectId);

}
