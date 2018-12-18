package com.schoolsupport.app.dmt91.schoolsupport.supportClass

import android.arch.lifecycle.LiveData

inline fun <T> android.arch.lifecycle.MutableLiveData<T>.notifiChange () {
    this.value = this.value
}