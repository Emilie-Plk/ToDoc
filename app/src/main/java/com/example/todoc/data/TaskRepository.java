package com.example.todoc.data;

import android.app.Application;
import android.graphics.Color;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoc.data.dao.AllDaos;
import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.ProjectWithTaskEntity;
import com.example.todoc.data.entities.TaskEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRepository {
    private final AllDaos allDaos;
    private final Executor executor;
    private final MutableLiveData<List<TaskEntity>> tasksMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<ProjectEntity>> projectsMutableLiveData = new MutableLiveData<>();

    public TaskRepository(Application application) {
        TaskManagementDatabase database = TaskManagementDatabase.getDatabase(application);
        this.allDaos = database.allDaos();
        this.executor = Executors.newSingleThreadExecutor();
        tasksMutableLiveData.setValue(allDaos.getAllTasks());
        projectsMutableLiveData.setValue(allDaos.getAllProjects());
        insertProjects();
    }

    public LiveData<List<TaskEntity>> getAllTasks() {
       return tasksMutableLiveData;
    }

    public void insertNewTask(TaskEntity task) {
        executor.execute(() -> allDaos.insertTask(task));
    }

    public void deleteTask(long taskId) {
        executor.execute(() ->allDaos.deleteTask(taskId));
    }


    public LiveData<List<ProjectWithTaskEntity>> getTasksByProjectId(long projectId) {
        return allDaos.getAllTasksbyProjectId(projectId);
    }

    public LiveData<List<ProjectWithTaskEntity>> getTasksWithProject()  {
        return allDaos.getAllTasksbyProject();
    }

   public LiveData<List<ProjectEntity>> getAllProjects() {
        return projectsMutableLiveData;
   }

    public ProjectEntity getProjectByName(String name) {
        return allDaos.getProjectByName(name);
    }

    public void insertProjects() {
        allDaos.insertProject(projectA);
        allDaos.insertProject(projectB);
        allDaos.insertProject(projectC);
    }


    // TODO: insert my projects like :
   ProjectEntity projectA = new ProjectEntity(1, "Project Tartampion", Color.RED);
    ProjectEntity projectB = new ProjectEntity(2, "Project Lucidia", Color.BLUE);
    ProjectEntity projectC = new ProjectEntity(3, "Project Circus", Color.GREEN);


}
