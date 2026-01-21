package com.githukudenis.smartduka.database

import com.githukudenis.smartduka.database.entity.PaymentStatus
import com.githukudenis.smartduka.database.entity.ProductEntity
import com.githukudenis.smartduka.database.entity.SaleEntity
import com.githukudenis.smartduka.database.entity.ShopEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import kotlin.test.assertContains

class SaleDaoTest : BaseRoomTest() {

    @Test
    fun insertSale_and_getSaleById_returnsSale() = runTest {
        val sale = insertSale()

        val fetched = saleDao.getSaleById(sale.saleId)

        assertNotNull(fetched)
        assertEquals(sale.saleId, fetched?.saleId)
        assertEquals(sale.totalAmount, fetched?.totalAmount)
    }

    @Test
    fun updateSale_updatesFieldsCorrectly() = runTest {
        val sale = insertSale()

        val updated = sale.copy(
            totalAmount = 750.0,
            paymentStatus = PaymentStatus.PAID
        )

        saleDao.updateSale(updated)

        val result = saleDao.getSaleById(sale.saleId)

        assertEquals(750.0, result?.totalAmount)
        assertEquals(PaymentStatus.PAID, result?.paymentStatus)
    }

    @Test
    fun deleteSale_removesSale() = runTest {
        val sale = insertSale()

        saleDao.deleteSale(sale.saleId)

        val result = saleDao.getSaleById(sale.saleId)
        assertNull(result)
    }

    @Test
    fun observeSalesForShop_ordersByDateDescending() = runTest {
        val shop = insertShop()

        val olderSale = TestDataFactory.sale(
            shopId = shop.shopId,
            date = 1_000L
        )

        val newerSale = TestDataFactory.sale(
            shopId = shop.shopId,
            date = 2_000L
        )

        saleDao.insertSale(olderSale)
        saleDao.insertSale(newerSale)

        val sales = saleDao
            .observeSalesForShop(shop.shopId)
            .first()

        assertEquals(2, sales.size)
        assertContains(sales.map { it.saleId }, olderSale.saleId)
        assertContains(sales.map { it.saleId }, newerSale.saleId)
    }

    @Test
    fun observeSaleWithItems_returnsSaleAndItems() = runTest {
        val (sale, product) = insertSaleWithProduct()

        val saleItem = TestDataFactory.saleItem(
            saleId = sale.saleId,
            productId = product.productId,
            quantity = 2,
            unitPrice = product.price
        )



        saleItemDao.insertSaleItems(listOf(saleItem))

        val result = saleDao
            .observeSaleWithItems(sale.saleId)
            .first()

        assertEquals(sale.saleId, result.sale.saleId)
        assertEquals(1, result.items.size)
        assertEquals(2, result.items.first().quantity)
    }

    // -------------------------
    // helpers
    // -------------------------

    private suspend fun insertShop(): ShopEntity {
        val user = TestDataFactory.user()
        userDao.insert(user)

        val shop = TestDataFactory.shop(userId = user.userId)
        shopDao.insert(shop)

        return shop
    }

    private suspend fun insertSale(): SaleEntity {
        val shop = insertShop()

        val sale = TestDataFactory.sale(shopId = shop.shopId)
        saleDao.insertSale(sale)

        return sale
    }

    private suspend fun insertSaleWithProduct(): Pair<SaleEntity, ProductEntity> {
        val user = TestDataFactory.user()
        userDao.insert(user)

        val shop = TestDataFactory.shop(userId = user.userId)
        shopDao.insert(shop)

        val product = TestDataFactory.product(shopId = shop.shopId)
        productDao.insert(product)

        val sale = TestDataFactory.sale(shopId = shop.shopId)
        saleDao.insertSale(sale)

        return sale to product
    }
}
