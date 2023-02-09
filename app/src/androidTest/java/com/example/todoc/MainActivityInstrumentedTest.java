package com.example.todoc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.todoc.RecyclerViewMatcher.withRecyclerView;

import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todoc.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    private static final String PROJECT_NAME = "Projet Circus";

    private static final String TASK_DESCRIPTION = "Test Task";

    private static final String TASK_DESCRIPTION_Z = "Z Test Task";

    private static final String TASK_DESCRIPTION_A = "A Test Task";

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void isRecyclerViewVisible_onAppLaunch() {
        onView(withId(R.id.list_tasks)).check(matches(isDisplayed()));
    }

    @Test
    public void taskRecyclerView_shouldBeEmptyOnTestLaunch() {
        onView(withId(R.id.list_tasks)).check(matches(hasChildCount(0)));
    }

    @Test
    public void onAddingNewTask_shouldAddOneTaskToRecyclerView() {
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.project_actv)).perform(click());
        onView(withText(PROJECT_NAME)).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText(TASK_DESCRIPTION));
        onView(withId(R.id.add_btn_dialog)).perform(click());

        onView(withId(R.id.list_tasks)).check(matches(hasChildCount(1)));
    }

    @Test
    public void onAddingNewTask_recyclerViewShouldDisplayNewlyAddedTaskInfo() {
        taskRecyclerView_shouldBeEmptyOnTestLaunch();

        onAddingNewTask_shouldAddOneTaskToRecyclerView();

        onView(withRecyclerView(R.id.list_tasks)
                .atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText(TASK_DESCRIPTION)));

        onView(withRecyclerView(R.id.list_tasks)
                .atPositionOnView(0, R.id.lbl_project_name))
                .check(matches(withText(PROJECT_NAME)));
    }

    @Test
    public void onDeleteTask_taskShouldBeRemovedFromRecyclerView() {
        onAddingNewTask_shouldAddOneTaskToRecyclerView();

        onView(withRecyclerView(R.id.list_tasks)
                .atPositionOnView(0, R.id.img_delete))
                .perform(click());

        onView(withId(R.id.list_tasks)).check(matches(hasChildCount(0)));
    }

    @Test
    public void onAdding3Tasks_recyclerViewShouldContain3Tasks() {
        addOneTask(TASK_DESCRIPTION);
        addOneTask(TASK_DESCRIPTION_Z);
        addOneTask(TASK_DESCRIPTION_A);

        onView(withId(R.id.list_tasks)).check(matches(hasChildCount(3)));
    }

    @Test
    public void onSortingTasksAtoZ_shouldSortTasksNamesAtoZ() {
        onAdding3Tasks_recyclerViewShouldContain3Tasks();

        onView(withId(R.id.action_filter)).check(matches(isDisplayed())).perform(click());
        onView(withText(R.string.sort_alphabetical)).perform(click());

        onView(withRecyclerView(R.id.list_tasks)
                .atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText(TASK_DESCRIPTION_A)));

        onView(withRecyclerView(R.id.list_tasks)
                .atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText(TASK_DESCRIPTION)));

        onView(withRecyclerView(R.id.list_tasks)
                .atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText(TASK_DESCRIPTION_Z)));
    }

    @Test
    public void onSortingTasksZtoA_shouldSortTasksNamesZtoA() {
        onAdding3Tasks_recyclerViewShouldContain3Tasks();

        onView(withId(R.id.action_filter)).check(matches(isDisplayed())).perform(click());
        onView(withText(R.string.sort_alphabetical)).perform(click());

        onView(withRecyclerView(R.id.list_tasks)
                .atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText(TASK_DESCRIPTION_Z)));

        onView(withRecyclerView(R.id.list_tasks)
                .atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText(TASK_DESCRIPTION)));

        onView(withRecyclerView(R.id.list_tasks)
                .atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText(TASK_DESCRIPTION_A)));
    }


    private void addOneTask(String taskDescription) {
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.project_actv)).perform(click());
        onView(withText(PROJECT_NAME)).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText(taskDescription));
        onView(withId(R.id.add_btn_dialog)).perform(click());
    }

}