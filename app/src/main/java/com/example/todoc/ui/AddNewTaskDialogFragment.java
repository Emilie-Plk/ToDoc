package com.example.todoc.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoc.R;
import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.databinding.FragmentAddNewTaskDialogBinding;

public class AddNewTaskDialogFragment extends DialogFragment {

    private FragmentAddNewTaskDialogBinding binding;

    private String chosenProject;

    private OnAddTaskListener listener;

    private AddNewTaskDialogFragmentViewModel viewModel;

    @NonNull
    public static AddNewTaskDialogFragment newInstance() {
        return new AddNewTaskDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,
                ViewModelFactory.getInstance(getContext())).get(AddNewTaskDialogFragmentViewModel.class);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnAddTaskListener) {
            listener = (OnAddTaskListener) context;
        } else {
            throw new ClassCastException(context + " must implement OnAddTaskListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddNewTaskDialogBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(binding.getRoot()).create();
        setProjectACTVAdapter();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.projectActv.setOnItemClickListener((adapterView, v, position, id) ->
                chosenProject = adapterView.getItemAtPosition(position).toString());

        binding.addBtnDialog.setOnClickListener(v -> {
            listener.onAddingNewTask(binding.txtTaskName.getText().toString(), chosenProject);
            dismiss();
        });
    }

    private void setProjectACTVAdapter() {
        viewModel.getProjectsNames().observe(this, projectNames -> {
            binding.projectActv.setAdapter(new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, projectNames));
        });

    }
}