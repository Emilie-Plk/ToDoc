package com.example.todoc.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoc.data.AppDatabase;
import com.example.todoc.data.repositories.ProjectRepository;
import com.example.todoc.data.repositories.TaskRepository;


public class ViewModelFactory implements ViewModelProvider.Factory {
    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance(Context context) {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(context);
                }
            }
        }
        return factory;
    }

    public ViewModelFactory(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        this.taskRepository = new TaskRepository(db.taskDao());
        this.projectRepository = new ProjectRepository(db.projectDao());
    }


    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(taskRepository, projectRepository);
        } else if (modelClass.isAssignableFrom(AddNewTaskDialogFragmentViewModel.class)) {
            return (T) new AddNewTaskDialogFragmentViewModel(projectRepository);
        } else
            throw new IllegalArgumentException("Unknown model class!");
    }

}
