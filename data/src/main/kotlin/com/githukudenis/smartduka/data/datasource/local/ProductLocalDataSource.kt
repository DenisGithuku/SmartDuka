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

import com.githukudenis.smartduka.database.dao.ProductDao
import com.githukudenis.smartduka.database.entity.ProductEntity
import com.githukudenis.smartduka.database.relation.ProductWithInventoryMovements
import kotlinx.coroutines.flow.Flow

// Local datasource for product data
interface ProductLocalDataSource {
    suspend fun insertProduct(productEntity: ProductEntity)

    suspend fun updateProduct(productEntity: ProductEntity)

    suspend fun archiveProduct(productId: String)

    suspend fun getProductById(productId: String): ProductEntity?

    fun observeByShop(shopId: String): Flow<List<ProductEntity>>

    fun observeLowStock(shopId: String): Flow<List<ProductEntity>>

    fun observeProductWithInventoryMovements(productId: String): Flow<ProductWithInventoryMovements>
}

class ProductLocalDataSourceImpl(private val productDao: ProductDao) : ProductLocalDataSource {
    override suspend fun insertProduct(productEntity: ProductEntity) {
        productDao.insert(productEntity)
    }

    override suspend fun updateProduct(productEntity: ProductEntity) {
        productDao.update(productEntity)
    }

    override suspend fun archiveProduct(productId: String) {
        productDao.archive(productId)
    }

    override suspend fun getProductById(productId: String): ProductEntity? {
        return productDao.getById(productId)
    }

    override fun observeByShop(shopId: String): Flow<List<ProductEntity>> {
        return productDao.observeByShop(shopId)
    }

    override fun observeLowStock(shopId: String): Flow<List<ProductEntity>> {
        return productDao.observeLowStock(shopId)
    }

    override fun observeProductWithInventoryMovements(
        productId: String
    ): Flow<ProductWithInventoryMovements> {
        return productDao.observeProductWithInventoryMovements(productId)
    }
}
