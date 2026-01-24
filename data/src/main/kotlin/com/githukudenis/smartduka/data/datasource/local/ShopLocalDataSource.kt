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
package com.githukudenis.smartduka.data.datasource.local

import com.githukudenis.smartduka.database.dao.ShopDao
import com.githukudenis.smartduka.database.entity.ShopEntity
import com.githukudenis.smartduka.database.relation.ShopWithProductsEntity
import com.githukudenis.smartduka.database.relation.ShopWithSalesEntity
import com.githukudenis.smartduka.database.relation.ShopWithSuppliersEntity
import kotlinx.coroutines.flow.Flow

// Local datasource for shop data
interface ShopLocalDataSource {
    suspend fun insertShop(shop: ShopEntity)

    suspend fun updateShop(shop: ShopEntity)

    suspend fun getShopById(shopId: String): ShopEntity?

    fun observeShopsByUser(userId: String): Flow<List<ShopEntity>>

    fun observeShopWithProducts(shopId: String): Flow<ShopWithProductsEntity>

    fun observeShopWithSales(shopId: String): Flow<ShopWithSalesEntity>

    fun observeShopWithSuppliers(shopId: String): Flow<ShopWithSuppliersEntity>
}

class ShopLocalDataSourceImpl(private val shopDao: ShopDao) : ShopLocalDataSource {
    override suspend fun insertShop(shop: ShopEntity) {
        shopDao.insert(shop)
    }

    override suspend fun updateShop(shop: ShopEntity) {
        shopDao.update(shop)
    }

    override suspend fun getShopById(shopId: String): ShopEntity? {
        return shopDao.getById(shopId)
    }

    override fun observeShopsByUser(userId: String): Flow<List<ShopEntity>> {
        return shopDao.observeByUser(userId)
    }

    override fun observeShopWithProducts(shopId: String): Flow<ShopWithProductsEntity> {
        return shopDao.observeShopWithProducts(shopId)
    }

    override fun observeShopWithSales(shopId: String): Flow<ShopWithSalesEntity> {
        return shopDao.observeShopWithSales(shopId)
    }

    override fun observeShopWithSuppliers(shopId: String): Flow<ShopWithSuppliersEntity> {
        return shopDao.observeShopWithSuppliers(shopId)
    }
}
