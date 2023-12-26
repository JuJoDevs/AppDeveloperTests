package com.jujodevs.appdevelopertests.usecases

import com.jujodevs.appdevelopertests.domain.repository.UserRepositoryContract
import javax.inject.Inject

class GetPagingUsersUseCase @Inject constructor(
    private val userRepository: UserRepositoryContract
) {
    operator fun invoke() = userRepository.pagingUser()
}
