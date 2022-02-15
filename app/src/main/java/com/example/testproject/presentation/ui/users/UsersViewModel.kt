package com.example.testproject.presentation.ui.users

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
class UsersViewModel @Inject constructor(
    private val userRepository: Repository
) : ViewModel() {

    private val _viewState = MutableSharedFlow<UsersViewState>()
    val viewState: SharedFlow<UsersViewState> = _viewState.asSharedFlow()

    init {
        getUsers()
    }

    fun processAction(action: UsersViewAction) {
        when (action) {
            is UsersViewAction.UserClick -> openUserScreen(action.user)
        }
    }

    private fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getUser({ users ->
                viewModelScope.launch {
                    _viewState.emit(UsersViewState.ShowProgress(false))
                    _viewState.emit(UsersViewState.Users(users))
                }
            }, { error ->
                viewModelScope.launch {
                    _viewState.emit(UsersViewState.ShowProgress(false))
                    _viewState.emit(UsersViewState.Error(error))
                }
            })
        }
    }

    private fun openUserScreen(user: User) {
        viewModelScope.launch {
            _viewState.emit(UsersViewState.OpenUserScreen(user))
        }
    }
}