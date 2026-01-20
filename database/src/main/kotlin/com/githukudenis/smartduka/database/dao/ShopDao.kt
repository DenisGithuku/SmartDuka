package com.githukudenis.smartduka.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.githukudenis.smartduka.database.entity.ShopEntity
import kotlinx.coroutines.flow.Flow

// Shop operations manage shop lifecycle and metadata.
// Shops act as boundaries for products, sales, inventory, and suppliers.

@Dao
interface ShopDao {
    @Insert
    suspend fun insert(shop: ShopEntity)

    @Update
    suspend fun update(shop: ShopEntity)

    @Query("UPDATE shops SET archived = 1 WHERE shop_id = :shopId")
    suspend fun archive(shopId: String)

    @Query("SELECT * FROM shops WHERE shop_id = :shopId")
    suspend fun getById(shopId: String): ShopEntity?

    @Query("""
        SELECT *
        FROM shops
        WHERE user_id = :userId
          AND archived = 0
        ORDER BY created_at ASC
    """)
    fun observeByUser(userId: String): Flow<List<ShopEntity>>
}