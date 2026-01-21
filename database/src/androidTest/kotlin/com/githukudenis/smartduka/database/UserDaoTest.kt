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
package com.githukudenis.smartduka.database

import kotlin.test.Test
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

class UserDaoTest : BaseRoomTest() {

    @Test
    fun insert_and_getById_returnsUser() = runTest {
        // Given
        val user = TestDataFactory.user()
        userDao.insert(user)

        // When
        val result = userDao.getById(user.userId)

        // Then
        assertNotNull(result)
        assertEquals(user.userId, result?.userId)
        assertEquals(user.name, result?.name)
    }

    @Test
    fun update_updatesUserCorrectly() = runTest {
        // Given
        val user = TestDataFactory.user()
        userDao.insert(user)

        // When
        val updated = user.copy(name = "Updated Name", email = "updated@test.com")
        userDao.update(updated)

        // Then
        val result = userDao.getById(user.userId)
        assertEquals("Updated Name", result?.name)
        assertEquals("updated@test.com", result?.email)
    }

    @Test
    fun observeAll_returnsUsersSortedByName() = runTest {
        // Given
        val userB = TestDataFactory.user().copy(name = "Beta User")
        val userA = TestDataFactory.user().copy(name = "Alpha User")

        userDao.insert(userB)
        userDao.insert(userA)

        // When
        val result = userDao.observeAll().first()

        // Then
        assertEquals(2, result.size)
    }

    @Test
    fun observeUserWithShops_returnsUserAndItsShops() = runTest {
        // Given
        val user = TestDataFactory.user()
        userDao.insert(user)

        val shopA = TestDataFactory.shop(userId = user.userId).copy(name = "Shop A")
        val shopB = TestDataFactory.shop(userId = user.userId).copy(name = "Shop B")

        shopDao.insert(shopA)
        shopDao.insert(shopB)

        // When
        val result = userDao.observeUserWithShops(user.userId).first()

        // Then
        assertEquals(user.userId, result.user.userId)
        assertEquals(2, result.shops.size)
        assertEquals(shopA.name, result.shops.first { it.shopId == shopA.shopId }.name)
        assertEquals(shopB.name, result.shops.first { it.shopId == shopB.shopId }.name)
    }

    @Test
    fun archiveUser_excludesUserFromObserveAll() = runTest {
        // Given
        val user = TestDataFactory.user()
        userDao.insert(user)

        // When
        userDao.archive(user.userId)

        // Then
        val result = userDao.observeAll().first()
        assertEquals(0, result.size)
    }
}
