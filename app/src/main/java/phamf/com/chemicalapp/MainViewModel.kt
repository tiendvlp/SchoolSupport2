package phamf.com.chemicalapp

import android.arch.lifecycle.ViewModel
import phamf.com.chemicalapp.Model.MemberRead

class  MainViewModel : ViewModel() {
    private val mMemberRead : MemberRead = MemberRead()

     fun getMember (userId:String, callback:MemberRead.Event) {
        mMemberRead.readMember(userId, callback)
    }
}