package com.example.todoc.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.todoc.data.TaskRepository;
import com.example.todoc.data.dao.ProjectDao;
import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.ProjectWithTaskEntity;
import com.example.todoc.data.entities.TaskEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    @NonNull
    private final TaskRepository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.repository = new TaskRepository(application);
    }

    public LiveData<List<TaskViewStateItem>> getTaskViewStateItemLiveData() {
        return Transformations.map(repository.getAllProjectWithTask(), tasks -> {
            List<TaskViewStateItem> taskViewStateItems = new ArrayList<>();
            for (ProjectWithTaskEntity projectWithTask : tasks) {
                for (TaskEntity task : projectWithTask.taskEntities) {
                    taskViewStateItems.add(new TaskViewStateItem(
                            task.taskId,
                            task.taskName,
                            projectWithTask.projectEntity.projectName,
                            projectWithTask.projectEntity.projectColor
                    ));
                }
            }
            return taskViewStateItems;
        });
    }

    public void onAddingNewTask(String taskName, String projectName) {
        ProjectEntity project = repository.getProjectByName(projectName);
        if (project != null) {
            TaskEntity task = new TaskEntity(taskName, project.projectId, projectName, new Timestamp(System.currentTimeMillis()));
            repository.insertTask(task);
        }
    }
}
