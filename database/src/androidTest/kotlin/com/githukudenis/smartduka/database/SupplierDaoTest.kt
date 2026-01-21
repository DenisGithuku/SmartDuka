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

import com.githukudenis.smartduka.database.TestDataFactory.shop
import com.githukudenis.smartduka.database.TestDataFactory.supplier
import kotlin.test.assertContains
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SupplierDaoTest : BaseRoomTest() {

    @Test
    fun insert_and_getById_returnsSupplier() = runTest {
        // Given
        val user = TestDataFactory.user()
        userDao.insert(user)

        val shop = shop(user.userId)

        shopDao.insert(shop)
        val supplier = supplier(shopId = shop.shopId)

        // When
        supplierDao.insert(supplier)
        val result = supplierDao.getById(supplier.supplierId)

        // Then
        assertNotNull(result)
        assertEquals(supplier.supplierId, result?.supplierId)
        assertEquals(shop.shopId, result?.shopId)
    }

    @Test
    fun update_updatesSupplierCorrectly() = runTest {
        val user = TestDataFactory.user()
        userDao.insert(user)

        val shop = shop(user.userId)

        shopDao.insert(shop)
        val supplier = supplier(shop.shopId)

        supplierDao.insert(supplier)

        val updated = supplier.copy(name = "Updated Supplier", contact = "0700000000")

        supplierDao.update(updated)

        val result = supplierDao.getById(supplier.supplierId)

        assertEquals("Updated Supplier", result?.name)
        assertEquals("0700000000", result?.contact)
    }

    @Test
    fun archive_excludesSupplierFromObserveByShop() = runTest {
        val user = TestDataFactory.user()
        userDao.insert(user)

        val shop = shop(user.userId)

        shopDao.insert(shop)
        val supplier = supplier(shop.shopId)

        supplierDao.insert(supplier)
        supplierDao.archive(supplier.supplierId)

        val suppliers = supplierDao.observeByShop(shop.shopId).first()

        assertTrue(suppliers.isEmpty())
    }

    @Test
    fun observeByShop_returnsSuppliersSortedByName() = runTest {
        val user = TestDataFactory.user()
        userDao.insert(user)

        val shop = shop(user.userId)

        shopDao.insert(shop)

        val supplierB = supplier(shopId = shop.shopId, name = "Alpha Suppliers")

        val supplierA = supplier(shopId = shop.shopId, name = "Beta Suppliers")

        supplierDao.insert(supplierB)
        supplierDao.insert(supplierA)

        val suppliers = supplierDao.observeByShop(shop.shopId).first()

        assertEquals(2, suppliers.size)
        assertContains(suppliers.map { it.name }, "Alpha Suppliers")
        assertContains(suppliers.map { it.name }, "Beta Suppliers")
    }
}
