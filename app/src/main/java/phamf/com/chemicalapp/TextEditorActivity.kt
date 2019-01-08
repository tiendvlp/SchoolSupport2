package com.schoolsupport.app.dmt91.schoolsupport.View.Activity

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.chinalwb.are.strategies.ImageStrategy
import com.chinalwb.are.styles.toolitems.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import phamf.com.chemicalapp.Model.AppDataSingleton
import phamf.com.chemicalapp.Model.Post
import phamf.com.chemicalapp.Model.PostManager
import phamf.com.chemicalapp.Model.PostRead
import phamf.com.chemicalapp.Model.SupportModel.ImageStrangy
import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.TextEditorViewModel
import phamf.com.chemicalapp.databinding.ActivityTextEditorBinding
import phamf.com.chemicalapp.databinding.CommitPostsDialogLayoutBinding
import phamf.com.chemicalapp.supportClass.getViewModel
import java.io.InputStream
import java.text.DateFormat
import java.util.*


@SuppressLint("ResourceAsColor")
@TargetApi(Build.VERSION_CODES.LOLLIPOP)

class TextEditorActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityTextEditorBinding
    private lateinit var mViewModel : TextEditorViewModel
    private lateinit var mDialogBinding : CommitPostsDialogLayoutBinding
    private lateinit var mDialog : AlertDialog
    private lateinit var mDialogBuilder : AlertDialog.Builder
    private  var imageStrategy: ImageStrategy = ImageStrangy()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_editor)
        addControls()
        addEvents()
        setup()
        initToolbar()
    }
    private fun setup() {
        mDialogBuilder = AlertDialog.Builder(this)
        mDialogBuilder.setView(mDialogBinding.root)
        mDialogBuilder.setCancelable(false)
        mDialog = mDialogBuilder.create()
    }

    private fun addControls() {
        mDialogBinding = DataBindingUtil.inflate(layoutInflater, R.layout.commit_posts_dialog_layout, null, false)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_text_editor)
        mViewModel = getViewModel()
        mBinding.edtContent.fromHtml("&nbsp;&nbsp;&nbsp;&nbsp; helloooo")
    }

    private fun addEvents() {
        mBinding.btnNext.setOnClickListener{
            mDialog.show()
            mDialogBinding.txtAuthor.text = AppDataSingleton.getInstance().currentUser.Name
            mDialogBinding.txtDate.text = DateFormat.getDateInstance().format(Date())
        }

        mDialogBinding.imgAvaPosts.setOnClickListener{
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(5,5)
                    .setFixAspectRatio(true)
                    .start(this)
        }
        mDialogBinding.btnCancel.setOnClickListener({
            mDialog.dismiss()
        })
        mBinding.btnDrafts.setOnClickListener {

        }


        mDialogBinding.btnSubmit.setOnClickListener({
            val callback = PostManager.Event(::onPostThePostSuccess, ::onPostThePostFailed)
            val post = Post()
            val currentDate : Long =  System.currentTimeMillis()
            post.Content = mBinding.edtContent.html
            post.Author = AppDataSingleton.getInstance().currentUser.Name
            post.LastEdit = currentDate
            post.Growth = Integer.parseInt(mDialogBinding.edtGrowth.text.toString())
            post.PostedPost = currentDate
            post.TAG = mDialogBinding.edtTag.text.toString()
            post.Title = mDialogBinding.edtTitle.text.toString()
            post.ID = "" + System.currentTimeMillis()
            var bitmap : Bitmap = (mDialogBinding.imgAvaPosts.drawable as BitmapDrawable).bitmap
            mViewModel.postThePost(AppDataSingleton.WhereNewPostWrite,post,bitmap, callback)
        })
    }

    private fun onPostThePostSuccess () {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        mDialog.dismiss()
        finish()
    }

    private fun onPostThePostFailed (e:java.lang.Exception?) {
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        mDialog.dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
        val result : CropImage.ActivityResult  = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            val resultUri : Uri  = result.getUri()
            val readStream : InputStream = contentResolver.openInputStream(resultUri)
            val bitmap : Bitmap = BitmapFactory.decodeStream(readStream)
            mDialogBinding.imgAvaPosts.setImageBitmap(bitmap)
            mDialogBinding.imgAvaPosts.scaleType = ImageView.ScaleType.FIT_XY
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            val error : Exception = result.getError()
            Log.e("activityResult", "submit dialog get img failed: " + error.toString())
        }
    }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun initToolbar() {
        var strikethrough = ARE_ToolItem_Strikethrough()
        var quote = ARE_ToolItem_Quote()
        var bold : IARE_ToolItem= ARE_ToolItem_Bold();
        var size : IARE_ToolItem = ARE_ToolItem_FontSize()
        var listNumber = ARE_ToolItem_ListNumber()
        var listBullet = ARE_ToolItem_ListBullet()
        var hr = ARE_ToolItem_Hr()
        var link = ARE_ToolItem_Link()

        var subscript = ARE_ToolItem_Subscript()
        var superscript = ARE_ToolItem_Superscript()
        var left = ARE_ToolItem_AlignmentLeft()
        var center = ARE_ToolItem_AlignmentCenter()
        var image = ARE_ToolItem_Image()
        var video = ARE_ToolItem_Video()
        var at = ARE_ToolItem_At()
        mBinding.areToolbar.addToolbarItem(strikethrough)
        mBinding.areToolbar.addToolbarItem(quote)
        mBinding.areToolbar.addToolbarItem(listNumber)
        mBinding.areToolbar.addToolbarItem(listBullet)
        mBinding.areToolbar.addToolbarItem(hr)
        mBinding.areToolbar.addToolbarItem(link)
        mBinding.areToolbar.addToolbarItem(subscript)
        mBinding.areToolbar.addToolbarItem(superscript)
        mBinding.areToolbar.addToolbarItem(size)
        mBinding.areToolbar.addToolbarItem(bold)
        mBinding.areToolbar.addToolbarItem(left)
        mBinding.areToolbar.addToolbarItem(center)
        mBinding.areToolbar.addToolbarItem(image)
        mBinding.areToolbar.addToolbarItem(video)
        mBinding.areToolbar.addToolbarItem(at)
        mBinding.edtContent.setToolbar(mBinding.areToolbar)
        mBinding.edtContent.imageStrategy = imageStrategy
    }
}
