package com.jujodevs.appdevelopertests.data.mapper

import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.appdevelopertests.data.database.UserEntity
import com.jujodevs.appdevelopertests.data.network.dto.UserDto
import com.jujodevs.appdevelopertests.data.network.dto.UsersDto

fun User.toUserEntity(page: Int) = UserEntity(
    id = 0,
    page = page,
    email = email,
    name = name,
    picture = picture,
    gender = gender,
    cell = cell,
    latitude = latitude,
    longitude = longitude,
    registered = registered,
)
fun List<User>.toUserEntity(page: Int) = map { it.toUserEntity(page) }

fun UserDto.dtoToUser() = User(
    id = 0,
    email = email,
    name = "${name.first} ${name.last}",
    picture = picture.large,
    gender = gender,
    cell = cell,
    latitude = location.coordinates.latitude,
    longitude = location.coordinates.longitude,
    registered = registered.date,
)

fun UsersDto.dtoToUser() = results.dtoToUser()
fun List<UserDto>.dtoToUser() = map { it.dtoToUser() }

fun UserEntity.entityToUser() = User(
    id = id,
    email = email,
    name = name,
    picture = picture,
    gender = gender,
    registered = registered,
    cell = cell,
    latitude = latitude,
    longitude = longitude,
)
