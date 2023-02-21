package com.example.todoc.ui.addTask;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoc.databinding.FragmentAddNewTaskDialogBinding;
import com.example.todoc.DI.ViewModelFactory;

public class AddNewTaskDialogFragment extends DialogFragment {

    private FragmentAddNewTaskDialogBinding binding;

    @Nullable
    private String chosenProject;

    private AddNewTaskDialogFragmentViewModel viewModel;

    @NonNull
    public static AddNewTaskDialogFragment newInstance() {
        return new AddNewTaskDialogFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddNewTaskDialogBinding.inflate(LayoutInflater.from(getContext()));
        viewModel = new ViewModelProvider(this,
                ViewModelFactory.getInstance()).get(AddNewTaskDialogFragmentViewModel.class);

        setProjectACTVAdapter();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObservers();
        getChosenProject();
        addNewTask();

        binding.txtTaskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.updateForTaskDescriptionCompletion(s.toString());
            }
        });

        // When clicking on return btn
        binding.returnBtnDialog.setOnClickListener(v -> dismiss());

        // observer for my chosen project being selected in the ACTV
        viewModel.getChosenProjectMutableLiveData().observe(this,
                mChosenProject -> chosenProject = mChosenProject);
    }

    private void setProjectACTVAdapter() {
        viewModel.getAllProjects().observe(this,
                projectEntities -> {
                    ProjectArrayAdapter projectAdapter = new ProjectArrayAdapter(getActivity(), projectEntities);
                    binding.projectActv.setAdapter(projectAdapter);
                });
    }

    private void setupObservers() {
        viewModel.getCloseFragment().observe(this,
                closeActivitySingleLiveEvent -> dismiss());

        viewModel.getIsEveryFieldComplete().observe(this,
                isEnabled -> binding.addBtnDialog.setEnabled(isEnabled));
    }

    private void getChosenProject() {
        binding.projectActv.setOnItemClickListener((adapterView, v, position, id) -> {
            chosenProject = adapterView.getItemAtPosition(position).toString();
            viewModel.updateForChosenProjectSelection(chosenProject);
        });
    }

    private void addNewTask() {
        binding.addBtnDialog.setOnClickListener(v -> {
            ProjectArrayAdapter projectAdapter = (ProjectArrayAdapter) binding.projectActv.getAdapter();
            String taskDescription = binding.txtTaskName.getText().toString();
            long projectId = projectAdapter.getProjectId(chosenProject);
            viewModel.onAddingNewTask(taskDescription, projectId);
        });
    }
}