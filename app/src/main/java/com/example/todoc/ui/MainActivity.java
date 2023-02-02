package com.example.todoc.ui;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

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
        viewModel.getTaskViewStateItemLiveData().observe(this, adapter::submitList);
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
    public void onAddingNewTask(String newTask, String projectName) {
        viewModel.onAddingNewTask(newTask, projectName);
    }
}