package com.schoolsupport.app.dmt91.schoolsupport.model

import android.arch.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class MemberManager {
    val mWrite : MemberWrite
    var mFirebaseAuth : FirebaseAuth
    constructor() {
        mFirebaseAuth = FirebaseAuth.getInstance()
        mWrite = MemberWrite()
    }

    fun addNewMember (member : MemberModel) {
        mWrite.addNewMember(member)
    }

    fun addNewMember (member: MemberModel, callback : MemberWrite.Event) {
        mWrite.addNewMember(member, callback)
    }

    fun sendEmailVerification () {
        mFirebaseAuth.currentUser!!.sendEmailVerification()
    }
}