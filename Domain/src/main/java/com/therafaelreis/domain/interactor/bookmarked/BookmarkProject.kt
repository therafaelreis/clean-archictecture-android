package com.therafaelreis.domain.interactor.bookmarked

import com.therafaelreis.domain.executor.PostExecutionThread
import com.therafaelreis.domain.interactor.CompletableUseCase
import com.therafaelreis.domain.repository.ProjectsRepository
import io.reactivex.Completable
import javax.inject.Inject

open class BookmarkProject @Inject constructor(
    private val repository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<BookmarkProject.Params>(postExecutionThread) {

    data class Params constructor(val projectId: String) {
        companion object {
            fun forProject(projectId: String): Params {
                return Params(projectId)
            }
        }
    }

    public override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        return repository.bookmarkProject(params.projectId)
    }
}