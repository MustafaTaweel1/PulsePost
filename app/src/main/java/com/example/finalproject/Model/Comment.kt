package com.example.finalproject.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Comment")
data class Comment (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val UserID: Int,
    val PostID: Int,
    val body: String,
)