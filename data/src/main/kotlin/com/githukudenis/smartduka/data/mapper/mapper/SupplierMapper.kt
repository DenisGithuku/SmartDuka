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
package com.githukudenis.smartduka.data.mapper.mapper

import com.githukudenis.smartduka.database.entity.SupplierEntity
import com.githukudenis.smartduka.domain.model.Supplier

// Maps supplier entities to domain objects and vice versa
fun SupplierEntity.toDomain(): Supplier {
    return Supplier(supplierId, shopId, name, contact, archived)
}

fun Supplier.toEntity(): SupplierEntity {
    val now: Long = System.currentTimeMillis()
    return SupplierEntity(supplierId, shopId, archived, name, contact)
}
