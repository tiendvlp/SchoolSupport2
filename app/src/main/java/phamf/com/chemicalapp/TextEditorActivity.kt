package com.schoolsupport.app.dmt91.schoolsupport.View.Activity

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.databinding.DataBindingUtil
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.Toast
import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.TextEditorViewModel
import phamf.com.chemicalapp.databinding.ActivityTextEditorBinding
import phamf.com.chemicalapp.databinding.CommitPostsDialogLayoutBinding
import phamf.com.chemicalapp.supportClass.getViewModel
import phamf.com.chemicalapp.supportClass.setDash
import phamf.com.chemicalapp.supportClass.setTab

@SuppressLint("ResourceAsColor")
@TargetApi(Build.VERSION_CODES.LOLLIPOP)

class TextEditorActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityTextEditorBinding
    private lateinit var mViewModel : TextEditorViewModel
    private lateinit var mDialogBinding : CommitPostsDialogLayoutBinding
    private lateinit var mDialog : AlertDialog
    private lateinit var mDialogBuilder : AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_editor)
        addControls()
        addEvents()
        setup()
    }

    private fun setup() {
        mDialogBuilder.setView(mDialogBinding.root)
        mDialogBuilder.setCancelable(false)
        mDialog = mDialogBuilder.create()
    }

    private fun addControls() {
        mDialogBinding = DataBindingUtil.inflate(layoutInflater, R.layout.commit_posts_dialog_layout, null, false)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_text_editor)
        mViewModel = getViewModel()
        mViewModel.mposts
        mBinding.edtContent.setFontSize(fontsizeList[0])
        mDialogBuilder = AlertDialog.Builder(this)
        mBinding.edtContent.setIndent()
        mBinding.edtContent.html = "&nbsp;&nbsp;&nbsp;&nbsp; helloooo"
        mBinding.edtContent.setIndent()
    }

    private fun addEvents() {
        mBinding.btnFontSize.setOnClickListener(::onBtnFontsizeClicked)
        mBinding.btnInsertImg.setOnClickListener(::onBtnInsertImgClicked)
        mBinding.btnNext.setOnClickListener(::onBtnNextClicked)
        mBinding.btnNumberic.setOnCheckedChangeListener(::onBtnNumbericCheckedChanged)
        mBinding.btnAlignment.setOnClickListener(::onBtnAlignmentClicked)
        mBinding.btnBullet.setOnCheckedChangeListener(::onBtnBulletCheckedChanged)
        mBinding.btnHorLine.setOnClickListener(::onBtnHorLineClicked)
        mBinding.btnTab.setOnClickListener(::onBtnTabClicked)
        mBinding.btnSubText.setOnCheckedChangeListener(::onBtnSubTextCheckedChanged)
        mBinding.btnSupText.setOnCheckedChangeListener(::onBtnSupCheckedChanged)
        mDialogBinding.btnCancel.setOnClickListener({
            mDialog.dismiss()
        })
        mDialogBinding.btnSubmit.setOnClickListener({
            Toast.makeText(this, mViewModel.mposts.value!!.TAG, Toast.LENGTH_SHORT).show()
        })
    }

    private fun onBtnSubTextCheckedChanged (view:CompoundButton?, checked: Boolean) {
//        if (mBinding.btnSupText.isChecked) {
//            view!!.isChecked = false
//            mBinding.edtContent.setSubscript()
//            mBinding.btnSupText.isChecked = false
//            return
//        }
        mBinding.edtContent.setSubscript()

//        if (!checked) {
////            mBinding.btnSupText.isChecked = false
////            mBinding.edtContent.setSuperscript()
////            return
//            mBinding.edtContent.addView(LayoutInflater.from(this).inflate(R.layout.commit_posts_dialog_layout, null, false))
//        } else {
//            mBinding.edtContent.setSubscript()
//        }
    }

    private fun onBtnSupCheckedChanged (view:CompoundButton?, checked: Boolean) {
    //        if (mBinding.btnSubText.isChecked) {
    //            view!!.isChecked = false
    //            mBinding.edtContent.setSuperscript()
    //            mBinding.btnSubText.isChecked = false
    //            mBinding.edtContent.set
    //            return
    //        }
    //
    //        if (!checked) {
    //            mBinding.edtContent.setSubscript()
    //            mBinding.btnSubText.isChecked = false
    //            return
    //        }
    mBinding.edtContent.setSuperscript()
//        if (checked) {
//            this.mBinding.edtContent.html = this.mBinding.edtContent.html
//        } else {
//            this.mBinding.edtContent.html = ""
//        }
    }

    private fun onBtnAlignmentClicked (view:View?) {
    }

    private fun onBtnTabClicked (view:View?) {
        mBinding.edtContent.setTab()
    }

    private fun onBtnNextClicked (view: View?) {
        mDialog.show()
    }

    private fun onBtnHorLineClicked (view: View?) {
        mBinding.edtContent.setDash()
    }

    private var currentFontsizeIndex = 0
    private var fontsizeList : Array<Int> = arrayOf(5,6,7)
    private fun onBtnFontsizeClicked (view: View?) {
        currentFontsizeIndex ++
        if (currentFontsizeIndex >= fontsizeList.size) {
            currentFontsizeIndex = 0
        }
        mBinding.edtContent.setFontSize(fontsizeList[currentFontsizeIndex])
    }
    private fun onBtnNumbericCheckedChanged (view:CompoundButton?, checked: Boolean) {
        if (checked) {
            keepCheckOneUnCheckAll(view as CheckBox, mBinding.checkGrFormat)
        }
        mBinding.edtContent.setNumbers()
    }

    private fun onBtnBulletCheckedChanged (view:CompoundButton?, checked: Boolean) {
        if (checked) {
            keepCheckOneUnCheckAll(view as CheckBox, mBinding.checkGrFormat)
        }
        mBinding.edtContent.setBullets()
        }

    private fun onBtnInsertImgClicked (view: View?) {

    }

    private fun keepCheckOneUnCheckAll (btn:CheckBox, group:ViewGroup) {
        for (i in 0..group.childCount) {
            var v : CheckBox? = group.getChildAt(i) as? CheckBox
            if (v!=btn) {
                v?.isChecked = false
            }
        }
    }
}
