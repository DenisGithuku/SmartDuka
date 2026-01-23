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

import app.cash.turbine.test
import com.githukudenis.smartduka.data.datasource.local.ProductLocalDataSource
import com.githukudenis.smartduka.database.entity.ProductEntity
import com.githukudenis.smartduka.database.relation.ProductWithInventoryMovementsEntity
import com.githukudenis.smartduka.domain.model.Product
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductRepositoryImplTest {

    private val productLocalDataSource: ProductLocalDataSource = mockk(relaxed = true)

    private lateinit var repository: ProductRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = ProductRepositoryImpl(productLocalDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun productEntity(id: String = "product-1", shopId: String = "shop-1") =
        ProductEntity(
            productId = id,
            shopId = shopId,
            name = "Sugar",
            description = "Test",
            price = 100.0,
            costPrice = 50.0,
            stockQuantity = 10,
            lowStockThreshold = 5,
            archived = false
        )

    private fun product(id: String = "product-1", shopId: String = "shop-1") =
        Product(
            productId = id,
            shopId = shopId,
            name = "Sugar",
            description = "Test",
            price = 100.0,
            stockQuantity = 10,
            lowStockThreshold = 5,
            costPrice = 50.0,
            archived = false
        )

    @Test
    fun insertProduct_callsLocalDataSource() = runTest {
        val product = product()

        repository.insertProduct(product)

        coVerify {
            productLocalDataSource.insertProduct(
                match { entity ->
                    entity.productId == product.productId
                    entity.name == product.name
                    entity.description == product.description
                    entity.price == product.price
                    entity.stockQuantity == product.stockQuantity
                    entity.lowStockThreshold == product.lowStockThreshold
                    entity.costPrice == product.costPrice
                }
            )
        }
    }

    @Test
    fun updateProduct_callsLocalDataSource() = runTest {
        val product = product()

        repository.updateProduct(product)

        coVerify {
            productLocalDataSource.updateProduct(
                match { productEntity ->
                    productEntity.productId == product.productId
                    productEntity.name == product.name
                    productEntity.description == product.description
                    productEntity.price == product.price
                    productEntity.stockQuantity == product.stockQuantity
                    productEntity.lowStockThreshold == product.lowStockThreshold
                    productEntity.costPrice == product.costPrice
                }
            )
        }
    }

    @Test
    fun archiveProduct_callsLocalDataSource() = runTest {
        val productId = "product-1"

        repository.archiveProduct(productId)

        coVerify { productLocalDataSource.archiveProduct(productId) }
    }

    @Test
    fun getProductById_returnsMappedDomain() = runTest {
        val entity = productEntity()

        coEvery { productLocalDataSource.getProductById("product-1") } returns entity

        val result = repository.getProductById("product-1")

        assertNotNull(result)
        assertEquals(entity.productId, result!!.productId)
        assertEquals(entity.name, result.name)
    }

    @Test
    fun getProductById_returnsNull_whenNotFound() = runTest {
        coEvery { productLocalDataSource.getProductById(any()) } returns null

        val result = repository.getProductById("unknown")

        assertNull(result)
    }

    @Test
    fun observeByShop_mapsEntitiesToDomain() = runTest {
        val entities = listOf(productEntity("p1"), productEntity("p2"))

        coEvery { productLocalDataSource.observeByShop("shop-1") } returns flowOf(entities)

        repository.observeByShop("shop-1").test {
            val result = awaitItem()

            assertEquals(2, result.size)
            assertEquals("p1", result[0].productId)
            assertEquals("p2", result[1].productId)

            awaitComplete()
        }
    }

    @Test
    fun observeLowStock_mapsEntitiesToDomain() = runTest {
        val entities = listOf(productEntity("p1"))

        coEvery { productLocalDataSource.observeLowStock("shop-1") } returns flowOf(entities)

        repository.observeLowStock("shop-1").test {
            val result = awaitItem()

            assertEquals(1, result.size)
            assertEquals("p1", result[0].productId)

            awaitComplete()
        }
    }

    @Test
    fun observeProductWithInventoryMovements_mapsRelationToDomain() = runTest {
        val entity =
            ProductWithInventoryMovementsEntity(product = productEntity(), movements = emptyList())

        coEvery { productLocalDataSource.observeProductWithInventoryMovements("product-1") } returns
            flowOf(entity)

        repository.observeProductWithInventoryMovements("product-1").test {
            val result = awaitItem()

            assertEquals("product-1", result.product.productId)
            assertTrue(result.inventoryMovements.isEmpty())

            awaitComplete()
        }
    }
}
