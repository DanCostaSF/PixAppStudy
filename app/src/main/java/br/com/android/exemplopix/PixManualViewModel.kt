package br.com.android.exemplopix

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PixManualViewModel : ViewModel() {

    val onTextView = MutableLiveData(true)
    val onTextView2 = MutableLiveData(false)
    val onNavigateBack = MutableLiveData(false)
    val buttonEnabled = MutableLiveData(false)

    fun onNavigationClick() {
        onNavigateBack.value = true
    }

    fun buttonIsEnabled(bt : Boolean) {
        if (bt) buttonEnabled.postValue(true) else buttonEnabled.postValue(false)
    }

    fun onTextViewVisibility() {
        if (onTextView.value == false) {
            onTextView.postValue(true)
        } else {
            onTextView.postValue(false)
        }
        if (onTextView2.value == false) {
            onTextView2.postValue(true)
        } else {
            onTextView2.postValue(false)
        }
    }
}