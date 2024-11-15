package com.example.finalproject.Model.DAO
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.finalproject.Model.Users

@Dao
interface UserDao {
    @Query("SELECT * FROM Users")
    suspend fun getAll(): List<Users>

    @Query("SELECT * FROM Users WHERE email = :email AND password = :password LIMIT 1") 
    suspend fun getUserForLogin(email: String, password: String): Users?

    @Query("SELECT * FROM Users WHERE email = :email")
    suspend fun getUserByEmail(email: String): Users?

    @Insert
    suspend fun insertUser(users: Users)
    
    @Update
    suspend fun updateUser(users: Users)
    
    @Delete
    suspend fun deleteUser(users: Users)
}