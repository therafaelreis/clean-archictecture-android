package com.therafaelreis.data

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.therafaelreis.data.mapper.ProjectMapper
import com.therafaelreis.data.model.ProjectEntity
import com.therafaelreis.data.repository.ProjectsCache
import com.therafaelreis.data.repository.ProjectsDataStore
import com.therafaelreis.data.store.ProjectDataStoreFactory
import com.therafaelreis.data.test.factory.DataFactory
import com.therafaelreis.data.test.factory.ProjectFactory
import com.therafaelreis.domain.model.Project
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectsDataRepositoryTest {

    private val mapper = mock<ProjectMapper>()
    private val store = mock<ProjectsDataStore>()
    private val factory = mock<ProjectDataStoreFactory>()
    private val cache = mock<ProjectsCache>()
    private val repository = ProjectsDataRepository(mapper, cache, factory)

    @Before
    fun setup(){
        stubFactoryGetDataStore()
        stubFactoryGetCacheDataStore()
        stubIsCacheExpired(Single.just(false))
        stubAreProjectsCached(Single.just(false))
        stubSaveProjects(Completable.complete())
    }

    @Test
    fun getProjectsCompletes(){
        stubGetProjects(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))
        stubMapper(ProjectFactory.makeProject(), any())

        val testObserver = repository.getProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsReturnsData(){
        val entity = ProjectFactory.makeProjectEntity()
        val model = ProjectFactory.makeProject()
        stubGetProjects(Flowable.just(listOf(entity)))
        stubMapper(model, entity)

        val testObserver = repository.getProjects().test()

        testObserver.assertValue(listOf(model))
    }


    @Test
    fun getBookmarkedProjectsComplete(){
        stubGetBookmarkedProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        stubMapper(ProjectFactory.makeProject(), any())

        val testObserver = repository.getBookmarkedProjects().test()

        testObserver.assertComplete()
    }

    @Test
    fun getBookmarkedProjectsReturnsData(){
        val entity = ProjectFactory.makeProjectEntity()
        val model = ProjectFactory.makeProject()
        stubGetBookmarkedProjects(Observable.just(listOf(entity)))
        stubMapper(model, entity)

        val testObserver = repository.getBookmarkedProjects().test()

        testObserver.assertValue(listOf(model))
    }

    @Test
    fun bookmarkProjectCompletes(){
        stubBookmarkProject(Completable.complete())
        val testObserver = repository.bookmarkProject(DataFactory.randomString()).test()

        testObserver.assertComplete()
    }

    @Test
    fun unbookmarkProjectCompletes() {
        stubUnBookmarkProject(Completable.complete())

        val testObserver = repository.unbookmarkProject(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    private fun stubBookmarkProject(completable: Completable){
        whenever(store.setProjectAsBookmarked(any()))
            .thenReturn(completable)
    }

    private fun stubUnBookmarkProject(completable: Completable) {
        whenever(store.setProjectAsNotBookmarked(any()))
            .thenReturn(completable)
    }

    private fun stubIsCacheExpired(single: Single<Boolean>){
        whenever(cache.isProjectsCachedExpired())
            .thenReturn(single)
    }

    private fun stubAreProjectsCached(single: Single<Boolean>){
        whenever(cache.areProjectsCached())
            .thenReturn(single)
    }

    private fun stubFactoryGetCacheDataStore(){
        whenever(factory.getCacheDataStore())
            .thenReturn(store)
    }

    private fun stubSaveProjects(completable: Completable){
        whenever(store.saveProjects(any()))
            .thenReturn(completable)
    }

    private fun stubMapper(model: Project, entity: ProjectEntity){
        whenever(mapper.mapFromEntity(entity))
            .thenReturn(model)
    }

    private fun stubGetProjects(flowable: Flowable<List<ProjectEntity>>){
        whenever(store.getProjects())
            .thenReturn(flowable)
    }

    private fun stubGetBookmarkedProjects(observable: Observable<List<ProjectEntity>>){
        whenever(store.getBookmarkedProjects())
            .thenReturn(observable)
    }

    private fun stubFactoryGetDataStore(){
        whenever(factory.getDataStore(any(), any()))
            .thenReturn(store)
    }
}