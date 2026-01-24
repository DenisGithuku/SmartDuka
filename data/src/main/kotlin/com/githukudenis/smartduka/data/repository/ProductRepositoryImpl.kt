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

import com.githukudenis.smartduka.data.datasource.local.ProductLocalDataSource
import com.githukudenis.smartduka.data.mapper.mapper.toDomain
import com.githukudenis.smartduka.data.mapper.mapper.toEntity
import com.githukudenis.smartduka.database.entity.ProductEntity
import com.githukudenis.smartduka.domain.model.Product
import com.githukudenis.smartduka.domain.model.ProductWithInventoryMovements
import com.githukudenis.smartduka.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

class ProductRepositoryImpl(private val productLocalDataSource: ProductLocalDataSource) :
    ProductRepository {
    override suspend fun insertProduct(product: Product) {
        val now: Long = System.currentTimeMillis()
        val dbProduct = product.toEntity().copy(createdAt = now, updatedAt = now)
        productLocalDataSource.insertProduct(dbProduct)
    }

    override suspend fun updateProduct(product: Product) {
        val now: Long = System.currentTimeMillis()
        val dbProduct = product.toEntity().copy(updatedAt = now)
        productLocalDataSource.updateProduct(dbProduct)
    }

    override suspend fun archiveProduct(productId: String) {
        productLocalDataSource.archiveProduct(productId)
    }

    override suspend fun getProductById(productId: String): Product? {
        return productLocalDataSource.getProductById(productId)?.toDomain()
    }

    override fun observeByShop(shopId: String): Flow<List<Product>> {
        return productLocalDataSource.observeByShop(shopId).mapLatest {
            it.map(ProductEntity::toDomain)
        }
    }

    override fun observeLowStock(shopId: String): Flow<List<Product>> {
        return productLocalDataSource.observeLowStock(shopId).mapLatest {
            it.map(ProductEntity::toDomain)
        }
    }

    override fun observeProductWithInventoryMovements(
        productId: String
    ): Flow<ProductWithInventoryMovements> {
        return productLocalDataSource.observeProductWithInventoryMovements(productId).mapLatest {
            it.toDomain()
        }
    }
}
