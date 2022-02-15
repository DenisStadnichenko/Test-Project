package com.example.testproject.presentation.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.data.repository.Repository
import com.example.testproject.data.repository.entity.User
import com.example.testproject.presentation.ui.user.UserViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: Repository
) : ViewModel() {

    private val _viewState = Channel<UsersViewState>(Channel.CONFLATED)
    val viewState: Flow<UsersViewState> = _viewState.receiveAsFlow()

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
                    _viewState.send(UsersViewState.ShowProgress(false))
                    _viewState.send(UsersViewState.Users(users))
                }
            }, { error ->
                viewModelScope.launch {
                    _viewState.send(UsersViewState.ShowProgress(false))
                    _viewState.send(UsersViewState.Error(error))
                }
            })
        }
    }

    private fun openUserScreen(user: User) {
        viewModelScope.launch {
            _viewState.send(UsersViewState.OpenUserScreen(user))
        }
    }
}