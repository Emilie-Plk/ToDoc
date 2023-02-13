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
import com.example.todoc.data.entities.TaskEntity;
import com.example.todoc.ui.TaskViewStateItem;

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
    public void insert_one_project() {
        // WHEN
        taskDao.insertTask(TASK);

        // THEN
        List<TaskEntity> result = TestUtil.getValueForTesting(taskDao.getTasksForTest());
        assertEquals(1, result.size());
    }

    @Test
    public void delete_one_project() {
        // WHEN
        taskDao.insertTask(TASK);

        // THEN
        List<TaskEntity> resultBeforeDeletion = TestUtil.getValueForTesting(taskDao.getTasksForTest());
        assertEquals(1, resultBeforeDeletion.size());

        taskDao.deleteTask(1);

        List<TaskEntity> resultAfterDeletion = TestUtil.getValueForTesting(taskDao.getTasksForTest());

        assertEquals(0, resultAfterDeletion.size());
    }

    @Test
    public void insert_one_task_should_return_one_TaskViewStateItem() {
        // WHEN
        taskDao.insertTask(TASK);

        // THEN
        List<TaskViewStateItem> result = TestUtil.getValueForTesting(taskDao.getTaskViewStateItems());
        assertEquals(1, result.get(0).getTaskId());
        assertEquals(TASK.getTaskDescription(), result.get(0).getTaskDescription());
        assertEquals(TASK.getTaskTimeStamp(), result.get(0).getTaskTimeStamp());
        assertEquals(PROJECT_1.getProjectName(), result.get(0).getProjectName());
        assertEquals(PROJECT_1.getProjectColor(), result.get(0).getProjectColor());

    }
}
