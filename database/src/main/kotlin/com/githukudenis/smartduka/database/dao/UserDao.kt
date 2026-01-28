/*
* Copyright 2026 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.githukudenis.smartduka.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.githukudenis.smartduka.database.entity.UserEntity
import com.githukudenis.smartduka.database.relation.UserWithShopsEntity
import kotlinx.coroutines.flow.Flow

// User operations manage metadata only.
// Authentication is handled by Supabase; local DB is offline cache.

@Dao
interface UserDao {
    @Insert suspend fun insert(user: UserEntity)

    @Update suspend fun update(user: UserEntity)

    @Query("UPDATE users SET archived = 1 WHERE user_id = :userId")
    suspend fun archive(userId: String)

    @Query("SELECT * FROM users LIMIT 1") suspend fun getUser(): UserEntity

    @Transaction
    @Query("SELECT * FROM users WHERE user_id = :userId")
    fun observeUserWithShops(userId: String): Flow<UserWithShopsEntity>
}
