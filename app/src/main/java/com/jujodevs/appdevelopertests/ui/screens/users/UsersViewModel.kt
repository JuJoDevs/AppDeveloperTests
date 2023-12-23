package com.jujodevs.appdevelopertests.ui.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jujodevs.appdevelopertests.data.remote.FakeUsers
import com.jujodevs.appdevelopertests.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class UsersViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val FAKE_DELAY = 1000L
    }

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            delay(FAKE_DELAY)
            _state.value = UiState(users = FakeUsers.users)
        }
    }
}

data class UiState(
    val loading: Boolean = false,
    val users: List<User> = emptyList()
)
