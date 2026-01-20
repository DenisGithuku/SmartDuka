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
    tableName = "shops",
    foreignKeys =
        [
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
    @PrimaryKey @ColumnInfo(name = "shop_id") val shopId: String,
    @ColumnInfo(name = "user_id") val userId: String,
    val archived: Boolean,
    val name: String,
    val location: String?,
    @ColumnInfo(name = "created_at") override val createdAt: Long,
    @ColumnInfo(name = "updated_at") override val updatedAt: Long?
) : Auditable
