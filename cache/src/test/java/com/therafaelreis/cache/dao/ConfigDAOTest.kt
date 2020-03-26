package com.therafaelreis.cache.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.therafaelreis.cache.db.ProjectsDatabase
import com.therafaelreis.cache.test.factory.ConfigDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ConfigDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application.applicationContext,
        ProjectsDatabase::class.java)
        .allowMainThreadQueries()
        .build()

    @After
    fun clearDb() {
        database.close()
    }

    @Test
    fun saveConfigurationSavesData() {
        val config = ConfigDataFactory.makeCachedConfig()
        database.configDAO().insertConfig(config)

        val testObserver = database.configDAO().getConfig().test()
        testObserver.assertValue(config)
    }

}