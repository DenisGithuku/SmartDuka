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
import com.githukudenis.smartduka.domain.model.PaymentStatus

@Entity(
    tableName = "sales",
    foreignKeys =
        [
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
    @PrimaryKey @ColumnInfo(name = "sale_id") val saleId: String,
    @ColumnInfo(name = "shop_id") val shopId: String,
    val date: Long,
    @ColumnInfo(name = "total_amount") val totalAmount: Double,
    @ColumnInfo(name = "payment_status") val paymentStatus: PaymentStatus,
    @ColumnInfo(name = "created_at") override val createdAt: Long,
    @ColumnInfo(name = "updated_at") override val updatedAt: Long?
) : Auditable
