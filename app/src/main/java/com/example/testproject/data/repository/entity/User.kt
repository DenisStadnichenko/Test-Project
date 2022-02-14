package com.example.testproject.data.repository.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    val id: Int,
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val avatar: String
): Serializable
