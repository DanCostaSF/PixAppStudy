package br.com.android.exemplopix

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InfoViewModel : ViewModel() {

    val textViewInfo = MutableLiveData("")

    fun textViewSetup(text: String) {
        if(text.isEmpty()) {
            textViewInfo.postValue("")
        }else {
            textViewInfo.postValue("Texto complementar - $text")
        }
    }
}