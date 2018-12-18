//package phamf.com.chemicalapp.Database
//
//import android.util.Log
//import android.widget.Toast
//import com.google.firebase.database.*
//
//import java.util.ArrayList
//import java.util.HashMap
//
//import phamf.com.chemicalapp.Model.Chapter
//import phamf.com.chemicalapp.Model.ChemicalEquation
//import phamf.com.chemicalapp.Model.Chemical_Element
//import phamf.com.chemicalapp.Model.DPDP
//
///**
// * We have a class named DataSnapshotConverter to convert data get from firebase to Object.
// * When we get all list from firebase, it return a ArrayList<HashMap></HashMap><String></String>, Object>> not ArrayList<Object>
// * That HashMap contain name of field of Object and its value and we have to convert it to Object that we need
//</Object> */
//
//class OnlineDatabaseManager {
//
//
//    private val DPDPs = "DPDP"
//
//
//    private val DATABASE_VERSION = "Version"
//
//
//    private val UPDATE_STATUS = "UpdateStatus"
//
//    private val isAvailable: Boolean = false
//
//    internal var mRef: DatabaseReference
//
//    internal var updateDataRef: DatabaseReference
//
//    private var onDataLoaded: OnDataLoaded? = null
//
//    init {
//        mRef = FirebaseDatabase.getInstance().reference
//        updateDataRef = mRef.child(UPDATE_DATA)
//    }
//
//
//    fun getAll_CE_Data() {
//
//        mRef.child(CHEMICAL).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val data = dataSnapshot.getValue(GenericTypeIndicator<ArrayList<ChemicalEquation>>())
//
//                val equations = ArrayList<ChemicalEquation>()
//                for (equation in data!!) {
//                    equations.add(equation)
//                }
//
//                /**
//                 * Send data to Presenter
//                 */
//                onDataLoaded!!.onCE_LoadedFromFirebase(equations)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//        })
//    }
//
//
//    fun getAll_Chapter_Data() {
//        mRef.child(CHAPTER).addChildEventListener(object : ChildEventListener {
//            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
//                /**
//                 * Send data to MainActivityPresenter
//                 */
//                onDataLoaded!!.onChapterLoadedFromFirebase(dataSnapshot.getValue(Chapter::class.java))
//            }
//
//            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
//
//            }
//
//            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//
//            }
//
//            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
//
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//        })
//    }
//
//
//    fun getAll_DPDP() {
//        mRef.child("DPDP").addChildEventListener(object : ChildEventListener {
//            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
//                /**
//                 * Send data to MainActivityPresenter
//                 */
//                onDataLoaded!!.onDPDP_LoadedFromFirebase(dataSnapshot.getValue<DPDP>(DPDP::class.java))
//            }
//
//            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
//
//            }
//
//            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//
//            }
//
//            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
//
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {}
//        })
//    }
//
//
//    fun getAllBangTuanHoan() {
//        mRef.child("PeriodicTable").addChildEventListener(object : ChildEventListener {
//            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
//                onDataLoaded!!.onChemElement_LoadedFromFirebase(dataSnapshot.getValue(Chemical_Element::class.java))
//            }
//
//            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
//
//            }
//
//            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//
//            }
//
//            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
//
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//        })
//    }
//
//
//    fun getUpdateStatus() {
//        mRef.child(UPDATE_STATUS).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                onDataLoaded!!.onStatusLoaded((dataSnapshot.value as Boolean?)!!)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//        })
//
//    }
//
//
//    fun getVersionUpdate() {
//        mRef.child(DATABASE_VERSION).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                onDataLoaded!!.onVersionLoaded((dataSnapshot.value as Long?)!!)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//        })
//    }
//
//
//    fun getNeedingUpdate_Chapter() {
//        updateDataRef.child(CHAPTER).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val indexList = dataSnapshot.value as ArrayList<Long>?
//                for (index in indexList!!) {
//                    mRef.child(CHAPTER).child(index.toString() + "").addValueEventListener(object : ValueEventListener {
//                        override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            onDataLoaded!!.onChapterLoadedFromFirebase(dataSnapshot.getValue(Chapter::class.java))
//                        }
//
//                        override fun onCancelled(databaseError: DatabaseError) {
//
//                        }
//                    })
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//        })
//    }
//
//    // Not offical
//    fun getNeedingUpdate_BangTuanHoan() {
//        updateDataRef.child(BANG_TUAN_HOAN).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val indexList = dataSnapshot.value as ArrayList<Long>?
//                for (index in indexList!!) {
//                    mRef.child(index.toString() + "").addValueEventListener(object : ValueEventListener {
//                        override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            onDataLoaded!!.onChapterLoadedFromFirebase(dataSnapshot.getValue(Chapter::class.java))
//                        }
//
//                        override fun onCancelled(databaseError: DatabaseError) {
//
//                        }
//                    })
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//        })
//    }
//
//
//    fun getNeedingUpdate_DPDP() {
//        updateDataRef.child(DPDPs).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val indexList = dataSnapshot.value as ArrayList<Long>?
//                for (index in indexList!!) {
//                    mRef.child(DPDPs).child(index.toString() + "").addValueEventListener(object : ValueEventListener {
//                        override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            onDataLoaded!!.onChapterLoadedFromFirebase(dataSnapshot.getValue(Chapter::class.java))
//                        }
//
//                        override fun onCancelled(databaseError: DatabaseError) {}
//
//                    })
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//        })
//    }
//
//
//    fun getNeedingUpdate_CE() {
//        updateDataRef.child(CHEMICAL).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val indexList = dataSnapshot.value as ArrayList<Long>?
//                val result = ArrayList<ChemicalEquation>()
//                addDataSuporting(result, indexList!!, 0)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//        })
//    }
//
//    /** This function is help adding Data to result list synchonously
//     * because addValueEventListener works follow unsynchornous mechanism, we can't use for
//     * loop to add Data to resultList
//     *
//     * We have to use recursive to do it by adding the next data when the previous value was returned
//     * from firebase
//     * and then check if the last data was added to the result list, published it to listener */
//    private fun addDataSuporting(resultList: ArrayList<ChemicalEquation>, indexList: ArrayList<Long>, i: Int) {
//        if (i < indexList.size) {
//            mRef.child(CHEMICAL).child(indexList[i].toString() + "").addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    resultList.add(dataSnapshot.getValue(ChemicalEquation::class.java)!!)
//
//                    // is that the last data
//                    if (i == indexList.size - 1) {
//                        onDataLoaded!!.onCE_LoadedFromFirebase(resultList)
//                        return
//                    }
//
//                    addDataSuporting(resultList, indexList, i + 1)
//
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//
//                }
//            })
//        }
//    }
//
//
//    fun setOnDataLoaded(onDataLoaded: OnDataLoaded) {
//        this.onDataLoaded = onDataLoaded
//    }
//
//
//    /**
//     * @see phamf.com.chemicalapp.Presenter.MainActivityPresenter
//     * which implement this interface
//     */
//
//    interface OnDataLoaded {
//
//        fun onChapterLoadedFromFirebase(chapter: Chapter?)
//
//        fun onCE_LoadedFromFirebase(equations: ArrayList<ChemicalEquation>)
//
//        fun onDPDP_LoadedFromFirebase(dpdp: DPDP?)
//
//        fun onChemElement_LoadedFromFirebase(element: Chemical_Element?)
//
//        fun onVersionLoaded(version: Long)
//
//        fun onStatusLoaded(isAvailable: Boolean)
//
//    }
//
//    companion object {
//
//
//        private val CHEMICAL = "ChemicalEquation"
//
//
//        private val CHAPTER = "Chapter"
//
//
//        private val UPDATE_DATA = "UpdateData"
//
//
//        private val BANG_TUAN_HOAN = "PediodicTable"
//    }
//
//}
//
//internal object DataSnapshotConverter {
//
//
//    fun toChemicalEquation(data: HashMap<String, Any>): ChemicalEquation {
//        val equation = ChemicalEquation()
//
//        val id = data["id"] as Long
//        val addingChemical = data["addingChemicals"] as String
//        val product = data["product"] as String
//        val condition = data["condition"] as String
//        val total_balance_number = data["total_balance_number"] as Long
//
//        equation.id = id.toInt()
//        equation.addingChemicals = addingChemical
//        equation.product = product
//        equation.condition = condition
//        equation.total_balance_number = total_balance_number.toInt()
//
//        return equation
//    }
//
//
//}
