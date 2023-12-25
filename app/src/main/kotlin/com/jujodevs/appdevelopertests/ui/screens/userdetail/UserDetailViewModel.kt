package com.jujodevs.appdevelopertests.ui.screens.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.appdevelopertests.ui.navigation.NavArg
import com.jujodevs.appdevelopertests.usecases.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = UserDetailUiState(loading = true)
            savedStateHandle.get<Int>(NavArg.Id.key)?.let {
                _uiState.value = UserDetailUiState(user = getUserUseCase(it))
            }
        }
    }
}

data class UserDetailUiState(
    val loading: Boolean = false,
    val user: User? = null
)
