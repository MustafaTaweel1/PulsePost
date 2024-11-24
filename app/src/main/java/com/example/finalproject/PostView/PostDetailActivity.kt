package com.example.finalproject.PostView

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.finalproject.DB.AppDatabase
import com.example.finalproject.Model.Comment
import com.example.finalproject.ModelView.CommentViewModel
import com.example.finalproject.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PostDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve post details from intent
        val postId = intent.getIntExtra("POST_ID", -1)
        val postTitle = intent.getStringExtra("POST_TITLE") ?: "Unknown Title"
        val postBody = intent.getStringExtra("POST_BODY") ?: "No Content Available"

        // Initialize database and view model
        val db = AppDatabase.getDatabase(applicationContext)
        val commentViewModel = CommentViewModel(db.commentDao())

        setContent {
            AppTheme {
                PostDetailScreen(
                    postId = postId,
                    postTitle = postTitle,
                    postBody = postBody,
                    commentViewModel = commentViewModel
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun PostDetailScreen(
    postId: Int,
    postTitle: String,
    postBody: String,
    commentViewModel: CommentViewModel
) {
    val context = LocalContext.current
    val comments by commentViewModel.getCommentsByPostId(postId).collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Post Details", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { (context as ComponentActivity).finish() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                // Display Post Details
                Text(
                    text = postTitle,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = postBody,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )

                CommentSection(postId = postId, commentViewModel = commentViewModel, coroutineScope = coroutineScope)
            }
        }
    )
}

@Composable
fun CommentSection(postId: Int, commentViewModel: CommentViewModel, coroutineScope: CoroutineScope) {
    val comments by commentViewModel.getCommentsByPostId(postId).collectAsState(initial = emptyList())
    var commentText by remember { mutableStateOf("") }

    Column {
        Text(
            text = "Comments",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        if (comments.isEmpty()) {
            Text(
                text = "No comments yet.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(comments.size) { index ->
                    val comment = comments[index]
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = comment.body,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // Add Comment
        TextField(
            value = commentText,
            onValueChange = { commentText = it },
            label = { Text("Add Comment", style = MaterialTheme.typography.labelLarge) },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        Button(
            onClick = {
                if (commentText.isNotBlank()) {
                    coroutineScope.launch {
                        commentViewModel.addComment(comment = Comment(0, 1,postId, commentText))
                        commentText = ""
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Submit", style = MaterialTheme.typography.labelLarge)
        }
    }
}
