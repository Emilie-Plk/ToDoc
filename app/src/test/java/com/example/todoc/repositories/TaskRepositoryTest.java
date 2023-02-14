package com.example.todoc.repositories;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entities.TaskEntity;
import com.example.todoc.data.repositories.TaskRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

public class TaskRepositoryTest {

    private final TaskDao taskDao = Mockito.mock(TaskDao.class);

    private TaskRepository repository;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        repository = new TaskRepository(taskDao);
    }

    @Test
    public void verify_getAllTasks() {
        // GIVEN
        LiveData<List<TaskEntity>> taskEntitiesLiveData = Mockito.spy(new MutableLiveData<>());
        Mockito.doReturn(taskEntitiesLiveData).when(taskDao).getTasksForTest();

        // WHEN
        LiveData<List<TaskEntity>> result = repository.getTasksForTest();

        // THEN
        assertEquals(taskEntitiesLiveData, result);
        Mockito.verify(taskDao).getTasksForTest();
        verifyNoMoreInteractions(taskDao);
    }

    @Test
    public void verify_addTask() {
        // GIVEN
        TaskEntity task = Mockito.mock(TaskEntity.class);

        // WHEN
        repository.addNewTask(task);

        Mockito.verify(taskDao).insertTask(task);
        verifyNoMoreInteractions(taskDao);
    }

    @Test
    public void verify_deleteTask() {
        // GIVEN
        long taskID = 0L;

        // WHEN
        repository.deleteTask(taskID);

        // THEN
        Mockito.verify(taskDao).deleteTask(taskID);
        verifyNoMoreInteractions(taskDao);
    }

    @Test
    public void verify_getTaskViewStateItem() {
        // WHEN
        repository.getTaskViewState();

        // THEN
        Mockito.verify(taskDao).getTaskViewStateItems();
        verifyNoMoreInteractions(taskDao);
    }
}
