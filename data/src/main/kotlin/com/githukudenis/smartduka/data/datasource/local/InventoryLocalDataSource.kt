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

import com.githukudenis.smartduka.database.dao.InventoryDao
import com.githukudenis.smartduka.database.entity.InventoryMovementEntity

// Local datasource for inventory data
interface InventoryLocalDataSource {
    suspend fun insertMovement(movement: InventoryMovementEntity)

    suspend fun calculateStock(shopId: String, productId: String): Int
}

class InventoryLocalDataSourceImpl(private val inventoryDao: InventoryDao) :
    InventoryLocalDataSource {
    override suspend fun insertMovement(movement: InventoryMovementEntity) {
        inventoryDao.insertMovement(movement)
    }

    override suspend fun calculateStock(shopId: String, productId: String): Int {
        return inventoryDao.calculateStock(shopId, productId)
    }
}
