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
import com.githukudenis.smartduka.data.datasource.local.ShopLocalDataSource
import com.githukudenis.smartduka.database.entity.ProductEntity
import com.githukudenis.smartduka.database.entity.SaleEntity
import com.githukudenis.smartduka.database.entity.ShopEntity
import com.githukudenis.smartduka.database.entity.SupplierEntity
import com.githukudenis.smartduka.database.relation.ShopWithProductsEntity
import com.githukudenis.smartduka.database.relation.ShopWithSalesEntity
import com.githukudenis.smartduka.database.relation.ShopWithSuppliersEntity
import com.githukudenis.smartduka.domain.model.PaymentStatus
import com.githukudenis.smartduka.domain.model.Shop
import com.githukudenis.smartduka.domain.repository.ShopRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ShopRepositoryImplTest {
    private val shopLocalDataSource: ShopLocalDataSource = mockk(relaxed = true)
    private lateinit var repository: ShopRepository

    @Before
    fun setUp() {
        repository = ShopRepositoryImpl(shopLocalDataSource)
    }

    @Test
    fun `insertShop maps domain to entity and delegates to datasource`() = runTest {
        val shop = TestDataFactory.shop()

        coEvery { shopLocalDataSource.insertShop(any()) } just Runs

        repository.insertShop(shop)

        coVerify {
            shopLocalDataSource.insertShop(
                match { it.shopId == shop.shopId && it.userId == shop.userId && it.name == shop.name }
            )
        }
    }

    // ---------------- Update ----------------

    @Test
    fun `updateShop maps domain to entity and delegates to datasource`() = runTest {
        val shop = TestDataFactory.shop()

        coEvery { shopLocalDataSource.updateShop(any()) } just Runs

        repository.updateShop(shop)

        coVerify { shopLocalDataSource.updateShop(match { it.shopId == shop.shopId }) }
    }

    // ---------------- Get By Id ----------------

    @Test
    fun `getShopById returns mapped domain object`() = runTest {
        val entity = TestDataFactory.shopEntity()

        coEvery { shopLocalDataSource.observeShop().first() } returns entity

        val result = repository.getShopById(entity.shopId)

        assertNotNull(result)
        assertEquals(entity.shopId, result!!.shopId)
        assertEquals(entity.name, result.name)
    }

    // ---------------- Observe With Products ----------------

    @Test
    fun `observeShopWithProducts maps relation to domain`() = runTest {
        val relation = TestDataFactory.shopWithProductsEntity()

        every { shopLocalDataSource.observeShopWithProducts("shop-1") } returns flowOf(relation)

        repository.observeShopWithProducts("shop-1").test {
            val result = awaitItem()

            assertEquals(relation.shop.shopId, result.shop.shopId)

            assertEquals(relation.products.size, result.products.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    // ---------------- Observe With Sales ----------------

    @Test
    fun `observeShopWithSales maps relation to domain`() = runTest {
        val relation = TestDataFactory.shopWithSalesEntity()

        every { shopLocalDataSource.observeShopWithSales("shop-1") } returns flowOf(relation)

        repository.observeShopWithSales("shop-1").test {
            val result = awaitItem()

            assertEquals(relation.shop.shopId, result.shop.shopId)

            assertEquals(relation.sales.size, result.sales.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    // ---------------- Observe With Suppliers ----------------

    @Test
    fun `observeShopWithSuppliers maps relation to domain`() = runTest {
        val relation = TestDataFactory.shopWithSuppliersEntity()

        every { shopLocalDataSource.observeShopWithSuppliers("shop-1") } returns flowOf(relation)

        repository.observeShopWithSuppliers("shop-1").test {
            val result = awaitItem()

            assertEquals(relation.shop.shopId, result.shop.shopId)

            assertEquals(relation.suppliers.size, result.suppliers.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    private object TestDataFactory {
        fun shop(id: String = "shop-1") =
            Shop(
                shopId = id,
                userId = "user-1",
                name = "Smart Duka",
                location = "Nairobi",
                archived = false
            )

        fun saleEntity(id: String = "sale-1") =
            SaleEntity(
                saleId = id,
                shopId = "shop-1",
                totalAmount = 1000.0,
                paymentStatus = PaymentStatus.PAID,
                date = System.currentTimeMillis()
            )

        fun shopEntity(id: String = "shop-1") =
            ShopEntity(
                shopId = id,
                userId = "user-1",
                name = "Smart Duka",
                location = "Nairobi",
                archived = false,
                createdAt = 1L,
                updatedAt = 1L
            )

        fun supplierEntity(id: String = "supplier-1") =
            SupplierEntity(
                supplierId = id,
                shopId = "shop-1",
                name = "Supplier 1",
                contact = "0700000000",
                archived = false
            )

        fun productEntity(id: String = "product-1") =
            ProductEntity(
                productId = id,
                shopId = "shop-1",
                name = "Sugar",
                description = "Test",
                price = 100.0,
                stockQuantity = 10,
                lowStockThreshold = 5,
                costPrice = 50.0,
                archived = false
            )

        fun shopWithProductsEntity() =
            ShopWithProductsEntity(shop = shopEntity(), products = listOf(productEntity()))

        fun shopWithSalesEntity() =
            ShopWithSalesEntity(shop = shopEntity(), sales = listOf(saleEntity()))

        fun shopWithSuppliersEntity() =
            ShopWithSuppliersEntity(shop = shopEntity(), suppliers = listOf(supplierEntity()))
    }
}
