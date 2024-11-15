package com.example.finalproject.DB


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.finalproject.Model.DAO.PostDao
import com.example.finalproject.Model.DAO.UserDao
import com.example.finalproject.Model.Users
import com.example.finalproject.Model.Post

@Database(entities = [Users::class, Post::class], version = 2, exportSchema = false)

abstract class AppDatabase: RoomDatabase(
) {
   abstract fun userDao(): UserDao
   abstract fun postDao(): PostDao

   companion object {
      const val DATABASE_NAME = "SocialDB"
   }

   fun getDatabaseName(): String {
      return DATABASE_NAME
   }
}