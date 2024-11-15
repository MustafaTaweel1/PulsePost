package com.example.finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.finalproject.ui.theme.FinalProjectTheme
import com.example.finalproject.ModelView.SessionViewModel
import androidx.room.Room
import com.example.finalproject.DB.AppDatabase
import com.example.finalproject.Model.Users


class LoginActivity : ComponentActivity() {
    private val sessionViewModel by viewModels<SessionViewModel>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        val userDao = db.userDao()
//        val postDao = db.postDao()
        val users: List<Users> = userDao.getAll()


        setContent {
            FinalProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        var email by remember { mutableStateOf("") }
                        var password by remember { mutableStateOf("") }

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
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        )

                        Button(
                            onClick = {
                                sessionViewModel.loginUser(email, password)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Login")
                        }
                    }
                }
            }
        }
    }
}