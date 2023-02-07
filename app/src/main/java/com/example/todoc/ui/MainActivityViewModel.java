package com.example.todoc.ui;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
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
    private final MediatorLiveData<List<TaskViewStateItem>> taskViewStateItemsMutableLiveData = new MediatorLiveData<>();

    private final MutableLiveData<Boolean> isNoTaskTextViewVisible = new MutableLiveData<>();


    public MainActivityViewModel(@NonNull TaskRepository taskRepository, @NonNull ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;

        taskViewStateItemsMutableLiveData.addSource(projectRepository.getProjectWithTasks(), projectsWithTasks -> {
            List<TaskViewStateItem> taskViewStateItems = new ArrayList<>();
            for (ProjectWithTasks projectWithTasks : projectsWithTasks) {
                ProjectEntity project = projectWithTasks.project;
                for (TaskEntity task : projectWithTasks.tasks) {
                    taskViewStateItems.add(new TaskViewStateItem(
                            task.getId(),
                            task.getTaskName(),
                            project.getProjectName(),
                            project.getProjectColor(),
                            task.getTimeStamp()));
                }
            }
            taskViewStateItemsMutableLiveData.setValue(taskViewStateItems);

            if (taskViewStateItemsMutableLiveData.getValue().isEmpty()) {
                isNoTaskTextViewVisible.setValue(true);
            } else {
                isNoTaskTextViewVisible.setValue(false);
            }
        });
    }


    public MutableLiveData<Boolean> getIsNoTaskTextViewVisible() {
        return isNoTaskTextViewVisible;
    }

    public LiveData<List<TaskViewStateItem>> getMeetingViewStateItemsMediatorLiveData() {
        return taskViewStateItemsMutableLiveData;
    }

    public void onDeleteTask(long taskId) {
        taskRepository.deleteTask(taskId);
    }

    public void sortTasks(SortMethod sortMethod) {
        List<TaskViewStateItem> taskViewStateItems = taskViewStateItemsMutableLiveData.getValue();
        assert taskViewStateItems != null;
        switch (sortMethod) {
            case ALPHABETICAL:
                sortTasksAlphabetically(taskViewStateItems);
                break;
            case ALPHABETICAL_INVERTED:
                sortTasksAlphabeticallyInverted(taskViewStateItems);
                break;
            case OLD_FIRST:
                sortTasksChronologically(taskViewStateItems);
                break;
            case RECENT_FIRST:
                sortTasksChronologicallyReverted(taskViewStateItems);
                break;
            case NONE:
                break;
        }
    }

    private void sortTasksChronologicallyReverted(List<TaskViewStateItem> taskViewStateItems) {
        taskViewStateItemsMutableLiveData.setValue(taskViewStateItemsMutableLiveData.getValue()
                .stream()
                .sorted(comparingLong((TaskViewStateItem o) -> o.getTimestamp().getTime()).reversed())
                .collect(toList()));
    }

    private void sortTasksChronologically(List<TaskViewStateItem> taskViewStateItems) {
        taskViewStateItemsMutableLiveData.setValue(taskViewStateItemsMutableLiveData.getValue()
                .stream()
                .sorted(comparingLong((TaskViewStateItem o) -> o.getTimestamp().getTime()))
                .collect(toList()));
    }

    private void sortTasksAlphabeticallyInverted(List<TaskViewStateItem> taskViewStateItems) {
        taskViewStateItemsMutableLiveData.setValue(
                taskViewStateItemsMutableLiveData.getValue()
                        .stream()
                        .sorted(comparing(o -> o.getTaskName().toLowerCase(), reverseOrder()))
                        .collect(toList()));
    }

    private void sortTasksAlphabetically(List<TaskViewStateItem> taskViewStateItems) {
        taskViewStateItemsMutableLiveData.setValue(
                taskViewStateItemsMutableLiveData.getValue()
                        .stream()
                        .sorted(comparing(o -> o.getTaskName().toLowerCase()))
                        .collect(toList()));
    }
}

