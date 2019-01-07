package phamf.com.chemicalapp.Model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.schoolsupport.app.dmt91.schoolsupport.model.MemberModel
import com.schoolsupport.app.dmt91.schoolsupport.model.MemberWrite
import java.lang.Exception

class MemberRead : ValueEventListener {


    private val mDatabase : DatabaseReference
    private val mFirebaseAuth: FirebaseAuth
    private lateinit var callback : MemberRead.Event
    constructor() {
        mFirebaseAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference.child("Member")
    }

    fun readMember (userId : String, callback:Event) {
        this.callback = callback
        mDatabase.child("Member").child(userId).addValueEventListener(this)
    }

    override fun onCancelled(p0: DatabaseError) {
        callback.onReadMemberFailed(p0.toException())
    }
    override fun onDataChange(p0: DataSnapshot) {
        var member = MemberModel()
        member.Name = mFirebaseAuth.currentUser!!.displayName!!
        member.Id = mFirebaseAuth.currentUser!!.uid
        member.isActivated = true
        callback.onReadMemberSuccess(member)
    }

    data class Event (var onReadMemberSuccess : (member : MemberModel) -> Unit, var onReadMemberFailed : (e:Exception?) -> Unit)
}