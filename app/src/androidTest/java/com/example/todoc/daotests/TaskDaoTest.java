package com.example.todoc.daotests;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todoc.data.AppDatabase;
import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.ProjectWithTasks;
import com.example.todoc.data.entities.TaskEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Timestamp;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TaskDao taskDao;
    private AppDatabase database;

    private static final TaskEntity TASK = new TaskEntity(0, 1L, "Test Task", new Timestamp(System.currentTimeMillis()));
    private static final ProjectEntity PROJECT_1 = new ProjectEntity(0, "Project Test 1", 0xFFEADAD1);
    private static final ProjectEntity PROJECT_2 = new ProjectEntity(0, "Project Test 2", 0xFFEADAD1);
    private static final ProjectEntity PROJECT_3 = new ProjectEntity(0, "Project Test 3", 0xFFEADAD1);


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        taskDao = database.getTaskDao();

        database.getProjectDao().insertProject(PROJECT_1);
        database.getProjectDao().insertProject(PROJECT_2);
        database.getProjectDao().insertProject(PROJECT_3);
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void insert_one_task() {
        // WHEN
        taskDao.insertTask(TASK);

        // THEN
        List<ProjectWithTasks> result = TestUtil.getValueForTesting(taskDao.getProjectWithTasks());
        assertEquals(1, result.get(0).getTasks().size());
    }

    @Test
    public void delete_one_task() {
        // GIVEN
        taskDao.insertTask(TASK);
        List<ProjectWithTasks> resultBeforeDeletion = TestUtil.getValueForTesting(taskDao.getProjectWithTasks());
        assertEquals(1, resultBeforeDeletion.get(0).getTasks().size());

        // WHEN
        taskDao.deleteTask(1);

        // THEN
        List<ProjectWithTasks> resultAfterDeletion = TestUtil.getValueForTesting(taskDao.getProjectWithTasks());
        assertEquals(0, resultAfterDeletion.get(0).getTasks().size());
    }

    @Test
    public void insert_one_task_should_return_one_ProjectWithTasks_item() {
        // WHEN
        taskDao.insertTask(TASK);

        // THEN
        List<ProjectWithTasks> result = TestUtil.getValueForTesting(taskDao.getProjectWithTasks());
        assertEquals(1, result.get(0).getTasks().size());
        assertEquals(TASK.getTaskDescription(), result.get(0).getTasks().get(0).getTaskDescription());
        assertEquals(TASK.getTaskTimeStamp(), result.get(0).getTasks().get(0).getTaskTimeStamp());
        assertEquals(PROJECT_1.getProjectName(), result.get(0).getProject().getProjectName());
        assertEquals(PROJECT_1.getProjectColor(), result.get(0).getProject().getProjectColor());
    }
}
