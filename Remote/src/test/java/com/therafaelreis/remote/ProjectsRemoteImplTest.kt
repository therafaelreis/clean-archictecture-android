package com.therafaelreis.remote

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.therafaelreis.data.model.ProjectEntity
import com.therafaelreis.remote.mapper.ProjectsResponseModelMapper
import com.therafaelreis.remote.model.ProjectModel
import com.therafaelreis.remote.model.ProjectsResponseModel
import com.therafaelreis.remote.service.GitTrendingService
import com.therafaelreis.remote.service.ProjectsRemoteImpl
import com.therafaelreis.remote.test.factory.ProjectDataFactory
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectsRemoteImplTest {

    private val mapper = mock<ProjectsResponseModelMapper>()
    private val service = mock<GitTrendingService>()
    private val remote = ProjectsRemoteImpl(service, mapper)

    @Test
    fun getProjectsCompletes(){
        stubGitTrendingServiceSearchRepositories(Flowable.just(ProjectDataFactory.makeProjectsResponse()))
        stubProjectsResponseModelMapperMapFromModel(any(), ProjectDataFactory.makeProjectEntity())

        val testObserver = remote.getProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsCallsServer(){
        stubGitTrendingServiceSearchRepositories(Flowable.just(ProjectDataFactory.makeProjectsResponse()))
        stubProjectsResponseModelMapperMapFromModel(any(), ProjectDataFactory.makeProjectEntity())
        remote.getProjects().test()

        verify(service).searchRepositories(any(), any(), any())
    }

    @Test
    fun getProjectsReturnsData(){
        val response = ProjectDataFactory.makeProjectsResponse()
        stubGitTrendingServiceSearchRepositories(Flowable.just(response))

        val entities = mutableListOf<ProjectEntity>()

        response.items.forEach {
            val entity = ProjectDataFactory.makeProjectEntity()
            entities.add(entity)

            stubProjectsResponseModelMapperMapFromModel(it, entity)
        }

        val testObserver = remote.getProjects().test()
        testObserver.assertValue(entities)
    }

    @Test
    fun getProjectsCallsServerWithCorrectParameters(){
        stubGitTrendingServiceSearchRepositories(Flowable.just(ProjectDataFactory.makeProjectsResponse()))
        stubProjectsResponseModelMapperMapFromModel(any(), ProjectDataFactory.makeProjectEntity())
        remote.getProjects().test()

        verify(service).searchRepositories("language:kotlin", "stars", "desc")
    }

    private fun stubGitTrendingServiceSearchRepositories(flowable: Flowable<ProjectsResponseModel>){
        whenever(service.searchRepositories(any(), any(), any()))
            .thenReturn(flowable)
    }

    private fun stubProjectsResponseModelMapperMapFromModel(model: ProjectModel, entity: ProjectEntity){
        whenever(mapper.mapFromModel(model))
            .thenReturn(entity)
    }

}