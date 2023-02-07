package com.example.todoc.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.ProjectWithTasks;
import com.example.todoc.data.entities.TaskEntity;
import com.example.todoc.data.repositories.ProjectRepository;
import com.example.todoc.data.repositories.TaskRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    @NonNull
    private final TaskRepository taskRepository;
    @NonNull
    private final ProjectRepository projectRepository;


    private final MutableLiveData<SortMethod> sortingMethodMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<TaskViewStateItem>> taskViewStateItemsMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<TaskEntity>> taskEntities = new MutableLiveData<>();


    private final MutableLiveData<Boolean> isNoTaskTextViewVisible = new MutableLiveData<>();


    public MainActivityViewModel(@NonNull TaskRepository taskRepository, @NonNull ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        taskEntities.setValue(taskRepository.getAllTasksSync());
    }


    public LiveData<List<TaskViewStateItem>> getMeetingViewStateItemsMediatorLiveData() {
        return taskViewStateItemsMutableLiveData;
    }

    public MutableLiveData<Boolean> getisNoTaskTextViewVisible() {
        return isNoTaskTextViewVisible;
    }

    public void setSortMethodMutableLiveData(SortMethod sortMethod) {
        sortingMethodMutableLiveData.setValue(sortMethod);
    }

    public MutableLiveData<SortMethod> getSortingMethodMutableLiveData() {
        return sortingMethodMutableLiveData;
    }


    public LiveData<List<TaskViewStateItem>> getTaskViewStateItemLiveData() {
        return Transformations.map(projectRepository.getProjectWithTasks(), projectsWithTasks -> {
            List<TaskViewStateItem> taskViewStateItems = new ArrayList<>();
            for (ProjectWithTasks projectWithTasks : projectsWithTasks) {
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
                isNoTaskTextViewVisible.setValue(true);
            } else {
                isNoTaskTextViewVisible.postValue(false);
            }
            taskViewStateItemsMutableLiveData.setValue(taskViewStateItems);

            return taskViewStateItemsMutableLiveData.getValue();
        });
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
