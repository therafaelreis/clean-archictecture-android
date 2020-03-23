package com.therafaelreis.domain.interactor.bookmark

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.therafaelreis.domain.executor.PostExecutionThread
import com.therafaelreis.domain.interactor.bookmarked.UnbookmarkProject
import com.therafaelreis.domain.repository.ProjectsRepository
import com.therafaelreis.domain.test.ProjectDataFactoryTest
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UnbookmarkProjectTest {

    private lateinit var unbookmarkProject: UnbookmarkProject
    @Mock
    lateinit var projectsRepository: ProjectsRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        unbookmarkProject = UnbookmarkProject(projectsRepository, postExecutionThread)
    }

    @Test
    fun unbookmarkProjectCompletes() {
        stubUnbookmarkProjects(Completable.complete())
        val testObserver =
            unbookmarkProject.buildUseCaseCompletable(
                UnbookmarkProject.Params(ProjectDataFactoryTest.randomUUID())
            ).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun unbookmarkProjectThrowsException() {
        unbookmarkProject.buildUseCaseCompletable()

    }

    private fun stubUnbookmarkProjects(completable: Completable) {
        whenever(projectsRepository.unbookmarkProject(any()))
            .thenReturn(completable)

    }
}