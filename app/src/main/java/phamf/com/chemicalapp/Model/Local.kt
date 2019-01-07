package phamf.com.chemicalapp.Model

import com.google.firebase.database.Exclude

class Local {
    var id:String = ""; get set
    var Name:String = ""; get set
    var Password:String = ""; get set
    var Creator:String = ""; get set
    @Exclude
    var posts:ArrayList<Post> = ArrayList()
}