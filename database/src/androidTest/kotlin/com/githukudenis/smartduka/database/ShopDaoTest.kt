package com.githukudenis.smartduka.database

import com.githukudenis.smartduka.database.TestDataFactory.product
import com.githukudenis.smartduka.database.TestDataFactory.sale
import com.githukudenis.smartduka.database.TestDataFactory.shop
import com.githukudenis.smartduka.database.TestDataFactory.supplier
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertContains

class ShopDaoTest : BaseRoomTest() {

    @Test
    fun insertAndGetById_returnsShop() = runTest {
        val user = TestDataFactory.user()
        userDao.insert(user)


        val shop = shop(user.userId)

        shopDao.insert(shop)

        val result = shopDao.getById(shop.shopId)

        assertNotNull(result)
        assertEquals(shop.shopId, result?.shopId)
    }

    @Test
    fun update_updatesShopFields() = runTest {
        val user = TestDataFactory.user()
        userDao.insert(user)

        val shop = shop(user.userId)
        shopDao.insert(shop)

        val updated = shop.copy(name = "Updated Shop")
        shopDao.update(updated)

        val result = shopDao.getById(shop.shopId)
        assertEquals("Updated Shop", result?.name)
    }

    @Test
    fun archive_marksShopAsArchived() = runTest {
        val user = TestDataFactory.user()
        userDao.insert(user)

        val shop = shop(user.userId)
        shopDao.insert(shop)

        shopDao.archive(shop.shopId)

        val result = shopDao.getById(shop.shopId)
        assertTrue(result!!.archived)
    }

    @Test
    fun observeByUser_returnsOnlyNonArchivedShops() = runTest {
        val user = TestDataFactory.user()
        userDao.insert(user)

        val activeShop = shop(user.userId)
        val archivedShop = shop(user.userId).copy(archived = true)

        shopDao.insert(activeShop)
        shopDao.insert(archivedShop)

        val shops = shopDao.observeByUser(user.userId).first()

        assertEquals(1, shops.size)
        assertEquals(activeShop.shopId, shops.first().shopId)
    }

    @Test
    fun observeShopWithProducts_returnsProducts() = runTest {
        val user = TestDataFactory.user()
        userDao.insert(user)

        val shop = shop(user.userId)
        shopDao.insert(shop)

        val product = product(shopId = shop.shopId)
        productDao.insert(product)

        val result = shopDao.observeShopWithProducts(shop.shopId).first()

        assertEquals(shop.shopId, result.shop.shopId)
        assertEquals(1, result.products.size)
    }

    @Test
    fun observeShopWithSales_returnsSales() = runTest {
        val user = TestDataFactory.user()
        userDao.insert(user)

        val shop = shop(user.userId)
        shopDao.insert(shop)

        val sale = sale(shop.shopId)
        saleDao.insertSale(sale)

        val result = shopDao.observeShopWithSales(shop.shopId).first()

        assertEquals(1, result.sales.size)
        assertContains(result.sales.map { it.saleId }, sale.saleId)
    }

    @Test
    fun observeShopWithSuppliers_returnsSuppliers() = runTest {
        val user = TestDataFactory.user()
        userDao.insert(user)

        val shop = shop(user.userId)
        shopDao.insert(shop)

        val supplier = supplier(shop.shopId)
        supplierDao.insert(supplier)

        val result = shopDao.observeShopWithSuppliers(shop.shopId).first()

        assertEquals(1, result.suppliers.size)
        assertEquals(supplier.supplierId, result.suppliers.first().supplierId)
        assertContains(result.suppliers.map { it.supplierId }, supplier.supplierId)


    }
}
