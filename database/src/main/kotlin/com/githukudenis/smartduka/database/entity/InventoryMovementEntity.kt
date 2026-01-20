package com.githukudenis.smartduka.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "inventory_movements",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["product_id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ShopEntity::class,
            parentColumns = ["shop_id"],
            childColumns = ["shop_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("product_id"),
        Index("shop_id"),
        Index("reference_id")
    ]
)
data class InventoryMovementEntity(
    @PrimaryKey
    @ColumnInfo(name = "movement_id")
    val movementId: String,

    @ColumnInfo(name = "product_id")
    val productId: String,

    @ColumnInfo(name = "shop_id")
    val shopId: String,

    val type: InventoryMovementType,

    val quantity: Int,

    val date: Long,

    @ColumnInfo(name = "reference_id")
    val referenceId: String?,

    @ColumnInfo(name = "created_at")
    val createdAt: Long
)
