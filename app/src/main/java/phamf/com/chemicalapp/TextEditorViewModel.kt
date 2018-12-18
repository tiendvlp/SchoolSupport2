package phamf.com.chemicalapp

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.database.Observable
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.schoolsupport.app.dmt91.schoolsupport.model.Posts

class TextEditorViewModel : ViewModel, android.databinding.Observable {
    override fun removeOnPropertyChangedCallback(callback: android.databinding.Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: android.databinding.Observable.OnPropertyChangedCallback?) {
    }

    var btnBoldClickCount = MutableLiveData<Boolean>()
    var btnFontSizeClickCount = MutableLiveData<Boolean>()
    var btnInsertImgClickCount = MutableLiveData<Boolean>()
    var btnDotClickCount = MutableLiveData<Boolean>()
    var btnNumbericClickCount = MutableLiveData<Boolean>()
    var btnNextClickCount = MutableLiveData<Boolean>()
    var edtContentClickCount = MutableLiveData<Boolean>()

    var mposts : MutableLiveData<Posts> = MutableLiveData()
    @Bindable get
    private set

    constructor() : super (){
        mposts.value = Posts()
        btnBoldClickCount.value = false
        btnDotClickCount.value = false
        btnNumbericClickCount.value = false
    }

    fun setmposts (mposts : Posts) {
        this.mposts.value = mposts
    }
}