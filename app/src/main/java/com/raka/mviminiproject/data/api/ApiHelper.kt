package com.raka.mviminiproject.data.api

import com.raka.mviminiproject.data.model.User

interface ApiHelper {
    suspend fun getUsers():List<User>
}