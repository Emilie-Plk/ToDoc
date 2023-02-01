package com.example.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.todoc.data.entities.ProjectWithTaskEntity;

import java.util.List;

@Dao
public interface ProjectWithTaskDao {

    @Transaction
    @Query("SELECT * FROM projects")
    public LiveData<List<ProjectWithTaskEntity>> getProjectWithTasks();
}
