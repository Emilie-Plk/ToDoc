package com.example.todoc.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.todoc.OnTaskDeleteClickListener;
import com.example.todoc.R;
import com.example.todoc.adapter.TaskListAdapter;
import com.example.todoc.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements OnAddTaskListener {

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
/*
        viewModel.getTaskViewStateItemLiveData().observe(this, adapter::submitList);
*/
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
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) {
          viewModel.sortTasksNamesByAtoZ();
        } else if (id == R.id.filter_alphabetical_inverted) {
            viewModel.sortTasksNamesByAtoZ();
        } else if (id == R.id.filter_oldest_first) {
            viewModel.sortTasksByTimeDesc();
        } else if (id == R.id.filter_recent_first) {
            viewModel.sortTasksByTimeAsc();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onAddingNewTask(String newTask, String projectName) {
        viewModel.onAddingNewTask(newTask, projectName);
    }
}