package com.example.testproject.data.repository.entity

import com.google.gson.annotations.SerializedName

data class UsersPageResponse(
    val page:Int,
    @SerializedName("per_page")
    val perPage:Int,
    val total:Int,
    @SerializedName("total_page")
    val totalPage:Int,
    val data:List<User>,

)
