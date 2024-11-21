package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.finalproject.DB.AppDatabase
import com.example.finalproject.ui.theme.AppTheme
import com.example.finalproject.ModelView.SessionViewModel
import com.example.finalproject.ModelView.UserViewModel

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Setting up the database
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
        val userDao = db.userDao()
        val sessionMV = SessionViewModel()
        val userMV = UserViewModel(userDao)
        setContent {
            AppTheme {
                // local context variable
                val context = LocalContext.current
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize()) {

                        // Background image with 80% opacity
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

                            // Email input field
                            TextField(
                                value = email,
                                onValueChange = { email = it },
                                label = { Text("Email") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            )

                            // Password input field
                            TextField(
                                value = password,
                                onValueChange = { password = it },
                                label = { Text("Password") },
                                visualTransformation = PasswordVisualTransformation(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            )

                            // Login button
                            Button(
                                onClick = {
                                    // Check if login credentials are valid using SessionViewModel
                                    sessionMV.setisLoggedIn(true)

                                    userMV.loginUser(email, password) { isSuccess ->
                                        if (isSuccess) {
                                           var user = userMV.getUserData(email, password)
                                            val intent = Intent(context, HomeActivity::class.java)
                                            context.startActivity(intent)
                                        } else {
                                            // If login failed, show a Toast message
                                            Toast.makeText(
                                                context,
                                                "Invalid email or password",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Login")
                            }

                            // Register button to navigate to Registration screen
                            TextButton(onClick = {
                                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                                startActivity(intent)
                            }) {
                                Text(text = "Register")
                            }
                        }
                    }
                }
            }
        }
    }
}
