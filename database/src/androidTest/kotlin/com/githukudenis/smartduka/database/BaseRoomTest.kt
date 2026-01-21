package com.githukudenis.smartduka.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.githukudenis.smartduka.database.dao.InventoryDao
import com.githukudenis.smartduka.database.dao.ProductDao
import com.githukudenis.smartduka.database.dao.ShopDao
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


    @Before
    fun setup() {
        // Create an in-memory database for testing
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SmartDukaLocalDatabase::class.java
        )
            .allowMainThreadQueries() // safe for testing
            .build()

        inventoryDao = database.inventoryDao()
        userDao = database.userDao()
        shopDao = database.shopDao()
        productDao = database.productDao()
    }

    @After
    fun teardown() {
        database.close()
    }
}