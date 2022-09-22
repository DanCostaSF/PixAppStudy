package br.com.android.exemplopix

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope

class InfoViewModel : ViewModel() {

    val onNavigateBack = MutableLiveData(false)

    fun onNavigationClick() {
        onNavigateBack.value = true
    }

    val textViewInfo = MutableLiveData("")

    val text = textViewInfo.map {
        if (it.isEmpty()) {
            ""
        } else {
            "Texto complementar - $it"
        }
    }
}

