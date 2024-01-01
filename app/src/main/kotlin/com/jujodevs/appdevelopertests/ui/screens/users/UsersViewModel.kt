package com.jujodevs.appdevelopertests.ui.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.jujodevs.appdevelopertests.usecases.GetPagingUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getPagingUsersUseCase: GetPagingUsersUseCase
) : ViewModel() {

    var userPagingFlow = getPagingUsersUseCase().cachedIn(viewModelScope)

    fun findUsers(text: String) {
        userPagingFlow = getPagingUsersUseCase()
            .map { pagingData ->
                pagingData.filter {
                    it.name.contains(text, true) || it.email.contains(text, true)
                }
            }
            .cachedIn(viewModelScope)
    }
}
