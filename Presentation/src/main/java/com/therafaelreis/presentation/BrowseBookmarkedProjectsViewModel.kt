package com.therafaelreis.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.therafaelreis.domain.interactor.bookmarked.GetBookMarkedProjects
import com.therafaelreis.domain.model.Project
import com.therafaelreis.presentation.mapper.ProjectViewMapper
import com.therafaelreis.presentation.model.ProjectView
import com.therafaelreis.presentation.state.Resource
import com.therafaelreis.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseBookmarkedProjectsViewModel @Inject constructor(
    private val getBookmarkedProjects: GetBookMarkedProjects,
    private val mapper: ProjectViewMapper): ViewModel() {

    private val liveData: MutableLiveData<Resource<List<ProjectView>>> =
        MutableLiveData()

    override fun onCleared() {
        getBookmarkedProjects.dispose()
        super.onCleared()
    }

    fun getProjects(): LiveData<Resource<List<ProjectView>>> {
        return liveData
    }

    fun fetchProjects() {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getBookmarkedProjects.execute(ProjectsSubscriber())
    }

    inner class ProjectsSubscriber: DisposableObserver<List<Project>>() {
        override fun onNext(projects: List<Project>) {
            liveData.postValue(Resource(ResourceState.SUCCESS,
                projects.map { mapper.mapToView(it) }, null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null,
                e.localizedMessage))
        }

        override fun onComplete() { }

    }

}