package com.githukudenis.smartduka.database

import com.githukudenis.smartduka.database.entity.InventoryMovementEntity
import com.githukudenis.smartduka.database.entity.InventoryMovementType
import com.githukudenis.smartduka.database.entity.ProductEntity
import com.githukudenis.smartduka.database.entity.ShopEntity
import com.githukudenis.smartduka.database.entity.UserEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

class InventoryDaoTest : BaseRoomTest() {

    @Test
    fun insertMovementInsertsAMovementSuccessfully() = runTest {
        val user = TestDataFactory.user()
        userDao.insert(user)

        val shop = TestDataFactory.shop(user.userId)
        shopDao.insert(shop)

        val product = TestDataFactory.product(shopId = shop.shopId)
        productDao.insert(product)

        val movement = TestDataFactory.inventoryMovement(
            product.productId,
            shop.shopId,
            10,
            InventoryMovementType.RESTOCK
        )
        inventoryDao.insertMovement(movement)

        val stock = inventoryDao.calculateStock(shopId = shop.shopId, productId = product.productId)
        assertEquals(10, stock)
    }

    @Test
    fun calculateStockSumsMultipleMovementsCorrectly() = runTest {

        val user = TestDataFactory.user()
        userDao.insert(user)

        val shop = TestDataFactory.shop(userId = user.userId)
        shopDao.insert(shop)

        val product = TestDataFactory.product(shopId = shop.shopId)
        productDao.insert(product)

        inventoryDao.insertMovement(
            TestDataFactory.inventoryMovement(
                productId = product.productId,
                shopId = shop.shopId,
                quantity = 10,
                type = InventoryMovementType.RESTOCK
            )
        )

        inventoryDao.insertMovement(
            TestDataFactory.inventoryMovement(
                productId = product.productId,
                shopId = shop.shopId,
                quantity = -3,
                type = InventoryMovementType.SALE
            )
        )

        val stock = inventoryDao.calculateStock(
            shopId = shop.shopId,
            productId = product.productId
        )
        assertEquals(7, stock) // 10 - 3 = 7
    }

    private suspend fun insertUser(): UserEntity {
        val userEntity = UserEntity(
            userId = "user-1",
            name = "John",
            email = "john@test.com",
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
            archived = false
        )

        userDao.insert(userEntity)
        return userEntity
    }

    private suspend fun insertShop(userId: String): ShopEntity {
        val shopEntity = ShopEntity(
            shopId = "shop-1",
            userId = userId,
            name = "Smart Duka",
            location = "Nairobi",
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
            archived = false
        )
        shopDao.insert(shopEntity)
        return shopEntity
    }

    private suspend fun insertProduct(productId: String): ProductEntity {

        val productEntity = ProductEntity(
            productId = productId,
            shopId = "shop-1",
            name = "Sugar",
            price = 100.0,
            stockQuantity = 0,
            lowStockThreshold = 5,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
            archived = false,
            description = "Sugar",
            costPrice = 30.50
        )
        productDao.insert(productEntity)
        return productEntity
    }


}

object TestDataFactory {
    fun user(
        id: String = UUID.randomUUID().toString(),
    ) = UserEntity(
        userId = id,
        name = "John",
        email = "john@test.com",
        createdAt = now(),
        updatedAt = now(),
        archived = false
    )

    fun shop(
        userId: String = UUID.randomUUID().toString(),
        id: String = UUID.randomUUID().toString(),
    ) = ShopEntity(
        shopId = id,
        userId = userId,
        name = "Smart Duka",
        location = "Nairobi",
        createdAt = now(),
        updatedAt = now(),
        archived = false
    )

    fun product(
        id: String = UUID.randomUUID().toString(),
        shopId: String = UUID.randomUUID().toString()
    ) = ProductEntity(
        productId = id,
        shopId = shopId,
        name = "Sugar",
        price = 100.0,
        stockQuantity = 0,
        lowStockThreshold = 5,
        createdAt = now(),
        updatedAt = now(),
        archived = false,
        description = "Sugar",
        costPrice = 30.50
    )

    fun inventoryMovement(
        productId: String,
        shopId: String,
        quantity: Int,
        type: InventoryMovementType,
        id: String = UUID.randomUUID().toString()
    ) = InventoryMovementEntity(
        movementId = id,
        productId = productId,
        shopId = shopId,
        type = type,
        quantity = quantity,
        date = now(),
        referenceId = null,
        createdAt = now()
    )

    private fun now() = System.currentTimeMillis()

}