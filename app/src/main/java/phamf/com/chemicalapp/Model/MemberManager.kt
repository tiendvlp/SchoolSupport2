package com.schoolsupport.app.dmt91.schoolsupport.model

import android.arch.lifecycle.ViewModel

class MemberManager {
    val mWrite : MemberWrite

    constructor() {
        mWrite = MemberWrite()
    }

    fun addNewMember (member : MemberModel) {
        mWrite.addNewMember(member)
    }

    fun addNewMember (member: MemberModel, callback : MemberWrite.Event) {
        mWrite.addNewMember(member, callback)
    }
}