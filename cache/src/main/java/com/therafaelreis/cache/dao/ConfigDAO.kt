package com.therafaelreis.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.therafaelreis.cache.db.ConfigConstants.QUERY_CONFIG
import com.therafaelreis.cache.model.Config
import io.reactivex.Flowable

@Dao
abstract class ConfigDAO {

    @Query(QUERY_CONFIG)
    abstract fun getConfig(): Flowable<Config>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertConfig(config: Config)

}