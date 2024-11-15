package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.finalproject.ui.theme.FinalProjectTheme
import com.example.finalproject.ModelView.SessionViewModel
import androidx.room.Room
import com.example.finalproject.DB.AppDatabase
import com.example.finalproject.Model.Users
import com.example.finalproject.Model.Post

class MainActivity : ComponentActivity() {
    private val SessionVM by viewModels<SessionViewModel>()
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
            if (SessionVM.userState.value == null) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            FinalProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinalProjectTheme {
        Greeting("Android")
    }
}