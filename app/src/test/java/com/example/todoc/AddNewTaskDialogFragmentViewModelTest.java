package com.example.todoc;

import static com.example.todoc.utils.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.repositories.ProjectRepository;
import com.example.todoc.data.repositories.TaskRepository;
import com.example.todoc.ui.TaskViewStateItem;
import com.example.todoc.ui.addTask.AddNewTaskDialogFragmentViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)

public class AddNewTaskDialogFragmentViewModelTest {

    @Mock
    private ProjectRepository projectRepository = mock(ProjectRepository.class);
    @Mock
    private TaskRepository taskRepository = mock(TaskRepository.class);

    private AddNewTaskDialogFragmentViewModel viewModel;

    private MutableLiveData<List<ProjectEntity>> projectList;

    private MutableLiveData<List<TaskViewStateItem>> taskViewStateItems;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();


    @Before
    public void setUp() {
        projectList = new MutableLiveData<>();
        List<ProjectEntity> projectEntityList = getProjectsTest();
        projectList.setValue(projectEntityList);

        doReturn(projectList).when(projectRepository).getAllProjects();

        taskViewStateItems = new MutableLiveData<>();

/*
        doReturn(taskViewStateItems).when(taskRepository).getTaskViewState();
*/

        viewModel = new AddNewTaskDialogFragmentViewModel(taskRepository, projectRepository);
    }

    @Test
    public void getAllProjects_returns3Projects() {
        List<ProjectEntity> projectEntityList = getValueForTesting(viewModel.getAllProjects());

        assertEquals(getProjectsTest().size(), projectEntityList.size());
    }

    @Test
    public void onAddingNewTask_shouldAddTask() { // TODO: doesn't work either...
        viewModel.onAddingNewTask("Mop the floor", 1L);

/*
        List<TaskViewStateItem> taskViewStateItemList = getValueForTesting(taskRepository.getTaskViewState());
*/

/*
        assertEquals(1, taskViewStateItemList.size());
*/
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
