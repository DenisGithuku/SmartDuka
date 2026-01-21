package com.githukudenis.smartduka.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.githukudenis.smartduka.database.entity.InventoryMovementType
import com.githukudenis.smartduka.database.entity.ProductEntity
import com.githukudenis.smartduka.database.entity.ShopEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@SmallTest
@RunWith(AndroidJUnit4::class)
class ProductDaoTest : BaseRoomTest() {

    @Test
    fun insert_and_observeByShop_emitsProduct() = runTest {
        val (shop, product) = insertShopAndProduct()

        val products = productDao
            .observeByShop(shop.shopId)
            .first()

        assertEquals(1, products.size)
        assertEquals(product.productId, products.first().productId)
    }

    @Test
    fun update_updatesProductFields() = runTest {
        val (shop, product) = insertShopAndProduct()

        val updated = product.copy(
            name = "Updated Sugar",
            price = 120.0
        )

        productDao.update(updated)

        val result = productDao
            .observeByShop(shop.shopId)
            .first()
            .first()

        assertEquals("Updated Sugar", result.name)
        assertEquals(120.0, result.price, 0.0)
    }

    @Test
    fun archive_setsArchivedFlag() = runTest {
        val (_, product) = insertShopAndProduct()

        productDao.archive(product.productId)

        val archivedProduct = productDao
            .observeByShop(product.shopId)
            .first()
            .first()

        assertEquals(true, archivedProduct.archived)
    }

    @Test
    fun observeLowStock_returnsOnlyLowStockProducts() = runTest {
        val user = TestDataFactory.user()
        userDao.insert(user)

        val shop = TestDataFactory.shop(userId = user.userId)
        shopDao.insert(shop)

        val lowStockProduct = TestDataFactory.product(
            shopId = shop.shopId
        ).copy(stockQuantity = 2, lowStockThreshold = 5, name = "Updated name")

        val healthyStockProduct = TestDataFactory.product(
            shopId = shop.shopId
        ).copy(stockQuantity = 20, lowStockThreshold = 5)

        productDao.insert(lowStockProduct)
        productDao.insert(healthyStockProduct)

        val lowStock = productDao
            .observeLowStock(shop.shopId)
            .first()

        assertEquals(1, lowStock.size)
        assertEquals(lowStockProduct.productId, lowStock.first().productId)
    }

    @Test
    fun observeProductWithInventoryMovements_returnsProductWithMovements() = runTest {
        val (shop, product) = insertShopAndProduct()

        inventoryDao.insertMovement(
            TestDataFactory.inventoryMovement(
                productId = product.productId,
                shopId = shop.shopId,
                quantity = 10,
                type = InventoryMovementType.RESTOCK
            )
        )

        val result = productDao
            .observeProductWithInventoryMovements(product.productId)
            .first()

        assertEquals(product.productId, result.product.productId)
        assertEquals(1, result.movements.size)
        assertEquals(10, result.movements.first().quantity)
    }

    // -------------------------
    // helpers
    // -------------------------

    private suspend fun insertShopAndProduct(): Pair<ShopEntity, ProductEntity> {
        val user = TestDataFactory.user()
        userDao.insert(user)

        val shop = TestDataFactory.shop(userId = user.userId)
        shopDao.insert(shop)

        val product = TestDataFactory.product(shopId = shop.shopId)
        productDao.insert(product)

        return shop to product
    }
}
