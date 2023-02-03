package com.example.todoc.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.todoc.data.dao.ProjectWithTasksDao;
import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.ProjectWithTasks;
import com.example.todoc.data.entities.TaskEntity;
import com.example.todoc.data.repositories.ProjectRepository;
import com.example.todoc.data.repositories.TaskRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    @NonNull
    private final TaskRepository taskRepository;
    @NonNull
    private final ProjectRepository projectRepository;

    private final MutableLiveData<List<TaskEntity>> taskEntities = new MutableLiveData<>();
    private final LiveData<List<ProjectEntity>> listProjectsLiveData;

    public MainActivityViewModel(@NonNull TaskRepository taskRepository, @NonNull ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        taskEntities.setValue(taskRepository.getAllTasksSync());
        listProjectsLiveData = projectRepository.getAllProjects();
    }

/*    public LiveData<List<TaskViewStateItem>> getTaskViewStateItemLiveData() {
        return Transformations.map(taskRepository.getAllTasks(), tasks -> {
            List<TaskViewStateItem> taskViewStateItems = new ArrayList<>();
            for (ProjectEntity project : ProjectEntity.getProjects()) {
                for (TaskEntity task : tasks) {
                    taskViewStateItems.add(new TaskViewStateItem(
                            task.getId(),
                            task.getTaskName(),
                            project.getProjectName(),
                            project.getProjectColor()
                    ));
                }
            }
            return taskViewStateItems;
        });
    }*/


    public void onAddingNewTask(String taskName, String projectName) {
        long projectId = 0L;
        for (ProjectEntity project : ProjectEntity.getProjects()) {
            if (project.getProjectName().equals(projectName)) {
                projectId = project.getId();

            }
        }
        ProjectEntity project = projectRepository.getProjectById(projectId);
        if (project != null) {
            TaskEntity task = new TaskEntity(taskName, new Timestamp(System.currentTimeMillis()));
            taskRepository.addNewTask(task);
        }
    }

    public void onDeleteTask(long taskId) {
        taskRepository.deleteTask(taskId);
    }

    public void sortTasksByTimeDesc() {
        taskEntities.setValue(taskRepository.getTasksByTimeDesc());
    }

    public void sortTasksByTimeAsc() {
        taskEntities.setValue(taskRepository.getTasksByTimeAsc());
    }

    public void sortTasksNamesByAtoZ() {
        taskEntities.setValue(taskRepository.getAllTasksSortedByAtoZ());
    }

    public void sortTasksNamesByZtoA() {
        taskEntities.setValue(taskRepository.getAllTasksSortedByZtoA());
    }

}
