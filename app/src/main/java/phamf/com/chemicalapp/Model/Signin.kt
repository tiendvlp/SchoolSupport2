package com.schoolsupport.app.dmt91.schoolsupport.model

import android.util.Log
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import phamf.com.chemicalapp.Model.AppDataSingleton
import java.lang.Exception
import javax.inject.Inject

class Signin : ValueEventListener{


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
        this.it = task
        if (task.isComplete) {
            if (task.isSuccessful) {
                FirebaseDatabase.getInstance().reference
                        .child("Member")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .addListenerForSingleValueEvent(this)
            }
        }
        callback.onFailedListener(task.exception)
    }

    override fun onCancelled(p0: DatabaseError) {
    }

    override fun onDataChange(p0: DataSnapshot) {
        if (p0.child("isActivated").value == null) {
            this.callback.onUserNotVerifyAccount()
            val member = AppDataSingleton.getInstance().currentUser
            member.Id = it.result!!.user.uid
            return
        } else {
            callback.onSuccessListener(it)
            return
        }
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
                AppDataSingleton.getInstance().currentUser.Id
                callback.onSuccessListener(this.it)
                return
            }
        }
        callback.onFailedListener(it.exception)
    }

    private fun onWriteMemberFailed (it : Exception?) {
        callback.onFailedListener(it)
    }

    data class Event (var onSuccessListener: (Task<AuthResult>) -> Unit,
                      var onFailedListener: (Exception?) -> Unit,
                      var onUserNotVerifyAccount: () -> Unit)
}
