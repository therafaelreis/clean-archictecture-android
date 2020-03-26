package com.therafaelreis.data.store

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.therafaelreis.data.model.ProjectEntity
import com.therafaelreis.data.repository.ProjectsRemote
import com.therafaelreis.data.test.factory.DataFactory
import com.therafaelreis.data.test.factory.ProjectFactory
import io.reactivex.Flowable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectsRemoteDataStoreTest {

    private val remote = mock<ProjectsRemote>()
    private val store = ProjectsRemoteDataStore(remote)


    @Test
    fun getProjectsCompletes(){
        stubRemoteGetProjects(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))

        val testObserver = store.getProjects().test()

        testObserver.assertComplete()
    }

    @Test
    fun getProjectsDataReturns(){
        val entity = listOf(ProjectFactory.makeProjectEntity())
        stubRemoteGetProjects(Flowable.just(entity))

        val testObserver = store.getProjects().test()

        testObserver.assertValue(entity)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveProjectsThrowsException(){
        store.saveProjects(listOf()).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearProjectsThrowsException(){
        store.clearProjects().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setProjectAsNotBookmarkedThrowsException(){
        store.setProjectAsNotBookmarked(DataFactory.randomString()).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setProjectAsBookmarkedThrowsException(){
        store.setProjectAsBookmarked(DataFactory.randomString()).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun getBookmarkedProjectsThrowsException(){
        store.getBookmarkedProjects().test()
    }

    private fun stubRemoteGetProjects(flowable: Flowable<List<ProjectEntity>>){
        whenever(remote.getProjects())
            .thenReturn(flowable)
    }
}