package com.example.finalproject

import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.finalproject.DB.AppDatabase
import com.example.finalproject.Model.Comment
import com.example.finalproject.Model.Post
import com.example.finalproject.ModelView.CommentViewModel
import com.example.finalproject.ModelView.PostViewModel
import com.example.finalproject.ModelView.SessionViewModel
import com.example.finalproject.ModelView.UserViewModel
import com.example.finalproject.PostView.InsertActivity
import com.example.finalproject.ui.theme.AppTheme
@OptIn(ExperimentalMaterial3Api::class)
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getDatabase(applicationContext)

        val postDao = db.postDao()
        val postViewModel = PostViewModel(postDao)
        val userDao = db.userDao()
        val sessionMV = SessionViewModel()
        val userMV = UserViewModel(userDao)
        val commentDao = db.commentDao()
        val commentVM = CommentViewModel(commentDao)

        setContent {
            AppTheme {
                var deleteState by remember { mutableStateOf(false) }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Post List", style = MaterialTheme.typography.titleLarge) },
                            navigationIcon = {
                                IconButton(onClick = { /* TODO */ }) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Menu",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = {
                                    deleteState = !deleteState
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Mark as Delete",
                                        tint = if (deleteState) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                                    )
                                }
                                IconButton(onClick = {
                                    val intent = Intent(this@HomeActivity, InsertActivity::class.java)
                                    startActivity(intent)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit Notes",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        )
                    },
                    content = { innerPadding ->
                        Column(modifier = Modifier.padding(innerPadding)) {
                            Greeting2(
                                postViewModel = postViewModel,
                                commentViewModel = commentVM,
                                deletestat=deleteState
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting2(modifier: Modifier = Modifier, postViewModel: PostViewModel, commentViewModel: CommentViewModel,deletestat:Boolean) {
    val postList by postViewModel.posts.collectAsState()
    Column(modifier = modifier) {
        ItemPost(postList, postViewModel, commentViewModel, deletestat)
    }
}

@Composable
fun ItemPost(postList: List<Post>, postViewModel: PostViewModel, commentViewModel: CommentViewModel, deletestat:Boolean) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(postList.sortedBy { it.title }) { post ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    if (deletestat) {
                        IconButton(onClick = {
                            postViewModel.deletePostById(post.id)
                            val intent = Intent(context, HomeActivity::class.java)
                            context.startActivity(intent)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                    Text(
                        color = MaterialTheme.colorScheme.primary,
                        text = post.title,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = post.body,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    CommentFadingInAndOutText(post.id, commentViewModel)
                }
            }
        }
    }
}

@Composable
fun CommentFadingInAndOutText(postid: Int, commentViewModel: CommentViewModel) {
    val commentList by commentViewModel.getCommentsByPostId(postid).collectAsState(initial = emptyList())
    var isVisible by remember { mutableStateOf(false) }
    var commentText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = { isVisible = !isVisible },
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Text(text = if (isVisible) "Hide Comments" else "Show Comments", style = MaterialTheme.typography.labelLarge)
        }

        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 500))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (commentList.isEmpty()) {
                        item {
                            Text(
                                text = "No comments available.",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    } else {
                        items(commentList) { comment ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainerHighest)
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Text(
                                        text = comment.body,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                                val comment = Comment(0, 1, postid, commentText)
                                commentViewModel.addComment(comment)
                                commentText = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text("Submit", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}
