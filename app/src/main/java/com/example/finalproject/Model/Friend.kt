package com.example.finalproject.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Friend")
data class Friend (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val UserID: Int,
    val FrindsID: Int,
)