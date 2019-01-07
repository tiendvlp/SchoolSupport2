package phamf.com.chemicalapp.Model

import android.graphics.Bitmap
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.lang.Error
import java.lang.Exception
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class FirebaseStorageUploader {
    private var mFirebaseStorage : FirebaseStorage
    constructor() {
        mFirebaseStorage = FirebaseStorage.getInstance()
    }

    fun uploadUserAvatar (userId:String, bitmap:Bitmap, callback:Event) {
        var baos: ByteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()
        mFirebaseStorage.getReference()
                .child("User")
                .child(userId)
                .child("Avatar.png")
                .putBytes(data)
                .addOnSuccessListener(callback.onUploadSuccess)
                .addOnFailureListener(callback.onUploadFailed)
    }

    fun uploadPostAvatar (postId:String, bitmap:Bitmap, callback:Event) {
        var baos: ByteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()
        mFirebaseStorage.getReference()
                .child("Post")
                .child(postId)
                .child("Avatar.png")
                .putBytes(data)
                .addOnSuccessListener(callback.onUploadSuccess)
                .addOnFailureListener(callback.onUploadFailed)
    }

    fun uploadPostContent (postId:String, content:String, callback: Event) {
        val data :ByteArray = content.toByteArray(StandardCharsets.UTF_8)
        mFirebaseStorage.getReference()
                .child("Post")
                .child(postId)
                .child("content")
                .putBytes(data)
                .addOnFailureListener(callback.onUploadFailed)
                .addOnSuccessListener(callback.onUploadSuccess)
    }

    data class Event (var onUploadSuccess : (UploadTask.TaskSnapshot) -> Unit,
                      var onUploadFailed : (e:Exception) -> Unit)
}