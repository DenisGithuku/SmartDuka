package com.githukudenis.smartduka.database.entity

interface Auditable {
    val createdAt: Long
    val updatedAt: Long?
}
