package com.example.todoc.repositories;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.todoc.data.AppDatabase;
import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entities.ProjectWithTasks;
import com.example.todoc.data.entities.TaskEntity;
import com.example.todoc.data.repositories.TaskRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

public class TaskRepositoryTest {

    @Mock
    private final TaskDao taskDao = Mockito.mock(TaskDao.class);

    private TaskRepository repository;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        repository = new TaskRepository(taskDao);
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
        Mockito.verify(taskDao).getProjectWithTasks();
        verifyNoMoreInteractions(taskDao);
    }

    @Test
    public void verify_addTask() { // TODO: works fine isolate but not grouped!
        // GIVEN
        TaskEntity task = Mockito.mock(TaskEntity.class);

        // WHEN
        repository.addNewTask(task);

        // THEN
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
}
