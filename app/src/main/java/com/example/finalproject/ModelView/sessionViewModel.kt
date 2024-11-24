package com.example.finalproject.ModelView

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.finalproject.Model.Users

class SessionViewModel : ViewModel() {
    // User state to track the current user
    private val _userState = MutableStateFlow<Users?>(null)
    val userState: StateFlow<Users?> = _userState.asStateFlow()

    // Boolean state to track if the user is logged in
    private var _isLoggedIn by mutableStateOf(false)
    val isLoggedIn: Boolean
        get() = _isLoggedIn

    // Set user state
    fun setUserState(user: Users?) {
        _userState.value = user
    }

    // Set login status
    fun setIsLoggedIn(isLoggedIn: Boolean) {
        _isLoggedIn = isLoggedIn
    }

    // Get user state
    fun getUserState(): Users? {
        return _userState.value
    }

    // Get login status
    fun getIsLoggedIn(): Boolean {
        return _isLoggedIn
    }
}
