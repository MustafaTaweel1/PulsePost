package com.example.finalproject.DB


import android.content.Context
import androidx.room.Database
import androidx.room.Room
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
      @Volatile
      private var INSTANCE: AppDatabase? = null

      fun getDatabase(context: Context): AppDatabase {
         return INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(context, AppDatabase::class.java, "SocialDB")
               .build().also { INSTANCE = it }
         }
      }

            var DATABASE_NAME = "SocialDB"
   }

   fun getDatabaseName(): String {
      return DATABASE_NAME
   }
}