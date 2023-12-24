package com.jujodevs.appdevelopertests.data.remote.mapper

import com.jujodevs.appdevelopertests.data.local.UserEntity
import com.jujodevs.appdevelopertests.data.remote.dto.UserDto
import com.jujodevs.appdevelopertests.data.remote.dto.UsersDto
import com.jujodevs.appdevelopertests.domain.User

fun UserDto.toUserEntity(page: Int) = UserEntity(
    id = 0,
    page = page,
    email = email,
    name = "${name.first} ${name.last}",
    picture = picture.large,
    gender = gender,
    cell = cell,
    latitude = location.coordinates.latitude,
    longitude = location.coordinates.longitude,
    registered = registered.date,
)

fun UsersDto.toUserEntity(page: Int) = results.toUserEntity(page)
fun List<UserDto>.toUserEntity(page: Int) = map { it.toUserEntity(page) }

fun UserEntity.toUser() = User(
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

fun List<UserEntity>.toUser() = map { it.toUser() }
