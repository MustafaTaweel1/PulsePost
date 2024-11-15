package com.example.finalproject.ModelView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.DAO.UserDao
import com.example.finalproject.Model.Users
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao) : ViewModel() {
    val user= userDao.getAll()
    private val _user = mutableListOf<String>()

    val userlist:List<String>get() = _user
    fun addUser(user: Users) {
        viewModelScope.launch {
            val existingUser = userDao.getUserByEmail(user.email)
            if (existingUser == null) {
                userDao.insertUser(user)
            } else {
                // Handle case where email already exists
                // Could throw exception or return false/error message
            }
        }
    }

}