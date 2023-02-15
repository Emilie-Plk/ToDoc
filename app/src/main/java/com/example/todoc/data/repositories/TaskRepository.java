package com.example.todoc.data.repositories;

import androidx.lifecycle.LiveData;

import com.example.todoc.data.AppDatabase;
import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entities.ProjectWithTasks;
import com.example.todoc.data.entities.TaskEntity;
import com.example.todoc.ui.TaskViewStateItem;

import org.jetbrains.annotations.TestOnly;

import java.util.List;

public class TaskRepository {

    private final TaskDao dao;


    public TaskRepository(TaskDao dao) {
        this.dao = dao;
    }


    public void addNewTask(TaskEntity task) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                dao.insertTask(task));
    }

    public void deleteTask(long taskId) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                dao.deleteTask(taskId));
    }

    public LiveData<List<ProjectWithTasks>> getProjectWithTasks() {
        return dao.getProjectWithTasks();
    }

    @TestOnly
    public LiveData<List<TaskEntity>> getTasksForTest() {
        return dao.getTasksForTest();
    }

    public LiveData<List<TaskViewStateItem>> getTaskViewState() {
        return dao.getTaskViewStateItems();
    }
}
