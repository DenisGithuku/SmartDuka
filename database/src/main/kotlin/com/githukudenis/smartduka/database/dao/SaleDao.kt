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
import com.githukudenis.smartduka.database.entity.SaleEntity
import com.githukudenis.smartduka.database.relation.SaleWithItems
import kotlinx.coroutines.flow.Flow

// Sale operations coordinate transactions:
// - manage SaleItems
// - validate stock
// - record inventory movements
// - finalize or cancel atomically

@Dao
interface SaleDao {
    // ---------- Sale ----------

    @Insert suspend fun insertSale(sale: SaleEntity)

    @Update suspend fun updateSale(sale: SaleEntity)

    @Query("DELETE FROM sales WHERE sale_id = :saleId") suspend fun deleteSale(saleId: String)

    @Query("SELECT * FROM sales WHERE sale_id = :saleId")
    suspend fun getSaleById(saleId: String): SaleEntity?

    @Query(
        """
                SELECT *
                FROM sales
                WHERE shop_id = :shopId
                ORDER BY date DESC
        """
    )
    fun observeSalesForShop(shopId: String): Flow<List<SaleEntity>>

    // ---------- Relations ----------

    @Transaction
    @Query("SELECT * FROM sales WHERE sale_id = :saleId")
    fun observeSaleWithItems(saleId: String): Flow<SaleWithItems>
}
