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

import com.githukudenis.smartduka.database.TestDataFactory.saleItem
import com.githukudenis.smartduka.database.entity.ProductEntity
import com.githukudenis.smartduka.database.entity.SaleEntity
import com.githukudenis.smartduka.database.entity.ShopEntity
import com.githukudenis.smartduka.database.entity.UserEntity
import java.util.UUID
import junit.framework.Assert.assertEquals
import kotlin.test.assertContains
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class SaleItemDaoTest : BaseRoomTest() {

    @Test
    fun insertSaleItems_insertsAllItems() = runTest {
        val user = insertUser()
        val shop = insertShop(user.userId)
        val product1 = insertProduct(shop.shopId, name = "Soya")
        val product2 = insertProduct(shop.shopId)
        val sale = insertSale(shop.shopId)

        val items =
            listOf(
                saleItem(
                    saleId = sale.saleId,
                    productId = product1.productId,
                    quantity = 2,
                    unitPrice = 100.0
                ),
                saleItem(
                    saleId = sale.saleId,
                    productId = product2.productId,
                    quantity = 1,
                    unitPrice = 50.0
                )
            )

        saleItemDao.insertSaleItems(items)

        val result = saleItemDao.getItemsForSale(sale.saleId)

        assertEquals(2, result.size)
        assertContains(result.map { it.productId }, product1.productId)
        assertContains(result.map { it.productId }, product2.productId)
    }

    @Test
    fun removeSaleItem_removesOnlyMatchingItem() = runTest {
        val user = insertUser()
        val shop = insertShop(user.userId)
        val product1 = insertProduct(shopId = shop.shopId, name = "Soya")
        val product2 = insertProduct(shopId = shop.shopId)
        val sale = insertSale(shop.shopId)

        val item1 = saleItem(saleId = sale.saleId, productId = product1.productId)
        val item2 = saleItem(saleId = sale.saleId, productId = product2.productId)

        saleItemDao.insertSaleItems(listOf(item1, item2))

        saleItemDao.removeSaleItem(sale.saleId, product1.productId)

        val remaining = saleItemDao.getItemsForSale(sale.saleId)

        assertEquals(1, remaining.size)
        assertEquals(product2.productId, remaining.first().productId)
    }

    @Test
    fun deleteItemsForSale_deletesAllItemsForSale() = runTest {
        val user = insertUser()
        val shop = insertShop(user.userId)
        val product = insertProduct(shop.shopId)
        val sale = insertSale(shop.shopId)

        val items =
            listOf(
                saleItem(saleId = sale.saleId, productId = product.productId),
                saleItem(saleId = sale.saleId, productId = product.productId)
            )

        saleItemDao.insertSaleItems(items)

        saleItemDao.deleteItemsForSale(sale.saleId)

        val result = saleItemDao.getItemsForSale(sale.saleId)
        assertTrue(result.isEmpty())
    }

    // ---------- helpers ----------

    private suspend fun insertUser(): UserEntity {
        val user = TestDataFactory.user()
        userDao.insert(user)
        return user
    }

    private suspend fun insertShop(userId: String): ShopEntity {
        val shop = TestDataFactory.shop(userId = userId)
        shopDao.insert(shop)
        return shop
    }

    private suspend fun insertProduct(
        shopId: String,
        name: String = "Sugar",
        productId: String = UUID.randomUUID().toString()
    ): ProductEntity {
        val product = TestDataFactory.product(id = productId, shopId = shopId, name)
        productDao.insert(product)
        return product
    }

    private suspend fun insertSale(shopId: String): SaleEntity {
        val sale = TestDataFactory.sale(shopId)
        saleDao.insertSale(sale)
        return sale
    }
}
