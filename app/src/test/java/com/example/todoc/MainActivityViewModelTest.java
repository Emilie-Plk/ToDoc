package com.example.todoc;

import static com.example.todoc.ui.SortMethod.ALPHABETICAL;
import static com.example.todoc.utils.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoc.data.repositories.TaskRepository;
import com.example.todoc.ui.MainActivityViewModel;
import com.example.todoc.ui.TaskViewStateItem;

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
public class MainActivityViewModelTest {

    @Mock
    private TaskRepository repository = mock(TaskRepository.class);

    private MainActivityViewModel viewModel;

    private MutableLiveData<List<TaskViewStateItem>> taskViewStateItems;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        doReturn(taskViewStateItems).when(repository).getTaskViewState();
        taskViewStateItems = new MutableLiveData<>();

        List<TaskViewStateItem> dummyViewStateItems = getTestTaskViewStateItems();
        taskViewStateItems.setValue(dummyViewStateItems);

        given(repository.getTaskViewState()).willReturn(taskViewStateItems);

        viewModel = new MainActivityViewModel(repository);

        verify(repository).getTaskViewState();
    }

    @Test
    public void nominalCase() {
        // WHEN
        List<TaskViewStateItem> result = getValueForTesting(viewModel.getMeetingViewStateItemsMediatorLiveData());

        // THEN
        assertEquals(4, result.size());
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void edgeCase_noTask() {
        // GIVEN
        List<TaskViewStateItem> emptyTaskList = new ArrayList<>();

        // WHEN
        taskViewStateItems.setValue(emptyTaskList);

        // THEN
        List<TaskViewStateItem> result = getValueForTesting(viewModel.getMeetingViewStateItemsMediatorLiveData());
        assertEquals(0, result.size());
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void onDeleteOneTask_shouldRemoveOneTask() throws InterruptedException {
        // GIVEN
       /* List<TaskViewStateItem> resultBeforeDeleting = getValueForTesting(viewModel.getMeetingViewStateItemsMediatorLiveData());
        assertEquals(4, resultBeforeDeleting.size());*/

        // WHEN
        viewModel.onDeleteTask(1L);
        // THEN

LiveData<List<TaskViewStateItem>> result = viewModel.getMeetingViewStateItemsMediatorLiveData();
    //    List<TaskViewStateItem> resultAfterDeleting = getValueForTesting(viewModel.getMeetingViewStateItemsMediatorLiveData());
        assertEquals(3, result.getValue().size()); // TODO: still find 4
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void filterAtoZ() {
        // WHEN
        viewModel.sortTasks(ALPHABETICAL);

        // THEN
        List<TaskViewStateItem> result = getValueForTesting(viewModel.getMeetingViewStateItemsMediatorLiveData());
        assertEquals(4, result.size());
    }

    //region helper method
    @NonNull
    private List<TaskViewStateItem> getTestTaskViewStateItems() {
        List<TaskViewStateItem> dummyTasks = new ArrayList<>();

        dummyTasks.add(
                new TaskViewStateItem(
                        1L,
                        "Mop the floor",
                        "Projet Tartampion",
                        0xFFEADAD1,
                        new Timestamp(System.currentTimeMillis()))
        );

        dummyTasks.add(
                new TaskViewStateItem(
                        2L,
                        "Clean the bathroom",
                        "Projet Circus",
                        0xFFA3CED2,
                        new Timestamp(System.currentTimeMillis()))
        );

        dummyTasks.add(
                new TaskViewStateItem(
                        3L,
                        "Empty the garbage",
                        "Projet Lucidia",
                        0xFFB4CDBA,
                        new Timestamp(System.currentTimeMillis()))
        );

        dummyTasks.add(
                new TaskViewStateItem(
                        4L,
                        "Do the laundry",
                        "Projet Circus",
                        0xFFA3CED2,
                        new Timestamp(System.currentTimeMillis()))
        );

        return dummyTasks;
    }
    //endregion
}
