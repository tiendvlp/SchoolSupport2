package com.schoolsupport.app.dmt91.schoolsupport.model

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class MemberWrite {
    private val mDatabase : DatabaseReference
    private lateinit var callback : Event
    constructor() {
        mDatabase = FirebaseDatabase.getInstance().reference.child("Member")
    }

    fun addNewMember (member : MemberModel) {
        mDatabase.child(member.Id).setValue(member)
    }

    fun addNewMember (member: MemberModel, callback : Event) {
        this.callback = callback
        mDatabase.child(member.Id).setValue(member).addOnCompleteListener(::onCompleteListener)
                .addOnCanceledListener (::onCancelListener)
                .addOnFailureListener(::onFailedListener)
    }

    private fun onCompleteListener (task : Task<Void>) {
        if (task.isComplete) {
            if (task.isSuccessful) {
                this.callback.onSuccessListener(task)
                return
            }
        }

        this.callback.onFailedListener(task.exception)
    }

    private fun onFailedListener (task : Exception) {
        this.callback.onFailedListener(task)
    }

    private fun onCancelListener () {
        this.callback.onFailedListener(null)
    }

    data class Event (var onSuccessListener: (Task<Void>) -> Unit, var onFailedListener: (Exception?) -> Unit)

}