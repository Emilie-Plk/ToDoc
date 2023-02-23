package com.example.todoc.ui.taskList;

import static com.example.todoc.ui.taskList.SortMethod.ALPHABETICAL;
import static com.example.todoc.ui.taskList.SortMethod.ALPHABETICAL_INVERTED;
import static com.example.todoc.ui.taskList.SortMethod.NONE;
import static com.example.todoc.ui.taskList.SortMethod.OLD_FIRST;
import static com.example.todoc.ui.taskList.SortMethod.RECENT_FIRST;
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

    /**
     * Used to sort and combine two parameters together
     * to produce a new list of TaskViewStateItem
     * depending on the sort method
     *
     * @param tasks      List of ProjectWithTasks
     * @param sortMethod SortMethod
     */
    private void combine(@Nullable List<ProjectWithTasks> tasks, @Nullable SortMethod sortMethod) {
        if (tasks == null) {
            return;
        }

        List<TaskViewStateItem> sortedTasks = new ArrayList<>();

        for (ProjectWithTasks projectWithTasks : tasks) {
            ProjectEntity project = projectWithTasks.getProject();
            for (TaskEntity taskEntity : projectWithTasks.getTasks()) {
                sortedTasks.add(
                        // pre-format data that should be displayed to the View with TaskViewStateItem
                        new TaskViewStateItem(
                                taskEntity.getId(),
                                taskEntity.getTaskDescription(),
                                project.getProjectName(),
                                project.getProjectColor(),
                                taskEntity.getTaskTimeStamp()));
            }
        }

        if (sortMethod != null) {
            switch (sortMethod) {
                case ALPHABETICAL:
                    sortTasksAlphabetically(sortedTasks);
                    break;
                case ALPHABETICAL_INVERTED:
                    sortTasksAlphabeticallyReversed(sortedTasks);
                    break;
                case OLD_FIRST:
                case NONE:
                    sortTasksChronologically(sortedTasks);
                    break;
                case RECENT_FIRST:
                    sortTasksChronologicallyReversed(sortedTasks);
                    break;
            }
        }

        taskViewStateItemsMediatorLiveData.setValue(sortedTasks);
        updateNoTaskTextViewVisibility();
    }

    public MutableLiveData<Boolean> getIsNoTaskTextViewVisible() {
        return isNoTaskTextViewVisible;
    }

    public LiveData<List<TaskViewStateItem>> getTaskViewStateItemsMediatorLiveData() {
        return taskViewStateItemsMediatorLiveData;
    }

    public void onDeleteTask(long taskId) {
        taskRepository.deleteTask(taskId);
    }

    /**
     * Set value for isNoTaskTextViewVisible depending on
     * taskViewStateItemsMediatorLiveData's value
     */
    private void updateNoTaskTextViewVisibility() {
        isNoTaskTextViewVisible.setValue(
                taskViewStateItemsMediatorLiveData.getValue() == null ||
                        taskViewStateItemsMediatorLiveData.getValue().isEmpty()
        );
    }

    /**
     * Set sortMethodMutableLiveData with sortMethod given in parameter
     */
    public void onSortingTasksSelected(SortMethod sortMethod) {
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

    private void sortTasksChronologicallyReversed(@NonNull List<TaskViewStateItem> taskViewStateItems) {
        taskViewStateItems.sort(comparingLong((TaskViewStateItem o) -> o.getTaskTimeStamp().getTime()).reversed());
        taskViewStateItemsMediatorLiveData.setValue(taskViewStateItems);
    }

    private void sortTasksChronologically(@NonNull List<TaskViewStateItem> taskViewStateItems) {
        taskViewStateItems.sort(comparingLong((TaskViewStateItem o) -> o.getTaskTimeStamp().getTime()));
        taskViewStateItemsMediatorLiveData.setValue(taskViewStateItems);
    }

    private void sortTasksAlphabeticallyReversed(@NonNull List<TaskViewStateItem> taskViewStateItems) {
        taskViewStateItems.sort(comparing(o -> o.getTaskDescription().toLowerCase(), reverseOrder()));
        taskViewStateItemsMediatorLiveData.setValue(taskViewStateItems);
    }

    private void sortTasksAlphabetically(@NonNull List<TaskViewStateItem> taskViewStateItems) {
        taskViewStateItems.sort(comparing(o -> o.getTaskDescription().toLowerCase()));
        taskViewStateItemsMediatorLiveData.setValue(taskViewStateItems);
    }
}

