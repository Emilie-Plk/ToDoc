package com.example.todoc;

import static org.junit.Assert.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoc.data.dao.ProjectDao;
import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.TaskEntity;
import com.example.todoc.data.repositories.ProjectRepository;
import com.example.todoc.data.repositories.TaskRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.List;

import kotlinx.coroutines.scheduling.Task;

public class TaskRepositoryTest {

    private final TaskDao taskDao = Mockito.mock(TaskDao.class);

    private TaskRepository repository;
    private static final TaskEntity TASK = new TaskEntity(0, 1L, "Test Task", new Timestamp(System.currentTimeMillis()));


    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();


    @Before
    public void setUp() {
        repository = new TaskRepository(taskDao);
    }


    @Test
    public void verify_getAllTasks() {
        LiveData<List<TaskEntity>> taskEntitiesLiveData = Mockito.spy(new MutableLiveData<>());
        Mockito.doReturn(taskEntitiesLiveData).when(taskDao).getTasksForTest();

        LiveData<List<TaskEntity>> result = repository.getTasksForTest();

        assertEquals(taskEntitiesLiveData, result);
        Mockito.verify(taskDao).getTasksForTest();
        Mockito.verifyNoMoreInteractions(taskDao);
    }

    @Test
    public void addTask(){
        TaskEntity task = Mockito.mock(TaskEntity.class);
        repository.addNewTask(task);

        Mockito.verify(taskDao).insertTask(task);
    }

    @Test
    public void deleteTask() {
        TaskEntity task = Mockito.mock(TaskEntity.class);
        repository.addNewTask(task);
        Mockito.verify(repository).addNewTask(task);

        repository.deleteTask(task.getId());
        Mockito.verify(repository).deleteTask(task.getId());
        Mockito.verifyNoMoreInteractions(repository);
    }


}
