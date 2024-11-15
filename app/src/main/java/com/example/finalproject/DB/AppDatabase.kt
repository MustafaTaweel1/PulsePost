package com.example.finalproject.DB


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.finalproject.Model.DAO.PostDao
import com.example.finalproject.Model.DAO.UserDao
import com.example.finalproject.Model.Users

@Database(entities = [Users::class], version = 1)

abstract class AppDatabase: RoomDatabase(
) {
   abstract fun userDao(): UserDao
/*   abstract  fun postDao(): PostDao*/

}