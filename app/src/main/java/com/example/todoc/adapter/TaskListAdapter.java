package com.example.todoc.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoc.OnTaskDeleteClickListener;
import com.example.todoc.databinding.ItemTaskBinding;
import com.example.todoc.ui.TaskViewStateItem;

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
            return new TaskViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            holder.bind(getItem(position), listener);
        }

        public static class TaskViewHolder extends RecyclerView.ViewHolder {

            private final ImageView deleteTask, imgProject;
            private final TextView taskTitle, projectName;

            public TaskViewHolder(@NonNull ItemTaskBinding binding) {
                super(binding.getRoot());
                taskTitle = binding.lblTaskName;
                imgProject = binding.imgProject;
                projectName = binding.lblProjectName;
                deleteTask = binding.imgDelete;
            }

            public void bind(TaskViewStateItem item, OnTaskDeleteClickListener listener) {
                taskTitle.setText(item.getTaskName());
                projectName.setText(item.getProjectName());
                imgProject.setColorFilter(item.getProjectColor());
                deleteTask.setOnClickListener(view -> listener.onDeleteTask(item.getTaskId()));
            }
        }


        /**
         * Utility class that can calculate the difference between two lists
         * and update only the necessary items or its content
         */
        private static class ListTaskItemCallback extends DiffUtil.ItemCallback<TaskViewStateItem> {

            @Override
            public boolean areItemsTheSame(@NonNull TaskViewStateItem oldItem, @NonNull TaskViewStateItem newItem) {
                return oldItem.getTaskId() == newItem.getTaskId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull TaskViewStateItem oldItem, @NonNull TaskViewStateItem newItem) {
                return oldItem.getTaskId() == newItem.getTaskId()
                        && oldItem.getTaskName().equals(newItem.getTaskName())
                        && oldItem.getProjectName().equals(newItem.getProjectName())
                        && oldItem.getProjectColor() == newItem.getProjectColor();
            }
        }
    }

