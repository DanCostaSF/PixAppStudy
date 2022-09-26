package br.com.android.exemplopix

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PixManualViewModel : ViewModel() {

    val onNavigateBack = MutableLiveData(false)

    fun onNavigationClick() {
        onNavigateBack.value = true
    }
}