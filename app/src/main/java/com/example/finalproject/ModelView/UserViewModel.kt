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
    private val _users = MutableStateFlow<List<Users>>(emptyList())
    val users: StateFlow<List<Users>> = _users.asStateFlow()

    init {
        viewModelScope.launch {
            _users.value = userDao.getAll()
        }
    }

//    private val _user = mutableListOf<String>()
//
//    val userlist:List<String>get() = _user
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

    fun checkLogin(email: String, password: String): Boolean {
        var isValidLogin = false
        viewModelScope.launch {
            val user = userDao.getUserForLogin(email, password)

            if(user!=null) {
                isValidLogin = true
            }


           /* isValidLogin = user != null*/
        }
        return isValidLogin
    }
/*    fun getData(email: String,password: String):Users?{
        var user:Users?=null
        viewModelScope.launch {
            user = userDao.getUserForLogin(email, password)
        }
        return user
    }
    */
    fun Login(email: String, password: String) {
        viewModelScope.launch {
            val user = userDao.getUserForLogin(email, password)
            if (user != null) {
                _users.value = listOf(user)
            }
        }
    }
}
