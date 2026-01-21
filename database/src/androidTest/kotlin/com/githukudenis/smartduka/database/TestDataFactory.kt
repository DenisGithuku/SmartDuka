package com.githukudenis.smartduka.database

import com.githukudenis.smartduka.database.entity.InventoryMovementEntity
import com.githukudenis.smartduka.database.entity.InventoryMovementType
import com.githukudenis.smartduka.database.entity.PaymentStatus
import com.githukudenis.smartduka.database.entity.ProductEntity
import com.githukudenis.smartduka.database.entity.SaleEntity
import com.githukudenis.smartduka.database.entity.SaleItemEntity
import com.githukudenis.smartduka.database.entity.ShopEntity
import com.githukudenis.smartduka.database.entity.UserEntity
import java.util.UUID

object TestDataFactory {
    fun user(id: String = UUID.randomUUID().toString()) =
        UserEntity(
            userId = id,
            name = "John",
            email = "john@test.com",
            createdAt = now(),
            updatedAt = now(),
            archived = false
        )

    fun shop(
        userId: String = UUID.randomUUID().toString(),
        id: String = UUID.randomUUID().toString()
    ) =
        ShopEntity(
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
    ) =
        ProductEntity(
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


    fun sale(
        shopId: String = UUID.randomUUID().toString(),
        id: String = UUID.randomUUID().toString(),
        date: Long = now()
    ) = SaleEntity(
        saleId = id,
        shopId = shopId,
        totalAmount = 100.0,
        paymentStatus = PaymentStatus.PENDING,
        date = now(),
        createdAt = now(),
        updatedAt = now()
    )

    fun saleItem(
        saleId: String = UUID.randomUUID().toString(),
        productId: String = UUID.randomUUID().toString(),
        quantity: Int = 1,
        unitPrice: Double = 100.0,
        id: String = UUID.randomUUID().toString()
    ) = SaleItemEntity(
        saleItemId = id,
        saleId = saleId,
        productId = productId,
        quantity = quantity,
        unitPrice = unitPrice,
        totalPrice = quantity * unitPrice
    )

        fun inventoryMovement(
            productId: String,
            shopId: String,
            quantity: Int,
            type: InventoryMovementType,
            id: String = UUID.randomUUID().toString()
        ) =
            InventoryMovementEntity(
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