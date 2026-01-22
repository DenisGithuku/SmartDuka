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
package com.githukudenis.smartduka.data.datasource.local

import com.githukudenis.smartduka.database.dao.UserDao
import com.githukudenis.smartduka.database.entity.UserEntity
import com.githukudenis.smartduka.database.relation.UserWithShops
import kotlinx.coroutines.flow.Flow

// Local datasource for user data
interface UserLocalDataSource {
    suspend fun insertUser(user: UserEntity)

    suspend fun updateUser(user: UserEntity)

    suspend fun archiveUser(userId: String)

    suspend fun getUserById(userId: String): UserEntity?

    fun observeUserWithShops(userId: String): Flow<UserWithShops>
}

class UserLocalDataSourceImpl(private val userDao: UserDao) : UserLocalDataSource {
    override suspend fun insertUser(user: UserEntity) {
        userDao.insert(user)
    }

    override suspend fun updateUser(user: UserEntity) {
        userDao.update(user)
    }

    override suspend fun archiveUser(userId: String) {
        userDao.archive(userId)
    }

    override suspend fun getUserById(userId: String): UserEntity? {
        return userDao.getById(userId)
    }

    override fun observeUserWithShops(userId: String): Flow<UserWithShops> {
        return userDao.observeUserWithShops(userId)
    }
}
