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
package com.githukudenis.smartduka.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.githukudenis.smartduka.database.entity.ProductEntity
import com.githukudenis.smartduka.database.relation.ProductWithInventoryMovements
import kotlinx.coroutines.flow.Flow

// Product operations manage product metadata only.
// Stock changes are handled exclusively by InventoryRepository.

@Dao
interface ProductDao {
    @Insert suspend fun insert(product: ProductEntity)

    @Update suspend fun update(product: ProductEntity)

    @Query("UPDATE products SET archived = 1 WHERE product_id = :productId")
    suspend fun archive(productId: String)

    @Query("SELECT * FROM products WHERE shop_id = :shopId")
    fun observeByShop(shopId: String): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE shop_id = :shopId AND stock_quantity <= low_stock_threshold")
    fun observeLowStock(shopId: String): Flow<List<ProductEntity>>

    @Transaction
    @Query("SELECT * FROM products WHERE product_id = :productId")
    fun observeProductWithInventoryMovements(productId: String): Flow<ProductWithInventoryMovements>
}
