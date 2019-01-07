package com.schoolsupport.app.dmt91.schoolsupport.model

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class MemberWrite {
    private val mDatabase : DatabaseReference
    private val mFirebaseAuth:FirebaseAuth
    private lateinit var callback : Event
    constructor() {
        mFirebaseAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference.child("Member")
    }

    fun addNewMember (member : MemberModel) {
        val profile = UserProfileChangeRequest.Builder()
                .setDisplayName(member.Name)
                .build()
        mFirebaseAuth.currentUser!!.updateProfile(profile)
    }

    fun addNewMember (member: MemberModel, callback : Event) {
        this.callback = callback
        val profile = UserProfileChangeRequest.Builder()
                .setDisplayName(member.Name)
                .build()
        mFirebaseAuth.currentUser!!.updateProfile(profile).addOnSuccessListener(callback.onSuccessListener)
                .addOnFailureListener(callback.onFailedListener)
    }

    data class Event (var onSuccessListener: (Void) -> Unit, var onFailedListener: (Exception?) -> Unit)

}