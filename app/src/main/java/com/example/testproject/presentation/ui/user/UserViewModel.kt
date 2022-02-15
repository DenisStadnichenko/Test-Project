package com.example.testproject.presentation.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.data.repository.Repository
import com.example.testproject.data.repository.entity.User
import com.example.testproject.presentation.ui.users.UsersViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor() : ViewModel() {

    private val _viewState = MutableSharedFlow<UserViewState>()
    val viewState: SharedFlow<UserViewState> = _viewState.asSharedFlow()

    fun processAction(action: UserViewAction) {
        when (action) {
            is UserViewAction.BackPressed -> onBackPressed()
        }
    }

    private fun onBackPressed() {
        viewModelScope.launch {
            _viewState.emit(UserViewState.BackPressed)
        }
    }
}