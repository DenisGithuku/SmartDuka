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
import com.githukudenis.smartduka.database.entity.ShopEntity
import com.githukudenis.smartduka.database.relation.ShopWithProductsEntity
import com.githukudenis.smartduka.database.relation.ShopWithSalesEntity
import com.githukudenis.smartduka.database.relation.ShopWithSuppliersEntity
import kotlinx.coroutines.flow.Flow

// Shop operations manage shop lifecycle and metadata.
// Shops act as boundaries for products, sales, inventory, and suppliers.

@Dao
interface ShopDao {
    @Insert suspend fun insert(shop: ShopEntity)

    @Update suspend fun update(shop: ShopEntity)

    @Query("UPDATE shops SET archived = 1 WHERE shop_id = :shopId")
    suspend fun archive(shopId: String)

    @Query("SELECT * FROM shops LIMIT 1") fun observeShop(): Flow<ShopEntity>

    @Transaction
    @Query("SELECT * FROM shops WHERE shop_id = :shopId")
    fun observeShopWithProducts(shopId: String): Flow<ShopWithProductsEntity>

    @Transaction
    @Query("SELECT * FROM shops WHERE shop_id = :shopId")
    fun observeShopWithSales(shopId: String): Flow<ShopWithSalesEntity>

    @Transaction
    @Query("SELECT * FROM shops WHERE shop_id = :shopId")
    fun observeShopWithSuppliers(shopId: String): Flow<ShopWithSuppliersEntity>
}
