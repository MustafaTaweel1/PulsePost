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
    /*    val post= postDao.getAllPost()
    private val _post = mutableListOf<String>()

    val userlist:List<String>get() = _post

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
    fun getPostsByUserWithComments(userId: String) = postDao.getPostByUserWithComments(userId)
    fun getallpost() = postDao.getAllPost()
    fun getPostWithComments() = postDao.getPostWithComments()
    fun getPostWithFriends(userId: Int) = postDao.getPostWithFriends(userId)
*/
    }

