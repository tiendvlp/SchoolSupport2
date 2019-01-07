package phamf.com.chemicalapp.Model

import android.graphics.Bitmap
import com.google.firebase.database.Exclude

class Post
{
    var Author : String = ""; get set
    var Clap : Int = 0; get set
    var Avatar:String=""; get set
    var Content : String = ""; get set
    var ID : String = ""; get set
    var LastEdit : Long = 0; get set
    var PostedPost : Long = 0; get set
    var TAG : String = ""; get set
    var Title : String = ""; get set
    var Growth : Int = 500; get set
    var View: Int = 0; get set
}