package com.therafaelreis.data.store

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.therafaelreis.data.model.ProjectEntity
import com.therafaelreis.data.repository.ProjectsCache
import com.therafaelreis.data.test.factory.DataFactory
import com.therafaelreis.data.test.factory.ProjectFactory
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectsCacheDataStoreTest {

    private val cache = mock<ProjectsCache>()
    private val store = ProjectsCacheDataStore(cache)

    @Test
    fun getProjectsCompletes() {
        stubProjectsCacheGetProjects(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))
        val testObserver = store.getProjects().test()
        testObserver.assertComplete()
    }


    @Test
    fun getProjectsReturnsData() {
        val entity = listOf(ProjectFactory.makeProjectEntity())
        stubProjectsCacheGetProjects(Flowable.just(entity))

        val testObserver = store.getProjects().test()
        testObserver.assertValue(entity)
    }

    @Test
    fun getProjectsCallCacheSource() {
        stubProjectsCacheGetProjects(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))
        store.getProjects().test()
        verify(cache).getProjects()
    }

    @Test
    fun saveProjectsCompletes() {
        stubSaveProjectsComplete(Completable.complete())
        stubProjectsCacheSetLastCacheTime(Completable.complete())

        val testObserver = store.saveProjects(listOf(ProjectFactory.makeProjectEntity())).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveProjectsCallsCache() {
        stubSaveProjectsComplete(Completable.complete())
        stubProjectsCacheSetLastCacheTime(Completable.complete())

        store.saveProjects(listOf(ProjectFactory.makeProjectEntity())).test()
        verify(cache).saveProjects(any())
    }

    @Test
    fun saveProjectsCallsCacheStore() {
        val entity = listOf(ProjectFactory.makeProjectEntity())
        stubSaveProjectsComplete(Completable.complete())
        stubProjectsCacheSetLastCacheTime(Completable.complete())

        store.saveProjects(entity).test()
        verify(cache).saveProjects(entity)
    }

    @Test
    fun clearProjectsCompletes() {
        stubProjectsClearProjects(Completable.complete())

        val testObserver = store.clearProjects().test()

        testObserver.assertComplete()
    }

    @Test
    fun clearProjectsCallsCacheStore() {
        stubProjectsClearProjects(Completable.complete())

        store.clearProjects().test()

        verify(cache).clearProjects()
    }

    @Test
    fun getBookmarkedProjectsCompletes() {
        stubProjectsCacheGetBookmarkedProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))

        val testObserver = store.getBookmarkedProjects().test()

        testObserver.assertComplete()
    }

    @Test
    fun getBookmarkedProjectsCallsCachedStore() {
        stubProjectsCacheGetBookmarkedProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))

        store.getBookmarkedProjects().test()

        verify(cache).getBookmarkedProjects()
    }

    @Test
    fun getBookmarkedProjectsReturnsData() {
        val entity = listOf(ProjectFactory.makeProjectEntity())
        stubProjectsCacheGetBookmarkedProjects(Observable.just(entity))

        val testObserver = store.getBookmarkedProjects().test()

        testObserver.assertValue(entity)
    }

    @Test
    fun setProjectAsBookmarkedCompletes(){
        stubProjectsCacheSetProjectAsBookmarked(Completable.complete())

        val testObserver = store.setProjectAsBookmarked(DataFactory.randomString()).test()

        testObserver.assertComplete()
    }

    @Test
    fun setProjectAsBookmarkedCallsCacheStore(){
        stubProjectsCacheSetProjectAsBookmarked(Completable.complete())

        store.setProjectAsBookmarked(DataFactory.randomString()).test()

        verify(cache).setProjectAsBookmarked(any())
    }

    @Test
    fun setProjectAsNotBookmarkedCompletes(){
        stubProjectsCacheNotSetProjectAsBookmarked(Completable.complete())

        val testObserver = store.setProjectAsNotBookmarked(DataFactory.randomString()).test()

        testObserver.assertComplete()
    }

    @Test
    fun setProjectAsNotBookmarkedCallsCacheStore(){
        stubProjectsCacheNotSetProjectAsBookmarked(Completable.complete())

        store.setProjectAsNotBookmarked(DataFactory.randomString()).test()

        verify(cache).setProjectAsNotBookmarked(any())
    }

    private fun stubSaveProjectsComplete(completable: Completable) {
        whenever(cache.saveProjects(any()))
            .thenReturn(completable)
    }

    private fun stubProjectsCacheSetLastCacheTime(completable: Completable) {
        whenever(cache.setLastCacheTime(any()))
            .thenReturn(completable)
    }

    private fun stubProjectsCacheGetProjects(flowable: Flowable<List<ProjectEntity>>) {
        whenever(cache.getProjects())
            .thenReturn(flowable)
    }

    private fun stubProjectsClearProjects(completable: Completable) {
        whenever(cache.clearProjects())
            .thenReturn(completable)
    }

    private fun stubProjectsCacheGetBookmarkedProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(cache.getBookmarkedProjects())
            .thenReturn(observable)
    }

    private fun stubProjectsCacheSetProjectAsBookmarked(completable: Completable){
        whenever(cache.setProjectAsBookmarked(any()))
            .thenReturn(completable)
    }

    private fun stubProjectsCacheNotSetProjectAsBookmarked(completable: Completable){
        whenever(cache.setProjectAsNotBookmarked(any()))
            .thenReturn(completable)
    }

}