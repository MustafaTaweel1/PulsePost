package com.example.finalproject.Model.DAO
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.finalproject.Model.Comment
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Query("SELECT * FROM Comment WHERE PostID = :PostID")
    fun getCommentByPostID(PostID: Int): Flow<List<Comment>>

    @Insert
    suspend fun insertComment(Comment: Comment)

    @Delete
    suspend fun deleteComment(Comment: Comment)
    @Query("UPDATE Comment SET body = :body WHERE id = :id")
    suspend fun updateComment(id: Int, body: String)


}