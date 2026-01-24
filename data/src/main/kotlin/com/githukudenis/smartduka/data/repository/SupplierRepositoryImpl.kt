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

import com.githukudenis.smartduka.data.datasource.local.SupplierLocalDataSource
import com.githukudenis.smartduka.data.mapper.mapper.toDomain
import com.githukudenis.smartduka.data.mapper.mapper.toEntity
import com.githukudenis.smartduka.database.entity.SupplierEntity
import com.githukudenis.smartduka.domain.model.Supplier
import com.githukudenis.smartduka.domain.repository.SupplierRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class SupplierRepositoryImpl(private val supplierLocalDataSource: SupplierLocalDataSource) :
    SupplierRepository {
    override suspend fun insertSupplier(supplier: Supplier) {
        val now: Long = System.currentTimeMillis()
        val dbSupplier = supplier.toEntity().copy(createdAt = now, updatedAt = now)
        supplierLocalDataSource.insertSupplier(dbSupplier)
    }

    override suspend fun updateSupplier(supplier: Supplier) {
        val now: Long = System.currentTimeMillis()
        val dbSupplier = supplier.toEntity().copy(updatedAt = now)
        supplierLocalDataSource.updateSupplier(dbSupplier)
    }

    override suspend fun archiveSupplier(supplierId: String) {
        supplierLocalDataSource.archiveSupplier(supplierId)
    }

    override suspend fun getSupplierById(supplierId: String): Supplier? {
        return supplierLocalDataSource.getSupplierById(supplierId)?.toDomain()
    }

    override fun observeSuppliersForShop(shopId: String): Flow<List<Supplier>> {
        return supplierLocalDataSource.observeSuppliersByShop(shopId).mapLatest {
            it.map(SupplierEntity::toDomain)
        }
    }
}
