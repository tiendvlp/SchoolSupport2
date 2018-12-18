package phamf.com.chemicalapp

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.Bindable
import com.schoolsupport.app.dmt91.schoolsupport.model.Posts
import com.schoolsupport.app.dmt91.schoolsupport.model.VerifyAccount

class RegisterViewModel : ViewModel {
    private val mVerify : VerifyAccount
    var enable : MutableLiveData<Boolean> = MutableLiveData()
    constructor() {
        mVerify = VerifyAccount()
    }

    fun verifyPassword (password : String) : Boolean {
        return mVerify.verifyPassword(password)
    }

    fun verifyEmail (email : String) : Boolean {
        return mVerify.verifyEmail(email)
    }

    fun verify (email: String, password: String) : Boolean {
        return verify(email, password)
    }

}