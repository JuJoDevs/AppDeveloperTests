package com.jujodevs.appdevelopertests.ui.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.jujodevs.appdevelopertests.data.local.UserEntity
import com.jujodevs.appdevelopertests.data.remote.mapper.toUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.map

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val pager: Pager<Int, UserEntity>
) : ViewModel() {

    var userPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toUser() }
        }
        .cachedIn(viewModelScope)

    fun findUsers(text: String) {
        userPagingFlow = pager
            .flow
            .map { pagingData ->
                pagingData.filter {
                    it.name.contains(text, true) || it.email.contains(text, true)
                }.map { it.toUser() }
            }
            .cachedIn(viewModelScope)
    }
}
