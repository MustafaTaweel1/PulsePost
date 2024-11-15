package com.example.finalproject.Model.DAO
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.finalproject.Model.Users
@Dao
interface UserDao {
    @Query("Select * FROM Users")
    fun getAll(): List<Users>

    @Query("SELECT * FROM Users WHERE email = :email AND password = :password LIMIT 1")
    fun getUserForLogin(email: String, password: String): Users?

    @Query("SELECT * FROM Users WHERE email = :email")
    fun getUserByEmail(email: String): Users?

    @Insert
    suspend fun insertUser(users: Users)




}