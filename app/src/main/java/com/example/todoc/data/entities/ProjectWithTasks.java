package com.example.todoc.data.entities;

import java.util.List;

public class ProjectWithTasks {
    public ProjectEntity project;
    public List<TaskEntity> tasks;

    public ProjectWithTasks(ProjectEntity project, List<TaskEntity> tasks) {
        this.project = project;
        this.tasks = tasks;
    }

}
