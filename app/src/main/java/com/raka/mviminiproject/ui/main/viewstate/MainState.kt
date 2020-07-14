package com.raka.mviminiproject.ui.main.viewstate

import com.raka.mviminiproject.data.model.User

sealed class MainState {
    object idle : MainState()
    object loading : MainState()
    data class Users(val user:List<User>):MainState()
    data class Error(val error:String?) :MainState()
}