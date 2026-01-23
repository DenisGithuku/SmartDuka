package com.githukudenis.smartduka.data.mapper.mapper

import com.githukudenis.smartduka.database.entity.ProductEntity
import com.githukudenis.smartduka.domain.model.Product

fun ProductEntity.toDomain(): Product {
    return Product(productId, shopId, name, description, price, archived, costPrice,stockQuantity, lowStockThreshold)
}

fun Product.toEntity(): ProductEntity {
    val now: Long = System.currentTimeMillis()
    return ProductEntity(productId, shopId, name, description, price, archived, costPrice,stockQuantity, lowStockThreshold, now, now)
}