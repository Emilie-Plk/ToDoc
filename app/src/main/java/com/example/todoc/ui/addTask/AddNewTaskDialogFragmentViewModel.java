package com.example.todoc.ui.addTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
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

    private final MutableLiveData<Boolean> isProjectFieldComplete = new MutableLiveData<>(false);

    private final MutableLiveData<Boolean> isTaskFieldComplete = new MutableLiveData<>(false);

    private final MutableLiveData<String> chosenProjectMutableLiveData = new MutableLiveData<>();

    private final MediatorLiveData<Boolean> isEveryFieldComplete = new MediatorLiveData<>();

    public AddNewTaskDialogFragmentViewModel(@NonNull TaskRepository taskRepository, @NonNull ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;

        isEveryFieldComplete.addSource(isProjectFieldComplete, isCompleted -> combine());
        isEveryFieldComplete.addSource(isTaskFieldComplete, isCompleted -> combine());
    }

    /**
     * Used to set/update value of isEveryFieldComplete MediatorLiveData (Boolean)
     * with values of isEveryFieldComplete and isProjectFieldComplete (Boolean)
     */
    private void combine() {
        assert isTaskFieldComplete.getValue() != null &&
                isProjectFieldComplete.getValue() != null;
        isEveryFieldComplete.setValue(isTaskFieldComplete.getValue() && isProjectFieldComplete.getValue());
    }

    public LiveData<List<ProjectEntity>> getAllProjects() {
        return projectRepository.getAllProjects();
    }

    public SingleLiveEvent<Void> getCloseFragment() {
        return closeDialogFragment;
    }

    public MediatorLiveData<Boolean> getIsEveryFieldComplete() {
        return isEveryFieldComplete;
    }

    public MutableLiveData<String> getChosenProjectMutableLiveData() {
        return chosenProjectMutableLiveData;
    }

    /**
     * Creates a new TaskEntity to add it to the database ;
     * calls closeDialogFragment afterwards
     *
     * @param taskDescription String
     * @param projectId       long
     */
    public void onAddingNewTask(String taskDescription, long projectId) {
        TaskEntity task = new TaskEntity(0, projectId, taskDescription, new Timestamp(System.currentTimeMillis()));
        taskRepository.addNewTask(task);

        closeDialogFragment.call();
    }

    public void updateForTaskDescriptionCompletion(String taskDescription) {
        isTaskFieldComplete.setValue(!taskDescription.isEmpty());
    }

    public void updateForChosenProjectSelection(String chosenProject) {
        chosenProjectMutableLiveData.setValue(chosenProject);
        isProjectFieldComplete.setValue(!chosenProject.isEmpty());
    }

}
