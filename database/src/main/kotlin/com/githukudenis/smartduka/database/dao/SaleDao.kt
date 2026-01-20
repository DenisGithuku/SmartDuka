package com.githukudenis.smartduka.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.githukudenis.smartduka.database.entity.SaleEntity
import com.githukudenis.smartduka.database.entity.SaleItemEntity
import com.githukudenis.smartduka.database.relation.SaleWithItemsEntity
import kotlinx.coroutines.flow.Flow

// Sale operations coordinate transactions:
// - manage SaleItems
// - validate stock
// - record inventory movements
// - finalize or cancel atomically

@Dao
interface SaleDao {
    /* ---------- Sale ---------- */

    @Insert
    suspend fun insertSale(sale: SaleEntity)

    @Update
    suspend fun updateSale(sale: SaleEntity)

    @Query("DELETE FROM sales WHERE sale_id = :saleId")
    suspend fun deleteSale(saleId: String)

    @Query("SELECT * FROM sales WHERE sale_id = :saleId")
    suspend fun getSaleById(saleId: String): SaleEntity?

    @Query("""
        SELECT *
        FROM sales
        WHERE shop_id = :shopId
        ORDER BY date DESC
    """)
    fun observeSalesForShop(shopId: String): Flow<List<SaleEntity>>

    /* ---------- Relations ---------- */

    @Transaction
    @Query("SELECT * FROM sales WHERE sale_id = :saleId")
    fun observeSaleWithItems(
        saleId: String
    ): Flow<SaleWithItemsEntity>
}