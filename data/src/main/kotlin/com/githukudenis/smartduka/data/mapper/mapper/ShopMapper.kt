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

import com.githukudenis.smartduka.database.entity.ProductEntity
import com.githukudenis.smartduka.database.entity.SaleEntity
import com.githukudenis.smartduka.database.entity.ShopEntity
import com.githukudenis.smartduka.database.entity.SupplierEntity
import com.githukudenis.smartduka.database.relation.ShopWithProductsEntity
import com.githukudenis.smartduka.database.relation.ShopWithSalesEntity
import com.githukudenis.smartduka.database.relation.ShopWithSuppliersEntity
import com.githukudenis.smartduka.domain.model.Product
import com.githukudenis.smartduka.domain.model.Sale
import com.githukudenis.smartduka.domain.model.Shop
import com.githukudenis.smartduka.domain.model.ShopWithProducts
import com.githukudenis.smartduka.domain.model.ShopWithSales
import com.githukudenis.smartduka.domain.model.ShopWithSuppliers
import com.githukudenis.smartduka.domain.model.Supplier

// Maps user to and from dtos and entitites
fun ShopEntity.toDomain(): Shop = Shop(shopId, userId, archived, name, location)

fun Shop.toEntity(): ShopEntity {
    return ShopEntity(shopId, userId, archived, name, location)
}

fun ShopWithProductsEntity.toDomain(): ShopWithProducts {
    return ShopWithProducts(shop.toDomain(), products.map(ProductEntity::toDomain))
}

fun ShopWithProducts.toEntity(): ShopWithProductsEntity {
    return ShopWithProductsEntity(shop.toEntity(), products.map(Product::toEntity))
}

fun ShopWithSalesEntity.toDomain(): ShopWithSales {
    return ShopWithSales(shop.toDomain(), sales.map(SaleEntity::toDomain))
}

fun ShopWithSales.toEntity(): ShopWithSalesEntity {
    return ShopWithSalesEntity(shop.toEntity(), sales.map(Sale::toEntity))
}

fun ShopWithSuppliersEntity.toDomain(): ShopWithSuppliers {
    return ShopWithSuppliers(shop.toDomain(), suppliers.map(SupplierEntity::toDomain))
}

fun ShopWithSuppliers.toEntity(): ShopWithSuppliersEntity {
    return ShopWithSuppliersEntity(shop.toEntity(), suppliers.map(Supplier::toEntity))
}
