package phamf.com.chemicalapp.supportClass

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

inline fun <reified F : ViewModel> Fragment.getViewModel(noinline creator: (() -> F)? = null) : F {
    if (creator == null) {
        return ViewModelProviders.of(this).get(F::class.java)
    } else {
        return ViewModelProviders.of(this, BaseViewModelFactory(creator!!)).get(F::class.java)
    }
}

inline fun <reified A : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> A)? = null) : A {
    if (creator == null) {
        return ViewModelProviders.of(this).get(A::class.java)
    } else {
        return ViewModelProviders.of(this, BaseViewModelFactory(creator!!)).get(A::class.java)
    }
}
class BaseViewModelFactory<T> (val creator:() -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator() as T
    }
}

