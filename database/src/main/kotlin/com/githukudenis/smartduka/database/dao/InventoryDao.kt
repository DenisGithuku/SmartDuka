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
import com.githukudenis.smartduka.database.entity.InventoryMovementEntity

// Inventory operations are intent-based:
// - recordSale
// - recordRestock
// - adjustStock
// All operations must create an InventoryMovement
// and update product stock atomically.

@Dao
interface InventoryDao {
    @Insert suspend fun insertMovement(movement: InventoryMovementEntity)

    @Query(
        """
                SELECT SUM(quantity)
                FROM inventory_movements
                WHERE product_id = :productId
                    AND shop_id = :shopId
        """
    )
    suspend fun calculateStock(shopId: String, productId: String): Int
}
