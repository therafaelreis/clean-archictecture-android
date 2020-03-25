package com.therafaelreis.remote.service

import com.therafaelreis.data.model.ProjectEntity
import com.therafaelreis.data.repository.ProjectsRemote
import com.therafaelreis.remote.mapper.ProjectsResponseModelMapper
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class ProjectsRemoteImpl @Inject constructor(
    private val service: GitTrendingService,
    private val mapper: ProjectsResponseModelMapper
) : ProjectsRemote {

    override fun getProjects(): Flowable<List<ProjectEntity>> {
        return service.searchRepositories("language:kotlin", "stars", "desc").map {
            it.items.map { projectModel ->
                mapper.mapFromModel(projectModel)
            }
        }
    }

}