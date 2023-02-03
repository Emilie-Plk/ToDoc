package com.example.todoc.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoc.data.dao.ProjectWithTasksDao;
import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.ProjectWithTasks;
import com.example.todoc.data.entities.TaskEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRepository {

    private final TaskDao dao;

    private final Executor executor = Executors.newSingleThreadExecutor();

    public TaskRepository(TaskDao dao) {
        this.dao = dao;
    }

    public LiveData<List<TaskEntity>> getAllTasks() {
        final MutableLiveData<List<TaskEntity>> tasks = new MutableLiveData<>();
        executor.execute(() -> tasks.postValue(dao.getTasks().getValue()));
        return tasks;
    }

    public List<TaskEntity> getAllTasksSync() {
        final List<TaskEntity> tasks = new ArrayList<>();
        executor.execute(() -> tasks.addAll(dao.getTasksSync()));
        return tasks;
    }

    public void addNewTask(TaskEntity task) {
        executor.execute(() -> dao.insertTask(task));
    }

    public void deleteTask(long taskId) {
        executor.execute(() -> dao.deleteTask(taskId));
    }

  /*  public LiveData<List<TaskEntity>> getTasksByProjectId(long projectId) {
        return dao.getAllTasksbyProjectId(projectId);
    }*/

    public List<TaskEntity> getTasksByTimeDesc() {
        return dao.getTasksByTimeDesc();
    }

    public List<TaskEntity> getTasksByTimeAsc() {
        return dao.getTasksByTimeAsc();
    }

    public List<TaskEntity> getAllTasksSortedByAtoZ() {
        return dao.getAllTasksSortedByAtoZ();
    }

    public List<TaskEntity> getAllTasksSortedByZtoA() {
        return dao.getAllTasksSortedByZtoA();
    }

}
