package com.jujodevs.appdevelopertests.data.network

import com.jujodevs.appdevelopertests.data.network.dto.CoordinatesDto
import com.jujodevs.appdevelopertests.data.network.dto.LocationDto
import com.jujodevs.appdevelopertests.data.network.dto.NameDto
import com.jujodevs.appdevelopertests.data.network.dto.PictureDto
import com.jujodevs.appdevelopertests.data.network.dto.RegisteredDto
import com.jujodevs.appdevelopertests.data.network.dto.UserDto

object DefaultUserDto {
    val userDto = UserDto(
        gender = "male",
        name = NameDto(first = "TestF", last = "TestL"),
        location = LocationDto(coordinates = CoordinatesDto(latitude = "0.0", longitude = "0.0")),
        email = "test@example.com",
        registered = RegisteredDto("2020-01-01T00:00:00.000Z"),
        cell = "666666666",
        picture = PictureDto(large = "https://randomuser.me/api/portraits/men/1.jpg"),
    )
}
