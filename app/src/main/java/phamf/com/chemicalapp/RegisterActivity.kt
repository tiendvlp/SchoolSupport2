package com.schoolsupport.app.dmt91.schoolsupport

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.RegisterViewModel
import phamf.com.chemicalapp.databinding.RegisterLayoutBinding
import phamf.com.chemicalapp.supportClass.getViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var mBinding : RegisterLayoutBinding
    private lateinit var mViewModel : RegisterViewModel
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addControls()
        addEvents()
    }

    private fun addControls() {
        mViewModel = getViewModel()
        mViewModel.enable.value = true
        mAuth = FirebaseAuth.getInstance()
        mBinding = DataBindingUtil.setContentView(this, R.layout.register_layout)
        mBinding.mModel = mViewModel
    }

    private fun addEvents () {
        mBinding.btnRegister.setOnClickListener(::onBtnRegisterClicked)
    }

    private fun onBtnRegisterClicked (view : View) {
        loading(true)
        if (!verifyAccount()) {
            loading(false)
            return
        }
        createAccount()
    }

    private fun createAccount () {
        val password = mBinding.edtPassword.text.toString()
        val email = mBinding.edtEmail.text.toString()

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isComplete) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Đăng ký không thành công, vui lòng kiểm tra lại kết nối", Toast.LENGTH_SHORT).show();
            }
            loading(false)
        }
    }

    private fun checkRewritePassword () : Boolean{
        val password = mBinding.edtPassword.text.toString()
        val rewritePassword = mBinding.edtPasswordRetype.text.toString()
        return password.equals(rewritePassword)
    }

    private fun checkEmail () : Boolean {
        return mViewModel.verifyEmail(mBinding.edtEmail.text.toString())
    }

    private fun checkPassword () : Boolean {
        return mViewModel.verifyPassword(mBinding.edtPassword.text.toString())
    }

    private fun verifyAccount () : Boolean {
        when (false) {
            checkRewritePassword() -> Toast.makeText(this, "2 mật khẩu mà bạn nhập vào không trùng với nhau", Toast.LENGTH_SHORT).show();
            checkPassword() -> Toast.makeText(this, "Mật khẩu phải có ít nhất 8 ký tự, có một chữ số, và một ký tự viết hoa", Toast.LENGTH_SHORT).show();
            checkEmail() -> Toast.makeText(this, "Tài khoản phải là một email hợp lệ", Toast.LENGTH_SHORT).show()
            else -> return true
        }

        return false
    }

    private fun loading (isLoading : Boolean) {
        mViewModel.enable.value = !isLoading
        if (isLoading) {
            mBinding.overlay.visibility = View.VISIBLE
            mBinding.loading.visibility = View.VISIBLE
        } else {
            mBinding.overlay.visibility = View.GONE
            mBinding.loading.visibility = View.GONE
        }
    }
}