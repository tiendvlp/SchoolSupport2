package phamf.com.chemicalapp

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import phamf.com.chemicalapp.Model.Post

class PostViewModel: ViewModel {
    val post : MutableLiveData<Post> = MutableLiveData()
    constructor() {
        post.value = Post()
    }
}