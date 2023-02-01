package com.example.todoc.ui;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.todoc.OnTaskClickedListener;
import com.example.todoc.R;
import com.example.todoc.adapter.TaskListAdapter;
import com.example.todoc.databinding.ActivityMainBinding;

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
        TaskListAdapter adapter = new TaskListAdapter(new OnTaskClickedListener() {
            @Override
            public void onDeleteTask() {

            }
        });


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.listTasks.addItemDecoration(dividerItemDecoration);
        binding.listTasks.setAdapter(adapter);
        viewModel.getTaskViewStateItemLiveData().observe(this, adapter::submitList);
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

}