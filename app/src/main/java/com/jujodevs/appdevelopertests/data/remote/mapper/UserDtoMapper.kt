package com.jujodevs.appdevelopertests.data.remote.mapper

import com.jujodevs.appdevelopertests.data.remote.dto.UserDto
import com.jujodevs.appdevelopertests.domain.User

fun UserDto.toDomain() = User(
    email = email,
    first = name.first,
    last = name.last,
    picture = picture.large,
    gender = gender,
    cell = cell,
    latitude = location.coordinates.latitude,
    longitude = location.coordinates.longitude,
    registered = registered.date,
)

fun List<UserDto>.toDomain() = map { it.toDomain() }
