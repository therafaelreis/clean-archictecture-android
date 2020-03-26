package com.therafaelreis.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.therafaelreis.domain.interactor.bookmarked.BookmarkProject
import com.therafaelreis.domain.interactor.bookmarked.UnbookmarkProject
import com.therafaelreis.domain.interactor.browse.GetProjects
import com.therafaelreis.domain.model.Project
import com.therafaelreis.presentation.BrowseProjectsViewModel
import com.therafaelreis.presentation.mapper.ProjectViewMapper
import com.therafaelreis.presentation.model.ProjectView
import com.therafaelreis.presentation.state.ResourceState
import com.therafaelreis.presentation.test.factory.DataFactory
import com.therafaelreis.presentation.test.factory.ProjectFactory
import io.reactivex.observers.DisposableObserver
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

@RunWith(JUnit4::class)
class BrowseProjectsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    var getProjects = mock<GetProjects>()
    var bookmarkProject = mock<BookmarkProject>()
    var unbookmarkProject = mock<UnbookmarkProject>()
    var projectMapper = mock<ProjectViewMapper>()
    var projectViewModel =
        BrowseProjectsViewModel(getProjects, bookmarkProject, unbookmarkProject, projectMapper)

    @Captor
    val captor = argumentCaptor<DisposableObserver<List<Project>>>()

    @Test
    fun fetchProjectsExecutesUseCase() {
        projectViewModel.fetchProjects()

        verify(getProjects, times(1)).execute(any(), eq(null))
    }

    @Test
    fun fetchProjectsReturnsSuccess() {
        val projects = ProjectFactory.makeProjectList(2)
        val projectViews = ProjectFactory.makeProjectViewList(2)
        stubProjectMapperMapToView(projectViews[0], projects[0])
        stubProjectMapperMapToView(projectViews[1], projects[1])

        projectViewModel.fetchProjects()

        verify(getProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)

        assertEquals(
            ResourceState.SUCCESS,
            projectViewModel.getProjects().value?.status
        )
    }

    @Test
    fun fetchProjectsReturnsData() {
        val projects = ProjectFactory.makeProjectList(2)
        val projectViews = ProjectFactory.makeProjectViewList(2)
        stubProjectMapperMapToView(projectViews[0], projects[0])
        stubProjectMapperMapToView(projectViews[1], projects[1])

        projectViewModel.fetchProjects()

        verify(getProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)

        assertEquals(
            projectViews,
            projectViewModel.getProjects().value?.data
        )
    }

    @Test
    fun fetchProjectsReturnsError() {
        projectViewModel.fetchProjects()

        verify(getProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())

        assertEquals(
            ResourceState.ERROR,
            projectViewModel.getProjects().value?.status
        )
    }

    @Test
    fun fetchProjectsReturnsMessageForError() {
        val errorMessage = DataFactory.randomString()
        projectViewModel.fetchProjects()

        verify(getProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException(errorMessage))

        assertEquals(
            errorMessage,
            projectViewModel.getProjects().value?.message
        )
    }

    private fun stubProjectMapperMapToView(
        projectView: ProjectView,
        project: Project
    ) {
        whenever(projectMapper.mapToView(project))
            .thenReturn(projectView)
    }

}