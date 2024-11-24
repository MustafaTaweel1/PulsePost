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
import com.example.finalproject.DB.AppDatabase
import com.example.finalproject.ModelView.SessionViewModel
import com.example.finalproject.ModelView.UserViewModel
import com.example.finalproject.PostView.HomeActivity
import com.example.finalproject.ui.theme.AppTheme

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Setting up the database
        val db = AppDatabase.getDatabase(applicationContext)

        val userDao = db.userDao()
        val sessionMV = SessionViewModel()
        val userMV = UserViewModel(userDao)
        setContent {
            AppTheme {
                // local context variable
                val context = LocalContext.current
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize()) {

                        // Background image with 80% opacity
                        Image(
                            painter = painterResource(id = R.drawable.backgroundv2),
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
                                label = { Text("Email", style = MaterialTheme.typography.labelMedium) },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                    cursorColor = MaterialTheme.colorScheme.primary,
                                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                                ),
                                shape = MaterialTheme.shapes.medium,
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(16.dp))



                            TextField(
                                value = password,
                                onValueChange = { password = it },
                                label = { Text("Password", style = MaterialTheme.typography.labelMedium) },
                                visualTransformation = PasswordVisualTransformation(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                    cursorColor = MaterialTheme.colorScheme.primary,
                                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                                ),
                                shape = MaterialTheme.shapes.medium,
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(16.dp))


                            Button(
                                onClick = {
                                    // Check if login credentials are valid using SessionViewModel

                                    userMV.loginUser(email, password) { isSuccess ->
                                        if (isSuccess) {
                                            val user = userMV.getUserData(email, password)
                                            sessionMV.setUserState(user)
                                            sessionMV.setIsLoggedIn(true)
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
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.tertiary,
                                    contentColor = MaterialTheme.colorScheme.onTertiary
                                ),
                                shape = MaterialTheme.shapes.large,
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 4.dp,
                                    pressedElevation = 8.dp
                                )
                            ) {
                                Text("Login", style = MaterialTheme.typography.labelLarge) // Use theme-defined typography
                            }

// Register TextButton with Material3 style
                            TextButton(
                                onClick = {
                                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                                    startActivity(intent)
                                },
                                modifier = Modifier.padding(vertical = 8.dp),
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = MaterialTheme.colorScheme.primary // Theme-defined primary color
                                )
                            ) {
                                Text(
                                    text = "Register",
                                    style = MaterialTheme.typography.labelMedium // Use theme-defined typography
                                )

                            }

                        }
                    }
                }
            }
        }
    }
}
