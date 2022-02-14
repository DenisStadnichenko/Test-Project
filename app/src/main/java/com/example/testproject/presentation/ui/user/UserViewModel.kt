package com.example.testproject.presentation.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.data.repository.Repository
import com.example.testproject.data.repository.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor() : ViewModel() {

    private val _viewState =
        MutableSharedFlow<UserViewState>(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)
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