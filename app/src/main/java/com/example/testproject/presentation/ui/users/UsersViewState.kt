package com.example.testproject.presentation.ui.users

import com.example.testproject.data.repository.entity.User

sealed class UsersViewState {
    object BackPressed : UsersViewState()
    data class OpenUserScreen(val user: User) : UsersViewState()
    data class ShowProgress(val show: Boolean) : UsersViewState()
    data class Error(val error: String) : UsersViewState()
    data class Users(val users: List<User>) : UsersViewState()
}


