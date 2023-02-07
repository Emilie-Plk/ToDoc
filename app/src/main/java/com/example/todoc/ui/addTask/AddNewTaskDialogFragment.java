package com.example.todoc.ui.addTask;

import android.os.Bundle;
import android.text.Editable;
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

import com.example.todoc.databinding.FragmentAddNewTaskDialogBinding;
import com.example.todoc.ui.ViewModelFactory;

public class AddNewTaskDialogFragment extends DialogFragment {

    private FragmentAddNewTaskDialogBinding binding;

    private String chosenProject;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddNewTaskDialogBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(binding.getRoot()).create();
        setProjectACTVAdapter();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.projectActv.setOnItemClickListener((adapterView, v, position, id) -> {
                    chosenProject = adapterView.getItemAtPosition(position).toString();
                    viewModel.updateForChosenProjectSelection(chosenProject);
                });


        binding.addBtnDialog.setOnClickListener(v -> {
            if (!binding.txtTaskName.getText().toString().isEmpty()) {
                viewModel.onAddingNewTask(binding.txtTaskName.getText().toString(), chosenProject);
            }
        });

        setupObservers();

        binding.txtTaskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.updateForTaskNameCompletion(s.toString());
            }
        });

        binding.returnBtnDialog.setOnClickListener(v -> dismiss());
    }

    private void setupObservers() {
        viewModel.getCloseFragment().observe(this, closeActivitySingleLiveEvent -> dismiss());

        viewModel.getIsEveryFieldComplete().observe(this, isEnabled -> {
            binding.addBtnDialog.setEnabled(isEnabled);
        });
    }


    private void setProjectACTVAdapter() {
        viewModel.getProjectsNames().observe(this, projectNames -> binding.projectActv.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, projectNames)));
    }
}