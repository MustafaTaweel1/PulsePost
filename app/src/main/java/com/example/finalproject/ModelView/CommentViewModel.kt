package com.example.finalproject.ModelView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.DAO.CommentDao
import com.example.finalproject.Model.Comment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
class CommentViewModel(private val commentDao: CommentDao) : ViewModel() {
    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

//    fun loadComments(postId: Int) {
//        viewModelScope.launch {
//            _comments.value = commentDao.getCommentByPostID(postId)
//        }
//    }

    fun addComment(comment: Comment) {
        viewModelScope.launch {
            commentDao.insertComment(comment)
        }
    }

    fun deleteComment(comment: Comment) {
        viewModelScope.launch {
            commentDao.deleteComment(comment)
        }
    }

    fun updateComment(id: Int, body: String) {
        viewModelScope.launch {
            commentDao.updateComment(id, body)
        }
    }

    fun getCommentsByPostId(postId: Int): Flow<List<Comment>> = commentDao.getCommentByPostID(postId)
}
