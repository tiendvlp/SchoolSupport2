package phamf.com.chemicalapp

import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.chinalwb.are.glidesupport.GlideApp
import com.google.firebase.storage.FirebaseStorage
import phamf.com.chemicalapp.Model.AppDataSingleton
import phamf.com.chemicalapp.Model.Post
import phamf.com.chemicalapp.databinding.ActivityReadPageBinding

class ReadPageActivity : AppCompatActivity() {
    companion object {
        lateinit var readingPost:Post
    }
    private lateinit var mBinding:ActivityReadPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_page)
        addControls()
        addEvent()
        loadAuthorAvatar()
        loadContent()
    }

    private fun addControls() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_read_page)
        mBinding.setLifecycleOwner(this)
    }

    private fun addEvent() {

    }

    private fun loadContent() {
        FirebaseStorage.getInstance().reference.child("Post/${readingPost.ID}/content")
                .getBytes(1024*1024)
                .addOnSuccessListener {
                    var str = String(it)
                    Log.d("stringfrombyte", str)
                    mBinding.edtContent.fromHtml(str)
                }.addOnFailureListener({
                    Log.d("stringfrombyte", it.toString())
                })
    }

    private fun loadAuthorAvatar () {
        val id = AppDataSingleton.getInstance().currentUser.Id
        FirebaseStorage.getInstance().reference.child("User/$id/Avatar.png").getBytes(1024*1024)
                .addOnSuccessListener {
                    var bitmap : Bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                    mBinding.imgAuthorAvatar.setImageBitmap(bitmap)
                }
    }


}
