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
