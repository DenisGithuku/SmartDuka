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
    tableName = "sale_items",
    foreignKeys =
        [
            ForeignKey(
                entity = SaleEntity::class,
                parentColumns = ["sale_id"],
                childColumns = ["sale_id"],
                onDelete = ForeignKey.CASCADE
            ),
            ForeignKey(
                entity = ProductEntity::class,
                parentColumns = ["product_id"],
                childColumns = ["product_id"],
                onDelete = ForeignKey.RESTRICT
            )
        ],
    indices = [Index("sale_id"), Index("product_id")]
)
data class SaleItemEntity(
    @PrimaryKey @ColumnInfo(name = "sale_item_id") val saleItemId: String,
    @ColumnInfo(name = "sale_id") val saleId: String,
    @ColumnInfo(name = "product_id") val productId: String,
    val quantity: Int,
    @ColumnInfo(name = "unit_price") val unitPrice: Double,
    @ColumnInfo(name = "total_price") val totalPrice: Double
)
