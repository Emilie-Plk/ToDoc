package com.example.todoc.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicReference;

public class TestUtil {

    @NonNull
    public static <T> T getValueForTesting(@NonNull final LiveData<T> liveData) {
        final Object lock = new Object();
        final AtomicReference<T> captured = new AtomicReference<>();

        final Observer<T> observer = t -> {
            captured.set(t);
            synchronized (lock) {
                lock.notifyAll();
            }
        };

        synchronized (lock) {
            liveData.observeForever(observer);
            try {
                lock.wait(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        T value = captured.get();
        liveData.removeObserver(observer);

        if (value == null) {
            throw new AssertionError("LiveData value is null !");
        }
        return value;
    }
}