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
import app.cash.turbine.test
import com.githukudenis.smartduka.data.datasource.local.UserLocalDataSource
import com.githukudenis.smartduka.data.repository.UserRepositoryImpl
import com.githukudenis.smartduka.database.entity.ShopEntity
import com.githukudenis.smartduka.database.entity.UserEntity
import com.githukudenis.smartduka.database.relation.UserWithShopsEntity
import com.githukudenis.smartduka.domain.model.User
import com.githukudenis.smartduka.domain.repository.UserRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any

class UserRepositoryImplTest {
    private val userLocalDataSource: UserLocalDataSource = mockk(relaxed = true)
    private lateinit var repository: UserRepository

    @Before
    fun setup() {
        repository = UserRepositoryImpl(userLocalDataSource)
    }

    @Test
    fun `insertUser maps to domain and delegates to datasource`() = runTest {
        val user = user()

        coEvery { userLocalDataSource.insertUser(any()) } just Runs
        repository.insertUser(user)

        coVerify {
            userLocalDataSource.insertUser(
                match {
                    user.userId == it.userId
                    user.archived == it.archived
                    user.name == it.name
                }
            )
        }
    }

    @Test
    fun `updateUser maps to domain and delegates to datasource`() = runTest {
        val user = user()

        coEvery { userLocalDataSource.updateUser(any()) } just Runs
        repository.updateProfile(user)

        coVerify {
            userLocalDataSource.updateUser(
                match {
                    user.userId == it.userId
                    user.archived == it.archived
                    user.name == it.name
                    it.createdAt != null
                    it.updatedAt != null
                }
            )
        }
    }

    @Test
    fun `getUserById returns domain object`() = runTest {
        val entity = userEntity()

        coEvery { userLocalDataSource.getUserById(entity.userId) } returns entity

        val result = repository.getUserById(entity.userId)

        assertNotNull(result)
        assertEquals(entity.userId, result!!.userId)
        assertEquals(entity.name, result.name)
    }

    @Test
    fun `observeUserWithShops maps entities to domain objects`() = runTest {
        val relation = userWithShopsEntity()

        every { userLocalDataSource.observeUserWithShops("shop-1") } returns flowOf(relation)

        repository.observeUserWithShops("shop-1").test {
            val result = awaitItem()

            assertEquals(relation.shops.size, result.shops.size)
            assertEquals(relation.user.userId, result.user.userId)

            cancelAndIgnoreRemainingEvents()
        }
    }

    // To user for test preparation
    fun user() = User(userId = "user-id", name = "Test user", archived = false)

    private fun userEntity(userId: String = "user-id") =
        UserEntity(userId = userId, name = "Test user", archived = false)

    private fun shopEntity() =
        ShopEntity(
            shopId = "shop-id",
            userId = "user-id",
            name = "Test shop",
            location = "Test location",
            archived = false
        )

    private fun userWithShopsEntity() = UserWithShopsEntity(userEntity(), listOf(shopEntity()))
}
