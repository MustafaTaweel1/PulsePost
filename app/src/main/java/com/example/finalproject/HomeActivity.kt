package com.example.finalproject

import androidx.compose.material3.ExperimentalMaterial3Api


import android.R

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.finalproject.DB.AppDatabase
import com.example.finalproject.Model.Post
import com.example.finalproject.ModelView.CommentViewModel
import com.example.finalproject.ModelView.PostViewModel
import com.example.finalproject.ModelView.SessionViewModel
import com.example.finalproject.ModelView.UserViewModel
import com.example.finalproject.PostView.InsertActivity
import com.example.finalproject.PostView.InsertPost
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
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("My App") },
                            navigationIcon = {
                                IconButton(onClick = { /* TODO */ }) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Menu"
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = { /* TODO */ }) {
                                    Icon(
                                        imageVector = Icons.Default.FavoriteBorder,
                                        contentDescription = "Mark as Favourite"
                                    )
                                }
                                IconButton(onClick = {
                                    val intent = Intent(this@HomeActivity, InsertActivity::class.java)
                                    startActivity(intent)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit Notes"
                                    )
                                }
                            }
                        )
                    },
                    content = { innerPadding ->
                        Column {
                            Greeting2(
                                modifier = Modifier.padding(innerPadding),
                                postViewModel = postViewModel,
                                commentViewModel = commentVM
                            )
                            Text(
                                    text = sessionMV.getIsLoggedIn().toString()
                                )

                            Text(
                                text = sessionMV.userState.value?.email ?: "No Email Available"                        )
                            Text(
                                text = sessionMV.userState.value?.firstName ?: "No Email Available"                        )
                            Text(
                                text = sessionMV.userState.value?.lastName ?: "No Email Available"                        )
                            Text(
                                text = "ssssssssssssssssssssssssssssssssssssssssssss"
                            )
                        }
                    }
                )
            }
        }
    }
}
@Composable
fun Greeting2(modifier: Modifier = Modifier, postViewModel: PostViewModel, commentViewModel: CommentViewModel) {
    val postList by postViewModel.posts.collectAsState()
    Column(modifier = modifier) {
        ItemPost(postList, postViewModel, commentViewModel)
    }
}

@Composable
fun ItemPost(postList: List<Post>, postViewModel: PostViewModel, commentViewModel: CommentViewModel) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(postList.sortedBy { it.title }) { post ->
            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    IconButton(onClick = {
                        postViewModel.deletePostById(post.id)
                        val intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                    Text(
                        color = MaterialTheme.colorScheme.primary,
                        text = post.title,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = post.body,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    CommentFadingInAndOutText(post.id, commentViewModel)
                }
            }

        }
    }
}
@Composable
fun CommentFadingInAndOutText(postid: Int, commentViewModel: CommentViewModel) {
    val commentlist by commentViewModel.getCommentsByPostId(postid).collectAsState(initial = emptyList())
    var isVisible by remember { mutableStateOf(false) }
    Column {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 500))
        ) {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxSize() // Ensure the parent has constrained height
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(commentlist.take(20)) { comment ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(
                                    text = comment.body,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }

            Row {
                TextField(
                    value = "",
                    onValueChange = { },
                    label = { Text("Add Comment") },
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Button(onClick = { }) {
                    Text("Submit")
                }
            }
        }
        }
        Button(onClick = { isVisible = !isVisible }) {
            Text(text = "Add Comment")
        }
    }
}
