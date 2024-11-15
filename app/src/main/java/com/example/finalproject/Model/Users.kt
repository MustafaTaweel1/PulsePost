package com.example.finalproject.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class Users(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val gender: String,
    val birthday: String


    )

