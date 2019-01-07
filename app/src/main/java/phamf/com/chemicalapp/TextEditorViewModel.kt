package phamf.com.chemicalapp

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.Bindable
import android.graphics.Bitmap
import phamf.com.chemicalapp.Model.Post
import phamf.com.chemicalapp.Model.PostManager

class TextEditorViewModel : ViewModel, android.databinding.Observable {
    override fun removeOnPropertyChangedCallback(callback: android.databinding.Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: android.databinding.Observable.OnPropertyChangedCallback?) {
    }

    var btnBoldClickCount = MutableLiveData<Boolean>()
    var btnDotClickCount = MutableLiveData<Boolean>()
    var btnNumbericClickCount = MutableLiveData<Boolean>()
    private var postManager = PostManager()
    var mposts : MutableLiveData<Post> = MutableLiveData()
    @Bindable get
    private set

    constructor() : super () {
        mposts.value = Post()
        btnBoldClickCount.value = false
        btnDotClickCount.value = false
        btnNumbericClickCount.value = false
    }

    fun postThePost (where:String,post:Post, bitmap:Bitmap, callback:PostManager.Event) {
        postManager.postThePost(where,post, bitmap, callback)
    }
}