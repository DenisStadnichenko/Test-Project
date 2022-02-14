package com.example.testproject.presentation.ui.user

import com.example.testproject.data.repository.entity.User

sealed class UserViewAction {
    object BackPressed: UserViewAction()
}
