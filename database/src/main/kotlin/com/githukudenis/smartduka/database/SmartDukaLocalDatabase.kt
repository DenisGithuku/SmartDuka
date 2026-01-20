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
package com.githukudenis.smartduka.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.githukudenis.smartduka.database.dao.InventoryDao
import com.githukudenis.smartduka.database.dao.ProductDao
import com.githukudenis.smartduka.database.dao.SaleDao
import com.githukudenis.smartduka.database.dao.SaleItemDao
import com.githukudenis.smartduka.database.dao.ShopDao
import com.githukudenis.smartduka.database.dao.SupplierDao
import com.githukudenis.smartduka.database.dao.UserDao
import com.githukudenis.smartduka.database.entity.EnumConverters
import com.githukudenis.smartduka.database.entity.InventoryMovementEntity
import com.githukudenis.smartduka.database.entity.ProductEntity
import com.githukudenis.smartduka.database.entity.SaleEntity
import com.githukudenis.smartduka.database.entity.SaleItemEntity
import com.githukudenis.smartduka.database.entity.ShopEntity
import com.githukudenis.smartduka.database.entity.SupplierEntity
import com.githukudenis.smartduka.database.entity.UserEntity

@Database(
    entities =
        [
            UserEntity::class,
            ProductEntity::class,
            ShopEntity::class,
            SaleEntity::class,
            SaleItemEntity::class,
            SupplierEntity::class,
            InventoryMovementEntity::class
        ],
    version = 1,
    exportSchema = false
)
@TypeConverters(EnumConverters::class)
abstract class SmartDukaLocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun productDao(): ProductDao

    abstract fun shopDao(): ShopDao

    abstract fun saleDao(): SaleDao

    abstract fun saleItemDao(): SaleItemDao

    abstract fun supplierDao(): SupplierDao

    abstract fun inventoryDao(): InventoryDao
}
