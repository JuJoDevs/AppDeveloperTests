package com.jujodevs.appdevelopertests.ui.screens.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jujodevs.appdevelopertests.data.remote.FakeUsers
import com.jujodevs.appdevelopertests.domain.User
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val FAKE_DELAY = 1000L
    }

    private val _uiState = MutableStateFlow(UserDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = UserDetailUiState(loading = true)
            delay(FAKE_DELAY)
            FakeUsers.users.firstOrNull { it.email == savedStateHandle.get<String>("email") }?.let {
                _uiState.value = UserDetailUiState(user = it)
            }
        }
    }
}

data class UserDetailUiState(
    val loading: Boolean = false,
    val user: User? = null
)
