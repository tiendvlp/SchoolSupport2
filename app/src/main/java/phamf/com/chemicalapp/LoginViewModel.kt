package phamf.com.chemicalapp

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import com.google.firebase.auth.AuthCredential
import com.google.firebase.storage.UploadTask
import com.schoolsupport.app.dmt91.schoolsupport.model.*
import phamf.com.chemicalapp.Model.AppDataSingleton
import phamf.com.chemicalapp.Model.FirebaseStorageUploader
import java.lang.Exception

class LoginViewModel : ViewModel{
    private var mSignin : Signin
    private var mVerifyAccount : VerifyAccount
    private var mMemberManager:MemberManager
    private var mStorageUploader : FirebaseStorageUploader
    var enable : MutableLiveData<Boolean> = MutableLiveData()
    var currentUser: MutableLiveData<MemberModel> = MutableLiveData()

    constructor() {
        mStorageUploader = FirebaseStorageUploader()
        mSignin = Signin()
        mMemberManager = MemberManager()
        mVerifyAccount = VerifyAccount()
        currentUser.value = AppDataSingleton.getInstance().currentUser
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
    private lateinit var addNewMemberCallback : LoginViewModel.Event
    fun addNewMember (bitmap: Bitmap, callback:LoginViewModel.Event) {
         this.addNewMemberCallback = callback
         var member = AppDataSingleton.getInstance().currentUser
         val avatarUploadCallback = FirebaseStorageUploader.Event(::onUploadAvatarSuccess, ::onUploadAvatarFailed)
         mStorageUploader.uploadUserAvatar(member.Id, bitmap, avatarUploadCallback)
    }

    fun onUploadAvatarSuccess (result: UploadTask.TaskSnapshot) {
        val infoUploadCallback = MemberWrite.Event(::onUploadInfoSuccess, ::onUploadInfoFailed)
        mMemberManager.addNewMember(AppDataSingleton.getInstance().currentUser, infoUploadCallback)
    }

    fun onUploadAvatarFailed (e:Exception?) {
        addNewMemberCallback.onCreateUserFailed(e)
    }

    fun onUploadInfoSuccess (v:Void?) {
        mMemberManager.sendEmailVerification()
        addNewMemberCallback.onCreateUserSuccess()
    }

    fun onUploadInfoFailed (e:Exception?) {
        addNewMemberCallback.onCreateUserFailed(e)
    }

    data class Event (var onCreateUserSuccess:() -> Unit, var onCreateUserFailed : (e:Exception?) -> Unit)
}