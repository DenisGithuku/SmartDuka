/*
* Copyright 2026 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.githukudenis.smartduka.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    foreignKeys =
        [
            ForeignKey(
                entity = ShopEntity::class,
                parentColumns = ["shop_id"],
                childColumns = ["shop_id"],
                onDelete = ForeignKey.CASCADE
            )
        ],
    indices = [Index("shop_id"), Index(value = ["shop_id", "name"], unique = true)]
)
data class ProductEntity(
    @PrimaryKey @ColumnInfo(name = "product_id") val productId: String,
    @ColumnInfo(name = "shop_id") val shopId: String,
    val name: String,
    val description: String?,
    val price: Double,
    val archived: Boolean,
    @ColumnInfo(name = "cost_price") val costPrice: Double?,
    @ColumnInfo(name = "stock_quantity") val stockQuantity: Int,
    @ColumnInfo(name = "low_stock_threshold") val lowStockThreshold: Int,
    @ColumnInfo(name = "created_at") override val createdAt: Long,
    @ColumnInfo(name = "updated_at") override val updatedAt: Long?
) : Auditable
