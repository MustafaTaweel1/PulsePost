package com.example.finalproject.ModelView

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.finalproject.Model.DAO.UserDao
import com.example.finalproject.Model.Users

class SessionViewModel(private val userDao: UserDao) : ViewModel() {
    private val _userState = mutableStateOf<Users?>(null)
    val userState = _userState

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val user = userDao.getUserForLogin(email, password)
            _userState.value = user
        }
    }

    fun logoutUser() {
        _userState.value = null
    }
}