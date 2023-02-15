package com.example.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.todoc.data.entities.ProjectWithTasks;
import com.example.todoc.data.entities.TaskEntity;
import com.example.todoc.ui.TaskViewStateItem;

import org.jetbrains.annotations.TestOnly;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insertTask(TaskEntity taskEntity);

    @Query("DELETE FROM tasks WHERE id = :taskId")
    void deleteTask(long taskId);

    @TestOnly
    @Query("SELECT * FROM tasks")
    LiveData<List<TaskEntity>> getTasksForTest();

    @Query("SELECT * FROM projects")
    LiveData<List<ProjectWithTasks>> getProjectWithTasks();

    @Query("SELECT tasks.id AS taskId, tasks.taskDescription, projects.projectName, projects.projectColor, tasks.taskTimeStamp "
            + "FROM tasks "
            + "INNER JOIN projects "
            + "ON tasks.projectId = projects.id ")
    LiveData<List<TaskViewStateItem>> getTaskViewStateItems();
}
