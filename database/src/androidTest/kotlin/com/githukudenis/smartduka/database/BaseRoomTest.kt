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
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.githukudenis.smartduka.database.dao.InventoryDao
import com.githukudenis.smartduka.database.dao.ProductDao
import com.githukudenis.smartduka.database.dao.SaleDao
import com.githukudenis.smartduka.database.dao.SaleItemDao
import com.githukudenis.smartduka.database.dao.ShopDao
import com.githukudenis.smartduka.database.dao.SupplierDao
import com.githukudenis.smartduka.database.dao.UserDao
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
abstract class BaseRoomTest {
    protected lateinit var database: SmartDukaLocalDatabase
    protected lateinit var inventoryDao: InventoryDao
    protected lateinit var userDao: UserDao
    protected lateinit var shopDao: ShopDao
    protected lateinit var productDao: ProductDao
    protected lateinit var saleDao: SaleDao
    protected lateinit var saleItemDao: SaleItemDao
    protected lateinit var supplierDao: SupplierDao

    @Before
    fun setup() {
        // Create an in-memory database for testing
        database =
            Room.inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    SmartDukaLocalDatabase::class.java
                )
                .allowMainThreadQueries() // safe for testing
                .build()

        inventoryDao = database.inventoryDao()
        userDao = database.userDao()
        shopDao = database.shopDao()
        productDao = database.productDao()
        saleDao = database.saleDao()
        saleItemDao = database.saleItemDao()
        supplierDao = database.supplierDao()
    }

    @After
    fun teardown() {
        database.close()
    }
}
