package com.example.todoc.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

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

    private final MutableLiveData<SortMethod> sortingMethodMutableLiveData = new MutableLiveData<>();

    private final MediatorLiveData<List<TaskViewStateItem>> meetingViewStateItemsMediatorLiveData = new MediatorLiveData<>();

    private long projectId;

    private final MutableLiveData<List<TaskEntity>> taskEntities = new MutableLiveData<>();

    public MutableLiveData<Integer> getNoTaskVisibility() {
        return noTaskVisibility;
    }

    private final MutableLiveData<Integer> noTaskVisibility = new MutableLiveData<>();


    public MainActivityViewModel(@NonNull TaskRepository taskRepository, @NonNull ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        taskEntities.setValue(taskRepository.getAllTasksSync());


       // meetingViewStateItemsMediatorLiveData.addSource(taskEntities, tasks -> combine(tasks, sortingMethodMutableLiveData));

    }

 /*   private void combine(List<TaskEntity> tasks, MutableLiveData<SortMethod> sortingMethodMutableLiveData) {
        if (tasks == null) {
            return;
        }

        List<TaskViewStateItem> filteredTasks = new ArrayList<>();
        for (ProjectWithTasks projectWithTasks : tasks) {
            ProjectEntity project = projectWithTasks.project;
            for (TaskEntity task : projectWithTasks.tasks) {
                taskViewStateItems.add(new TaskViewStateItem(
            }
        }
    }

    public MediatorLiveData<List<TaskViewStateItem>> getMeetingViewStateItemsMediatorLiveData() {
        return meetingViewStateItemsMediatorLiveData;
    }*/


    public LiveData<List<TaskViewStateItem>> getTaskViewStateItemLiveData() { // MEDIATOR LIVE DATA
        return Transformations.map(projectRepository.getProjectWithTasks(), tasks -> {
            List<TaskViewStateItem> taskViewStateItems = new ArrayList<>();
            for (ProjectWithTasks projectWithTasks : tasks) {
                ProjectEntity project = projectWithTasks.project;
                for (TaskEntity task : projectWithTasks.tasks) {
                    taskViewStateItems.add(new TaskViewStateItem(
                            task.getId(),
                            task.getTaskName(),
                            project.getProjectName(),
                            project.getProjectColor()
                    ));
                }
            }
            if (taskViewStateItems.isEmpty()) {
                noTaskVisibility.postValue(View.VISIBLE); // is it UI related?
            } else {
                noTaskVisibility.postValue(View.GONE);
            }
            return taskViewStateItems;
        });
    }


    public void onAddingNewTask(String taskName, String projectName) {
        for (ProjectEntity project : ProjectEntity.getProjects()) {
            if (project.getProjectName().equals(projectName)) {
                projectId = project.getId();
            }
        }
            TaskEntity task = new TaskEntity(projectId, taskName, new Timestamp(System.currentTimeMillis()));
            taskRepository.addNewTask(task);
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
