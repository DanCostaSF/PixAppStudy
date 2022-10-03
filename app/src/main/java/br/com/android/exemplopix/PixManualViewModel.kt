package br.com.android.exemplopix

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import br.com.android.exemplopix.commons.isCnpjValid
import br.com.android.exemplopix.commons.isCpfValid

class PixManualViewModel : ViewModel() {

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
}