package com.example.todoc.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoc.data.AppDatabase;
import com.example.todoc.data.repositories.ProjectRepository;
import com.example.todoc.data.repositories.TaskRepository;
import com.example.todoc.ui.addTask.AddNewTaskDialogFragmentViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ViewModelFactory implements ViewModelProvider.Factory {
    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    private static volatile ViewModelFactory factory;

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
        Executor executor = Executors.newFixedThreadPool(4);
        AppDatabase db = AppDatabase.getDatabase(context);
        this.taskRepository = new TaskRepository(db.getTaskDao());
        this.projectRepository = new ProjectRepository(db.getProjectDao());
    }


    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(taskRepository, projectRepository);
        } else if (modelClass.isAssignableFrom(AddNewTaskDialogFragmentViewModel.class)) {
            return (T) new AddNewTaskDialogFragmentViewModel(taskRepository, projectRepository);
        } else
            throw new IllegalArgumentException("Unknown model class!");
    }

}
