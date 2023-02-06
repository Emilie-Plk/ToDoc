package com.example.todoc.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoc.data.AppDatabase;
import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entities.TaskEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class TaskRepository {

    private final TaskDao dao;

    private final Executor executor;
    private final MutableLiveData<List<TaskEntity>> tasksMutableLiveData;

    private final List<TaskEntity> tasks = new ArrayList<>();

    public TaskRepository(TaskDao dao, Executor executor) {
        this.dao = dao;
        this.executor = executor;
        tasksMutableLiveData = new MutableLiveData<>();
    }

    public LiveData<List<TaskEntity>> getAllTasks() {
        tasksMutableLiveData.postValue(dao.getTasks().getValue());
        return tasksMutableLiveData;
    }

    public List<TaskEntity> getAllTasksSync() {
        AppDatabase.databaseWriteExecutor.execute(() -> tasks.addAll(dao.getTasksSync()));
        return tasks;
    }

    public void addNewTask(TaskEntity task) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            dao.insertTask(task);
        });
    }

    public void deleteTask(long taskId) {
        AppDatabase.databaseWriteExecutor.execute(() -> dao.deleteTask(taskId));
    }

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
