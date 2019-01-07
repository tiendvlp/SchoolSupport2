package phamf.com.chemicalapp.Model

import com.schoolsupport.app.dmt91.schoolsupport.model.MemberModel


class AppDataSingleton {
    companion object {
        private val instance:AppDataSingleton = AppDataSingleton()
         val ACTION_CREATE_NEW_WORLD_POST = 1
         val ACTION_CREATE_NEW_LOCAL_POST = 2
         val ACTION_CREATE_NEW_LOCAL = 3
         var btnCreateAction  = ACTION_CREATE_NEW_WORLD_POST
         var WhereNewPostWrite : String = "WorldPost"
         var currentFragment = 0
        fun getInstance () = instance
    }

    private constructor() {

        currentUser = MemberModel()
    }

    lateinit var currentUser : MemberModel
}