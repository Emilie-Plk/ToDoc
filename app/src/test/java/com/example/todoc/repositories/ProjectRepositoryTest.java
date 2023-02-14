package com.example.todoc.repositories;


import static org.junit.Assert.assertEquals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoc.data.dao.ProjectDao;
import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.repositories.ProjectRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

public class ProjectRepositoryTest {

    private final ProjectDao projectDao = Mockito.mock(ProjectDao.class);

    private ProjectRepository repository;


    @Before
    public void setUp() {
        repository = new ProjectRepository(projectDao);
    }

    @Test
    public void verify_getAllProjects() {
        LiveData<List<ProjectEntity>> projectEntitiesLiveData = Mockito.spy(new MutableLiveData<>());
        Mockito.doReturn(projectEntitiesLiveData).when(projectDao).getProjects();

        LiveData<List<ProjectEntity>> result = repository.getAllProjects();

        assertEquals(projectEntitiesLiveData, result);
        Mockito.verify(projectDao).getProjects();
        Mockito.verifyNoMoreInteractions(projectDao);
    }

}
