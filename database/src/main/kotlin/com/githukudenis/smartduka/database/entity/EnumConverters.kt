package com.githukudenis.smartduka.database.entity

import androidx.room.TypeConverter

class EnumConverters {

    @TypeConverter
    fun fromPaymentStatus(value: PaymentStatus): String = value.name

    @TypeConverter
    fun toPaymentStatus(value: String): PaymentStatus =
        PaymentStatus.valueOf(value)

    @TypeConverter
    fun fromMovementType(value: InventoryMovementType): String = value.name

    @TypeConverter
    fun toMovementType(value: String): InventoryMovementType =
        InventoryMovementType.valueOf(value)
}
