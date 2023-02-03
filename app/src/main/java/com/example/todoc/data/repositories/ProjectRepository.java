package com.example.todoc.data.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoc.data.dao.ProjectDao;
import com.example.todoc.data.entities.ProjectEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class ProjectRepository {

    private final ProjectDao dao;

    private final Executor executor = Executors.newSingleThreadExecutor();

    public ProjectRepository(ProjectDao dao) {
        this.dao = dao;
        /*       this.executor = Executors.newSingleThreadExecutor();*/
    }

    public LiveData<List<ProjectEntity>> getAllProjects() {
        return dao.getProjects();
    }

    public ProjectEntity getProjectById(long id) {
        FutureTask<ProjectEntity> task = new FutureTask<>(() -> dao.getProjectId(id));
        executor.execute(task);
        try {
            return task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("ERROR", "Error fetching project by id", e);
        }
        return null;
    }

    public LiveData<List<String>> getAllProjectsNames() {
       return dao.getAllProjectNames();
    }
}
