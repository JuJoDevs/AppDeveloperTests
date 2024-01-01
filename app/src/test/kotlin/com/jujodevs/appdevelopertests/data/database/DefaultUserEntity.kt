package com.jujodevs.appdevelopertests.data.database

object DefaultUserEntity {
    private val userEntity = UserEntity(
        id = 1,
                page = 1,
                email = "test1@example.com",
                name = "test1",
                picture = "https://randomuser.me/api/portraits/men/1.jpg",
                gender = "male",
                registered = "2012-12-12",
                cell = "1234567890",
                latitude = "0",
                longitude = "0",
    )
    fun getUserEntity(id: Int) = userEntity.copy(
        id = id,
        email = "test$id@example.com",
        name = "test$id",
        picture = "https://randomuser.me/api/portraits/men/$id.jpg",
    )
}
