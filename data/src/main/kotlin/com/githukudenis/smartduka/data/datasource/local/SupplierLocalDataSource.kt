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

import com.githukudenis.smartduka.database.dao.SupplierDao
import com.githukudenis.smartduka.database.entity.SupplierEntity
import kotlinx.coroutines.flow.Flow

// Local datasource for supplier data
interface SupplierLocalDataSource {
    suspend fun insertSupplier(supplier: SupplierEntity)

    suspend fun updateSupplier(supplier: SupplierEntity)

    suspend fun archiveSupplier(supplierId: String)

    suspend fun getSupplierById(supplierId: String): SupplierEntity?

    fun observeSuppliersByShop(shopId: String): Flow<List<SupplierEntity>>
}

class SupplierLocalDataSourceImpl(private val supplierDao: SupplierDao) : SupplierLocalDataSource {
    override suspend fun insertSupplier(supplier: SupplierEntity) {
        supplierDao.insert(supplier)
    }

    override suspend fun updateSupplier(supplier: SupplierEntity) {
        supplierDao.update(supplier)
    }

    override suspend fun archiveSupplier(supplierId: String) {
        supplierDao.archive(supplierId)
    }

    override suspend fun getSupplierById(supplierId: String): SupplierEntity? {
        return supplierDao.getById(supplierId)
    }

    override fun observeSuppliersByShop(shopId: String): Flow<List<SupplierEntity>> {
        return supplierDao.observeByShop(shopId)
    }
}
