package com.cloneversion.foody.badguy.foody.view.activity

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityResetPasswordBinding
    private lateinit var mAuth : FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        addControls()
        addEvents()
    }

    private fun addEvents() {
        mAuth = FirebaseAuth.getInstance()
        mBinding.btnResetPassword.setOnClickListener(::onBtnResetClick)
    }

    private fun addControls() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password)
    }

    private fun onBtnResetClick (v: View?) {
        resetPassword()
    }

    private fun resetPassword () {
        mAuth.sendPasswordResetEmail(mBinding.edtEmail.text.toString()).addOnCompleteListener {
            if (it.isComplete) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Đã gửi email xác nhận đổi mật khẩu", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Gửi email thất bại vui lòng kiểm tra lại tài khoản hoặc đường truyển internet của bạn", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
