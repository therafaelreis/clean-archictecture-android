package com.therafaelreis.domain.interactor.bookmark

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.therafaelreis.domain.executor.PostExecutionThread
import com.therafaelreis.domain.interactor.bookmarked.BookmarkProject
import com.therafaelreis.domain.repository.ProjectsRepository
import com.therafaelreis.domain.test.ProjectDataFactoryTest
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.IllegalArgumentException

class BookmarkProjectTest {

    private lateinit var bookmarkProject: BookmarkProject
    @Mock
    lateinit var projectsRepository: ProjectsRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        bookmarkProject = BookmarkProject(projectsRepository, postExecutionThread)
    }

    @Test
    fun bookmarkProjectsCompletes() {
        stubBookmarkProject(Completable.complete())
        val testObserver = bookmarkProject.buildUseCaseCompletable(
            BookmarkProject.Params.forProject(ProjectDataFactoryTest.randomUUID())
        ).test()

        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun bookmarkProjectsThrowsException() {
        bookmarkProject.buildUseCaseCompletable().test()
    }

    private fun stubBookmarkProject(completable: Completable) {
        whenever(projectsRepository.bookmarkProject(any()))
            .thenReturn(completable)
    }
}