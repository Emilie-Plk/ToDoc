package com.example.todoc;

import static com.example.todoc.utils.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.ProjectWithTasks;
import com.example.todoc.data.entities.TaskEntity;
import com.example.todoc.data.repositories.ProjectRepository;
import com.example.todoc.data.repositories.TaskRepository;
import com.example.todoc.ui.addTask.AddNewTaskDialogFragmentViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)

public class AddNewTaskDialogFragmentViewModelTest {

    @Mock
    private ProjectRepository projectRepository = mock(ProjectRepository.class);
    @Mock
    private TaskRepository taskRepository = mock(TaskRepository.class);

    private AddNewTaskDialogFragmentViewModel viewModel;

    private final MutableLiveData<List<ProjectEntity>> projectList = new MutableLiveData<>();

    private final static String TASK_DESCRIPTION = "Test task description";
    private final static String CHOSEN_PROJECT = "Projet Lucidia";

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();


    @Before
    public void setUp() {
        List<ProjectEntity> projectEntityList = getProjectsTest();
        projectList.setValue(projectEntityList);
        doReturn(projectList).when(projectRepository).getAllProjects();


        viewModel = new AddNewTaskDialogFragmentViewModel(taskRepository, projectRepository);
    }

    @Test
    public void getAllProjects_returns3Projects() {
        List<ProjectEntity> projectEntityList = getValueForTesting(viewModel.getAllProjects());

        assertEquals(getProjectsTest().size(), projectEntityList.size());
    }

    @Test
    public void onAddingNewTask_repositoryShouldAddNewTask() {
        String taskDescription = "Test task description";
        long projectId = 1L;
        TaskEntity expectedTaskEntity = new TaskEntity(0, projectId, taskDescription, new Timestamp(System.currentTimeMillis()));

        viewModel.onAddingNewTask(taskDescription, projectId);

        verify(taskRepository).addNewTask(expectedTaskEntity);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void onAddingNewTask_callsIsEveryFieldComplete() {


        viewModel.updateForTaskDescriptionCompletion(TASK_DESCRIPTION);
        viewModel.updateForChosenProjectSelection(CHOSEN_PROJECT);

        Boolean result = getValueForTesting(viewModel.getIsEveryFieldComplete());

        assertTrue(result);
    }


    @Test
    public void onAddingNewTask_onlyIfTaskAndProjectChosen_callsIsEveryFieldComplete() {

        Boolean isTaskAndProjectUnselected = getValueForTesting(viewModel.getIsEveryFieldComplete());
        assertFalse(isTaskAndProjectUnselected);

        viewModel.updateForTaskDescriptionCompletion(TASK_DESCRIPTION);
        Boolean isProjectUnselected = getValueForTesting(viewModel.getIsEveryFieldComplete());
        assertFalse(isProjectUnselected);

        viewModel.updateForChosenProjectSelection(CHOSEN_PROJECT);
        Boolean result = getValueForTesting(viewModel.getIsEveryFieldComplete());
        assertTrue(result);
    }

    @Test
    public void updateForChosenProjectSelection_updatesIsProjectFieldComplete() {
        viewModel.updateForChosenProjectSelection(CHOSEN_PROJECT);
        Boolean isProjectFieldComplete = getValueForTesting(viewModel.getIsEveryFieldComplete());
        assertFalse(isProjectFieldComplete);
    }

    @Test
    public void updateForTaskDescriptionCompletion_updatesIsProjectFieldComplete() {
        viewModel.updateForTaskDescriptionCompletion(TASK_DESCRIPTION);
        Boolean isTaskFieldCompleted = getValueForTesting(viewModel.getIsEveryFieldComplete());
        assertFalse(isTaskFieldCompleted);
    }

    //region Project entities for test
    private List<ProjectEntity> getProjectsTest() {
        List<ProjectEntity> projectList = new ArrayList<>();
        projectList.add(new ProjectEntity(1L, "Projet Tartampion", 0xFFEADAD1));
        projectList.add(new ProjectEntity(2L, "Projet Lucidia", 0xFFB4CDBA));
        projectList.add(new ProjectEntity(3L, "Projet Circus", 0xFFA3CED2));
        return projectList;
    }

    private List<ProjectWithTasks> getProjectWithTasks() {
        ProjectEntity project1 = new ProjectEntity(1L, "Projet Tartampion", 0xFFEADAD1);
        ProjectEntity project2 = new ProjectEntity(2L, "Projet Lucidia", 0xFFB4CDBA);
        ProjectEntity project3 = new ProjectEntity(3L, "Projet Circus", 0xFFA3CED2);

        TaskEntity task1 = new TaskEntity(1L, 1L, "Task A", new Timestamp(System.currentTimeMillis()));
        TaskEntity task2 = new TaskEntity(2L, 1L, "Task B", new Timestamp(System.currentTimeMillis()));
        TaskEntity task3 = new TaskEntity(3L, 2L, "Task C", new Timestamp(System.currentTimeMillis()));
        TaskEntity task4 = new TaskEntity(4L, 3L, "Task D", new Timestamp(System.currentTimeMillis()));

        List<ProjectWithTasks> dummyProjectWithTasks = new ArrayList<>();
        dummyProjectWithTasks.add(new ProjectWithTasks(project1, Arrays.asList(task1, task2)));
        dummyProjectWithTasks.add(new ProjectWithTasks(project2, Collections.singletonList(task3)));
        dummyProjectWithTasks.add(new ProjectWithTasks(project3, Collections.singletonList(task4)));

        return dummyProjectWithTasks;
    }
    //endregion
}
