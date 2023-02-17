package com.example.todoc.repositories;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entities.ProjectWithTasks;
import com.example.todoc.data.entities.TaskEntity;
import com.example.todoc.data.repositories.TaskRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.List;

public class TaskRepositoryTest {

    @Mock
    private final TaskDao taskDao = mock(TaskDao.class);

    private TaskRepository repository;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        repository = new TaskRepository(taskDao);
    }

    @Test
    public void verify_addTask() { // TODO: works fine separately but not grouped!
        // GIVEN
        TaskEntity task = mock(TaskEntity.class);

        // WHEN
        repository.addNewTask(task);
        // When I add some delay (ie. sleep(2000), it works, but it's ugly

        // THEN
        verify(taskDao).insertTask(task);
        verifyNoMoreInteractions(taskDao);
    }

    @Test
    public void verify_getProjectWithTasks() {
        // GIVEN
        LiveData<List<ProjectWithTasks>> projectWithTasksList = Mockito.spy(new MutableLiveData<>());
        Mockito.doReturn(projectWithTasksList).when(taskDao).getProjectWithTasks();

        // WHEN
        LiveData<List<ProjectWithTasks>> result = repository.getProjectWithTasks();

        // THEN
        assertEquals(projectWithTasksList, result);
        verify(taskDao).getProjectWithTasks();
        verifyNoMoreInteractions(taskDao);
    }

    @Test
    public void verify_deleteTask() {
        // GIVEN
        long taskID = 0L;

        // WHEN
        repository.deleteTask(taskID);

        // THEN
        verify(taskDao).deleteTask(taskID);
        verifyNoMoreInteractions(taskDao);
    }
}
