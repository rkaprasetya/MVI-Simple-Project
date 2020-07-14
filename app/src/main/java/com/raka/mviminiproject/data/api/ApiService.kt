package com.raka.mviminiproject.data.api

import com.raka.mviminiproject.data.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers():List<User>
}