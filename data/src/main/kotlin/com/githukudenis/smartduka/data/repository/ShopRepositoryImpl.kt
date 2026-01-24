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
package com.githukudenis.smartduka.data.repository

import com.githukudenis.smartduka.data.datasource.local.ShopLocalDataSource
import com.githukudenis.smartduka.data.mapper.mapper.toDomain
import com.githukudenis.smartduka.data.mapper.mapper.toEntity
import com.githukudenis.smartduka.domain.model.Shop
import com.githukudenis.smartduka.domain.model.ShopWithProducts
import com.githukudenis.smartduka.domain.model.ShopWithSales
import com.githukudenis.smartduka.domain.model.ShopWithSuppliers
import com.githukudenis.smartduka.domain.repository.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class ShopRepositoryImpl(private val shopLocalDataSource: ShopLocalDataSource) : ShopRepository {
    override suspend fun insertShop(shop: Shop) {
        val now: Long = System.currentTimeMillis()
        val dbShop = shop.toEntity().copy(createdAt = now, updatedAt = now)
        shopLocalDataSource.insertShop(dbShop)
    }

    override suspend fun updateShop(shop: Shop) {
        val now: Long = System.currentTimeMillis()
        val dbShop = shop.toEntity().copy(updatedAt = now)
        shopLocalDataSource.updateShop(dbShop)
    }

    override suspend fun getShopById(shopId: String): Shop? {
        return shopLocalDataSource.getShopById(shopId)?.toDomain()
    }

    override fun observeByUser(userId: String): Flow<List<Shop>> {
        return shopLocalDataSource.observeShopsByUser(userId).mapLatest { shopEntities ->
            shopEntities.map { it.toDomain() }
        }
    }

    override fun observeShopWithProducts(shopId: String): Flow<ShopWithProducts> {
        return shopLocalDataSource.observeShopWithProducts(shopId).mapLatest { it.toDomain() }
    }

    override fun observeShopWithSales(shopId: String): Flow<ShopWithSales> {
        return shopLocalDataSource.observeShopWithSales(shopId).mapLatest { it.toDomain() }
    }

    override fun observeShopWithSuppliers(shopId: String): Flow<ShopWithSuppliers> {
        return shopLocalDataSource.observeShopWithSuppliers(shopId).mapLatest { it.toDomain() }
    }
}
