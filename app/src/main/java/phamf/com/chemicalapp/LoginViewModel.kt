package phamf.com.chemicalapp

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.schoolsupport.app.dmt91.schoolsupport.model.Signin
import com.schoolsupport.app.dmt91.schoolsupport.model.VerifyAccount

class LoginViewModel : ViewModel{
    private var mSignin : Signin
    private var mVerifyAccount : VerifyAccount
    var enable : MutableLiveData<Boolean> = MutableLiveData()
    constructor() {
        mSignin = Signin()
        mVerifyAccount = VerifyAccount()
    }

    fun siginWithEmailandPassword (email:String, password:String, callback : Signin.Event ) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            mSignin.signinWithEmailAndPassword(email, password, callback)
        } else {
            callback.onFailedListener(null)
        }
    }

    fun signinWithCredential (credential : AuthCredential, callback: Signin.Event) {
        mSignin.signinWithCredential(credential, callback)
    }
}