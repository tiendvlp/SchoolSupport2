package phamf.com.chemicalapp.Model

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.UploadTask
import java.lang.Exception

class PostManager {
    private val mWrite = PostWrite()
    private val mStorageUploader = FirebaseStorageUploader()
    private lateinit var postThePostCallback : Event

    private lateinit var contentPostThePost : Post
    private lateinit var bitmap: Bitmap
    private lateinit var where:String
    fun postThePost (where:String, post: Post, bitmap : Bitmap, callback : Event) {
        this.where = where
        this.bitmap = bitmap
        this.contentPostThePost = post
        this.postThePostCallback = callback
        val uploadCallback = FirebaseStorageUploader.Event(::onUploadPostContentSuccess, ::onUploadPostContentFailed)
        mStorageUploader.uploadPostContent(this.contentPostThePost.ID, contentPostThePost.Content, uploadCallback)
    }

    private fun onWritePostSuccess (task:Task<Void>) {
        postThePostCallback.onPostThePostSuccess()
    }

    private fun onWritePostFailed (e:Exception?) {
        postThePostCallback.onPostThePostFailed(e)
    }

    private fun onUploadPostContentSuccess (result: UploadTask.TaskSnapshot) {
        val uploadCallback = FirebaseStorageUploader.Event(::onUploadPostAvatarSuccess, ::onUploadPostAvatarFailed)
        mStorageUploader.uploadPostAvatar(contentPostThePost.ID, bitmap, uploadCallback)
    }

    private fun onUploadPostContentFailed (e:Exception?) {
        postThePostCallback.onPostThePostFailed(e)
    }

    private fun onUploadPostAvatarSuccess (result: UploadTask.TaskSnapshot) {
         result.storage.downloadUrl.addOnSuccessListener {
             val writeCallback = PostWrite.Event(::onWritePostSuccess, ::onWritePostFailed)
             contentPostThePost.Avatar = it.toString()
             contentPostThePost.Content = ""
             mWrite.writeNewPost(where,contentPostThePost,writeCallback)
        }
    }

    private fun onUploadPostAvatarFailed (e:Exception?) {
        postThePostCallback.onPostThePostFailed(e)
    }

    data class Event (var onPostThePostSuccess : () -> Unit,
                      var onPostThePostFailed : (e:Exception?) -> Unit)
}