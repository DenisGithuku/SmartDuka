package com.githukudenis.smartduka.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.githukudenis.smartduka.database.entity.SaleEntity
import com.githukudenis.smartduka.database.entity.SaleItemEntity

data class SaleWithItemsEntity(
    @Embedded val sale: SaleEntity,

    @Relation(
        parentColumn = "sale_id",
        entityColumn = "sale_id"
    )
    val items: List<SaleItemEntity>
)
