package com.example.todoc.ui;

import static com.example.todoc.ui.SortMethod.ALPHABETICAL;
import static com.example.todoc.ui.SortMethod.ALPHABETICAL_INVERTED;
import static com.example.todoc.ui.SortMethod.NONE;
import static com.example.todoc.ui.SortMethod.OLD_FIRST;
import static com.example.todoc.ui.SortMethod.RECENT_FIRST;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingLong;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.ProjectWithTasks;
import com.example.todoc.data.entities.TaskEntity;
import com.example.todoc.data.repositories.TaskRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    @NonNull
    private final TaskRepository taskRepository;
    private final MediatorLiveData<List<TaskViewStateItem>> taskViewStateItemsMediatorLiveData = new MediatorLiveData<>();

    private final MutableLiveData<SortMethod> sortMethodMutableLiveData = new MutableLiveData<>(NONE);
    private final MutableLiveData<Boolean> isNoTaskTextViewVisible = new MutableLiveData<>();


    public MainActivityViewModel(@NonNull TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

        LiveData<List<ProjectWithTasks>> projectWithTasks = taskRepository.getProjectWithTasks();

        taskViewStateItemsMediatorLiveData.addSource(projectWithTasks, tasks ->
                combine(tasks, sortMethodMutableLiveData.getValue()));


        taskViewStateItemsMediatorLiveData.addSource(sortMethodMutableLiveData, sortMethod ->
                combine(projectWithTasks.getValue(), sortMethod));


        updateNoTaskTextViewVisibility();
    }


    private void combine(@Nullable List<ProjectWithTasks> tasks, @Nullable SortMethod sortMethod) {
        if (tasks == null) {
            return;
        }

        List<TaskViewStateItem> sortedMeetings = new ArrayList<>();

        for (ProjectWithTasks projectWithTasks : tasks) {
            ProjectEntity project = projectWithTasks.getProject();
            for (TaskEntity taskEntity : projectWithTasks.getTasks()) {
                sortedMeetings.add(
                        new TaskViewStateItem(
                                taskEntity.getId(),
                                taskEntity.getTaskDescription(),
                                project.getProjectName(),
                                project.getProjectColor(),
                                taskEntity.getTaskTimeStamp()));
            }
        }


        assert sortMethod != null;
        switch (sortMethod) {
            case ALPHABETICAL:
                sortTasksAlphabetically(sortedMeetings);
                break;
            case ALPHABETICAL_INVERTED:
                sortTasksAlphabeticallyInverted(sortedMeetings);
                break;
            case OLD_FIRST:
            case NONE:
                sortTasksChronologically(sortedMeetings);
                break;
            case RECENT_FIRST:
                sortTasksChronologicallyReverted(sortedMeetings);
                break;
        }

        taskViewStateItemsMediatorLiveData.setValue(sortedMeetings);
        updateNoTaskTextViewVisibility();

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
        switch (sortMethod) {
            case ALPHABETICAL:
                sortMethodMutableLiveData.setValue(ALPHABETICAL);
                break;
            case ALPHABETICAL_INVERTED:
                sortMethodMutableLiveData.setValue(ALPHABETICAL_INVERTED);
                break;
            case RECENT_FIRST:
                sortMethodMutableLiveData.setValue(RECENT_FIRST);
                break;
            case OLD_FIRST:
                sortMethodMutableLiveData.setValue(OLD_FIRST);
                break;
            case NONE:
                sortMethodMutableLiveData.setValue(NONE);
        }
    }

    private void sortTasksChronologicallyReverted(@NonNull List<TaskViewStateItem> taskViewStateItems) {
        taskViewStateItems.sort(comparingLong((TaskViewStateItem o) -> o.getTaskTimeStamp().getTime()).reversed());
        taskViewStateItemsMediatorLiveData.setValue(taskViewStateItems);
    }

    private void sortTasksChronologically(@NonNull List<TaskViewStateItem> taskViewStateItems) {
        taskViewStateItems.sort(comparingLong((TaskViewStateItem o) -> o.getTaskTimeStamp().getTime()));
        taskViewStateItemsMediatorLiveData.setValue(taskViewStateItems);
    }

    private void sortTasksAlphabeticallyInverted(@NonNull List<TaskViewStateItem> taskViewStateItems) {
        taskViewStateItems.sort(comparing(o -> o.getTaskDescription().toLowerCase(), reverseOrder()));
        taskViewStateItemsMediatorLiveData.setValue(taskViewStateItems);
    } // Comparator pour refactor

    private void sortTasksAlphabetically(@NonNull List<TaskViewStateItem> taskViewStateItems) {
        taskViewStateItems.sort(comparing(o -> o.getTaskDescription().toLowerCase()));
        taskViewStateItemsMediatorLiveData.setValue(taskViewStateItems);
    }
}

