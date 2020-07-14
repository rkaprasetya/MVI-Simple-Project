package com.raka.mviminiproject.data.repository

import com.raka.mviminiproject.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getUsers() = apiHelper.getUsers()
}