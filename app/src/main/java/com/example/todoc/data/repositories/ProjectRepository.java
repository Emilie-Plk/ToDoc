package com.example.todoc.data.repositories;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.example.todoc.data.dao.ProjectDao;
import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.ProjectWithTasks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class ProjectRepository {

    private final ProjectDao dao;


    private final Executor executor;

    public ProjectRepository(ProjectDao dao, Executor executor) {
        this.dao = dao;
        this.executor = executor;
    }

    @MainThread
    public LiveData<List<ProjectEntity>> getAllProjects() {
        return dao.getProjects();
    }

    @WorkerThread
    public List<ProjectEntity> getAllProjectsSync() {
   return dao.getProjectsSync();
    }

    @MainThread
    public LiveData<List<String>> getAllProjectsNames() {
        return dao.getAllProjectNames();
    }


    @MainThread
    public  LiveData<List<ProjectWithTasks>> getProjectWithTasks() {
        return dao.getProjectWithTasks();
    }
}
