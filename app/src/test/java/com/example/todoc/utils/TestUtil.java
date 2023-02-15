package com.example.todoc.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicReference;

public class TestUtil {

        @NonNull
        public static <T> T getValueForTesting(@NonNull final LiveData<T> liveData) {
            liveData.observeForever(t -> {
            });

            T captured = liveData.getValue();

            if (captured == null) {
                throw new AssertionError("LiveData value is null !");
            }
            return captured;
        }
    }