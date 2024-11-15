package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
                    var st by remember { mutableStateOf("") }
                    var stuser by remember { mutableStateOf("") }
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
                                        // Check if the login is valid
                                       val isValidLogin = UsersMV.checkLogin(email, password)
                                        //Test Login data
//                                        val userslog=UsersMV.getData(email, password)
//                                        st=isValidLogin.toString()
//                                        stuser=userslog.toString()
                                        if (isValidLogin) {
                                            //change value in session model
                                            UsersMV.Login(email, password)
                                            // after login go to page
                                            val intent = Intent(this@LoginActivity, GuestActivity::class.java)
                                            startActivity(intent)
                                        } else {
                                            // Show invalid login message
                                            Toast.makeText(
                                                this@LoginActivity,
                                                "Invalid email or password",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Login")
                                }
                                TextButton(onClick={
                                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                                    startActivity(intent)
                                }) {

                                    //test login
/*                                    Text(text = st)
                                    Text(text = stuser)*/
                                    Text(text = " Register ")}
                            }
                        }
                    }
                }
            }

    }
}
