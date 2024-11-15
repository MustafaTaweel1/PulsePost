package com.example.finalproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.finalproject.DB.AppDatabase
import com.example.finalproject.ui.theme.AppTheme
import com.example.finalproject.Model.Users
import com.example.finalproject.ModelView.UserViewModel

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            enableEdgeToEdge()

            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, AppDatabase.DATABASE_NAME
            ).fallbackToDestructiveMigration() .build()
            val userDao = db.userDao()
            val UsersMV = UserViewModel(userDao)

            setContent {
                AppTheme {
                    var email by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }

                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Box(modifier = Modifier.fillMaxSize()) {

                            Image(
                                painter = painterResource(id = R.drawable.background),
                                contentDescription = "Background Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                alpha = 0.8f
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {

                                TextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    label = { Text("Email") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp)
                                )

                                TextField(
                                    value = password,
                                    onValueChange = { password = it },

                                    label = { Text("Password") },
                                    //visualTransformation = PasswordVisualTransformation(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp)
                                )
                                Button(
                                    onClick = {

                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Register")
                                }
                            }
                        }
                    }
                }
            }

    }
}
