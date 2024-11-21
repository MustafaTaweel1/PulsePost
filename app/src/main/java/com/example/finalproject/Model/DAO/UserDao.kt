package com.example.finalproject.Model.DAO
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.finalproject.Model.Users

@Dao
interface UserDao {
    @Query("SELECT * FROM Users")
    suspend fun getAll(): List<Users>
    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun getUserForLogin(email: String, password: String): Users?


    @Query("SELECT * FROM Users WHERE email = '4' AND password = '4'")
    suspend fun TestgetUserForLogin(): Users?

    @Query("SELECT * FROM Users WHERE email = :email")
    suspend fun getUserByEmail(email: String): Users?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(users: Users)
    
    @Update
    suspend fun updateUser(users: Users)
    
    @Delete
    suspend fun deleteUser(users: Users)
}