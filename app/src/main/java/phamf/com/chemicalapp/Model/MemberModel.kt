package com.schoolsupport.app.dmt91.schoolsupport.model

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MemberModel {
     var Name : String = ""; get set
     var Avatar : String = ""; get set
     var Id : String = ""; get set
     var Posts : ArrayList<String> = ArrayList(); get set
     var likedPosts : ArrayList<String> = ArrayList(); get set
     var isActivated : Boolean = false; get set
}