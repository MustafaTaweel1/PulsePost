package com.example.finalproject.ModelView

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.finalproject.Model.DAO.UserDao
import com.example.finalproject.Model.Users
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SessionViewModel() : ViewModel() {
    private var _userState = MutableStateFlow<Users?>(null)
    val userState: StateFlow<Users?> = _userState
    private var _isLoggedIn = mutableStateOf(false)
    val isLoggedIn = _isLoggedIn

    fun setUserState(user: Users?) {
        _userState.value = user
    }
    fun setisLoggedIn(bol: Boolean) {
        _isLoggedIn.value = bol
    }
    fun getUserState():Users?{
        return _userState.value
    }
    fun getIsLoggedIn(): Boolean {
        return _isLoggedIn.value
    }

/*    fun loginUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = userDao.getUserForLogin(email, password)
            if (user != null) {
                _userState.value = user
                _isLoggedIn.value = true
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }*/
//    fun getIsLogin():Boolean{
//        return _isLoggedIn.value
//    }


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