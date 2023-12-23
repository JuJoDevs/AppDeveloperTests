package com.jujodevs.appdevelopertests.ui.screens.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jujodevs.appdevelopertests.data.local.UserDao
import com.jujodevs.appdevelopertests.data.remote.mapper.toUser
import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.appdevelopertests.ui.navigation.NavArg
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    dao: UserDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = UserDetailUiState(loading = true)
            savedStateHandle.get<Int>(NavArg.Id.key)?.let {
                _uiState.value = UserDetailUiState(
                    user = withContext(Dispatchers.IO) {
                        dao.getUser(it)?.toUser()
                    },
                )
            }
        }
    }
}

data class UserDetailUiState(
    val loading: Boolean = false,
    val user: User? = null
)
