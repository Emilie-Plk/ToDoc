package com.example.todoc.ui.taskList.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoc.R;
import com.example.todoc.databinding.ItemTaskBinding;
import com.example.todoc.ui.taskList.OnTaskDeleteClickListener;
import com.example.todoc.ui.taskList.TaskViewStateItem;

public class TaskListAdapter extends ListAdapter<TaskViewStateItem, TaskListAdapter.TaskViewHolder> {

    private final OnTaskDeleteClickListener listener;

    public TaskListAdapter(OnTaskDeleteClickListener listener) {
        super(new ListTaskItemCallback());
        this.listener = listener;
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaskBinding binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskViewHolder(binding, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        private final ImageView deleteTask;
        private final ImageView imgProject;
        private final TextView taskDescription;
        private final TextView projectName;
        private final Context context;

        public TaskViewHolder(@NonNull ItemTaskBinding binding, Context context) {
            super(binding.getRoot());
            taskDescription = binding.lblTaskName;
            imgProject = binding.imgProject;
            projectName = binding.lblProjectName;
            deleteTask = binding.imgDelete;
            this.context = context;
        }

        public void bind(TaskViewStateItem item, OnTaskDeleteClickListener listener) {
            taskDescription.setText(item.getTaskDescription());
            projectName.setText(item.getProjectName());
            imgProject.setColorFilter(item.getProjectColor());
            deleteTask.setContentDescription(context.getString(R.string.delete_acessibility, item.getTaskDescription(), item.getProjectName()));
            deleteTask.setOnClickListener(view -> listener.onDeleteTask(item.getTaskId()));
        }
    }

    /**
     * Utility class that can calculate the difference between two lists
     * and update only the necessary items or their content
     */
    private static class ListTaskItemCallback extends DiffUtil.ItemCallback<TaskViewStateItem> {

        @Override
        public boolean areItemsTheSame(@NonNull TaskViewStateItem oldItem, @NonNull TaskViewStateItem newItem) {
            return oldItem.getTaskId() == newItem.getTaskId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TaskViewStateItem oldItem, @NonNull TaskViewStateItem newItem) {
            return oldItem.equals(newItem);
        }
    }
}

