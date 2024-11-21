package com.example.finalproject.PostView

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.finalproject.DB.AppDatabase
import com.example.finalproject.Model.Post
import com.example.finalproject.ModelView.PostViewModel
import com.example.finalproject.ui.theme.AppTheme

class InsertActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

        val postDao = db.postDao()
        val postVM = PostViewModel(postDao)  // ViewModel should handle DB interaction

        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    InsertPost(
                        modifier = Modifier.padding(innerPadding),
                        postVM = postVM
                    )
                }
            }
        }
    }
}

@Composable
fun InsertPost(modifier: Modifier = Modifier, postVM: PostViewModel) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxWidth()) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )
        TextField(
            value = body,
            onValueChange = { body = it },
            label = { Text("Body") }
        )
        Button(
            onClick = {
                // Add the post to the database asynchronously via ViewModel
                postVM.addPost(Post(0, title, body, 1))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Post")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    AppTheme {
//        Greeting3("Android",)
    }
}