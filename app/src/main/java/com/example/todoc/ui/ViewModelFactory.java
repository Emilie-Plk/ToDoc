package com.example.todoc.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Application mApplication;

    public ViewModelFactory(Application mApplication) {
        this.mApplication = mApplication;
    }
    public static ViewModelFactory getInstance(Application application) {
        return new ViewModelFactory(application);
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(mApplication);
        } else
            throw new IllegalArgumentException("Unknown model class!");
    }

}
