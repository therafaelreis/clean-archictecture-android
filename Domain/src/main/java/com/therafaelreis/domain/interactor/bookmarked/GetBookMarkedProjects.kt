package com.therafaelreis.domain.interactor.bookmarked

import com.therafaelreis.domain.executor.PostExecutionThread
import com.therafaelreis.domain.interactor.ObservableUseCase
import com.therafaelreis.domain.model.Project
import com.therafaelreis.domain.repository.ProjectsRepository
import io.reactivex.Observable
import javax.inject.Inject

open class GetBookMarkedProjects @Inject constructor(
    private val repository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
): ObservableUseCase<List<Project>, Nothing?>(postExecutionThread){

    public override fun buildCaseObservable(params: Nothing?): Observable<List<Project>> {
        return repository.getBookmarkedProjects()
    }
}