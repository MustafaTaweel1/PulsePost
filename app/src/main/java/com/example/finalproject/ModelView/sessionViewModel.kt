package com.example.finalproject.ModelView

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.finalproject.Model.DAO.UserDao
import com.example.finalproject.Model.Users

class SessionViewModel(private val userDao: UserDao) : ViewModel() {
    private val _userState = mutableStateOf<Users?>(null)
    public val userState = _userState

    private val _isLoggedIn = mutableStateOf(false)
//    public val isLoggedIn = _isLoggedIn

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val user = userDao.getUserForLogin(email, password)
            _userState.value = user
            _isLoggedIn.value = true
        }
    }

    fun isUserLoggedIn(): Boolean {
        return _isLoggedIn.value
    }

    fun logoutUser() {
        _userState.value = null
        _isLoggedIn.value = false
    }

}


/*
*
class SessionViewModel(private val userDao: UserDao) : ViewModel() {
    private val _userState = mutableStateOf<Users?>(null)
    public val userState = _userState

    private val _isLoggedIn = mutableStateOf(false)
//    public val isLoggedIn = _isLoggedIn

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val user = userDao.getUserForLogin(email, password)
            _userState.value = user
            _isLoggedIn.value = true
        }
    }

    fun isUserLoggedIn(): Boolean {
        return _isLoggedIn.value
    }

    fun logoutUser() {
        _userState.value = null
        _isLoggedIn.value = false
    }
}*/