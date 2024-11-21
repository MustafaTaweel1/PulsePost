package com.example.finalproject.PostView

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.finalproject.DB.AppDatabase
import com.example.finalproject.Model.Post
import com.example.finalproject.ModelView.PostViewModel
import com.example.finalproject.ui.theme.AppTheme

class InsertActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getDatabase(applicationContext)

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
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Page Title
        Text(
            text = "Create a Post",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 16.dp)
        )


        // Title Input Field
        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
            },
            label = { Text("Title") },
            placeholder = { Text("Enter your post title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                // لون خلفية الحاوية عند التركيز
                // Background color when the field is focused

                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                // لون خلفية الحاوية عندما يكون غير نشط
                // Background color when the field is unfocused

                cursorColor = MaterialTheme.colorScheme.primary,
                // لون مؤشر الكتابة داخل مربع النص
                // Color of the cursor inside the text field

                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                // لون النص عندما يكون مربع النص في حالة التركيز
                // Text color when the field is focused

                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                // لون النص عندما يكون مربع النص غير نشط
                // Text color when the field is unfocused

                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                // لون الخط السفلي أو الحد عند التركيز
                // Bottom line or border color when the field is focused

                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                // لون الخط السفلي أو الحد عندما يكون غير نشط
                // Bottom line or border color when the field is unfocused
            ),
            shape = MaterialTheme.shapes.medium,
            singleLine = true,
        )

        // Body Input Field
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp) // Adjust height for multi-line input
                .padding(bottom = 16.dp)
        ) {
            OutlinedTextField(
                value = body,
                onValueChange = {
                    body = it
                    showError = false
                },
                label = { Text("Body") },
                placeholder = { Text("Write your post content here...") },
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
                modifier = Modifier.fillMaxSize(),
            )
        }



        // Submit Button
        Button(
            onClick = {
                    postVM.addPost(Post(0, title, body, 1))
                    title = ""
                    body = ""

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = MaterialTheme.shapes.large,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            )
        ) {
            Text(
                text = "Submit Post",
                style = MaterialTheme.typography.bodyLarge
            )
        }


        // Extra Information Text
        Text(
            text = "course Mobile Computing",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    AppTheme {
//        Greeting3("Android",)
    }
}