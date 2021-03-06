package com.jgiovannysn.movies.data.dao

import androidx.room.*

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(t: List<T>)

    @Update
    fun update(t: T)

    @Delete
    fun delete(t: T)
}