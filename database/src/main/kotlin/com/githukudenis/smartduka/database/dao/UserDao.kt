package com.githukudenis.smartduka.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.githukudenis.smartduka.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

// User operations manage metadata only.
// Authentication is handled by Supabase; local DB is offline cache.

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity)

    @Query("UPDATE users SET archived = 1 WHERE user_id = :userId")
    suspend fun archive(userId: String)

    @Query("SELECT * FROM users WHERE user_id = :userId")
    suspend fun getById(userId: String): UserEntity?

    @Query("SELECT * FROM users WHERE archived = 0 ORDER BY name ASC")
    fun observeAll(): Flow<List<UserEntity>>
}