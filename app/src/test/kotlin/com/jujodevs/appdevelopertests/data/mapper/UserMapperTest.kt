package com.jujodevs.appdevelopertests.data.mapper

import com.jujodevs.appdevelopertests.data.database.UserEntity
import com.jujodevs.appdevelopertests.data.network.dto.CoordinatesDto
import com.jujodevs.appdevelopertests.data.network.dto.InfoDto
import com.jujodevs.appdevelopertests.data.network.dto.LocationDto
import com.jujodevs.appdevelopertests.data.network.dto.NameDto
import com.jujodevs.appdevelopertests.data.network.dto.PictureDto
import com.jujodevs.appdevelopertests.data.network.dto.RegisteredDto
import com.jujodevs.appdevelopertests.data.network.dto.UserDto
import com.jujodevs.appdevelopertests.data.network.dto.UsersDto
import com.jujodevs.appdevelopertests.domain.User
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class UserMapperTest {

    private val expectedUser = User(
        id = 0,
        email = "test1@example.com",
        name = "test1 test1",
        picture = "https://randomuser.me/api/portraits/men/1.jpg",
        gender = "male",
        registered = "2020-01-01T00:00:00.000Z",
        cell = "666666666",
        latitude = "0.0",
        longitude = "0.0"
    )

    private val expectedUserEntity = UserEntity(
        id = 0,
        page = 1,
        email = "test1@example.com",
        name = "test1 test1",
        picture = "https://randomuser.me/api/portraits/men/1.jpg",
        gender = "male",
        registered = "2020-01-01T00:00:00.000Z",
        cell = "666666666",
        latitude = "0.0",
        longitude = "0.0"
    )

    private val expectedUserDto = UserDto(
        gender = "male",
        name = NameDto(first = "test1", last = "test1"),
        location = LocationDto(coordinates = CoordinatesDto(latitude = "0.0", longitude = "0.0")),
        email = "test1@example.com",
        registered = RegisteredDto("2020-01-01T00:00:00.000Z"),
        cell = "666666666",
        picture = PictureDto(large = "https://randomuser.me/api/portraits/men/1.jpg"),
    )


    @Test
    fun `GIVEN User WHEN toUserEntity THEN return UserEntity`() {
        val result = expectedUser.toUserEntity(1)
        result shouldBeEqualTo expectedUserEntity
    }

    @Test
    fun `GIVEN User list WHEN toUserEntity THEN return UserEntity list`() {
        val users = listOf(expectedUser)
        val result = users.toUserEntity(1)
        result shouldBeEqualTo listOf(expectedUserEntity)
    }

    @Test
    fun `GIVEN UserDto WHEN dtoToUser THEN return User`() {
        val result = expectedUserDto.dtoToUser()
        result shouldBeEqualTo expectedUser
    }

    @Test
    fun `GIVEN UserDto list WHEN dtoToUser THEN return User list`() {
        val users = listOf(expectedUserDto)
        val result = users.dtoToUser()
        result shouldBeEqualTo listOf(expectedUser)
    }

    @Test
    fun `GIVEN UsersDto WHEN dtoToUser THEN return User list`() {
        val usersDto = UsersDto(listOf(expectedUserDto), InfoDto(1, 1))
        val result = usersDto.dtoToUser()
        result shouldBeEqualTo listOf(expectedUser)
    }

    @Test
    fun `GIVEN UserEntity WHEN entityToUser THEN return User`() {
        val result = expectedUserEntity.copy(id = 1).entityToUser()
        result shouldBeEqualTo expectedUser.copy(id = 1)
    }
}
