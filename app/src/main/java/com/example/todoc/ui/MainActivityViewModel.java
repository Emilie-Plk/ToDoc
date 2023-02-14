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

import com.example.todoc.data.repositories.TaskRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    @NonNull
    private final TaskRepository taskRepository;
    private final MediatorLiveData<List<TaskViewStateItem>> taskViewStateItemsMediatorLiveData = new MediatorLiveData<>();
    private final MutableLiveData<Boolean> isNoTaskTextViewVisible = new MutableLiveData<>();


    public MainActivityViewModel(@NonNull TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

        taskViewStateItemsMediatorLiveData.addSource(taskRepository.getTaskViewState(), tasks -> {
            List<TaskViewStateItem> taskViewStateItems = new ArrayList<>();
            for (TaskViewStateItem task : tasks) {
                taskViewStateItems.add(new TaskViewStateItem(
                        task.getTaskId(),
                        task.getTaskDescription(),
                        task.getProjectName(),
                        task.getProjectColor(),
                        task.getTaskTimeStamp()));
            }
            taskViewStateItemsMediatorLiveData.setValue(taskViewStateItems);

            updateNoTaskTextViewVisibility();
        });
    }


    public MutableLiveData<Boolean> getIsNoTaskTextViewVisible() {
        return isNoTaskTextViewVisible;
    }

    public LiveData<List<TaskViewStateItem>> getMeetingViewStateItemsMediatorLiveData() {
        return taskViewStateItemsMediatorLiveData;
    }

    public void onDeleteTask(long taskId) {
        taskRepository.deleteTask(taskId);
    }

    private void updateNoTaskTextViewVisibility() {
        isNoTaskTextViewVisible.setValue(
                taskViewStateItemsMediatorLiveData.getValue() == null ||
                        taskViewStateItemsMediatorLiveData.getValue().isEmpty()
        );
    }

    public void sortTasks(SortMethod sortMethod) {
        List<TaskViewStateItem> taskViewStateItems = taskViewStateItemsMediatorLiveData.getValue();
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

    private void sortTasksChronologicallyReverted(@NonNull List<TaskViewStateItem> taskViewStateItems) {
        taskViewStateItemsMediatorLiveData.setValue(
                taskViewStateItems
                        .stream()
                        .sorted(comparingLong((TaskViewStateItem o) -> o.getTaskTimeStamp().getTime()).reversed())
                        .collect(toList()));
    }

    private void sortTasksChronologically(@NonNull List<TaskViewStateItem> taskViewStateItems) {
        taskViewStateItemsMediatorLiveData.setValue(
                taskViewStateItems
                        .stream()
                        .sorted(comparingLong((TaskViewStateItem o) -> o.getTaskTimeStamp().getTime()))
                        .collect(toList()));
    }

    private void sortTasksAlphabeticallyInverted(@NonNull List<TaskViewStateItem> taskViewStateItems) {
        taskViewStateItemsMediatorLiveData.setValue(
                taskViewStateItems
                        .stream()
                        .sorted(comparing(o -> o.getTaskDescription().toLowerCase(), reverseOrder()))
                        .collect(toList()));
    }

    private void sortTasksAlphabetically(@NonNull List<TaskViewStateItem> taskViewStateItems) {
        taskViewStateItemsMediatorLiveData.setValue(
                taskViewStateItems
                        .stream()
                        .sorted(comparing(o -> o.getTaskDescription().toLowerCase()))
                        .collect(toList()));
    }
}

