package com.jujodevs.appdevelopertests.usecases

import com.jujodevs.appdevelopertests.domain.repository.UserRepositoryContract
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepositoryContract
) {
    suspend operator fun invoke(id: Int) = userRepository.getUser(id)
}
