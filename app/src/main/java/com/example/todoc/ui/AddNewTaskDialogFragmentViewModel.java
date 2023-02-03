package com.example.todoc.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.example.todoc.data.repositories.ProjectRepository;

import java.util.List;

public class AddNewTaskDialogFragmentViewModel extends ViewModel {

    @NonNull
    private final ProjectRepository projectRepository;

    public AddNewTaskDialogFragmentViewModel(@NonNull ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public LiveData<List<String>> getProjectsNames() {
       return projectRepository.getAllProjectsNames();
    }
}
