package com.raka.mviminiproject.data.api

import com.raka.mviminiproject.data.model.User

class ApiHelperImpl(private val apiService: ApiService):ApiHelper {
    override suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }
}