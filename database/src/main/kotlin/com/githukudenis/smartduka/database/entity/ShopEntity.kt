package com.githukudenis.smartduka.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "shops",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("user_id")]
)
data class ShopEntity(
    @PrimaryKey
    @ColumnInfo(name = "shop_id")
    val shopId: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    val archived: Boolean,

    val name: String,
    val location: String?,

    @ColumnInfo(name = "created_at")
    override val createdAt: Long,

    @ColumnInfo(name = "updated_at")
    override val updatedAt: Long?
) : Auditable
