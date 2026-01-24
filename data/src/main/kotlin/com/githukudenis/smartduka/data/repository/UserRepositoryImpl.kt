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
package com.githukudenis.smartduka.data.repository

import com.githukudenis.smartduka.data.datasource.local.UserLocalDataSource
import com.githukudenis.smartduka.data.mapper.mapper.toDomain
import com.githukudenis.smartduka.data.mapper.mapper.toEntity
import com.githukudenis.smartduka.domain.model.User
import com.githukudenis.smartduka.domain.model.UserWithShops
import com.githukudenis.smartduka.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class UserRepositoryImpl(private val userLocalDataSource: UserLocalDataSource) : UserRepository {

    override suspend fun insertUser(user: User) {
        val now = System.currentTimeMillis()
        val dbUser = user.toEntity().copy(createdAt = now, updatedAt = now)
        userLocalDataSource.insertUser(dbUser)
    }

    override suspend fun updateProfile(user: User) {
        val now = System.currentTimeMillis()
        val dbUser = user.toEntity().copy(updatedAt = now)
        userLocalDataSource.updateUser(dbUser)
    }

    override suspend fun getUserById(userId: String): User? {
        return userLocalDataSource.getUserById(userId)?.toDomain()
    }

    override fun observeUserWithShops(userId: String): Flow<UserWithShops> {
        return userLocalDataSource.observeUserWithShops(userId).mapLatest { it.toDomain() }
    }
}
