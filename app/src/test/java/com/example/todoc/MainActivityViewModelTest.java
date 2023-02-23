package com.example.todoc;

import static com.example.todoc.ui.taskList.SortMethod.ALPHABETICAL;
import static com.example.todoc.ui.taskList.SortMethod.ALPHABETICAL_INVERTED;
import static com.example.todoc.ui.taskList.SortMethod.NONE;
import static com.example.todoc.ui.taskList.SortMethod.OLD_FIRST;
import static com.example.todoc.ui.taskList.SortMethod.RECENT_FIRST;
import static com.example.todoc.utils.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.ProjectWithTasks;
import com.example.todoc.data.entities.TaskEntity;
import com.example.todoc.data.repositories.TaskRepository;
import com.example.todoc.ui.taskList.MainActivityViewModel;
import com.example.todoc.ui.taskList.TaskViewStateItem;

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
public class MainActivityViewModelTest {

    @Mock
    private TaskRepository repository = mock(TaskRepository.class);

    private MainActivityViewModel viewModel;

    private MutableLiveData<List<ProjectWithTasks>> projectWithTasksMutableLiveData;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        projectWithTasksMutableLiveData = new MutableLiveData<>();
        List<ProjectWithTasks> dummyProjectWithTasksList = getProjectWithTasks();
        projectWithTasksMutableLiveData.setValue(dummyProjectWithTasksList);
        doReturn(projectWithTasksMutableLiveData).when(repository).getProjectWithTasks();

        viewModel = new MainActivityViewModel(repository);

        verify(repository).getProjectWithTasks();
    }

    @Test
    public void nominalCase() {
        // WHEN
        List<TaskViewStateItem> result = getValueForTesting(viewModel.getTaskViewStateItemsMediatorLiveData());

        // THEN
        assertEquals(4, result.size());
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void edgeCase_noTask() {
        // GIVEN
        List<ProjectWithTasks> emptyTaskList = new ArrayList<>();

        // WHEN
        projectWithTasksMutableLiveData.setValue(emptyTaskList);

        // THEN
        List<TaskViewStateItem> result = getValueForTesting(viewModel.getTaskViewStateItemsMediatorLiveData());
        assertEquals(0, result.size());
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void onDeleteGivenTask_verifyRepositoryDeletesGivenTask() {
        // GIVEN
        long taskId = 1L;

        // WHEN
        viewModel.onDeleteTask(taskId);

        // THEN
        verify(repository).deleteTask(taskId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void onDisplayTasks_shouldSortTasksChronologically() {
        // WHEN
        viewModel.onSortingTasksSelected(NONE);

        // THEN
        List<TaskViewStateItem> result = getValueForTesting(viewModel.getTaskViewStateItemsMediatorLiveData());
        assertEquals("Task C", result.get(0).getTaskDescription());
        assertEquals("Task Z", result.get(1).getTaskDescription());
        assertEquals("Task B", result.get(2).getTaskDescription());
        assertEquals("Task A", result.get(3).getTaskDescription());
    }

    @Test
    public void onSortingTasksAtoZ_shouldSortTasksAccordingly() {
        // WHEN
        viewModel.onSortingTasksSelected(ALPHABETICAL);

        // THEN
        List<TaskViewStateItem> result = getValueForTesting(viewModel.getTaskViewStateItemsMediatorLiveData());
        assertEquals("Task A", result.get(0).getTaskDescription());
        assertEquals("Task B", result.get(1).getTaskDescription());
        assertEquals("Task C", result.get(2).getTaskDescription());
        assertEquals("Task Z", result.get(3).getTaskDescription());
    }

    @Test
    public void onSortingTasksZtoA_shouldSortTasksAccordingly() {
        // WHEN
        viewModel.onSortingTasksSelected(ALPHABETICAL_INVERTED);

        // THEN
        List<TaskViewStateItem> result = getValueForTesting(viewModel.getTaskViewStateItemsMediatorLiveData());
        assertEquals("Task Z", result.get(0).getTaskDescription());
        assertEquals("Task C", result.get(1).getTaskDescription());
        assertEquals("Task B", result.get(2).getTaskDescription());
        assertEquals("Task A", result.get(3).getTaskDescription());
    }

    @Test
    public void onSortingTasksChronologically_shouldSortTasksAccordingly() {
        // WHEN
        viewModel.onSortingTasksSelected(OLD_FIRST);

        // THEN
        List<TaskViewStateItem> result = getValueForTesting(viewModel.getTaskViewStateItemsMediatorLiveData());
        assertEquals("Task C", result.get(0).getTaskDescription());
        assertEquals("Task Z", result.get(1).getTaskDescription());
        assertEquals("Task B", result.get(2).getTaskDescription());
        assertEquals("Task A", result.get(3).getTaskDescription());
    }

    @Test
    public void onSortingTasksChronologicallyReversed_shouldSortTasksAccordingly() {
        // WHEN
        viewModel.onSortingTasksSelected(RECENT_FIRST);

        // THEN
        List<TaskViewStateItem> result = getValueForTesting(viewModel.getTaskViewStateItemsMediatorLiveData());
        assertEquals("Task A", result.get(0).getTaskDescription());
        assertEquals("Task B", result.get(1).getTaskDescription());
        assertEquals("Task Z", result.get(2).getTaskDescription());
        assertEquals("Task C", result.get(3).getTaskDescription());
    }

    //region helper method
    private List<ProjectWithTasks> getProjectWithTasks() {
        ProjectEntity project1 = new ProjectEntity(1L, "Projet Tartampion", 0xFFEADAD1);
        ProjectEntity project2 = new ProjectEntity(2L, "Projet Lucidia", 0xFFB4CDBA);
        ProjectEntity project3 = new ProjectEntity(3L, "Projet Circus", 0xFFA3CED2);

        long currentTime = System.currentTimeMillis();

        TaskEntity task1 = new TaskEntity(1L, 1L, "Task C", new Timestamp(currentTime));
        TaskEntity task2 = new TaskEntity(2L, 1L, "Task Z", new Timestamp(currentTime + 1000));
        TaskEntity task3 = new TaskEntity(3L, 2L, "Task B", new Timestamp(currentTime + 2000));
        TaskEntity task4 = new TaskEntity(4L, 3L, "Task A", new Timestamp(currentTime + 3000));

        List<ProjectWithTasks> dummyProjectWithTasks = new ArrayList<>();
        dummyProjectWithTasks.add(new ProjectWithTasks(project1, Arrays.asList(task1, task2)));
        dummyProjectWithTasks.add(new ProjectWithTasks(project2, Collections.singletonList(task3)));
        dummyProjectWithTasks.add(new ProjectWithTasks(project3, Collections.singletonList(task4)));

        return dummyProjectWithTasks;
    }

    //endregion
}
