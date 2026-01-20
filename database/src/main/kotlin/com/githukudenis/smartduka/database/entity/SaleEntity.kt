package com.githukudenis.smartduka.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sales",
    foreignKeys = [
        ForeignKey(
            entity = ShopEntity::class,
            parentColumns = ["shop_id"],
            childColumns = ["shop_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("shop_id")]
)
data class SaleEntity(
    @PrimaryKey
    @ColumnInfo(name = "sale_id")
    val saleId: String,

    @ColumnInfo(name = "shop_id")
    val shopId: String,

    val date: Long,

    @ColumnInfo(name = "total_amount")
    val totalAmount: Double,

    @ColumnInfo(name = "payment_status")
    val paymentStatus: PaymentStatus,

    @ColumnInfo(name = "created_at")
    override val createdAt: Long,

    @ColumnInfo(name = "updated_at")
    override val updatedAt: Long?
) : Auditable
