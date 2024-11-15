package com.example.finalproject.Model.DAO
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.finalproject.Model.Post
@Dao
interface PostDao {
    @Query("Select * FROM Post")
    fun getAllPost(): List<Post>
    //GET POST BY User
    @Query("SELECT * FROM Post WHERE userId = :userId ")
    fun getPostByUser(userId: Int): Post?

/*    //GET POST BY User WITH COMMENT
    @Query("SELECT * FROM Post JOIN Comment ON Post.id = Comment.PostID WHERE userId = :userId ")
    fun getPostByUserWithComments(userId: String): Post?*/

    //INSERT POST
    @Insert
    suspend fun insertPost(Post: Post)
    //DELETE POST
    @Delete
    suspend fun deletePost(Post: Post)

    // DELETE POST BY ID
    @Query("DELETE FROM Post WHERE id = :id")
    suspend fun deletePostById(id: Int)
    // GET ALL POST WITH COMMENTS
/*    @Query("SELECT * FROM Post JOIN Comment ON Post.id = Comment.PostID ")
    fun getPostWithComments(): List<Post>*/
    // GET POST WITH FRIENDS
/*
    @Query("SELECT * FROM Post JOIN Friend ON Post.userId = Friend.FrindsID  Where Friend.UserID = :userId ")
    fun getPostWithFriends(userId: Int): List<Post>

*/




}