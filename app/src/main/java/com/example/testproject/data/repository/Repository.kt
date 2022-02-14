package com.example.testproject.data.repository

import com.example.testproject.data.repository.entity.User
import com.example.testproject.presentation.di.Api
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: Api
) {

    suspend fun getUser(success: (List<User>) -> Unit, error: (String) -> Unit) {
        val response = api.getVideos("1")
        if (response.code() != 200) {
            error.invoke("Error")
        } else {
            success.invoke(response.body()?.data ?: emptyList())
        }
    }
}
