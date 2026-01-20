package com.githukudenis.smartduka.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.githukudenis.smartduka.database.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

// Product operations manage product metadata only.
// Stock changes are handled exclusively by InventoryRepository.

@Dao
interface ProductDao {
    @Insert
    suspend fun insert(product: ProductEntity)

    @Update
    suspend fun update(product: ProductEntity)

    @Query("UPDATE products SET archived = 1 WHERE product_id = :productId")
    suspend fun archive(productId: String)

    @Query("SELECT * FROM products WHERE shop_id = :shopId")
    fun observeByShop(shopId: String): Flow<List<ProductEntity>>

    @Query("""
        SELECT *
        FROM products
        WHERE shop_id = :shopId
          AND stock_quantity <= low_stock_threshold
    """)
    fun observeLowStock(shopId: String): Flow<List<ProductEntity>>
}