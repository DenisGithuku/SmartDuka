package com.githukudenis.smartduka.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.githukudenis.smartduka.database.entity.SupplierEntity
import kotlinx.coroutines.flow.Flow

// Supplier operations manage supplier metadata and lifecycle.
// Suppliers are referenced in restocks but do not manage stock themselves.

@Dao
interface SupplierDao {
    @Insert
    suspend fun insert(supplier: SupplierEntity)

    @Update
    suspend fun update(supplier: SupplierEntity)

    @Query("UPDATE suppliers SET archived = 1 WHERE supplier_id = :supplierId")
    suspend fun archive(supplierId: String)

    @Query("SELECT * FROM suppliers WHERE supplier_id = :supplierId")
    suspend fun getById(supplierId: String): SupplierEntity?

    @Query("""
        SELECT *
        FROM suppliers
        WHERE shop_id = :shopId
          AND archived = 0
        ORDER BY name ASC
    """)
    fun observeByShop(shopId: String): Flow<List<SupplierEntity>>
}