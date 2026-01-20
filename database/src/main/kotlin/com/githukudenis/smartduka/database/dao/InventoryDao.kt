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
    @Insert
    suspend fun insertMovement(movement: InventoryMovementEntity)

    @Query("""
        SELECT SUM(quantity)
        FROM inventory_movements
        WHERE product_id = :productId
          AND shop_id = :shopId
    """)
    suspend fun calculateStock(
        shopId: String,
        productId: String
    ): Int
}