package com.githukudenis.smartduka.domain.model

data class UserWithShops(
    val user: User,
    val shops: List<Shop>
)