package com.example.todoc.ui.addTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.TaskEntity;
import com.example.todoc.data.repositories.ProjectRepository;
import com.example.todoc.data.repositories.TaskRepository;
import com.example.todoc.ui.utils.SingleLiveEvent;

import java.sql.Timestamp;
import java.util.List;

public class AddNewTaskDialogFragmentViewModel extends ViewModel {

    @NonNull
    private final ProjectRepository projectRepository;

    @NonNull
    private final TaskRepository taskRepository;
    private final SingleLiveEvent<Void> closeDialogFragment = new SingleLiveEvent<>();

    private final MediatorLiveData<Long> projectIdMutableLiveData = new MediatorLiveData<>();
    private final MutableLiveData<Boolean> isEveryFieldComplete = new MutableLiveData<>(false);
    private boolean isProjectChosen;

    private boolean isTaskDescriptionCompleted;

    private long projectId;

    public AddNewTaskDialogFragmentViewModel(@NonNull TaskRepository taskRepository, @NonNull ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public LiveData<List<String>> getProjectsNames() {
        return projectRepository.getAllProjectsNames();
    }

    public SingleLiveEvent<Void> getCloseFragment() {
        return closeDialogFragment;
    }

    public MutableLiveData<Boolean> getIsEveryFieldComplete() {
        return isEveryFieldComplete;
    }

    public void onAddingNewTask(String taskDescription, String projectName) {
        for (ProjectEntity project : ProjectEntity.getProjects()) {
            if (project.getProjectName().equals(projectName)) {
                projectId = project.getId();
            }
        }
        TaskEntity task = new TaskEntity(projectId, taskDescription, new Timestamp(System.currentTimeMillis()));
        closeDialogFragment.call();
        taskRepository.addNewTask(task);
    }

    public void updateForTaskDescriptionCompletion(String taskDescription) {
        isTaskDescriptionCompleted = !taskDescription.isEmpty();
        updateForFieldsCompletion();
    }

    public void updateForChosenProjectSelection(String chosenProject) {
        isProjectChosen = !chosenProject.isEmpty();
        updateForFieldsCompletion();
    }

    public void updateForFieldsCompletion() {
        isEveryFieldComplete.setValue(isTaskDescriptionCompleted && isProjectChosen);
    }
}
