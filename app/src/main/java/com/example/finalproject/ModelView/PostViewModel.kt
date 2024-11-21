package com.example.finalproject.ModelView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.DAO.PostDao
import com.example.finalproject.Model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
class PostViewModel(private val postDao: PostDao) : ViewModel() {
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _posts.value = postDao.getAllPost()
        }
    }

    fun addPost(post: Post) {
        viewModelScope.launch {
            postDao.insertPost(post)
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            postDao.deletePost(post)
        }
    }

    fun deletePostById(id: Int) {
        viewModelScope.launch {
            postDao.deletePostById(id)
        }
    }

    fun getPostsByUser(userId: Int) = postDao.getPostByUser(userId)
}
