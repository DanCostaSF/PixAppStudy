package br.com.android.exemplopix

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class InfoViewModel : ViewModel() {

    val textViewInfo = MutableLiveData("")

   val text = MutableLiveData(if (textViewInfo.value.isNullOrEmpty()) {
       ""
   } else {
       "Teste ${textViewInfo.value.toString()}"
   })

}

