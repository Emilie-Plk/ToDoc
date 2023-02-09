package com.example.todoc.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.todoc.R;
import com.example.todoc.adapter.TaskListAdapter;
import com.example.todoc.databinding.ActivityMainBinding;
import com.example.todoc.ui.addTask.AddNewTaskDialogFragment;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setViewModel();

        initRecyclerView();

        binding.fabAddTask.setOnClickListener(v -> AddNewTaskDialogFragment.newInstance().show(getSupportFragmentManager(), null));
    }

    private void initRecyclerView() {
        TaskListAdapter adapter = new TaskListAdapter(taskID -> viewModel.onDeleteTask(taskID));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.listTasks.addItemDecoration(dividerItemDecoration);

        binding.listTasks.setAdapter(adapter);
        binding.listTasks.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getMeetingViewStateItemsMediatorLiveData().observe(this, adapter::submitList);
        viewModel.getIsNoTaskTextViewVisible().observe(this, isVisible ->
                binding.lblNoTask.setVisibility(Boolean.TRUE.equals(isVisible) ? View.VISIBLE : View.GONE));
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this,
                ViewModelFactory.getInstance(getApplication())).get(MainActivityViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.filter_alphabetical):
                viewModel.sortTasks(SortMethod.ALPHABETICAL);
                break;
            case (R.id.filter_alphabetical_inverted):
                viewModel.sortTasks(SortMethod.ALPHABETICAL_INVERTED);
                break;
            case (R.id.filter_oldest_first):
                viewModel.sortTasks(SortMethod.OLD_FIRST);
                break;
            case (R.id.filter_recent_first):
                viewModel.sortTasks(SortMethod.RECENT_FIRST);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}