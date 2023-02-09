package com.example.todoc.ui.addTask;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.todoc.data.entities.ProjectEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class ProjectArrayAdapter extends ArrayAdapter<String> {

    @NonNull
    private final Map<String, Long> projectIdMap;

    public ProjectArrayAdapter(Context context, List<ProjectEntity> projectEntities) {
        super(context, android.R.layout.simple_spinner_dropdown_item,
                projectEntities
                        .stream()
                        .map(ProjectEntity::getProjectName).collect(Collectors.toList()));

        projectIdMap = projectEntities
                .stream()
                .collect(Collectors.toMap(ProjectEntity::getProjectName, ProjectEntity::getId));
    }

    public long getProjectId(String projectName) {
        Long id = projectIdMap.get(projectName);
        return id == null ? -1 : id;
    }
}