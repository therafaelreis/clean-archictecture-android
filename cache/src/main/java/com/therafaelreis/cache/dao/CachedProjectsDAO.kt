package com.therafaelreis.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.therafaelreis.cache.db.ProjectConstants.DELETE_PROJECTS
import com.therafaelreis.cache.db.ProjectConstants.QUERY_BOOKMARKED_PROJECTS
import com.therafaelreis.cache.db.ProjectConstants.QUERY_PROJECTS
import com.therafaelreis.cache.db.ProjectConstants.QUERY_UPDATE_BOOKMARK_STATUS
import com.therafaelreis.cache.model.CachedProject
import io.reactivex.Flowable

@Dao
abstract class CachedProjectsDAO {

    @Query(QUERY_PROJECTS)
    @JvmSuppressWildcards
    abstract fun getProjects(): Flowable<List<CachedProject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertProjects(projects: List<CachedProject>)

    @Query(DELETE_PROJECTS)
    abstract fun deleteProjects()

    @Query(QUERY_BOOKMARKED_PROJECTS)
    abstract fun getBookmarkedProjects(): Flowable<List<CachedProject>>

    @Query(QUERY_UPDATE_BOOKMARK_STATUS)
    abstract fun setBookmarkStatus(isBookmarked: Boolean,
                                   projectId: String)

}