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

import androidx.room.Room
import com.githukudenis.smartduka.database.dao.InventoryDao
import com.githukudenis.smartduka.database.dao.ProductDao
import com.githukudenis.smartduka.database.dao.SaleDao
import com.githukudenis.smartduka.database.dao.SaleItemDao
import com.githukudenis.smartduka.database.dao.ShopDao
import com.githukudenis.smartduka.database.dao.SupplierDao
import com.githukudenis.smartduka.database.dao.UserDao
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule: Module = module {
    single<SmartDukaLocalDatabase> {
        Room.databaseBuilder(
                androidContext(),
                SmartDukaLocalDatabase::class.java,
                "smart_duka_database"
            )
            .build()
    }

    single<UserDao> { get<SmartDukaLocalDatabase>().userDao() }
    single<ProductDao> { get<SmartDukaLocalDatabase>().productDao() }
    single<ShopDao> { get<SmartDukaLocalDatabase>().shopDao() }
    single<SaleDao> { get<SmartDukaLocalDatabase>().saleDao() }
    single<SaleItemDao> { get<SmartDukaLocalDatabase>().saleItemDao() }
    single<SupplierDao> { get<SmartDukaLocalDatabase>().supplierDao() }
    single<InventoryDao> { get<SmartDukaLocalDatabase>().inventoryDao() }
}
