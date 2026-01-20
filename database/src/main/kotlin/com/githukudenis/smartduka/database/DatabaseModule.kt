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
            androidContext(), SmartDukaLocalDatabase::class.java, "smart_duka_database"
        ).build()
    }

    single<UserDao> { get<SmartDukaLocalDatabase>().userDao() }
    single<ProductDao> { get<SmartDukaLocalDatabase>().productDao() }
    single<ShopDao> { get<SmartDukaLocalDatabase>().shopDao() }
    single<SaleDao> { get<SmartDukaLocalDatabase>().saleDao() }
    single<SaleItemDao> { get<SmartDukaLocalDatabase>().saleItemDao() }
    single<SupplierDao> { get<SmartDukaLocalDatabase>().supplierDao() }
    single<InventoryDao> { get<SmartDukaLocalDatabase>().inventoryDao() }
}