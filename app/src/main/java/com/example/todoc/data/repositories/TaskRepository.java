package com.example.todoc.data.repositories;

import androidx.lifecycle.LiveData;

import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entities.ProjectWithTasks;
import com.example.todoc.data.entities.TaskEntity;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskRepository {

    private final TaskDao dao;
    private final Executor executor;


    public TaskRepository(TaskDao dao, Executor executor) {
        this.dao = dao;
        this.executor = executor;
    }


    public void addNewTask(TaskEntity task) {
        executor.execute(() ->
                dao.insertTask(task));
    }

    public void deleteTask(long taskId) {
        executor.execute(() ->
                dao.deleteTask(taskId));
    }

    public LiveData<List<ProjectWithTasks>> getProjectWithTasks() {
        return dao.getProjectWithTasks();
    }
}
