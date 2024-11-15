package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.finalproject.DB.AppDatabase
import com.example.finalproject.Model.Users
import com.example.finalproject.ModelView.UserViewModel
import com.example.finalproject.ui.theme.AppTheme

class RegisterActivity : ComponentActivity() {

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
                // local context variable
                val context = LocalContext.current
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
                            var firstName by remember { mutableStateOf("") }
                            var lastName by remember { mutableStateOf("") }
                            var email by remember { mutableStateOf("") }
                            var password by remember { mutableStateOf("") }
                            var phoneNumber by remember { mutableStateOf("") }
                            var gender by remember { mutableStateOf("") }
                            var birthday by remember { mutableStateOf("") }

                            TextField(
                                value = firstName,
                                onValueChange = { firstName = it },
                                label = { Text("First Name") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            )

                            TextField(
                                value = lastName,
                                onValueChange = { lastName = it },
                                label = { Text("Last Name") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            )

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

                            TextField(
                                value = phoneNumber,
                                onValueChange = { phoneNumber = it },
                                label = { Text("Phone Number") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            )

                            TextField(
                                value = gender,
                                onValueChange = { gender = it },
                                label = { Text("Gender") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            )

                            TextField(
                                value = birthday,
                                onValueChange = { birthday = it },
                                label = { Text("Birthday") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                            )

                            Button(
                                onClick = {
                                    try {
                                        val user = Users(
                                            firstName = firstName,
                                            lastName = lastName,
                                            email = email,
                                            password = password,
                                            phoneNumber = phoneNumber,
                                            gender = gender,
                                            birthday = birthday
                                        )
                                        UsersMV.addUser(user)
                                        val intent = Intent(context, LoginActivity::class.java)
                                        context.startActivity(intent)
                                        Log.d("LoginActivity", "User registered successfully")
                                    } catch (e: Exception) {
                                        Log.e("LoginActivity", "Error registering user", e)
                                    }
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

