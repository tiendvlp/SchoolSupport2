package com.schoolsupport.app.dmt91.schoolsupport.model

import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception
import javax.inject.Inject

class Signin {
    private val mAuth : FirebaseAuth
    private val mMemManager : MemberManager

    private lateinit var it : Task<AuthResult>
    private lateinit var callback: Event

    @Inject
    constructor( ) {
        mAuth = FirebaseAuth.getInstance()
        mMemManager = MemberManager()
    }

    fun signinWithEmailAndPassword (email:String, password : String, callback : Event) {
        this.callback = callback
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener (::onAuthCompleteListener)
    }

    fun signinWithCredential (credential: AuthCredential, callback : Event) {
        this.callback = callback
        mAuth.signInWithCredential(credential).addOnCompleteListener (::onAuthCompleteListener)
                .addOnCanceledListener (::onAuthCancelListener)
                .addOnFailureListener(::onAuthFailedListener)
    }

    private fun onAuthCompleteListener (task : Task<AuthResult>) {
        if (task.isComplete) {
            if (task.isSuccessful) {
                if (!task.result!!.additionalUserInfo.isNewUser) {
                    callback.onSuccessListener(task)
                    return
                }
                this.it = task
                val member = MemberModel()
                member.Id = task.result!!.user.uid
                member.Avatar = "user.png"
                member.Name = task.result!!.user.email!!
                mMemManager.addNewMember(member, MemberWrite.Event(::onWriteMemberSuccess, ::onWriteMemberFailed))
                return
            }
        }
        callback.onFailedListener(task.exception)
    }

    private fun onAuthFailedListener (task:Exception) {
        this.callback.onFailedListener(task)
    }

    private fun onAuthCancelListener () {
        this.callback.onFailedListener(null)
    }

    private fun onWriteMemberSuccess (it : Task<Void>) {
        if (it.isComplete) {
            if (it.isSuccessful) {
                callback.onSuccessListener(this.it)
                return
            }
        }
        callback.onFailedListener(it.exception)
    }

    private fun onWriteMemberFailed (it : Exception?) {
        callback.onFailedListener(it)
    }

    data class Event (var onSuccessListener: (Task<AuthResult>) -> Unit, var onFailedListener: (Exception?) -> Unit)
}
