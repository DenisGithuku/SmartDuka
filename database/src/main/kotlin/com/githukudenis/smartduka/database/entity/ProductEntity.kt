package com.githukudenis.smartduka.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = ShopEntity::class,
            parentColumns = ["shop_id"],
            childColumns = ["shop_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("shop_id"),
        Index(value = ["shop_id", "name"], unique = true)
    ]
)
data class ProductEntity(
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val productId: String,

    @ColumnInfo(name = "shop_id")
    val shopId: String,

    val name: String,
    val description: String?,

    val price: Double,
    val archived: Boolean,

    @ColumnInfo(name = "cost_price")
    val costPrice: Double?,

    @ColumnInfo(name = "stock_quantity")
    val stockQuantity: Int,

    @ColumnInfo(name = "low_stock_threshold")
    val lowStockThreshold: Int,

    @ColumnInfo(name = "created_at")
    override val createdAt: Long,

    @ColumnInfo(name = "updated_at")
    override val updatedAt: Long?
) : Auditable
