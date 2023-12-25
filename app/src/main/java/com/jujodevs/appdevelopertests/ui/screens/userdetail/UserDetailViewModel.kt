package com.jujodevs.appdevelopertests.ui.screens.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jujodevs.appdevelopertests.data.repository.UserRepository
import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.appdevelopertests.ui.navigation.NavArg
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = UserDetailUiState(loading = true)
            savedStateHandle.get<Int>(NavArg.Id.key)?.let {
                _uiState.value = UserDetailUiState(user = userRepository.getUser(it))
            }
        }
    }
}

data class UserDetailUiState(
    val loading: Boolean = false,
    val user: User? = null
)
