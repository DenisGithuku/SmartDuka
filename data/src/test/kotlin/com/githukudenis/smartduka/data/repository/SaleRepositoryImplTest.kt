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
import com.githukudenis.smartduka.data.datasource.local.SaleLocalDataSource
import com.githukudenis.smartduka.database.entity.SaleEntity
import com.githukudenis.smartduka.database.entity.SaleItemEntity
import com.githukudenis.smartduka.database.relation.SaleWithItemsEntity
import com.githukudenis.smartduka.domain.model.PaymentStatus
import com.githukudenis.smartduka.domain.model.Sale
import com.githukudenis.smartduka.domain.model.SaleItem
import com.githukudenis.smartduka.domain.repository.SaleRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SaleRepositoryImplTest {
    private val saleLocalDataSource: SaleLocalDataSource = mockk(relaxed = true)
    private lateinit var repository: SaleRepository

    @Before
    fun setup() {
        repository = SaleRepositoryImpl(saleLocalDataSource)
    }

    @Test
    fun `insertSale calls local datasource with mapped entity`() = runTest {
        val sale = TestDataFactory.sale()

        coEvery { saleLocalDataSource.insertSale(any()) } just Runs

        repository.insertSale(sale)

        coVerify {
            saleLocalDataSource.insertSale(
                match {
                    it.saleId == sale.saleId && it.shopId == sale.shopId && it.totalAmount == sale.total
                }
            )
        }
    }

    // ---------------- Update Sale ----------------

    @Test
    fun `updateSale calls local datasource with mapped entity`() = runTest {
        val sale = TestDataFactory.sale()

        coEvery { saleLocalDataSource.updateSale(any()) } just Runs

        repository.updateSale(sale)

        coVerify { saleLocalDataSource.updateSale(match { it.saleId == sale.saleId }) }
    }

    // ---------------- Delete Sale ----------------

    @Test
    fun `deleteSale delegates to local datasource`() = runTest {
        val saleId = "sale-1"

        coEvery { saleLocalDataSource.deleteSale(saleId) } just Runs

        repository.deleteSale(saleId)

        coVerify { saleLocalDataSource.deleteSale(saleId) }
    }

    // ---------------- Get By Id ----------------

    @Test
    fun `getSaleById returns mapped domain model`() = runTest {
        val entity = TestDataFactory.saleEntity()

        coEvery { saleLocalDataSource.getSaleById(entity.saleId) } returns entity

        val result = repository.getSaleById(entity.saleId)

        assertNotNull(result)
        assertEquals(entity.saleId, result!!.saleId)
        assertEquals(entity.totalAmount, result.total, 0.0001)
    }

    @Test
    fun `getSaleById returns null when not found`() = runTest {
        coEvery { saleLocalDataSource.getSaleById(any()) } returns null

        val result = repository.getSaleById("missing")

        assertNull(result)
    }

    // ---------------- Observe Sales ----------------

    @Test
    fun `observeSalesForShop maps entities to domain`() = runTest {
        val entities = listOf(TestDataFactory.saleEntity("1"), TestDataFactory.saleEntity("2"))

        val flow = flowOf(entities)

        every { saleLocalDataSource.observeSalesForShop("shop-1") } returns flow

        repository.observeSalesForShop("shop-1").test {
            val result = awaitItem()

            assertEquals(2, result.size)
            assertEquals("1", result[0].saleId)

            cancelAndIgnoreRemainingEvents()
        }
    }

    // ---------------- Observe Sale With Items ----------------

    @Test
    fun `observeSaleWithItems maps relation to domain`() = runTest {
        val relation = TestDataFactory.saleWithItemsEntity()

        every { saleLocalDataSource.observeSaleWithItems("sale-1") } returns flowOf(relation)

        repository.observeSaleWithItems("sale-1").test {
            val result = awaitItem()

            assertEquals(relation.sale.saleId, result.sale.saleId)
            assertEquals(1, result.saleItems.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getSalesBetween maps entities to domain`() = runTest {
        val entities =
            listOf(
                TestDataFactory.saleEntity("1").copy(date = 1L),
                TestDataFactory.saleEntity("2").copy(date = 2L)
            )
        val start = 1L
        val end = 3L

        val flow = flowOf(entities)

        every { saleLocalDataSource.getSalesBetween(start, end) } returns flow

        repository.getSalesBetween(start, end).test {
            val result = awaitItem()

            assertEquals(2, result.size)
            assertEquals("1", result[0].saleId)

            cancelAndIgnoreRemainingEvents()
        }

        // ---------------- Insert Sale Items ----------------

        @Test
        fun `insertSaleItems maps items to entities`() = runTest {
            val items = listOf(TestDataFactory.saleItem(), TestDataFactory.saleItem())

            coEvery { saleLocalDataSource.insertSaleItems(any()) } just Runs

            repository.insertSaleItems(items)

            coVerify { saleLocalDataSource.insertSaleItems(match { it.size == items.size }) }
        }

        // ---------------- Remove Sale Item ----------------

        @Test
        fun `removeSaleItem delegates to local datasource`() = runTest {
            coEvery { saleLocalDataSource.removeSaleItem("sale-1", "prod-1") } just Runs

            repository.removeSaleItem("sale-1", "prod-1")

            coVerify { saleLocalDataSource.removeSaleItem("sale-1", "prod-1") }
        }

        // ---------------- Delete Items For Sale ----------------

        @Test
        fun `deleteItemsForSale delegates to local datasource`() = runTest {
            coEvery { saleLocalDataSource.deleteItemsForSale("sale-1") } just Runs

            repository.deleteItemsForSale("sale-1")

            coVerify { saleLocalDataSource.deleteItemsForSale("sale-1") }
        }

        // ---------------- Get Items For Sale ----------------

        @Test
        fun `getItemsForSale maps entities to domain`() = runTest {
            val entities = listOf(TestDataFactory.saleItemEntity(), TestDataFactory.saleItemEntity())

            coEvery { saleLocalDataSource.getItemsForSale("sale-1") } returns entities

            val result = repository.getItemsForSale("sale-1")

            assertEquals(2, result.size)
            assertEquals(entities[0].saleItemId, result[0].saleItemId)
        }
    }

    private object TestDataFactory {

        fun sale(id: String = "sale-1") =
            Sale(
                saleId = id,
                shopId = "shop-1",
                total = 1000.0,
                date = System.currentTimeMillis(),
                paymentStatus = PaymentStatus.PAID
            )

        fun saleEntity(id: String = "sale-1") =
            SaleEntity(
                saleId = id,
                shopId = "shop-1",
                totalAmount = 1000.0,
                paymentStatus = PaymentStatus.PAID,
                date = System.currentTimeMillis(),
                createdAt = 1L,
                updatedAt = 1L
            )

        fun saleItem() =
            SaleItem(
                saleItemId = "item-1",
                saleId = "sale-1",
                productId = "prod-1",
                quantity = 2,
                unitPrice = 100.0,
                totalPrice = 200.0
            )

        fun saleItemEntity() =
            SaleItemEntity(
                saleItemId = "item-1",
                saleId = "sale-1",
                productId = "prod-1",
                quantity = 2,
                unitPrice = 100.0,
                totalPrice = 200.0
            )

        fun saleWithItemsEntity() =
            SaleWithItemsEntity(sale = saleEntity(), items = listOf(saleItemEntity()))
    }
}
