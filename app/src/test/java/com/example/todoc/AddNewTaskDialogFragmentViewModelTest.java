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
import androidx.lifecycle.Observer;

import com.example.todoc.data.entities.ProjectEntity;
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
import java.util.List;

@RunWith(MockitoJUnitRunner.class)

public class AddNewTaskDialogFragmentViewModelTest {

    @Mock
    private ProjectRepository projectRepository = mock(ProjectRepository.class);
    @Mock
    private TaskRepository taskRepository = mock(TaskRepository.class);

    @Mock
    private Observer<Void> closeDialogFragmentObserver;

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
        // WHEN
        List<ProjectEntity> projectEntityList = getValueForTesting(viewModel.getAllProjects());

        // THEN
        assertEquals(getProjectsTest().size(), projectEntityList.size());
    }

    @Test
    public void onAddingNewTask_repositoryShouldAddNewTask() {
        // GIVEN
        String taskDescription = "Test task description";
        long projectId = 1L;
        TaskEntity expectedTaskEntity = new TaskEntity(0, projectId, taskDescription, new Timestamp(System.currentTimeMillis()));

        // WHEN
        viewModel.onAddingNewTask(taskDescription, projectId);

        // THEN
        verify(taskRepository).addNewTask(expectedTaskEntity);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void onAddingNewTask_closeDialogObserverShouldBeTriggeredOnce() {
        // GIVEN
        TaskEntity expectedTaskEntity = mock(TaskEntity.class);
        viewModel.getCloseFragment().observeForever(closeDialogFragmentObserver);

        // WHEN
        viewModel.onAddingNewTask(expectedTaskEntity.getTaskDescription(), expectedTaskEntity.getProjectId());

        // THEN
        verify(closeDialogFragmentObserver).onChanged(null);
        verifyNoMoreInteractions(closeDialogFragmentObserver);

        // extra verification to check that if we resubscribe this observer
        // on the same LiveData, then we do not receive data
        viewModel.getCloseFragment().observeForever(closeDialogFragmentObserver);
        verifyNoMoreInteractions(closeDialogFragmentObserver);
    }

    @Test
    public void onAddingNewTask_callsIsEveryFieldComplete() {
        // GIVEN
        viewModel.updateForTaskDescriptionCompletion(TASK_DESCRIPTION);
        viewModel.updateForChosenProjectSelection(CHOSEN_PROJECT);

        // WHEN
        Boolean result = getValueForTesting(viewModel.getIsEveryFieldComplete());

        // THEN
        assertTrue(result);
    }


    @Test
    public void onAddingNewTask_onlyIfTaskAndProjectChosen_callsIsEveryFieldComplete() {
        // WHEN
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
        // GIVEN
        viewModel.updateForChosenProjectSelection(CHOSEN_PROJECT);

        // WHEN
        Boolean isProjectFieldComplete = getValueForTesting(viewModel.getIsEveryFieldComplete());

        // THEN
        assertFalse(isProjectFieldComplete);
    }

    @Test
    public void updateForTaskDescriptionCompletion_updatesIsProjectFieldComplete() {
        // GIVEN
        viewModel.updateForTaskDescriptionCompletion(TASK_DESCRIPTION);

        // WHEN
        Boolean isTaskFieldCompleted = getValueForTesting(viewModel.getIsEveryFieldComplete());

        // THEN
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

    //endregion
}
