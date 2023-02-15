package com.example.todoc.data.repositories;

import androidx.lifecycle.LiveData;

import com.example.todoc.data.dao.ProjectDao;
import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.ProjectWithTasks;
import com.example.todoc.data.entities.TaskEntity;

import java.util.List;

public class ProjectRepository {

    private final ProjectDao dao;

    public ProjectRepository(ProjectDao dao) {
        this.dao = dao;
    }

    public LiveData<List<ProjectEntity>> getAllProjects() {
        return dao.getProjects();
    }

}
