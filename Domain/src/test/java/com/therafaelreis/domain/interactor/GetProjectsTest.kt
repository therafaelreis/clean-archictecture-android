package com.therafaelreis.domain.interactor

import com.nhaarman.mockito_kotlin.whenever
import com.therafaelreis.domain.executor.PostExecutionThread
import com.therafaelreis.domain.interactor.browse.GetProjects
import com.therafaelreis.domain.model.Project
import com.therafaelreis.domain.repository.ProjectsRepository
import com.therafaelreis.domain.test.ProjectDataFactoryTest
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetProjectsTest{

    private lateinit var getProjects: GetProjects
    @Mock private lateinit var projectsRepository: ProjectsRepository
    @Mock private lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        getProjects = GetProjects(projectsRepository, postExecutionThread)
    }

    @Test
    fun getProjectsCompletes(){
        stubGetProjects(Observable.just(ProjectDataFactoryTest.makeProjectList(2)))
        val testObserver = getProjects.buildCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsReturnsData(){
        val projects = ProjectDataFactoryTest.makeProjectList(2)
        stubGetProjects(Observable.just(projects))
        val testObserver = getProjects.buildCaseObservable().test()
        testObserver.assertValue(projects)
    }

    private fun stubGetProjects(observable: Observable<List<Project>>){
        whenever(projectsRepository.getProjects())
            .thenReturn(observable)
    }

}