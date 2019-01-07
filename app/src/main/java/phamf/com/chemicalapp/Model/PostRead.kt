package phamf.com.chemicalapp.Model

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PostRead {
    private val mDatabase = FirebaseDatabase.getInstance().reference
    private lateinit var mFastestGrowthEvent : MultipleValueEvent
    private lateinit var mNewestPostEvent : MultipleValueEvent
    fun getFastestGrowthPost (where:String,endAt:Int, callback:MultipleValueEvent) {
        mFastestGrowthEvent = callback
            mDatabase.child(where)
                    .orderByChild("growth")
                    .limitToLast(4)
                    .endAt(endAt.toDouble(), "growth")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            callback.onFailed(p0.toException())
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            var data : ArrayList<Post> = ArrayList()
                            p0.children.forEach({
                                var post:Post = it.getValue(Post::class.java)!!
                                data.add(post)
                            })
                            callback.onSuccess(data)
                        }
                    })
    }

    fun getNewestPost (where: String, endAt: Long, callback: MultipleValueEvent) {
        this.mNewestPostEvent = callback
        if (where.equals("WorldPost")) {
            mDatabase.child("WorldPost")
                    .orderByChild("postedPost")
                    .limitToLast(4)
                    .startAt(endAt.toDouble(), "postedPost")
                    .addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {
                            callback.onFailed(p0.toException())
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            var data : ArrayList<Post> = ArrayList()
                            p0.children.forEach({
                                var post:Post = it.getValue(Post::class.java)!!
                                data.add(post)
                            })
                            callback.onSuccess(data)
                        }

                    })

        }
    }

    data class SingleValueEvent (var onSuccess:(post:Post)-> Unit, var onFailed : (e:Exception?)-> Unit)
    data class MultipleValueEvent (var onSuccess:(listPost:ArrayList<Post>)-> Unit, var onFailed : (e:Exception?)-> Unit)

}
