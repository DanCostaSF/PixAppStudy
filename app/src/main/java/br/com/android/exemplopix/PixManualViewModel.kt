package br.com.android.exemplopix

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import br.com.android.exemplopix.commons.isCnpjValid
import br.com.android.exemplopix.commons.isCpfValid

class PixManualViewModel : ViewModel() {

    val onTextView = MutableLiveData(true)
    val onTextView2 = MutableLiveData(false)
    val onNavigateBack = MutableLiveData(false)
    val buttonEnabled = MutableLiveData(false)

    val editTextCpf = MutableLiveData("")

    val validCpf = editTextCpf.map {
        when {
            isCpfValid(it) -> true
            isCnpjValid(it) -> true
            else -> false
        }
    }

    fun onNavigationClick() {
        onNavigateBack.value = true
    }

    fun buttonOn() {
        buttonEnabled.postValue(true)
    }

    fun buttonOff() {
        buttonEnabled.postValue(false)
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