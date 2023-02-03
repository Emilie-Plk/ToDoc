package com.example.todoc.data.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.todoc.data.entities.ProjectWithTasks;

import java.util.List;

@Dao
public interface ProjectWithTasksDao {
    @Transaction
    @Query("SELECT * FROM projects")
    List<ProjectWithTasks> getProjectWithTasks();
}
