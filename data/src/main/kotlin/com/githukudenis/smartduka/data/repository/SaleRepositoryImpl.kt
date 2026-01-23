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

import com.githukudenis.smartduka.data.datasource.local.SaleLocalDataSource
import com.githukudenis.smartduka.data.mapper.mapper.toDomain
import com.githukudenis.smartduka.data.mapper.mapper.toEntity
import com.githukudenis.smartduka.database.entity.SaleEntity
import com.githukudenis.smartduka.database.entity.SaleItemEntity
import com.githukudenis.smartduka.domain.model.Sale
import com.githukudenis.smartduka.domain.model.SaleItem
import com.githukudenis.smartduka.domain.model.SaleWithItems
import com.githukudenis.smartduka.domain.repository.SaleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class SaleRepositoryImpl(private val saleLocalDataSource: SaleLocalDataSource) : SaleRepository {
    override suspend fun insertSale(sale: Sale) {
        val now: Long = System.currentTimeMillis()
        val dbSale = sale.toEntity().copy(createdAt = now, updatedAt = now)
        saleLocalDataSource.insertSale(dbSale)
    }

    override suspend fun updateSale(sale: Sale) {
        val now: Long = System.currentTimeMillis()
        val dbSale = sale.toEntity().copy(createdAt = now, updatedAt = now)
        saleLocalDataSource.updateSale(dbSale)
    }

    override suspend fun deleteSale(saleId: String) {
        saleLocalDataSource.deleteSale(saleId)
    }

    override suspend fun getSaleById(saleId: String): Sale? {
        return saleLocalDataSource.getSaleById(saleId)?.toDomain()
    }

    override fun observeSalesForShop(shopId: String): Flow<List<Sale>> {
        return saleLocalDataSource.observeSalesForShop(shopId).mapLatest {
            it.map(SaleEntity::toDomain)
        }
    }

    override fun observeSaleWithItems(saleId: String): Flow<SaleWithItems> {
        return saleLocalDataSource.observeSaleWithItems(saleId).mapLatest { it.toDomain() }
    }

    override suspend fun insertSaleItems(items: List<SaleItem>) {
        saleLocalDataSource.insertSaleItems(items.map(SaleItem::toEntity))
    }

    override suspend fun removeSaleItem(saleId: String, productId: String) {
        saleLocalDataSource.removeSaleItem(saleId, productId)
    }

    override suspend fun deleteItemsForSale(saleId: String) {
        saleLocalDataSource.deleteItemsForSale(saleId)
    }

    override suspend fun getItemsForSale(saleId: String): List<SaleItem> {
        return saleLocalDataSource.getItemsForSale(saleId).map(SaleItemEntity::toDomain)
    }
}
