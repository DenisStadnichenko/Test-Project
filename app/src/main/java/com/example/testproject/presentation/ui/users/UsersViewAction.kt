package com.example.testproject.presentation.ui.users

import com.example.testproject.data.repository.entity.User

sealed class UsersViewAction {
    data class UserClick(val user:User) : UsersViewAction()
}
