package com.githukudenis.smartduka.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "suppliers",
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
data class SupplierEntity(
    @PrimaryKey
    @ColumnInfo(name = "supplier_id")
    val supplierId: String,

    @ColumnInfo(name = "shop_id")
    val shopId: String,
    val archived: Boolean,

    val name: String,
    val contact: String?,

    @ColumnInfo(name = "created_at")
    override val createdAt: Long,

    @ColumnInfo(name = "updated_at")
    override val updatedAt: Long?
) : Auditable
