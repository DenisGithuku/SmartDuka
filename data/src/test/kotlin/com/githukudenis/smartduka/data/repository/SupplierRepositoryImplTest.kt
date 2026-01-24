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
import com.githukudenis.smartduka.data.datasource.local.SupplierLocalDataSource
import com.githukudenis.smartduka.database.entity.SupplierEntity
import com.githukudenis.smartduka.domain.model.Supplier
import com.githukudenis.smartduka.domain.repository.SupplierRepository
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
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class SupplierRepositoryImplTest {

    private val supplierLocalDataSource: SupplierLocalDataSource = mockk(relaxed = true)
    private lateinit var repository: SupplierRepository

    @Before
    fun setup() {
        repository = SupplierRepositoryImpl(supplierLocalDataSource)
    }

    // ---------------- Insert ----------------

    @Test
    fun `insertSupplier maps domain to entity and delegates to datasource`() = runTest {
        val supplier = TestDataFactory.supplier()

        coEvery { supplierLocalDataSource.insertSupplier(any()) } just Runs

        repository.insertSupplier(supplier)

        coVerify {
            supplierLocalDataSource.insertSupplier(
                match {
                    it.supplierId == supplier.supplierId &&
                        it.shopId == supplier.shopId &&
                        it.name == supplier.name &&
                        it.createdAt != null &&
                        it.updatedAt != null
                }
            )
        }
    }

    // ---------------- Update ----------------

    @Test
    fun `updateSupplier updates timestamp and delegates to datasource`() = runTest {
        val supplier = TestDataFactory.supplier()

        coEvery { supplierLocalDataSource.updateSupplier(any()) } just Runs

        repository.updateSupplier(supplier)

        coVerify {
            supplierLocalDataSource.updateSupplier(
                match { it.supplierId == supplier.supplierId && it.updatedAt != null }
            )
        }
    }

    // ---------------- Archive ----------------

    @Test
    fun `archiveSupplier delegates to datasource`() = runTest {
        coEvery { supplierLocalDataSource.archiveSupplier(any()) } just Runs

        repository.archiveSupplier("supplier-1")

        coVerify { supplierLocalDataSource.archiveSupplier("supplier-1") }
    }

    // ---------------- Get By Id ----------------

    @Test
    fun `getSupplierById returns mapped domain object`() = runTest {
        val entity = TestDataFactory.supplierEntity()

        coEvery { supplierLocalDataSource.getSupplierById(entity.supplierId) } returns entity

        val result = repository.getSupplierById(entity.supplierId)

        assertNotNull(result)
        assertEquals(entity.supplierId, result!!.supplierId)
        assertEquals(entity.name, result.name)
    }

    @Test
    fun `getSupplierById returns null when not found`() = runTest {
        coEvery { supplierLocalDataSource.getSupplierById(any()) } returns null

        val result = repository.getSupplierById("missing")

        assertNull(result)
    }

    // ---------------- Observe ----------------

    @Test
    fun `observeSuppliersForShop maps entities to domain`() = runTest {
        val entities = listOf(TestDataFactory.supplierEntity("1"), TestDataFactory.supplierEntity("2"))

        every { supplierLocalDataSource.observeSuppliersByShop("shop-1") } returns flowOf(entities)

        repository.observeSuppliersForShop("shop-1").test {
            val result = awaitItem()

            assertEquals(2, result.size)
            assertEquals("1", result[0].supplierId)

            cancelAndIgnoreRemainingEvents()
        }
    }

    private object TestDataFactory {
        fun supplier(id: String = "supplier-1") =
            Supplier(
                supplierId = id,
                shopId = "shop-1",
                name = "Default Supplier",
                contact = "0700000000",
                archived = false
            )

        fun supplierEntity(id: String = "supplier-1") =
            SupplierEntity(
                supplierId = id,
                shopId = "shop-1",
                name = "Default Supplier",
                contact = "0700000000",
                archived = false,
                createdAt = 1L,
                updatedAt = 1L
            )
    }
}
