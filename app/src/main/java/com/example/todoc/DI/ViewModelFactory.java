package com.example.todoc.DI;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoc.MainApplication;
import com.example.todoc.data.AppDatabase;
import com.example.todoc.data.repositories.ProjectRepository;
import com.example.todoc.data.repositories.TaskRepository;
import com.example.todoc.ui.addTask.AddNewTaskDialogFragmentViewModel;
import com.example.todoc.ui.taskList.MainActivityViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ViewModelFactory implements ViewModelProvider.Factory {
    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    private static final int THREADS = Runtime.getRuntime().availableProcessors();

    private static final Executor EXECUTOR = Executors.newFixedThreadPool(THREADS);

    private static volatile ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory();
                }
            }
        }
        return factory;
    }

    public ViewModelFactory() {
        AppDatabase db = AppDatabase.getDatabase(MainApplication.getApplication(), EXECUTOR);
        this.taskRepository = new TaskRepository(db.getTaskDao(), EXECUTOR);
        this.projectRepository = new ProjectRepository(db.getProjectDao());
    }


    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(taskRepository);
        } else if (modelClass.isAssignableFrom(AddNewTaskDialogFragmentViewModel.class)) {
            return (T) new AddNewTaskDialogFragmentViewModel(taskRepository, projectRepository);
        } else
            throw new IllegalArgumentException("Unknown model class!");
    }

}
