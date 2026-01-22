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

import com.githukudenis.smartduka.database.dao.SaleDao
import com.githukudenis.smartduka.database.dao.SaleItemDao
import com.githukudenis.smartduka.database.entity.SaleEntity
import com.githukudenis.smartduka.database.entity.SaleItemEntity
import com.githukudenis.smartduka.database.relation.SaleWithItems
import kotlinx.coroutines.flow.Flow

// Local datasource for sale data

interface SaleLocalDataSource {
    suspend fun insertSale(sale: SaleEntity)

    suspend fun updateSale(sale: SaleEntity)

    suspend fun deleteSale(saleId: String)

    suspend fun getSaleById(saleId: String): SaleEntity?

    fun observeSalesForShop(shopId: String): Flow<List<SaleEntity>>

    fun observeSaleWithItems(saleId: String): Flow<SaleWithItems>

    suspend fun insertSaleItems(items: List<SaleItemEntity>)

    suspend fun removeSaleItem(saleId: String, productId: String)

    suspend fun deleteItemsForSale(saleId: String)

    suspend fun getItemsForSale(saleId: String): List<SaleItemEntity>
}

class SalesLocalDataSourceImpl(private val saleDao: SaleDao, private val saleItemDao: SaleItemDao) :
    SaleLocalDataSource {
    override suspend fun insertSale(sale: SaleEntity) {
        saleDao.insertSale(sale)
    }

    override suspend fun updateSale(sale: SaleEntity) {
        saleDao.updateSale(sale)
    }

    override suspend fun deleteSale(saleId: String) {
        saleDao.deleteSale(saleId)
    }

    override suspend fun getSaleById(saleId: String): SaleEntity? = saleDao.getSaleById(saleId)

    override fun observeSalesForShop(shopId: String): Flow<List<SaleEntity>> =
        saleDao.observeSalesForShop(shopId)

    override fun observeSaleWithItems(saleId: String): Flow<SaleWithItems> =
        saleDao.observeSaleWithItems(saleId)

    override suspend fun insertSaleItems(items: List<SaleItemEntity>) {
        saleItemDao.insertSaleItems(items)
    }

    override suspend fun removeSaleItem(saleId: String, productId: String) {
        saleItemDao.removeSaleItem(saleId, productId)
    }

    override suspend fun deleteItemsForSale(saleId: String) {
        saleItemDao.deleteItemsForSale(saleId)
    }

    override suspend fun getItemsForSale(saleId: String): List<SaleItemEntity> =
        saleItemDao.getItemsForSale(saleId)
}
