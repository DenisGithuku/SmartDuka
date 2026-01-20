package com.githukudenis.smartduka.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.githukudenis.smartduka.database.entity.SaleItemEntity

@Dao
interface SaleItemDao {
    @Insert
    suspend fun insertSaleItems(items: List<SaleItemEntity>)

    @Query("""
        DELETE FROM sale_items
        WHERE sale_id = :saleId
          AND product_id = :productId
    """)
    suspend fun removeSaleItem(
        saleId: String,
        productId: String
    )

    @Query("DELETE FROM sale_items WHERE sale_id = :saleId")
    suspend fun deleteItemsForSale(saleId: String)

    @Query("""
        SELECT *
        FROM sale_items
        WHERE sale_id = :saleId
    """)
    suspend fun getItemsForSale(saleId: String): List<SaleItemEntity>
}