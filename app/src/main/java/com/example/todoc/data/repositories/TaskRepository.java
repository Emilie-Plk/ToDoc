package com.example.todoc.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoc.data.AppDatabase;
import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entities.TaskEntity;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private final TaskDao dao;

    private final MutableLiveData<List<TaskEntity>> tasksMutableLiveData;

    private final List<TaskEntity> tasks = new ArrayList<>();


    public TaskRepository(TaskDao dao) {
        this.dao = dao;
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
        AppDatabase.databaseWriteExecutor.execute(() ->
            dao.insertTask(task));
    }

    public void deleteTask(long taskId) {
        AppDatabase.databaseWriteExecutor.execute(() -> dao.deleteTask(taskId));
    }

}
