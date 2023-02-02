package com.example.todoc.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.todoc.data.dao.ProjectDao;
import com.example.todoc.data.dao.ProjectWithTaskDao;
import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.ProjectWithTaskEntity;
import com.example.todoc.data.entities.TaskEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRepository {
    private final TaskDao taskDao;

    private final ProjectWithTaskDao projectWithTaskDao;

    private final ProjectDao projectDao;

    private final Executor executor;
    private final MediatorLiveData<List<TaskEntity>> allTasksMediator = new MediatorLiveData<>();

    public TaskRepository(Application application) {
        TaskManagementDatabase database = TaskManagementDatabase.getDatabase(application);
        this.taskDao = database.taskDao();
        this.projectWithTaskDao = database.projectWithTaskDao();
        this.projectDao = database.projectDao();
        this.executor = Executors.newSingleThreadExecutor();
        /*allTasksMediator.postValue(taskDao.getAllTasks());*/
    }

    public LiveData<List<TaskEntity>> getAllTasks() {
        return allTasksMediator;
    }

    public LiveData<List<ProjectWithTaskEntity>> getAllProjectWithTask() {
        return projectWithTaskDao.getProjectWithTasks();
    }

    public LiveData<List<TaskEntity>> getTasksByProjectId(long projectId) {
        return taskDao.getTasksByProjectId(projectId);
    }

    public LiveData<ProjectEntity> getProjectByName(String projectName) {
        return projectDao.getProjectByName(projectName);
    }

    public void insertTask(TaskEntity task) {
        TaskManagementDatabase.databaseWriteExecutor.execute(() -> taskDao.createTask(task));
    }

    public void deleteTask(long taskId) {
        TaskManagementDatabase.databaseWriteExecutor.execute(() -> taskDao.deleteTask(taskId));
    }

    // TODO: insert my projects like :
 /*   ProjectEntity projectA = new ProjectEntity(1, "Project A", Color.RED);
    ProjectEntity projectB = new ProjectEntity(2, "Project B", Color.BLUE);
    ProjectEntity projectC = new ProjectEntity(3, "Project C", Color.GREEN);

projectDao.insertProject(projectA);
projectDao.insertProject(projectB);
projectDao.insertProject(projectC);*/
}
