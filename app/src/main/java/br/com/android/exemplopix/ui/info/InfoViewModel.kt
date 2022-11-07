package br.com.android.exemplopix.ui.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

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

