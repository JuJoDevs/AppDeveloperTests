package com.jujodevs.appdevelopertests.ui.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.jujodevs.appdevelopertests.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.map

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var userPagingFlow = userRepository.pagingUser().cachedIn(viewModelScope)

    fun findUsers(text: String) {
        userPagingFlow = userRepository
            .pagingUser()
            .map { pagingData ->
                pagingData.filter {
                    it.name.contains(text, true) || it.email.contains(text, true)
                }
            }
            .cachedIn(viewModelScope)
    }
}
