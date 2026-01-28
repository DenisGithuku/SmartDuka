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
package com.githukudenis.smartduka.domain.repository

import com.githukudenis.smartduka.domain.model.Sale
import com.githukudenis.smartduka.domain.model.SaleItem
import com.githukudenis.smartduka.domain.model.SaleWithItems
import kotlinx.coroutines.flow.Flow

interface SaleRepository {
    suspend fun insertSale(sale: Sale)

    suspend fun updateSale(sale: Sale)

    suspend fun deleteSale(saleId: String)

    suspend fun getSaleById(saleId: String): Sale?

    fun observeSalesForShop(shopId: String): Flow<List<Sale>>

    fun observeSaleWithItems(saleId: String): Flow<SaleWithItems>

    fun getSalesBetween(start: Long, end: Long): Flow<List<Sale>>

    suspend fun insertSaleItems(items: List<SaleItem>)

    suspend fun removeSaleItem(saleId: String, productId: String)

    suspend fun deleteItemsForSale(saleId: String)

    suspend fun getItemsForSale(saleId: String): List<SaleItem>
}
