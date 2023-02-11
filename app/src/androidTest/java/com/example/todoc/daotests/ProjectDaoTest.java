package com.example.todoc.daotests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.services.storage.internal.TestStorageUtil;

import com.example.todoc.data.AppDatabase;
import com.example.todoc.data.dao.ProjectDao;
import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entities.ProjectEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    private ProjectDao projectDao;
    private AppDatabase database;

    private static final ProjectEntity PROJECT = new ProjectEntity(0, "Project Test 1", 0xFFEADAD1);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        projectDao = database.getProjectDao();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void insert_one_project() {
        // WHEN
        projectDao.insertProject(PROJECT);

        // THEN
        List<ProjectEntity> result = TestUtil.getValueForTesting(projectDao.getProjects());
        assertEquals(1, result.size());
    }

    @Test
    public void insert_three_project() {
        // GIVEN
        ProjectEntity project2 = new ProjectEntity(0, "Project Test 2", 0xFFEADAD1);
        ProjectEntity project3 = new ProjectEntity(0, "Project Test 3", 0xFFEADAD1);

        // WHEN
        projectDao.insertProject(PROJECT);
        projectDao.insertProject(project2);
        projectDao.insertProject(project3);

        // THEN
        List<ProjectEntity> result = TestUtil.getValueForTesting(projectDao.getProjects());
        assertEquals(3, result.size());
    }

    @Test
    @SuppressWarnings("UnnecessaryLocalVariable")
    public void insert_two_projects_with_same_name_should_return_one_project() {
        // GIVEN
        ProjectEntity projectDuplicate = PROJECT;

        // WHEN
        try {
            projectDao.insertProject(PROJECT);
            projectDao.insertProject(projectDuplicate);
        } catch (SQLiteConstraintException e) {
            // the exception is expected in this test case
            Log.e("ProjectDaoTest", "Error inserting project", e);
        }

        // THEN
        List<ProjectEntity> result = TestUtil.getValueForTesting(projectDao.getProjects());
        assertEquals(1, result.size());
    }
}
