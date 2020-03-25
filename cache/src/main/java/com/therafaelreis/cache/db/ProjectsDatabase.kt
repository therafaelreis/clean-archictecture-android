package com.therafaelreis.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.therafaelreis.cache.dao.CachedProjectsDAO
import com.therafaelreis.cache.dao.ConfigDAO
import com.therafaelreis.cache.model.CachedProject
import com.therafaelreis.cache.model.Config
import javax.inject.Inject

@Database(entities = [CachedProject::class,
    Config::class], version = 1)
abstract class ProjectsDatabase @Inject constructor(): RoomDatabase() {

    abstract fun cachedProjectsDAO(): CachedProjectsDAO

    abstract fun configDAO(): ConfigDAO

    companion object {
        @Volatile
        private var INSTANCE: ProjectsDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): ProjectsDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                            ProjectsDatabase::class.java, "projects.db")
                            .build()
                    }
                    return INSTANCE as ProjectsDatabase
                }
            }
            return INSTANCE as ProjectsDatabase
        }
    }

}