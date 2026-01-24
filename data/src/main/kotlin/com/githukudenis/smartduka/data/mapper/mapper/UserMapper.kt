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

import com.githukudenis.smartduka.database.entity.UserEntity
import com.githukudenis.smartduka.database.relation.UserWithShopsEntity
import com.githukudenis.smartduka.domain.model.User
import com.githukudenis.smartduka.domain.model.UserWithShops

fun UserEntity.toDomain(): User {
    return User(userId, name, archived)
}

fun User.toEntity(): UserEntity {
    return UserEntity(userId, name, archived)
}

fun UserWithShopsEntity.toDomain(): UserWithShops {
    return UserWithShops(user.toDomain(), shops.map { it.toDomain() })
}

fun UserWithShops.toEntity(): UserWithShopsEntity {
    return UserWithShopsEntity(user.toEntity(), shops.map { it.toEntity() })
}