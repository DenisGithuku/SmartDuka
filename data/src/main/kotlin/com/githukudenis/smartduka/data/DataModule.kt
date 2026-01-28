package com.githukudenis.smartduka.data

import com.githukudenis.smartduka.data.datasource.local.ProductLocalDataSource
import com.githukudenis.smartduka.data.datasource.local.ProductLocalDataSourceImpl
import com.githukudenis.smartduka.data.datasource.local.SaleLocalDataSource
import com.githukudenis.smartduka.data.datasource.local.SalesLocalDataSourceImpl
import com.githukudenis.smartduka.data.datasource.local.ShopLocalDataSource
import com.githukudenis.smartduka.data.datasource.local.ShopLocalDataSourceImpl
import com.githukudenis.smartduka.data.repository.ProductRepositoryImpl
import com.githukudenis.smartduka.data.repository.SaleRepositoryImpl
import com.githukudenis.smartduka.data.repository.ShopRepositoryImpl
import com.githukudenis.smartduka.data.repository.UserRepositoryImpl
import com.githukudenis.smartduka.domain.repository.ProductRepository
import com.githukudenis.smartduka.domain.repository.SaleRepository
import com.githukudenis.smartduka.domain.repository.ShopRepository
import com.githukudenis.smartduka.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    single<SaleRepository> { SaleRepositoryImpl(get()) }
    single<ShopRepository> { ShopRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<ShopLocalDataSource> { ShopLocalDataSourceImpl(get()) }
    single<ProductLocalDataSource> { ProductLocalDataSourceImpl(get()) }
    single<SaleLocalDataSource> { SalesLocalDataSourceImpl(get(), get()) }
}