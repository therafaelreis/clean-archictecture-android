package com.therafaelreis.cache.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.therafaelreis.cache.db.ProjectsDatabase
import com.therafaelreis.cache.test.factory.ProjectDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CachedProjectsDAOTest {

    @get:Rule
    //allows to retrieve data from our flowables instantly
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application.applicationContext,
        ProjectsDatabase::class.java)
        .allowMainThreadQueries()
        .build()

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getProjectsReturnsData() {
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDAO().insertProjects(listOf(project))

        val testObserver = database.cachedProjectsDAO().getProjects().test()
        testObserver.assertValue(listOf(project))
    }

    @Test
    fun deleteProjectsClearsData() {
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDAO().insertProjects(listOf(project))
        database.cachedProjectsDAO().deleteProjects()

        val testObserver = database.cachedProjectsDAO().getProjects().test()
        testObserver.assertValue(emptyList())
    }

    @Test
    fun getBookmarkedProjectsReturnsData() {
        val project = ProjectDataFactory.makeCachedProject()
        val bookmarkedProject = ProjectDataFactory.makeBookmarkedCachedProject()
        database.cachedProjectsDAO().insertProjects(listOf(project, bookmarkedProject))

        val testObserver = database.cachedProjectsDAO().getBookmarkedProjects().test()
        testObserver.assertValue(listOf(bookmarkedProject))
    }

    @Test
    fun setProjectAsBookmarkedSavesData() {
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDAO().insertProjects(listOf(project))
        database.cachedProjectsDAO().setBookmarkStatus(true, project.id)
        project.isBookmarked = true

        val testObserver = database.cachedProjectsDAO().getBookmarkedProjects().test()
        testObserver.assertValue(listOf(project))
    }

    @Test
    fun setProjectAsNotBookmarkedSavesData() {
        val project = ProjectDataFactory.makeBookmarkedCachedProject()
        database.cachedProjectsDAO().insertProjects(listOf(project))
        database.cachedProjectsDAO().setBookmarkStatus(false, project.id)
        project.isBookmarked = false

        val testObserver = database.cachedProjectsDAO().getBookmarkedProjects().test()
        testObserver.assertValue(emptyList())
    }
}