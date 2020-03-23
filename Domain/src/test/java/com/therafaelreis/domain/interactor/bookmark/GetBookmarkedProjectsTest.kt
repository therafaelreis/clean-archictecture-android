package com.therafaelreis.domain.interactor.bookmark

import com.nhaarman.mockito_kotlin.whenever
import com.therafaelreis.domain.executor.PostExecutionThread
import com.therafaelreis.domain.interactor.bookmarked.GetBookMarkedProjects
import com.therafaelreis.domain.model.Project
import com.therafaelreis.domain.repository.ProjectsRepository
import com.therafaelreis.domain.test.ProjectDataFactoryTest
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetBookmarkedProjectsTest {

    private lateinit var getBookMarkedProjects: GetBookMarkedProjects
    @Mock lateinit var projectsRepository: ProjectsRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        getBookMarkedProjects = GetBookMarkedProjects(projectsRepository, postExecutionThread)
    }

    @Test
    fun getBookmarkedProjectsCompletes(){
        stubGetProjects(Observable.just(ProjectDataFactoryTest.makeProjectList(2)))
        val testObserver = getBookMarkedProjects.buildCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBookmarkedReturnsData(){
        val projects = ProjectDataFactoryTest.makeProjectList(2)
        stubGetProjects(Observable.just(projects))
        val testObserver = getBookMarkedProjects.buildCaseObservable().test()
        testObserver.assertValue(projects)
    }

    private fun stubGetProjects(observable: Observable<List<Project>>){
        whenever(projectsRepository.getBookmarkedProjects())
            .thenReturn(observable)

    }
}