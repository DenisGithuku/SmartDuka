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
    entities = [
        UserEntity::class,
        ProductEntity::class,
        ShopEntity::class,
        SaleEntity::class,
        SaleItemEntity::class,
        SupplierEntity::class,
        InventoryMovementEntity::class
    ],
    version = 1,
    exportSchema = false,
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